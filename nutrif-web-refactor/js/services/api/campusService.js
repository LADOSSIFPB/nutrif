angular.module("NutrifApp").factory("campusService", function($http, config){

	var _path = config.baseUrl() + "/campus";

	var _listarCampi = function(){

		return $http.get(_path + "/listar");
	}

	return {
		listarCampi: _listarCampi
	};
});
