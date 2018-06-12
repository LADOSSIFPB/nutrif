/*
 *  Mapeamento dos serviço de Refeição Realizada.
 */
nutrIFApp.factory("refeicaoRealizadaService", function($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/refeicaorealizada";

	var _cadastrar = function(refeicaoRealizada){
		return $http.post(_path, refeicaoRealizada)
	};

    var _buscarQuantidade = function(diaRefeicao){
		return $http.post(_path + "/quantificar" , diaRefeicao);
	};
    
    var _listByExtratoRefeicao = function(idExtratoRefeicao) {
        return $http.get(_path + "/extratorefeicao/" + encodeURI(idExtratoRefeicao));
    };

	return {
		cadastrar: _cadastrar,
        buscarQuantidade: _buscarQuantidade,
        listByExtratoRefeicao: _listByExtratoRefeicao
	};
});
