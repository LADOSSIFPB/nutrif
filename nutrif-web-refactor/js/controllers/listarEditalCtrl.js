angular.module('NutrifApp').controller('listarEditalCtrl', function ($scope, $mdToast, editalService) {

    $scope.texto = "";
    $scope.editais = [];

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.editais = [];
    };

    $scope.pesquisar = function (texto){
        if(texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                alunoService.buscaAlunoPorNome(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
            }
        } else if (texto.length === 0) {
            $scope.editais = [];
        }
    };

    function onSuccessCallback(data, status) {
        $scope.editais = data;
    }

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Não foram encontrados alunos com esses dados'
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
