angular.module('NutrifApp').controller('editarEventoCtrl', function ($scope,
	$stateParams, $state, $mdToast, eventoService) {

	$scope.atualizar = function (evento) {

		eventoService.atualizarEvento(evento)
			.success(function (data, status) {

				$state.transitionTo('home.listar-eventos', {reload: true});
				$mdToast.show(
					$mdToast.simple()
					.textContent('Evento atualizado com sucesso!')
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
			$state.transitionTo('home.listar-eventos', {reload: true});
		}

		eventoService.getEventoById(_id)
			.success(function (data, status) {
				$scope.evento = data;
			})
			.error(onErrorLoadCallback);
	}

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
		$state.transitionTo('home.listar-eventos', {reload: true});
	}

	carregamentoInicial();
});
