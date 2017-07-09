/*
 *  Controlar atualização do Aluno.
 */
nutrifApp.controller('atualizarAlunoCtrl', function (cursoService, alunoService, turnoService, turmaService, campusService, periodoService, $scope, $mdToast, $state, $stateParams) {

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
                
                    $mdToast.show(
                        $mdToast.simple()
                        .textContent('Aluno(a) atualizado(a) com sucesso.')
                        .position('top right')
                        .action('OK')
                        .hideDelay(20000)
                    );

                    $state.transitionTo("login.aluno", {
                        matricula: $scope.aluno.matricula
                    });
                })
                .catch(onErrorCallback)
            
        } else {

            var _message = "A senha de confirmação não está correta.";

            $mdToast.show(
                $mdToast.simple()
                .textContent(_message)
                .position('top right')
                .action('OK')
                .hideDelay(6000)
            );
        }
    }

    function onErrorCallback(data) {

        var _message = "";

        if (!data) {
            _message = "Ocorreu um erro na comunicação com o servidor, favor chamar o suporte."
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