/*
 *  Controlar ações da listagem do Aluno.
 */
nutrIFApp.controller('listarAlunosCtrl', function ($scope, toastUtil,
    alunoService) {

    var TAM_MINIMO_MATRICULA = 11;
    var TAM_MAXIMO_MATRICULA = 13;

    $scope.nomeOuMatricula = "";
    $scope.alunos = [];

    $scope.pesquisar = function () {
        
        let nomeOuMatricula = $scope.nomeOuMatricula;
        
        if (nomeOuMatricula.length >= 3) {
            if (nomeOuMatricula.match(/[a-zA-Z]/i) != null) {
                alunoService.buscarPorNome(nomeOuMatricula)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);                
            } else if ((parseInt(nomeOuMatricula.substring(0, 4)) <= 2015
                        && nomeOuMatricula.length == TAM_MINIMO_MATRICULA)
                       ||((parseInt(nomeOuMatricula.substring(0, 4)) >= 2016 && nomeOuMatricula.length <= TAM_MAXIMO_MATRICULA))) {
                alunoService.buscarPorMatricula(nomeOuMatricula)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback); 
            }
        } else if (nomeOuMatricula.length === 0) {
            $scope.alunos = [];
        }
    };

    function onSuccessCallback(response) {
        $scope.alunos = response.data;
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }
    
    $scope.limparBusca = function () {
        $scope.nomeOuMatricula = "";
        $scope.alunos = [];
    };

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
});