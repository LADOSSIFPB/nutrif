angular.module('NutrifApp').controller('listarCursoCtrl', function ($scope, $mdToast, cursoService) {

    $scope.texto = "";
    $scope.cursos = [];

    $scope.limparBusca = function () {
        $scope.texto = "";
        $scope.cursos = [];
    };


    $scope.carregarCursos = function() {
          cursoService.listarCursos()
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
    }

    $scope.pesquisar = function (texto){
        if(texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                cursoService.buscarCursoPorNome(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
            }
        } else if (texto.length === 0) {
            $scope.carregarCursos();
        }
    };


    function onSuccessCallback(data, status) {
        $scope.cursos = data;
    }

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Não foram encontrados cursos'
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

    $scope.carregarCursos();
});
