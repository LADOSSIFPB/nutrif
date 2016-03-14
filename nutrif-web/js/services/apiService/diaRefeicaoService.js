angular.module("NutrifApp").factory("diaRefeicaoService", function($http, config){

    var _path = config.baseUrl() + "/diarefeicao";

	var _buscaRefeicaoPorNome = function (nome){
		return $http.get(_path + "/buscar/aluno/nome/" + nome)
	};

	var _buscaRefeicaoPorMatricula = function (matricula){
		return $http.get(_path + "/buscar/aluno/matricula/" + matricula)
	};

	var _cadastrarRefeicao = function (refeicao) {
		return $http.post(_path + "/inserir", refeicao)
	};

	var _listaRefeicaoPorMatricula = function (matricula){
		return $http.get(_path + "/listar/aluno/matricula/" + matricula)
	};

	return {
		buscaRefeicaoPorNome: _buscaRefeicaoPorNome,
		buscaRefeicaoPorMatricula: _buscaRefeicaoPorMatricula,
		cadastrarRefeicao: _cadastrarRefeicao,
		listaRefeicaoPorMatricula: _listaRefeicaoPorMatricula
	};

});
