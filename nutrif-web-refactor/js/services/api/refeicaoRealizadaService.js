angular.module("NutrifApp").factory("refeicaoRealizadaService", function($http, config){

    var _path = config.baseUrl() + "/refeicaorealizada";

	var _inserirRefeicao = function(refeicao){

		return $http.post(_path + "/inserir", refeicao)
	};

    var _mapaRefeicao = function(peirodoPretensao){
		return $http.post(_path + "/mapa/consultar", peirodoPretensao);
	};

	var _getQuantidadeRefeicoesRealizadas = function(diaRefeicao){

		return $http.post(_path+ "/quantificar" , diaRefeicao);

	}

	return {
		inserirRefeicao: _inserirRefeicao,
        mapaRefeicao: _mapaRefeicao,
		getQuantidadeRefeicoesRealizadas: _getQuantidadeRefeicoesRealizadas
	};

});
