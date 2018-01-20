/*
 *  Controlar atualização do Aluno.
 */
nutrIFApp.controller('atualizarAlunoCtrl', function (toastUtil, cursoService, alunoService, turnoService, turmaService, campusService, periodoService, $scope, $mdToast, $state, $stateParams) {

    $scope.aluno = {};
    $scope.turnos = [];
    $scope.turmas = [];
    $scope.periodos = [];
    
    $scope.atualizarBasicoAluno = function (aluno) {

        // Adicionar Acesso do Aluno.
        if (aluno.senha == aluno.resenha) {

            delete aluno.resenha;

            alunoService.atualizarCadastro(aluno)
                .then(function(response) {
                    
                    $scope.aluno = response.data;
                    
                    var message = "Aluno(a) atualizado(a) com sucesso.";
                    toastUtil.showSuccessToast(message);

                    $state.transitionTo("login.aluno", {
                        matricula: $scope.aluno.matricula
                    });
                })
                .catch(onErrorCallback);
            
        } else {

            var message = "A senha de confirmação não está correta.";

            toastUtil.showSuccessToast(message);
        }
    }

    function onErrorCallback(error) {

        return toastUtil.showErrorToast(error);
    }

    function carregamentoInicial() {

        var _matricula = $stateParams.matricula;

        alunoService.buscaAlunoPorMatricula(_matricula)
            .then(function(response) {
                $scope.aluno = response.data;
            })
            .catch(onErrorCallback);

        turnoService.listarTurnos()
            .then(function(response) {
                $scope.turnos = response.data;
            })
            .catch(onErrorCallback);

        turmaService.listarTurma()
            .then(function(response) {
                $scope.turmas = response.data;
            })
            .catch(onErrorCallback);

        periodoService.listarPeriodo()
            .then(function(response) {
                $scope.periodos = response.data;
            })
            .catch(onErrorCallback);
    }

    carregamentoInicial();
});