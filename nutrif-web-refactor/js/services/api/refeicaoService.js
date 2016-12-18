angular.module("NutrifApp").factory("refeicaoService", function($http, config){

    var _path = config.baseUrl() + "/refeicao";

	var _listarRefeicoes = function(){
		return $http.get(_path + "/listar");
	};

	var _cadastrarRefeicao = function (refeicao){
		return $http.post(_path + "/inserir", refeicao)
	};
	
	var _buscarRefeicaoPorTipo = function (tipo){
		return $http.get(_path + "/listar/tipo/" +tipo)
	};
	
	var _getById = function(id){
		return $http.get(_path + "/id/"+ id);
	};	
	var _editarRefeicao = function(refeicao){
		return $http.post(_path + "/atualizar", refeicao)
	};

	return {
		listarRefeicoes: _listarRefeicoes,
		getById: _getById,
		cadastrarRefeicao: _cadastrarRefeicao,
		buscarRefeicaoPorTipo: _buscarRefeicaoPorTipo,
		editarRefeicao: _editarRefeicao
	};

});
