angular.module("NutrifApp").factory("editalService", function($http, config){

	var _path = config.baseUrl() + "/edital";

	var _cadastrarEdital = function (edital){
		return $http.post(_path + "/inserir", edital)
	};

	return {
		cadastrarEdital: _cadastrarEdital
	};
});
