/*
 *  Mapeamento dos serviço de Funcionário.
 */
nutrifApp.factory("funcionarioService", function($http, config){

	var _path = config.baseUrl() + "/funcionario";

	var _cadastrarFuncionario = function (funcionario){
		return $http.post(_path + "/inserir", funcionario);
	}

	var _getAll = function(){
		return $http.get(_path + "/listar");
	}

	var _getFuncionarioById = function (id){
		return $http.get(_path + "/id/" + encodeURI(id));
	}

	var _getFuncionarioByNome = function (nome){
		return $http.get(_path+ "/listar/nome/" + nome);
	}

	var _getRoles = function(){
		return $http.get(config.baseUrl() + "/role/listar");
	}

	var _atualizarFuncionario = function(funcionario){
		return $http.post(_path + "/atualizar" ,funcionario);
	}

	return {
		cadastrarFuncionario: _cadastrarFuncionario,
		getAll: _getAll,
		getFuncionarioById: _getFuncionarioById,
		getFuncionarioByNome : _getFuncionarioByNome ,
		getRoles: _getRoles,
		atualizarFuncionario:  _atualizarFuncionario
	};
});
