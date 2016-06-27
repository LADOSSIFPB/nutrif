angular.module("NutrifApp").factory("cursoService", function($http, config){

    var _path = config.baseUrl() + "/curso";

	var _listarCursos = function(curso){
		return $http.get(_path + "/listar");
	};

	return {
		listarCursos: _listarCursos
	};

});
