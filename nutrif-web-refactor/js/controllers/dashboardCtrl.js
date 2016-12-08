angular.module('NutrifApp').controller('dashboardCtrl', function (
		$scope, $mdToast, diaService, refeicaoService) {

  
    $scope.dias = [];
	$scope.refeicoes = [];
	$scope.dataHoje = new Date();

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.cursos = [];
    };

    $scope.carregarDia = function() {
          diaService.listarDias()
            .success(function (data, status){
				$scope.dias = data;
				console.log(data);
			})
			.error(onErrorCallback);       
    }
    
    $scope.carregarRefeicao = function() {
        refeicaoService.listarRefeicoes()
            .success(function (data, status){
				$scope.refeicoes = data;
				console.log(data);
			})
            .error(onErrorCallback);
    }

   
    function onSuccessCallback(data, status) {
        $scope.cursos = data;
    }

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Não foram encontrados cursos'
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

    $scope.carregarDia();
	$scope.carregarRefeicao();
});
