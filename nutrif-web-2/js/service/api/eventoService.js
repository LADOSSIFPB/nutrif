/*
 *  Mapeamento dos serviço de Evento.
 */
nutrifApp.factory("eventoService", function ($http, config) {

    var _path = config.baseUrl() + "/evento";

    var _listarEvento = function () {
        return $http.get(_path + "/listar");
    }

    var _cadastrarEvento = function (evento) {
        return $http.post(_path + "/inserir", evento);
    }

    var _buscarEventoPorNome = function (nome) {
        return $http.get(_path + "/listar/nome/" + nome);
    }

    var _getEventoById = function (id) {
        return $http.get(_path + "/id/" + id);
    }

    var _atualizarEvento = function (evento) {
        return $http.post(_path + "/atualizar", evento);
    }

    return {
        listarEvento: _listarEvento,
        cadastrarEvento: _cadastrarEvento,
        buscarEventoPorNome: _buscarEventoPorNome,
        getEventoById: _getEventoById,
        atualizarEvento: _atualizarEvento
    };
});