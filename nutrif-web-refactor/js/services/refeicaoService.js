angular.module("NutrifApp").factory("refeicaoService", function($http, config){

    var _path = config.baseUrl() + "/refeicao";

	var _listarRefeicoes = function(){
		return $http.get(_path + "/listar");
	};

	return {
		listarRefeicoes: _listarRefeicoes
	};

});
