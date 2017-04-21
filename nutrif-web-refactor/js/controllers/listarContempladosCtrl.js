angular.module('NutrifApp').controller('listarContempladosCtrl', function ($scope,
	$stateParams, $state, $mdToast, diaRefeicaoService) {

	$scope.diasRefeicao = [];
	
	function carregamentoInicial() {
		var _idEdital = $stateParams.id;

		if (_idEdital == 0){
			$state.transitionTo('home.listar-edital', {reload: true});
		}

		diaRefeicaoService.getAllByEdital (_idEdital)
			.success(function (data, status) {
				$scope.diasRefeicao = data;
				console.log($scope.diasRefeicao)
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
		$state.transitionTo('home.listar-edital', {reload: true});
	}

	 $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };

	carregamentoInicial();
});
