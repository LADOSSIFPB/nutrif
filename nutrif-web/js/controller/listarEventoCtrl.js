/*
 *  Controlar ações da listagem do Evento.
 */
nutrIFApp.controller('listarEventoCtrl', function ($scope, toastUtil, eventoService) {

    $scope.nome = "";
    $scope.eventos = [];

    $scope.pesquisar = function (nome){
        if(nome.length >= 3) {
            if (nome.match(/[a-zA-Z]/i) != null) {
                eventoService.buscarEventoPorNome(nome)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);
            }
        } else if (nome.length === 0) {
            $scope.eventos = [];
        }
    };

    $scope.listarEventos = function() {
          eventoService.listarEvento()
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);
    }
    
    $scope.limparBusca = function () {
        $scope.nome = "";
        $scope.eventos = [];
    };

    function onSuccessCallback(response) {
        $scope.eventos = response.data;
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };

    $scope.listarEventos();
});
