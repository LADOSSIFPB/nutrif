angular.module('NutrifApp').controller('editarRefeicaoCtrl', function ($scope,
	$stateParams, $state, $mdToast, refeicaoService) {

	$scope.refeicao = {};

	$scope.atualizar = function (refeicao) {

		refeicao.horaInicio = Date.parse(refeicao.horaInicio);
		refeicao.horaFinal = Date.parse(refeicao.horaFinal);
		refeicao.horaPrevisaoPretensao = Date.parse(refeicao.horaPrevisaoPretensao);

		refeicaoService.editarRefeicao(refeicao)
			.success(function (data, status) {
				$state.transitionTo('home.listar-refeicoes', {reload: true});
				$mdToast.show(
					$mdToast.simple()
					.textContent('Refeição atualizada com sucesso!')
					.position('top right')
					.action('OK')
					.hideDelay(6000)
				);
			})
			.error(onErrorCallback);
	}

	function carregamentoInicial() {
		var _id = $stateParams.id;

		if (_id == 0){
			$state.transitionTo('home.listar-refeicoes', {reload: true});
		}

		  refeicaoService.getById(_id)
	      .success(function(data,status){
	    	  $scope.refeicao = data;
	      })
	      .error(onErrorLoadCallback);

	}

	carregamentoInicial();

	function onErrorCallback(data, status) {
		var _message = '';

		if (!data) {
			_message = 'Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.'
		} else {
			_message = data.mensagem
		}

		$mdToast.show(
			$mdToast.simple()
			.textContent(_message)
			.position('top right')
			.action('OK')
			.hideDelay(6000)
		);
	}

	function onErrorLoadCallback(data, status) {
		onErrorCallback(data, status);
		$state.transitionTo('home.listar-refeicoes', {reload: true});
	}
});
