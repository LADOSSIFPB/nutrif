angular.module("NutrifApp").factory("refeicaoService", function($http, config){

    var _path = config.baseUrl() + "/refeicao";

	var _listarRefeicoes = function(refeicao){
		return $http.get(_path + "/listar", refeicao);
	};

	return {
		listarRefeicoes: _listarRefeicoes
	};

});
