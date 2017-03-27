angular.module("NutrifApp").factory("alunoService", function($http, config){

	var _path = config.baseUrl() + "/aluno";

	var _buscaAlunoPorNome = function (nome){
		return $http.get(_path + "/listar/nome/" + encodeURI(nome))
	};
	
	var _fazerLogin = function (aluno){
		return $http.post(_path + "/login", aluno)
	};

	var _cadastrarAluno = function (aluno){
		return $http.post(_path + "/inserir", aluno)
	};

	var _atualizarAluno = function (aluno){
		return $http.post(_path + "/atualizar", aluno)
	};
	
	var _atualizarCadastro = function (aluno){
		return $http.post(_path + "/inserir/acesso ", aluno)
	};
	

	var _buscaAlunoPorMatricula = function (matricula){
		return $http.get(_path + "/matricula/" + encodeURI(matricula))
	};
	
	var _verificarAcesso = function (matricula){
		return $http.get(_path + "/verificar/acesso/matricula/" + encodeURI(matricula))
	};
	
	return {
		buscaAlunoPorNome: _buscaAlunoPorNome,
		cadastrarAluno: _cadastrarAluno,
		atualizarAluno: _atualizarAluno,
		atualizarCadastro: _atualizarCadastro,
		buscaAlunoPorMatricula: _buscaAlunoPorMatricula,
		fazerLogin: _fazerLogin,
		verificarAcesso: _verificarAcesso
	};

});
