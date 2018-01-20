/*
 *  Mapeamento dos serviço de Período.
 */
nutrIFApp.factory("periodoService", function($http, config){

    var _path = config.baseUrl() + "/periodo";

	var _listarPeriodo = function(){
		return $http.get(_path + "/listar");
	};

	return {
		listarPeriodo: _listarPeriodo
	};
});
