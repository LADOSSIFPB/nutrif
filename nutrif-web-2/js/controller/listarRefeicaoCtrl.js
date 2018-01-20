/*
 *  Controlar ações da listagem da Refeição.
 */
nutrIFApp.controller('listarRefeicaoCtrl', function ($scope, $mdToast, refeicaoService) {

    $scope.tipo = "";
    $scope.refeicoes = [];

    $scope.limparBusca = function () {
        $scope.tipo = "";
        $scope.refeicoes = [];
    };

    $scope.pesquisar = function (tipo){
        if(tipo.length >= 3) {
            if (tipo.match(/[a-zA-Z]/i) != null) {
            	
                refeicaoService.buscarRefeicaoPorTipo(tipo)
                    .then(function (response) {
                        $scope.refeicoes = response.data;
                    })
                    .catch(function (error) {
                        toastUtil.showErrorToast(error);
                    });
            }
        } else if (tipo.length === 0) {
            $scope.refeicoes = [];
        }
    };

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
});
