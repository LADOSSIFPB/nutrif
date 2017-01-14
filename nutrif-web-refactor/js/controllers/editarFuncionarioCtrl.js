angular.module('NutrifApp').controller('editarFuncionarioCtrl', function ($scope,
	$stateParams, $state, $mdToast, funcionarioService, campusService) {

	$scope.funcionarioCopy = {};
	$scope.funcionario = {};
	
	$scope.atualizar = function (funcionario) {

		var _roles = $scope.roles;
		var _campi = $scope.campi;

		var rolesFunc = [];
		rolesFunc.push(_roles[(funcionario.roles[0].id)-1]);

		funcionario.roles = rolesFunc;

		funcionarioService.atualizarFuncionario(funcionario)
			.success(function (data, status) {
				$scope.funcionario = data;
				$scope.funcionarioCopy = angular.copy($scope.funcionario);
				$state.transitionTo('home.listar-funcionarios', {reload: true});
				$mdToast.show(
					$mdToast.simple()
					.textContent('Funcionario atualizado com sucesso!')
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
			$state.transitionTo('home.listar-funcionarios', {reload: true});
		}

		funcionarioService.getFuncionarioById(_id)
			.success(function (data, status) {
				$scope.funcionario = data;
				$scope.funcionarioCopy = angular.copy($scope.funcionario);
			})
			.error(onErrorLoadCallback);

		funcionarioService.getRoles()
			.success(function (data, status){
				$scope.roles = data;
				console.log($scope.funcionarioCopy);
				if($scope.funcionarioCopy.tipo == 1)
					$scope.funcionarioCopy.roles.push($scope.roles[0])
				else if($scope.funcionarioCopy.tipo == 2)
					$scope.funcionarioCopy.roles.push($scope.roles[1])
				else if($scope.funcionarioCopy.tipo == 3)
					$scope.funcionarioCopy.roles.push($scope.roles[2])
			})
			.error(onErrorLoadCallback);

		campusService.listarCampi()
			.success(function (data, status){
				$scope.campi = data;
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
		$state.transitionTo('home.listar-alunos', {reload: true});
	}

	carregamentoInicial();
});
