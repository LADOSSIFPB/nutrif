angular.module("NutrifApp").factory("pretensaoService", function ($http, config, userService) {

    var _path = config.baseUrl() + "/pretensaorefeicao";

    var _verifyDiaRefeicao = function (pretensaoRefeicao) {
        return $http.post(_path + "/diarefeicao/verificar", pretensaoRefeicao);
    };

    var _insertPretensao = function (pretensao) {
        delete pretensao.confirmaPretensaoDia.diaRefeicao.refeicao.horaFinal;
        delete pretensao.confirmaPretensaoDia.diaRefeicao.refeicao.horaInicio;
        delete pretensao.confirmaPretensaoDia.diaRefeicao.refeicao.horaPrevisaoPretensao;
        return $http.post(_path + "/inserir", pretensao);
    };

    var _verifyChaveAcesso = function (code) {
        //TODO: var pretensao = {funcionario: userService.getUser(), keyAccess: code};
        var pretensao = {
            keyAccess: code
        };
        return $http.post(_path + "/verificar/chaveacesso", pretensao);
    };

    var _getQuantidadePretensao = function (diaRefeicao) {

        return $http.post(_path + "/quantificar", diaRefeicao);
    }

    var _mapaRefeicao = function (periodoPretensao) {
        return $http.post(_path + "/consultar/mapa", periodoPretensao);
    };

    var _getPretensaoRefeicaoByDiaRefeicao = function (idDiaRefeicao) {

        return $http.get(_path + "/vigente/diarefeicao/id/" + idDiaRefeicao);
    }

    return {
        verifyDiaRefeicao: _verifyDiaRefeicao,
        verifyChaveAcesso: _verifyChaveAcesso,
        mapaRefeicao: _mapaRefeicao,
        insertPretensao: _insertPretensao,
        getQuantidadePretensao: _getQuantidadePretensao,
        pretensaoRefeicaoByDiaRefeicao: _getPretensaoRefeicaoByDiaRefeicao
    };
});