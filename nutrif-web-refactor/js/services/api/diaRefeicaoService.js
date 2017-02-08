angular.module("NutrifApp").factory("diaRefeicaoService", function($http, config){

    var _path = config.baseUrl() + "/diarefeicao";

	var _buscaRefeicaoPorNome = function (nome){
		return $http.get(_path + "/buscar/aluno/nome/" + encodeURI(nome))
	};

	var _buscaRefeicaoPorMatricula = function (matricula){
		return $http.get(_path + "/buscar/aluno/matricula/" + encodeURI(matricula))
	};

	var _cadastrarRefeicao = function (refeicao) {
        delete refeicao.refeicao.horaFinal;
        delete refeicao.refeicao.horaInicio;
        delete refeicao.refeicao.horaPretensao;
		return $http.post(_path + "/inserir", refeicao)
	};

	var _removerRefeicao = function (refeicao) {
        delete refeicao.refeicao.horaFinal;
        delete refeicao.refeicao.horaInicio;
        delete refeicao.refeicao.horaPretensao;
		return $http.post(_path + "/remover", refeicao)
	};

	var _listarHistoricoRefeicaoPorMatricula = function (matricula){
		return $http.get(_path + "/listar/aluno/matricula/" + encodeURI(matricula))
	};
	
	 var _getQuantidadeRefeicoes = function(diaRefeicao){
		
		return $http.post(_path+ "/quantificar" , diaRefeicao);
		
	};
	
	var _getAllVigentesByAlunoMatricula =  function(matricula){
	 
	 return $http.get(_path+ "/vigentes/listar/aluno/matricula/"+ matricula);
	 
	}

	return {
		buscaRefeicaoPorNome: _buscaRefeicaoPorNome,
		buscaRefeicaoPorMatricula: _buscaRefeicaoPorMatricula,
		cadastrarRefeicao: _cadastrarRefeicao,
		removerRefeicao: _removerRefeicao,
		listarHistoricoRefeicaoPorMatricula: _listarHistoricoRefeicaoPorMatricula,
		getQuantidadeRefeicoes: _getQuantidadeRefeicoes,
		getAllVigentesByAlunoMatricula: _getAllVigentesByAlunoMatricula
	};

});
