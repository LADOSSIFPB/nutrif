angular.module('NutrifApp').controller('dashboardCtrl', function (
		$scope, $mdToast, diaService, refeicaoService,$timeout,pretensaoService,refeicaoRealizadaService,$state,$interval) {

  
    $scope.dias = [];
	$scope.refeicoes = [];
	$scope.dataHoje = new Date();
	$scope.diaRefeicao={};
	$scope.mapaPretensao={};
	$scope.mapaRefeicaoRealizada={};

   
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
	
	$scope.getQuantidadePretensao = function(){
		
		pretensaoService.getQuantidadePretensao($scope.diaRefeicao).
		    success(function (data, status){
				$scope.mapaPretensao = data;
				console.log($scope.mapaPretensao);
			})
            .error(onErrorCallback);
		
	}
	
	$scope.getQuantidadeRefeicoesRealizadas = function(){
		
		refeicaoRealizadaService.getQuantidadeRefeicoesRealizadas($scope.diaRefeicao).
		    success(function (data, status){
				$scope.mapaRefeicaoRealizada = data;
				console.log($scope.mapaRefeicaoRealizada);
			})
            .error(onErrorCallback);
		
	}

    $scope.carregarDia();
	$scope.carregarRefeicao();	
	
	//Enquanto o usuário estiver na página
        $interval(function() {
				var currentCtrl = $state.current.controller;
				if (currentCtrl === "dashboardCtrl") {
					   $scope.getQuantidadePretensao();
					   $scope.getQuantidadeRefeicoesRealizadas();
				}
            }, 6000);
});
