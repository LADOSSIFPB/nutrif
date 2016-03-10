angular.module("NutrifApp").factory("alunoService", function($http, config){

    var _path = config.baseUrl() + "/aluno";

	var _listarRefeicoes = function(refeicao){
		return $http.get(_path + "/listar", refeicao);
	};

	return {
		listarRefeicoes: _listarRefeicoes
	};

});
