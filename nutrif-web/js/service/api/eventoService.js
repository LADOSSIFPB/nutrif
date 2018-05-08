/*
 *  Mapeamento dos serviço de Evento.
 */
nutrIFApp.factory("eventoService", function ($http, serviceCfg) {

    var _path = serviceCfg.baseUrl() + "/evento";

    var _listar = function () {
        return $http.get(_path);
    }

    var _cadastrar = function (evento) {
        return $http.post(_path, evento);
    }

    var _buscarPorNome = function (nome) {
        return $http.get(_path + "/nome/" + encodeURI(nome));
    }

    var _getById = function (id) {
        return $http.get(_path + "/" + encodeURI(id));
    }

    var _atualizar = function (evento) {
        return $http.put(_path, evento);
    }

    return {
        listar: _listar,
        cadastrar: _cadastrar,
        buscarPorNome: _buscarPorNome,
        getById: _getById,
        atualizar: _atualizar        
    };
});