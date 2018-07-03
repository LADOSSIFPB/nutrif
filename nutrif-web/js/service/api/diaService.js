/*
 *  Mapeamento dos serviço de Dia.
 */
nutrIFApp.factory("diaService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/dia";

	var _listar = function(){
		return $http.get(_path);
	};
    
    var _getById = function (id) {
        return $http.get(_path + "/" + encodeURI(id));
    }

	return {
		listar: _listar,
        getById: _getById
	};
});
