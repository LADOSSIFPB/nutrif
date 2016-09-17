angular.module("NutrifApp").factory("cursoService", function($http, config){

    var _path = config.baseUrl() + "/curso";

	var _listarCursos = function(curso){
		return $http.get(_path + "/listar");
	};

  var _cadastrarCurso = function (curso){
		return $http.post(_path + "/inserir", curso);
	}

	return {
		listarCursos: _listarCursos,
    cadastrarCurso: _cadastrarCurso
	};

});
