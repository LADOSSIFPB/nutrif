angular.module('NutrifApp').controller('listarAlunosCtrl', function ($scope, $mdToast, alunoService) {

    $scope.texto = "";
    $scope.alunos = [];

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.alunos = [];
    };

    $scope.pesquisar = function (texto){
        if(texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                alunoService.buscaAlunoPorNome(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
            } else if (texto.length === 11) {
                alunoService.buscaAlunoPorMatricula(texto)
                    .success(function (data, status) {
                        $scope.alunos = [];
                        $scope.alunos.push(data);
                    })
                    .error(onErrorCallback)
            }
        } else if (texto.length === 0) {
            $scope.alunos = [];
        }
    };

    function onSuccessCallback(data, status) {
        $scope.alunos = data;
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
