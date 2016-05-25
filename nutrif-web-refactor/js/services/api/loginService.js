angular.module("NutrifApp").factory("loginService", function($http, config){

	var _path = config.baseUrl() + "/pessoa";

	var _fazerLogin = function (pessoa){
		return $http.post(_path + "/login", pessoa);
	};

	return {
		fazerLogin: _fazerLogin
	};

});
