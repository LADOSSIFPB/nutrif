/*
 *  Mapeamento dos serviço de Refeição Realizada.
 */
nutrIFApp.factory("refeicaoRealizadaService", function($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/refeicaorealizada";

	var _cadastrar = function(refeicaoRealizada){
		return $http.post(_path + "/inserir", refeicaoRealizada)
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
		cadastrar: _cadastrar,
        mapaRefeicao: _mapaRefeicao,
		getQuantidadeRefeicoesRealizadas: _getQuantidadeRefeicoesRealizadas,
        getMapaRefeicaoRealizadaByDiaRefeicao: _getMapaRefeicaoRealizadaByDiaRefeicao,
        detalharRefeicaoRealizadaByEditalAluno: _detalharRefeicaoRealizadaByEditalAluno
	};
});
