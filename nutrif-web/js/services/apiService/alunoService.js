angular.module("NutrifApp").factory("alunoService", function($http, config){

	var _path = config.baseUrl() + "/aluno";

	var _buscaAlunoPorNome = function (nome){
		return $http.get(_path + "/nome/" + encodeURI(nome))
	};

	var _cadastrarAluno = function (aluno){
		return $http.post(_path + "/inserir", aluno)
	};

	var _buscaAlunoPorMatricula = function (matricula){
		return $http.get(_path + "/matricula/" + encodeURI(matricula))
	};

	return {
		buscaAlunoPorNome: _buscaAlunoPorNome,
		cadastrarAluno: _cadastrarAluno,
		buscaAlunoPorMatricula: _buscaAlunoPorMatricula
	};

});
