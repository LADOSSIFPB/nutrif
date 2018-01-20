/*
 *  Mapeamento dos serviço de Aluno.
 */
nutrIFApp.factory("alunoService", function($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/aluno";

	var _buscaAlunoPorNome = function (nome){
		return $http.get(_path + "/listar/nome/" + encodeURI(nome))
	};
	
	var _fazerLogin = function (aluno){
		return $http.post(_path + "/login", aluno)
	};

	var _cadastrarAluno = function (aluno){
		return $http.post(_path + "/inserir", aluno)
	};

	var _atualizarBasico = function (aluno){
		return $http.post(_path + "/atualizar/basico", aluno)
	};
    
    var _atualizarAcesso = function (aluno){
		return $http.post(_path + "/atualizar/acesso", aluno)
	};
	
	var _atualizarCadastro = function (aluno){
		return $http.post(_path + "/inserir/acesso", aluno)
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
		atualizarBasico: _atualizarBasico,
        atualizarAcesso: _atualizarAcesso,
		atualizarCadastro: _atualizarCadastro,
		buscaAlunoPorMatricula: _buscaAlunoPorMatricula,
		fazerLogin: _fazerLogin,
		verificarAcesso: _verificarAcesso
	};
});
