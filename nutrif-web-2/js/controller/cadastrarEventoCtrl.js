angular.module('NutrifApp').controller('cadastrarEventoCtrl', function ($scope, $mdToast, $state,eventoService) {

		this.cadastrar = function (evento) {

			// Enviar para o serviço de cadastro de Edital.
			eventoService.cadastrarEvento(evento)
			.success(onSuccessCallback)
			.error(onErrorCallback);
		}

		function onSuccessCallback (data, status) {

			$mdToast.show(
				$mdToast.simple()
				.textContent('Evento cadastrado com sucesso!')
				.position('top right')
				.action('OK')
				.hideDelay(6000)
			);

			$state.transitionTo('home.listar-eventos');
		}

		function onErrorCallback (data, status) {
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

			$state.transitionTo('home.listar-eventos');
		}


});
