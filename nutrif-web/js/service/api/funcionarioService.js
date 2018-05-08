/*
 *  Mapeamento dos serviço de Funcionário.
 */
nutrIFApp.factory("funcionarioService", function($http, serviceCfg) {

    var _path = serviceCfg.baseUrl() + "/funcionario";

	var _cadastrar = function (funcionario){
		return $http.post(_path, funcionario);
	}

	var _getAll = function(){
		return $http.get(_path);
	}

	var _getById = function (id){
		return $http.get(_path + "/" + encodeURI(id));
	}

	var _listByNome = function (nome){
		return $http.get(_path + "/nome/" + encodeURI(nome));
	}

	var _getRoles = function(){
		return $http.get(_path + "/role/");
	}

	var _atualizar = function(funcionario){
		return $http.put(_path, funcionario);
	}

	return {
		cadastrar: _cadastrar,
		getAll: _getAll,
		getById: _getById,
		listByNome : _listByNome,
		getRoles: _getRoles,
		atualizar:  _atualizar
	};
});
