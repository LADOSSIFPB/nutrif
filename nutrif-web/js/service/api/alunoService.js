/*
 *  Mapeamento dos serviço de Aluno.
 */
nutrIFApp.factory("alunoService", function($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/aluno";

    var _cadastrar = function (aluno){
		return $http.post(_path, aluno)
	};

	var _atualizar = function (aluno){
		return $http.put(_path, aluno)
	};
    
    var _buscarPorId = function (id){
		return $http.get(_path + "/" + encodeURI(id))
	};
    
	var _buscarPorNome = function (nome){
		return $http.get(_path + "/nome/" + encodeURI(nome))
	};
    
    var _buscarPorMatricula = function (matricula){
		return $http.get(_path + "/matricula/" + encodeURI(matricula))
	};
	
	var _fazerLogin = function (aluno){
		return $http.post(_path + "/login", aluno)
	};
    
    var _atualizarAcesso = function (aluno){
		return $http.put(_path + "/acesso", aluno)
	};
	
	var _cadastrarAcesso = function (aluno){
		return $http.post(_path + "/acesso", aluno)
	};
    
    var _verificarAcesso = function (matricula){
		return $http.get(_path + "/acesso/matricula/" + encodeURI(matricula))
	};
    
	return {		
		cadastrar: _cadastrar,
		atualizar: _atualizar,
        buscarPorId: _buscarPorId,
        buscarPorNome: _buscarPorNome,
        buscarPorMatricula: _buscarPorMatricula,
        atualizarAcesso: _atualizarAcesso,
		cadastrarAcesso: _cadastrarAcesso,		
		fazerLogin: _fazerLogin,
		verificarAcesso: _verificarAcesso
	};
});
