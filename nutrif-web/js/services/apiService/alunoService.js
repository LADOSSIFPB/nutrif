angular.module("NutrifApp").factory("alunoService", function($http, config){

	var _path = config.baseUrl() + "/aluno";

	var _buscaAlunoPorNome = function (nome){
		return $http.get(_path + "/nome/" + nome)
	};

	var _buscaAlunoPorMatricula = function (matricula){
		return $http.get(_path + "/matricula/" + matricula)
	};

	return {
		buscaAlunoPorNome: _buscaAlunoPorNome,
		buscaAlunoPorMatricula: _buscaAlunoPorMatricula	
	};

});
