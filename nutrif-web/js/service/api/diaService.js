/*
 *  Mapeamento dos serviço de Dia.
 */
nutrIFApp.factory("diaService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/dia";

	var _listar = function(){
		return $http.get(_path);
	};

	return {
		listar: _listar
	};
});
