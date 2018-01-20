/*
 *  Controlar cadastro do Funcionário.
 */
nutrifApp.controller('cadastrarFuncionarioCtrl', function ($scope, $mdToast, $state, toastUtil, funcionarioService, campusService) {

    $scope.roles = [];
    $scope.campi = [];

    this.cadastrar = function (funcionario) {

        var _roles = $scope.roles;
        var _length = _roles.length;

        var rolesFunc = [];
        rolesFunc.push(_roles[(funcionario.roles.role.id) - 1]);

        funcionario.roles = rolesFunc;

        funcionarioService.cadastrarFuncionario(funcionario)     
            .then(function(response) {
                // Mensagem de sucesso.
                var message = 'Funcionario(a) cadastrado(a) com sucesso.';
                toastUtil.showSuccessToast(message);
                
                $state.transitionTo('home.listar-funcionarios');
            })
            .catch(onErrorCallback);
    }

    function onErrorCallback(error) {
        // Toast.
        return toastUtil.showErrorToast(error);
    }

    function carregamentoInicial() {
        
        // Carregamento das Roles para Funcionário.
        funcionarioService.getRoles()
            .then(function(response) {
                $scope.roles = response.data;
            })
            .catch(onErrorCallback);
        
        // Carregar os Campi.
        campusService.listarCampi()
            .then(function(response) {
                $scope.campi = response.data;
            })
            .catch(onErrorCallback);
    }

    // Inicializar listagem de roles e campi.
    carregamentoInicial();
});