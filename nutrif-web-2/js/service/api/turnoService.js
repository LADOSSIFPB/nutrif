/*
 *  Mapeamento dos serviço de Turno.
 */
nutrifApp.factory("turnoService", function($http, config){

    var _path = config.baseUrl() + "/turno";

	var _listarTurnos = function(){
		return $http.get(_path + "/listar");
	};
	
	return {
		listarTurnos: _listarTurnos
	};
});
