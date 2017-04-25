angular.module('NutrifApp').controller('listarContempladosEditalCtrl', function ($scope,
	$stateParams, $state, $mdToast, diaRefeicaoService) {

	var _idEdital = $stateParams.id;

	$scope.diasRefeicao = [];
	
	function carregamentoInicial() {
        
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

	$scope.limparBusca = function () {
        $scope.texto = "";
       	carregamentoInicial() 
    };

    $scope.pesquisar = function (texto) {
        if (texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                 diaRefeicaoService.getAllByEditalAndNomeAluno(_idEdital, texto)
                    .success(function (data, status) {
						$scope.diasRefeicao = data;
						console.log($scope.diasRefeicao)
					})
                    .error(onErrorCallback);
            }
        } else if (texto.length === 0) {
           carregamentoInicial() 
        }
    };

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
