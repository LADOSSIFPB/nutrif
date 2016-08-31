/**
* Cadastro do Edital.
*/
angular.module('NutrifApp').controller('cadastrarEditalCtrl', function ($scope, $mdToast, $state, $window,
	editalService, userService, funcionarioService, campusService, eventoService) {

		$scope.campi = [];
		$scope.eventos = [];

		// Responsáveis
		this.selectedItem = null;
		this.searchText = null;
		this.autocompleteDemoRequireMatch = true;
		$scope.responsaveis = [];

		this.cadastrar = function (edital) {

			// Adicionar funcionário.
			edital.funcionario = {};
			edital.funcionario.id = userService.getUser().id;

			// Responsável
			console.log(this.selectedItem);
			edital.responsavel = this.selectedItem;

			console.log(edital);

			// Enviar para o serviço de cadastro de Edital.
			editalService.cadastrarEdital(edital)
			.success(onSuccessCallback)
			.error(onErrorCallback);
		}

		function onSuccessCallback (data, status) {

			$mdToast.show(
				$mdToast.simple()
				.textContent('Edital cadastrado com sucesso!')
				.position('top right')
				.action('OK')
				.hideDelay(6000)
			);

			$state.transitionTo('home.listar-edital');
		}

		function onErrorCallback (data, status) {
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

			$state.transitionTo('home.listar-edital');
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

		// Carregar os Campi para seleção no Edital
		function carregarCampi () {
			campusService.listarCampi()
			.success(function (data, status){
				$scope.campi = data;
			})
			.error(function (data, status){
				alert("Houve um problema ao carregar os Campus.");
			});
		}

		// Carregar os Eventos para definir o tipo do Edital.
		function carregarEventos () {
			eventoService.listarEvento()
			.success(function (data, status){
				$scope.eventos = data;
			})
			.error(function (data, status){
				alert("Houve um problema ao carregar os Eventos.");
			});
		}

		carregarEventos();
		carregarCampi();
});
