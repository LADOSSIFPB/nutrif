angular.module("NutrifApp").factory("refeicaoRealizadaService", function($http, config){

    var _path = config.baseUrl() + "/refeicaorealizada";

	var _inserirRefeicao = function(refeicao){

        delete refeicao.confirmaRefeicaoDia.diaRefeicao.refeicao.horaInicio;
		delete refeicao.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal;
		delete refeicao.confirmaRefeicaoDia.diaRefeicao.refeicao.horaPretensao;

		return $http.post(_path + "/inserir", refeicao)
	};

	return {
		inserirRefeicao: _inserirRefeicao
	};

});
