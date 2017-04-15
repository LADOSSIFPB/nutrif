angular.module('NutrifApp').controller('listarRefeicaoCtrl', function ($scope, $mdToast, refeicaoService) {

    $scope.texto = "";
    $scope.refeicoes = [];

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.refeicoes = [];
    };

    $scope.pesquisar = function (texto){
        if(texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
            	
                refeicaoService.buscarRefeicaoPorTipo(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
            }
        } else if (texto.length === 0) {
            $scope.refeicoes = [];
        }
    };

    function onSuccessCallback(data, status) {
        $scope.refeicoes = data;
    }

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Não foram encontrados Refeições'
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
});
