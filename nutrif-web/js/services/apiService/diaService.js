angular.module("NutrifApp").factory("diaService", function($http, config){

    var _path = config.baseUrl() + "/dia";

	var _listarDias = function(refeicao){
		return $http.get(_path + "/listar", refeicao);
	};

	return {
		listarDias : _listarDias
	};

});
