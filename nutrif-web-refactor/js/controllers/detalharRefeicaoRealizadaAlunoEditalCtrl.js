nutrifApp.controller('detalharRefeicaoRealizadaAlunoEditalCtrl', function ($scope,
	$stateParams, $state, $mdToast, refeicaoRealizadaService) {
    
    $scope.idEdital = $stateParams.idEdital;
    
    $scope.matricula = $stateParams.matricula;
    
    $scope.mapas;
    
    function carregamentoInicial() {
        
		if ($scope.idEdital == 0 && $scope.matricula.length === 0){
			$state.transitionTo('home.listar-edital', {reload: true});
		}

		refeicaoRealizadaService.detalharRefeicaoRealizadaByEditalAluno($scope.idEdital, $scope.matricula)
			.success(function (data, status) {
				$scope.mapas = data;
			})
			.error(onErrorLoadCallback);
	}
    
    // Inicializar os dados.
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
		$state.transitionTo('home.listar-edital', {reload: true});
	}
});
