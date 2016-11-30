angular.module('NutrifApp').controller('editarEditalCtrl', function ($scope, 
		editalService, campusService, eventoService, userService,
		funcionarioService, $stateParams, $state, $mdToast, $mdDialog) {

  $scope.nomes = [];
  $scope.campi = [];
  $scope.eventos = [];
  $scope.validade = [];
  $scope.editalCopy = {};
  $scope.edital = {};
  $scope.responsavel = {};
  $scope.selectedItem = null;
  $scope.legal={};

  // Responsáveis
  this.searchText = null;
  this.autocompleteDemoRequireMatch = true;
  $scope.responsaveis = [];

  this.editar = function (edital) {
  
	  
	  
	  console.log($scope.responsavel);
	  // Funcionário.
   edital.funcionario = {};
	edital.funcionario.id = userService.getUser().id;

	
	edital.responsavel = $scope.selectedItem;
	
	console.log($scope.responsavel);
	
	editalService.atualizarEdital(edital)
	.success(function (data, status) {

		$state.transitionTo('home.listar-edital', {reload: true});
		$mdToast.show(
			$mdToast.simple()
			.textContent('Edital atualizado com sucesso!')
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
            $state.transitionTo('home.listar-edital', {reload: true});
        }

        editalService.getEditalById(_id)
            .success(function (data, status) {
                $scope.edital = data;
                $scope.editalCopy = angular.copy($scope.edital);
                var inicialData = $scope.editalCopy.dataInicial;
                var finalData =$scope.editalCopy.dataFinal;
				$scope.editalCopy.dataInicial = new Date(inicialData);
				$scope.editalCopy.dataFinal = new Date(finalData);

			
				$scope.selectedItem = $scope.editalCopy.responsavel;
				
            })
            .error(onErrorLoadCallback);


        campusService.listarCampi()
            .success(function (data, status){
                $scope.campi = data;
            })
            .error(onErrorLoadCallback);

		eventoService.listarEvento()
            .success(function (data, status){
                $scope.eventos = data;
            })
            .error(onErrorLoadCallback);
    }

  
	carregamentoInicial();
	



	function onErrorLoadCallback(data, status) {
    onErrorCallback(data, status);
    $state.transitionTo('home.listar-edital', {reload: true});
  }

  function onErrorCallback (data, status){
    var _message = '';
    if (!data) {
      _message = 'Erro no servidor, por favor chamar administração ou suporte.'
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

	// Selecionar responsável pelo Edital.
	this.buscarResponsaveis = function buscarResponsaveis(query) {
		var lowerCaseQuery = angular.lowercase(query);

		console.log(lowerCaseQuery);

		var results = this.listarFuncionario(lowerCaseQuery);

		return results || [];
	}

	// Consultar responsável no serviço.
	this.listarFuncionario = function listarFuncionario(query) {

		funcionarioService.getFuncionarioByNome(query)
			.success(onSuccessListarFuncionario)
				.error(onErrorCallback);

		return $scope.responsaveis;

	}

	function onSuccessListarFuncionario(data, status) {
		return $scope.responsaveis = data;
	}

	function transformChip(responsavel) {
    // If it is an object, it's already a known chip
		if (angular.isObject(responsavel)) {
			console.log("Responsá" + responsavel);
			return responsavel;
		}
    }
		

	
});
