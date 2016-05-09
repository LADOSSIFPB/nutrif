angular.module("NutrifApp").factory("pretensaoService", function($http, config){

    var _path = config.baseUrl() + "/pretensaorefeicao";

	var _verifyDiaRefeicao = function(pretensao){
		return $http.post(_path + "/diarefeicao/verificar", pretensao);
	};

	return {
		verifyDiaRefeicao : _verifyDiaRefeicao
	};

});
