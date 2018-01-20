/*
 *  Controlar ações da listagem do Edital.
 */
nutrifApp.controller('listarEditalCtrl', function ($scope, toastUtil, editalService) {

    $scope.texto = "";
    $scope.editais = [];

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.editais = [];
    };

    $scope.pesquisar = function (texto){
        if(texto.length >= 3) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                editalService.buscarEditalPorNome(texto)
                    .then(function (response) {
                        $scope.editais = response.data;
                    })
                    .catch(function (error) {
                        toastUtil.showErrorToast(error);
                    });
            }
        } else if (texto.length === 0) {
            $scope.editais = [];
        }
    };

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
});
