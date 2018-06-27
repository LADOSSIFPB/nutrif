/*
 *  Controlar inserção do Funcionário.
 */
nutrIFApp.controller('cadastrarFuncionarioCtrl', function ($scope, $state, alunoService, campusService, toastUtil) {

    $scope.funcionario = {};

    $scope.adicionar = function () {
        
        alunoService.cadastrar($scope.funcionario)
            .then(function (response) {
                // Adicionar
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
    
    function carregamentoInicial() {

        // Carregar Roles para seleção no cadastro do Funcionário.
    }

    // Inicializar listagem de campi.
    carregamentoInicial();
});