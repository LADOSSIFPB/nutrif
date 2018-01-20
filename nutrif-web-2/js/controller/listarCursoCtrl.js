/*
 *  Controlar ações da listagem do Curso.
 */
nutrIFApp.controller('listarCursoCtrl', function ($scope, toastUtil, cursoService) {

    $scope.nome = "";
    $scope.cursos = [];

    $scope.listarCursos = function () {
        cursoService.listarCursos()
            .then(onSuccessCallback)
            .catch(onErrorCallback);
    }

    $scope.pesquisar = function (nome) {
        if (nome.length >= 3) {
            if (nome.match(/[a-zA-Z]/i) != null) {
                cursoService.buscarCursoPorNome(nome)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);
            }
        } else if (nome.length === 0) {
            $scope.cursos = [];
        }
    };
    
    $scope.limparBusca = function () {
        $scope.nome = "";
        $scope.cursos = [];
    };
    
    function onSuccessCallback(response) {
        $scope.cursos = response.data;
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };

    $scope.listarCursos();
});