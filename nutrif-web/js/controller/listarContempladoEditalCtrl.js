nutrIFApp.controller('listarContempladosEditalCtrl', function ($scope,
	$stateParams, $state, $mdToast, diaRefeicaoService) {

	$scope.idEdital = $stateParams.id;

	$scope.alunos = [];
	
	function carregamentoInicial() {
        
		if ($scope.idEdital == 0){
			$state.transitionTo('home.listar-edital', {reload: true});
		}

		diaRefeicaoService.getAllByEdital ($scope.idEdital)
			.success(function (data, status) {
				$scope.alunos = data;
			})
			.error(onErrorLoadCallback);
	}

	$scope.limparBusca = function () {
        $scope.texto = "";
       	carregamentoInicial() 
    };

    $scope.pesquisar = function (texto) {
        if (texto.length > 3) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                 diaRefeicaoService.getAllByEditalAndNomeAluno($scope.idEdital, texto)
                    .success(function (data, status) {
						$scope.alunos = data;
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

	carregamentoInicial();
});
