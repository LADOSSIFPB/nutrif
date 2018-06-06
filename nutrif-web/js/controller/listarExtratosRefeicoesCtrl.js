/*
 *  Controlar ações da listagem dos Extratos.
 */
nutrIFApp.controller('listarExtratosRefeicoesCtrl', function ($scope, $state, toastUtil, extratoRefeicaoService) {

    $scope.extratos = [];
    
    $scope.dataInicial;
    $scope.dataFinal;

    $scope.pesquisar = function () {
        
        let dataInicial = Date.parse($scope.dataInicial);
        let dataFinal = Date.parse($scope.dataFinal);        
        
        extratoRefeicaoService.buscarByPeriodo(dataInicial, dataFinal)
            .then(function (response) {
                $scope.extratos = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
        
    };

    $scope.query = {
        order: 'nome',
        limit: 60,
        page: 1
    };
});