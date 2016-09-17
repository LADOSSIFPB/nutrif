angular.module('NutrifApp').controller('cadastrarCursoCtrl', function ($scope, $mdToast, $state,cursoService) {

		this.cadastrar = function (curso) {

			// Enviar para o serviço de cadastro de curso.
			cursoService.cadastrarCurso(curso)
			.success(onSuccessCallback)
			.error(onErrorCallback);
		}

		function onSuccessCallback (data, status) {

			$mdToast.show(
				$mdToast.simple()
				.textContent('Curso cadastrado com sucesso!')
				.position('top right')
				.action('OK')
				.hideDelay(6000)
			);

			$state.transitionTo('home.listar-cursos');
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

			$state.transitionTo('home.listar-cursos');
		}


});
