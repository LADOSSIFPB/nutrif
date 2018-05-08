/*
 *  Controlar ações da listagem dos Campi.
 */
nutrIFApp.controller('listarCampiCtrl', function ($scope, $state, toastUtil, campusService) {

    $scope.cidade = "";
    $scope.campi = [];

    $scope.limparBusca = function () {
        $scope.cidade = "";
        $scope.campi = [];
    };

    $scope.pesquisar = function (){
        
        let cidade = $scope.cidade;
        
        if(cidade.length >= 3) {
            if (cidade.match(/[a-zA-Z]/i) != null) {
            	
                campusService.buscarPorCidade(cidade)
                    .then(function (response) {
                        $scope.campi = response.data;
                    })
                    .catch(function (error) {
                        toastUtil.showErrorToast(error);
                    });
            }
        } else if (cidade.length === 0) {
            $scope.campi = [];
        }
    };

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
});
