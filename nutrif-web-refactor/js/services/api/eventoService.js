angular.module("NutrifApp").factory("eventoService", function($http, config){

	var _path = config.baseUrl() + "/evento";

	var _listarEvento = function(){

		return $http.get(_path + "/listar");
	}

	return {
		listarEvento: _listarEvento
	};
});
