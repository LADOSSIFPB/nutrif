angular.module('NutrifApp').controller('listarAlunosCtrl', function ($scope, $mdToast,
    alunoService) {

    var TAM_MINIMO_MATRICULA = 11;
    var TAM_MAXIMO_MATRICULA = 13;

    $scope.texto = "";
    $scope.alunos = [];

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.alunos = [];
    };

    $scope.pesquisar = function (texto) {
        if (texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                alunoService.buscaAlunoPorNome(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
            } else if ((parseInt(texto.substring(0, 4)) <= 2015 && texto.length == TAM_MINIMO_MATRICULA) ||
                ((parseInt(texto.substring(0, 4)) >= 2016 && texto.length <= TAM_MAXIMO_MATRICULA))) {
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