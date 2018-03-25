/*
 *  Mapeamento dos serviço de Turno.
 */
nutrIFApp.factory("turnoService", function($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/turno";

	var _listar = function(){
		return $http.get(_path);
	};
	
	return {
		listar: _listar
	};
});
