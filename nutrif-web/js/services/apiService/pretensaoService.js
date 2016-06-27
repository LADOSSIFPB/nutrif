angular.module("NutrifApp").factory("pretensaoService", function($http, config){

    var _path = config.baseUrl() + "/pretensaorefeicao";

	var _verifyDiaRefeicao = function(pretensao){
		return $http.post(_path + "/diarefeicao/verificar", pretensao);
	};

	var _insertPretensao = function(pretensao){
		return $http.post(_path + "/inserir", pretensao);
	};

	return {
		verifyDiaRefeicao : _verifyDiaRefeicao,
		insertPretensao: _insertPretensao
	};

});
