angular.module('NutrifApp').controller('atualizarDadosAlunoCtrl', function (cursoService, alunoService, turnoService, turmaService, campusService, periodoService, $scope, $mdToast,
    $state, $stateParams) {

    $scope.aluno = {};
    $scope.turnos = [];
    $scope.turmas = [];
    $scope.periodos = [];

    this.atualizar = function (aluno) {

        // Adicionar funcionário.
        if (aluno.senha == aluno.resenha) {

            delete aluno.resenha;

            alunoService.atualizarCadastro(aluno)
                .success(onSuccessCallback)
                .error(onErrorCallback);
            
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

    function onSuccessCallback(data, status) {

        $mdToast.show(
            $mdToast.simple()
            .textContent('Aluno(a) atualizado(a) com sucesso.')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        $state.go("login.pretensao");
    }

    function onErrorCallback(data, status) {

        var _message = '';

        if (!data) {
            _message = 'Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.'
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
            .success(function (data, status) {
                $scope.aluno = data;
            })
            .error(onErrorCallback);

        turnoService.listarTurnos()
            .success(function (data, status) {
                $scope.turnos = data;
            })
            .error(onErrorCallback);

        turmaService.listarTurma()
            .success(function (data, status) {
                $scope.turmas = data;
            })
            .error(onErrorCallback);

        periodoService.listarPeriodo()
            .success(function (data, status) {
                $scope.periodos = data;
            })
            .error(onErrorCallback);
    }

    carregamentoInicial();
});