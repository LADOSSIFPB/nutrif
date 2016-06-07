angular.module("NutrifApp").factory("pretensaoService", function($http, config){

    var _path = config.baseUrl() + "/pretensaorefeicao";

	var _verifyDiaRefeicao = function(pretensao){
		return $http.post(_path + "/diarefeicao/verificar", pretensao);
	};

	var _insertPretensao = function(pretensao){
		return $http.post(_path + "/inserir", pretensao);
	};

	var _verifyChaveAcesso = function(code){
		var pretensao = {keyAccess: code};
		return $http.post(_path + "/chaveacesso/verificar", pretensao);
	};

	return {
		verifyDiaRefeicao : _verifyDiaRefeicao,
		verifyChaveAcesso: _verifyChaveAcesso,
		insertPretensao: _insertPretensao
	};

});
