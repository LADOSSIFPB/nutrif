angular.module("NutrifApp").factory("campusService", function($http, config){

	var _path = config.baseUrl() + "/campus";

	var _listarCampus = function(){
		
		return $http.get(_path + "/listar");		
	}
	
	return {
		listarCampus: _listarCampus
	};
});
