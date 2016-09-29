angular.module('NutrifApp').controller('listarEventoCtrl', function ($scope, $mdToast, eventoService) {

    $scope.texto = "";
    $scope.eventos = [];

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.eventos = [];
    };


    $scope.pesquisar = function (texto){
        if(texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                eventoService.buscarEventoPorNome(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
            }
        } else if (texto.length === 0) {
            $scope.carregarEventos();
        }
    };

    $scope.carregarEventos = function() {
          eventoService.listarEvento()
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
    }


    function onSuccessCallback(data, status) {
        $scope.eventos = data;
    }

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Não foram encontrados eventos'
        } else {
            _message = data.mensagem
        }

        $mdToast.show(
            $mdToast.simple()
            .textContent(_message)
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
    }

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };

    $scope.carregarEventos();
});
