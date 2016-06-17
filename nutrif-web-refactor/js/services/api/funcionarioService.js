angular.module("NutrifApp").factory("funcionarioService", function($http, config){

	var _path = config.baseUrl() + "/funcionario";

	var _cadastrarFuncionario = function (funcionario){
		return $http.post(_path + "/inserir", funcionario)
	};
	
	var _getAll = function(){
		return $http.get(_path + "/listar")
	};
	
	var _getFuncionarioById = function (id){
		return $http.get(_path + "/id/" + encodeURI(id))
	};

	return {
		cadastrarFuncionario: _cadastrarFuncionario,
		getAll: _getAll,
		getFuncionarioById: _getFuncionarioById
	};

});
