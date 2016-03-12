angular.module('NutrifApp').controller("pesquisaCtrl", function ($scope, diaRefeicaoService) {
    
	var TAM_MIN_BUSCA_MATRICULA = 11;
	var TAM_MIN_BUSCA_NOME = 3;

	$scope.refeicoes = [];

	$scope.buscarRefeicoes = function (refeicao) {

		var _matricula = refeicao.aluno.matricula;
		var _nome = refeicao.aluno.nome;

		if (_matricula != undefined && _matricula.length == TAM_MIN_BUSCA_MATRICULA) {

			diaRefeicaoService.buscaRefeicaoPorMatricula(_matricula).success(function (data, status) {

				$scope.refeicoes = data;

			}).error(function (data, status) {

				window.alert("Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.");

			});

		} else if (_nome != undefined && _nome.length >= TAM_MIN_BUSCA_NOME) {

			diaRefeicaoService.buscaRefeicaoPorNome(_nome).success(function (data, status) {

				$scope.refeicoes = data;

			}).error(function (data, status) {

				window.alert("Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.");

			});

		}

	}

});
