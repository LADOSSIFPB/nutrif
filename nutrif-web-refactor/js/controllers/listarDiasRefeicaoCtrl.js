angular.module('NutrifApp').controller('listarDiasRefeicaoCtrl', function ($scope,
	$stateParams, $state, $mdToast, refeicaoRealizadaService) {

	var _idDiaRefeicao = $stateParams.id;

	$scope.diasRefeicao = [];
	
	function carregamentoInicial() {
        
		if (_idDiaRefeicao == 0){
			$state.transitionTo('home.listar-edital', {reload: true});
		}

		refeicaoRealizadaService.getQuantidadeRefeicoesRealizadasByDiaRefeicao(_idDiaRefeicao)
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
        limit: 100,
        page: 1
    };

	carregamentoInicial();
});
