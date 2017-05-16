nutrifApp.factory("refeicaoRealizadaService", function($http, config){

    var _path = config.baseUrl() + "/refeicaorealizada";

	var _inserirRefeicao = function(refeicao){

		return $http.post(_path + "/inserir", refeicao)
	};

    var _mapaRefeicao = function(peirodoPretensao){
		return $http.post(_path + "/consultar/mapa", peirodoPretensao);
	};

	var _getQuantidadeRefeicoesRealizadas = function(diaRefeicao){

		return $http.post(_path + "/quantificar" , diaRefeicao);
	};
    
    var _detalharRefeicaoRealizadaByEditalAluno = function(idEdital, matricula){
        
		return $http.get(_path + "/detalhar/edital/" + idEdital + "/aluno/" + matricula);
	};

	return {
		inserirRefeicao: _inserirRefeicao,
        mapaRefeicao: _mapaRefeicao,
		getQuantidadeRefeicoesRealizadas: _getQuantidadeRefeicoesRealizadas,
        detalharRefeicaoRealizadaByEditalAluno: _detalharRefeicaoRealizadaByEditalAluno
	};
});
