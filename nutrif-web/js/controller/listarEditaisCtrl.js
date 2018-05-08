/*
 *  Controlar ações da listagem do Edital.
 */
nutrIFApp.controller('listarEditaisCtrl', function ($scope, toastUtil, editalService) {

    $scope.nome = "";
    $scope.editais = [];

    $scope.pesquisar = function () {
        
        let nome = $scope.nome;
        
        if (nome.length >= 3) {
            if (nome.match(/[a-zA-Z]/i) != null) {
                editalService.buscarPorNome(nome)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);
            }
        } else if (nome.length === 0) {
            $scope.editais = [];
        }
    };
    
    $scope.limparBusca = function () {
        $scope.nome = "";
        $scope.editais = [];
    };
    
    function onSuccessCallback(response) {
        $scope.editais = response.data;
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
});