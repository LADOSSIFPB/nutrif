/*
 *  Mapeamento dos serviço de Turma.
 */
nutrIFApp.factory("turmaService", function($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/turma";

	var _listar = function(){
		return $http.get(_path);
	};
	
	return {
		listar: _listar
	};
});
