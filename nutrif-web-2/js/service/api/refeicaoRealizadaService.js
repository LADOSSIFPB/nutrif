/*
 *  Mapeamento dos serviço de Refeição Realizada.
 */
nutrIFApp.factory("refeicaoRealizadaService", function($http, config){

    var _path = config.baseUrl() + "/refeicaorealizada";

	var _inserirRefeicao = function(refeicao){
		return $http.post(_path + "/inserir", refeicao)
	};

    var _mapaRefeicao = function(periodoPretensao){
		return $http.post(_path + "/consultar/mapa", periodoPretensao);
	};    

	var _getQuantidadeRefeicoesRealizadas = function(diaRefeicao){
		return $http.post(_path + "/quantificar" , diaRefeicao);
	};
    
    var _getMapaRefeicaoRealizadaByDiaRefeicao = function(idDia, idRefeicao){
		return $http.get(_path + "/consultar/mapa/dia/" + idDia + "/refeicao/" + idRefeicao);
	};
    
    var _detalharRefeicaoRealizadaByEditalAluno = function(idEdital, matricula){   
		return $http.get(_path + "/detalhar/edital/" + idEdital + "/aluno/" + matricula);
	};

	return {
		inserirRefeicao: _inserirRefeicao,
        mapaRefeicao: _mapaRefeicao,
		getQuantidadeRefeicoesRealizadas: _getQuantidadeRefeicoesRealizadas,
        getMapaRefeicaoRealizadaByDiaRefeicao: _getMapaRefeicaoRealizadaByDiaRefeicao,
        detalharRefeicaoRealizadaByEditalAluno: _detalharRefeicaoRealizadaByEditalAluno
	};
});
