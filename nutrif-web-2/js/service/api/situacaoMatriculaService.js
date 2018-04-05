/*
 *  Mapeamento dos serviço de SituacaoMatricula.
 */
nutrIFApp.factory("situacaoMatriculaService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/situacaomatricula";
    
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