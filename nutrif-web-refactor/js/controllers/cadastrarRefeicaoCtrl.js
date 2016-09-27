angular.module('NutrifApp').controller('cadastrarRefeicaoCtrl', function ($scope, $mdToast, $state,refeicaoService) {

	  // Enviar para o serviço de cadastro de refeição.

    this.cadastrar = function (refeicao) {
      alunoService.cadastrarRefeicao(refeicao)
      .success(onSuccessCallback)
      .error(onErrorCallback);
    }

    function onSuccessCallback (data, status) {
      $mdToast.show(
        $mdToast.simple()
        .textContent('Refeição cadastrada com sucesso!')
        .position('top right')
        .action('OK')
        .hideDelay(6000)
      );
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

			$state.transitionTo('home.listar-refeicoes');
		}

});
