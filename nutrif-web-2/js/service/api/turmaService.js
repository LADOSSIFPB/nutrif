/*
 *  Mapeamento dos serviço de Turma.
 */
nutrIFApp.factory("turmaService", function($http, config){

    var _path = config.baseUrl() + "/turma";

	var _listarTurma = function(){
		return $http.get(_path + "/listar");
	};
	
	return {
		listarTurma: _listarTurma
	};
});
