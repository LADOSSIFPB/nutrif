/*
 *  Controlar ações da listagem do Funcionário.
 */
nutrIFApp.controller('listarFuncionariosCtrl', function ($scope, toastUtil, funcionarioService) {

    $scope.nome = "";
    $scope.funcionarios = [];

    $scope.pesquisar = function (nome) {
        if (nome.length >= 3) {
            funcionarioService.getFuncionarioByNome(nome)
                .then(function (response) {
                    $scope.funcionarios = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
        } else if (nome.length === 0) {
            $scope.funcionarios = [];
        }
    }
    
    $scope.limparBusca = function () {
        $scope.nome = "";
        $scope.funcionarios = [];
    }

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    }
});