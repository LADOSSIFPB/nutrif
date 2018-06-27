/*
 *  Controlar inserção do Funcionário.
 */
nutrIFApp.controller('cadastrarFuncionarioCtrl', function ($scope, $state, funcionarioService, roleService, campusService, toastUtil) {

    $scope.funcionario = {};

    $scope.campi = [];
    $scope.roles = [];

    $scope.adicionar = function () {

        let funcionario = $scope.funcionario;
        
        // Roles (Perfis) do Funcionário.
        let role = funcionario.role;
		let roles = [];        
		roles.push(role);

        delete(funcionario.role);
		funcionario.roles = roles;

        funcionarioService.cadastrar(funcionario)
            .then(function (response) {

                // Mensagem
                toastUtil.showSuccessToast('Funcionário ou Servidor(a) cadastrado(a) com sucesso.');

                // Redirecionamento            
                $state.transitionTo('administrador.listar-funcionarios', {
                    reload: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    function carregamentoInicial() {

        // Carregar Roles para seleção no cadastro do Funcionário.
        roleService.listar()
            .then(function (response) {
                $scope.roles = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });

        // Carregar Cursos para seleção no cadastro do Aluno.
        campusService.listar()
            .then(function (response) {
                $scope.campi = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    // Inicializar listagem de campi.
    carregamentoInicial();
});