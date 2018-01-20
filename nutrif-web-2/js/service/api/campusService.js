/*
 *  Mapeamento dos serviço de Campus.
 */
nutrIFApp.factory("campusService", function ($http, config) {

    var _path = config.baseUrl() + "/campus";

    var _listarCampi = function () {
        return $http.get(_path + "/listar");
    }

    var _getById = function (id) {
        return $http.get(_path + "/id/" + id);
    }

    return {
        listarCampi: _listarCampi,
        getById: _getById
    };
});