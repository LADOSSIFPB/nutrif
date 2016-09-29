angular.module('NutrifApp').controller('editarCursoCtrl', function ($scope,
	$stateParams, $state, $mdToast, cursoService) {

	$scope.atualizar = function (curso) {

		cursoService.atualizarCurso(curso)
			.success(function (data, status) {

				$state.transitionTo('home.listar-cursos', {reload: true});
				$mdToast.show(
					$mdToast.simple()
					.textContent('Curso atualizado com sucesso!')
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
			$state.transitionTo('home.listar-cursos', {reload: true});
		}

		cursoService.getCursoById(_id)
			.success(function (data, status) {
				$scope.curso = data;
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
		$state.transitionTo('home.listar-cursos', {reload: true});
	}

	carregamentoInicial();
});
