/*
 *  Mapeamento dos serviço de Período.
 */
nutrIFApp.factory("periodoService", function($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/periodo";

	var _listar = function(){
		return $http.get(_path);
	};

	return {
		listar: _listar
	};
});
