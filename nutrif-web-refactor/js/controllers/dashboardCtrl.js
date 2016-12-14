angular.module('NutrifApp').controller('dashboardCtrl', function (
		$scope, $mdToast, diaService, refeicaoService,$timeout,pretensaoService,refeicaoRealizadaService,$state,$interval,diaRefeicaoService) {

  
    $scope.dias = [];
	$scope.refeicoes = [];
	$scope.dataHoje = new Date();
	$scope.diaRefeicao={};
	$scope.mapaPretensao={};
	$scope.mapaRefeicaoRealizada={};
	$scope.mapaDiaRefeicao={};

   
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
	
	//inicia a quantidade de mapas 
	$scope.initMapas = function(){
		$scope.mapaRefeicaoRealizada.quantidade = 0;
		$scope.mapaPretensao.quantidade = 0;
		$scope.mapaDiaRefeicao.quantidade = 0;
		
		$scope.mapaRefeicaoRealizada.data = new Date();
		$scope.mapaPretensao.data = new Date();
		$scope.mapaDiaRefeicao.data = new Date();
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
			})
            .error(onErrorCallback);
		
	}
	
	$scope.getQuantidadeRefeicoesRealizadas = function(){
		
		refeicaoRealizadaService.getQuantidadeRefeicoesRealizadas($scope.diaRefeicao).
		    success(function (data, status){
				$scope.mapaRefeicaoRealizada = data;
				console.log(data);
			})
            .error(onErrorCallback);
		
	}
	
	$scope.getQuantidadeRefeicoesDoDia = function(){
		
		diaRefeicaoService.getQuantidadeRefeicoes($scope.diaRefeicao).
		    success(function (data, status){
				$scope.mapaDiaRefeicao = data;
				console.log($scope.mapaDiaRefeicao);
			})
            .error(onErrorCallback);
		
	}
	$scope.consulta = function(){
		 $scope.getQuantidadePretensao();
		 $scope.getQuantidadeRefeicoesRealizadas();
		 $scope.getQuantidadeRefeicoesDoDia();
	}

    $scope.carregarDia();
	$scope.carregarRefeicao();	
	$scope.initMapas();
	
	$scope.consultaAutomatica = $interval(function() {
				var currentCtrl = $state.current.controller;
				if (currentCtrl === "dashboardCtrl") {
					   $scope.getQuantidadePretensao();
					   $scope.getQuantidadeRefeicoesRealizadas();
					   $scope.getQuantidadeRefeicoesDoDia();
				}
            }, 10000)
});
