angular.module('NutrifApp').controller("pesquisaCtrl", function ($scope, diaRefeicaoService, refeicaoRealizadaService) {

	var TAM_MIN_BUSCA_MATRICULA = 11;
	var TAM_MIN_BUSCA_NOME = 3;

	$scope.refeicoes = [];

	$scope.limparBusca = function () {
		$scope.refeicoes = [];
		delete $scope.refeicao;
	}

	$scope.buscarRefeicoes = function (refeicao) {

		var _matricula = refeicao.aluno.matricula;
		var _nome = refeicao.aluno.nome;

		if (_matricula != undefined && _matricula.length == TAM_MIN_BUSCA_MATRICULA) {

			diaRefeicaoService.buscaRefeicaoPorMatricula(_matricula).success(function (data, status) {

				$scope.refeicoes = data;

			}).error(function (data, status) {

                if (!data) {

					alert("Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.");

				} else {

					alert(data.mensagem);

				}

			});

		} else if (_nome != undefined && _nome.length >= TAM_MIN_BUSCA_NOME) {

			diaRefeicaoService.buscaRefeicaoPorNome(_nome).success(function (data, status) {

				$scope.refeicoes = data;

			}).error(function (data, status) {

				if (!data) {

					alert("Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.");

				} else {

					alert(data.mensagem);

				}

			});

		}

	}

	$scope.selecionarRefeicao = function (refeicao) {
		$scope.refeicaoSelecionada = refeicao;
		$('#confirmar-refeicao').openModal();
	}

	$scope.registrarRefeicao = function (refeicaoSelecionada) {

		var _refeicaoRealizada = {};
		_refeicaoRealizada.confirmaRefeicaoDia = {};
		_refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao = refeicaoSelecionada;

		delete _refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao.refeicao.horaInicio;
		delete _refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal;

		refeicaoRealizadaService.inserirRefeicao(_refeicaoRealizada).success(function (data, status) {

			$scope.refeicaoSelecionada = {};
			$scope.refeicoes = [];
			$scope.refeicao = {};

			Materialize.toast('Refeição realizada com sucesso', 6000);

		}).error(function (data, status){

			if (!data) {

				alert("Erro ao registrar refeição, tente novamente ou contate os administradores.");

			} else {

				alert(data.mensagem);

			}

		});

	}

});
