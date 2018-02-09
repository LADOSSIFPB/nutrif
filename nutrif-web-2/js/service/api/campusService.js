/*
 *  Mapeamento dos serviço de Campus.
 */
nutrIFApp.factory("campusService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/campus";

    var _listar = function () {
        return $http.get(_path);
    }

    var _getById = function (id) {
        return $http.get(_path + "/" + id);
    }

    return {
        listar: _listar,
        getById: _getById
    };
});