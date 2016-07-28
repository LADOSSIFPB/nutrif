angular.module("NutrifApp").factory("editalService", function($http, config){

	var _path = config.baseUrl() + "/edital";

	var _cadastrarEdital = function (edital){
		return $http.post(_path + "/inserir", edital)
	};

	var _listarEdital = function (){
		return $http.get(_path + "/listar/")
	};

	var _buscarEditalPorNome = function (nome){
		return $http.get(_path + "/listar/nome/" + encodeURI(nome))
	};

	return {
		cadastrarEdital: _cadastrarEdital,
		listarEdital: _listarEdital;
		buscarEditalPorNome: _buscarEditalPorNome
	};
});
