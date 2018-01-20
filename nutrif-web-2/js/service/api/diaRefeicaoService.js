/*
 *  Mapeamento dos serviço de DiaRefeicao.
 */
nutrifApp.factory("diaRefeicaoService", function ($http, config) {

    var _path = config.baseUrl() + "/diarefeicao";

    var _buscaRefeicaoPorNome = function (nome) {
        return $http.get(_path + "/buscar/aluno/nome/" + encodeURI(nome))
    };

    var _buscaRefeicaoPorMatricula = function (matricula) {
        return $http.get(_path + "/buscar/aluno/matricula/" + encodeURI(matricula))
    };

    var _cadastrarRefeicao = function (refeicao) {

        delete refeicao.refeicao.horaFinal;
        delete refeicao.refeicao.horaInicio;
        delete refeicao.refeicao.horaPrevisaoPretensao;

        return $http.post(_path + "/inserir", refeicao)
    };

    var _removerRefeicao = function (refeicao) {

        delete refeicao.refeicao.horaFinal;
        delete refeicao.refeicao.horaInicio;
        delete refeicao.refeicao.horaPrevisaoPretensao;

        return $http.post(_path + "/remover", refeicao)
    };

    var _listarHistoricoRefeicaoPorMatricula = function (matricula) {
        return $http.get(_path + "/listar/aluno/matricula/" + encodeURI(matricula))
    };

    var _getAllVigentesByAlunoMatricula = function (matricula) {
        return $http.get(_path + "/listar/vigentes/aluno/matricula/" + matricula);
    }

    var _getAllByEdital = function (idEdital) {
        return $http.get(_path + "/listar/edital/" + idEdital);
    }

    var _getAllByEditalAndNomeAluno = function (idEdital, nomeAluno) {
        return $http.get(_path + "/listar/edital/" + idEdital + "/aluno/nome/" + nomeAluno);
    }

    var _getQuantidadeRefeicoes = function (diaRefeicao) {
        return $http.post(_path + "/quantificar", diaRefeicao);
    };

    var _getQuantidadeBeneficiadosByEdital = function (idEdital) {
        return $http.get(_path + "/quantificar/edital/" + idEdital);
    };

    var _getAllByDiaAndRefeicao = function (idDia, idRefeicao) {
        return $http.get(_path + "/listar/dia/" + idDia + "/refeicao/" + idRefeicao);
    }

    var _migrarSabadoLetivo = function (diaOrigem, edital) {

        var SABADO = 7;

        return $http.post(_path + "/migrar/dia/origem/" + diaOrigem + "/destino/" + SABADO, edital);
    }

    return {
        buscaRefeicaoPorNome: _buscaRefeicaoPorNome,
        buscaRefeicaoPorMatricula: _buscaRefeicaoPorMatricula,
        cadastrarRefeicao: _cadastrarRefeicao,
        removerRefeicao: _removerRefeicao,
        listarHistoricoRefeicaoPorMatricula: _listarHistoricoRefeicaoPorMatricula,
        getAllByEdital: _getAllByEdital,
        getAllByEditalAndNomeAluno: _getAllByEditalAndNomeAluno,
        getAllByDiaAndRefeicao: _getAllByDiaAndRefeicao,
        getQuantidadeRefeicoes: _getQuantidadeRefeicoes,
        getQuantidadeBeneficiadosByEdital: _getQuantidadeBeneficiadosByEdital,
        getAllVigentesByAlunoMatricula: _getAllVigentesByAlunoMatricula,
        migrarSabadoLetivo: _migrarSabadoLetivo
    };
});