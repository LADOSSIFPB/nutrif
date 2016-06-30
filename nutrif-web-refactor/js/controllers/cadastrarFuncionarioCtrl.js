angular.module('NutrifApp').controller('cadastrarFuncionarioCtrl', function ($scope, $mdToast, funcionarioService, $state) {

	$scope.roles = [];

	

    
	this.cadastrar = function (funcionario) {
		
		var _roles = $scope.roles;
		var _length = _roles.length;
		
		var rolesFunc = [];
		rolesFunc.push(_roles[(funcionario.roles.role.id)-1]);
		
		funcionario.roles = rolesFunc;
		
		
        funcionarioService.cadastrarFuncionario(funcionario)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    }

    function onSuccessCallback (data, status) {
        $mdToast.show( 
            $mdToast.simple()
            .textContent('Funcionario(a) cadastrado(a) com sucesso!')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        $state.transitionTo('home.entrada-alunos');
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
    }
    
    function carregarRoles () {
        funcionarioService.getRoles()
            .success(function (data, status){
                $scope.roles = data;
            })
            .error(function (data, status){
                alert("Houve um erro ao carregar os cargos. Contate um administrador.");
            });
    }

    carregarRoles();

});
