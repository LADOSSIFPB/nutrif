/*
 *  Controlar ações da listagem do Aluno.
 */
nutrifApp.controller('listarAlunosCtrl', function ($scope, toastUtil,
    alunoService) {

    var TAM_MINIMO_MATRICULA = 11;
    var TAM_MAXIMO_MATRICULA = 13;

    $scope.nomeMatricula = "";
    $scope.alunos = [];

    $scope.pesquisar = function (nomeMatricula) {
        if (nomeMatricula.length >= 3) {
            if (nomeMatricula.match(/[a-zA-Z]/i) != null) {
                alunoService.buscaAlunoPorNome(nomeMatricula)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);                
            } else if ((parseInt(nomeMatricula.substring(0, 4)) <= 2015 && nomeMatricula.length == TAM_MINIMO_MATRICULA) ||
                ((parseInt(nomeMatricula.substring(0, 4)) >= 2016 && nomeMatricula.length <= TAM_MAXIMO_MATRICULA))) {
                alunoService.buscaAlunoPorMatricula(nomeMatricula)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback); 
            }
        } else if (nomeMatricula.length === 0) {
            $scope.alunos = [];
        }
    };

    function onSuccessCallback(response) {
        $scope.alunos = response.data;
    }

    function onErrorCallback(data, status) {
        toastUtil.showErrorToast(error);
    }
    
    $scope.limparBusca = function () {
        $scope.nomeMatricula = "";
        $scope.alunos = [];
    };

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
});