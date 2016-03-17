angular.module("NutrifApp").factory("loginService", function($http, config){

	var _path = config.baseUrl() + "/funcionario";

	var _fazerLogin = function (funcionario){
		return $http.post(_path + "/login", funcionario);
	};

	return {
		fazerLogin: _fazerLogin
	};

});
