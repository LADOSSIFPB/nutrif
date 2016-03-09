angular.module("NutrifApp").factory("cronogramaRefeicaoService", function($http, config){

    var _path = config.baseUrl() + "/cronogramaRefeicao";

	var _buscaRefeicao = function(refeicao){
		return $http.post(_path + "/buscar", refeicao)
	};

	return {
		buscaRefeicao: _buscaRefeicao
	};

});
