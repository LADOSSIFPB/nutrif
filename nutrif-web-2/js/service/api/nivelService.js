/*
 *  Mapeamento dos serviço de Nível.
 */
nutrIFApp.factory("nivelService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/nivel";
    
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