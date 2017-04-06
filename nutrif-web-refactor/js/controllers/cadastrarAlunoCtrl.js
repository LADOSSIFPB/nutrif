angular.module('NutrifApp').controller('cadastrarAlunoCtrl', function ($scope, $mdToast, $state, cursoService, alunoService, campusService, periodoService, turnoService, turmaService) {

    $scope.cursos = [];
    $scope.campi = [];
    $scope.periodos = [];
    $scope.turmas = [];
    $scope.turnos = [];

    this.cadastrar = function (aluno) {
        alunoService.cadastrarAluno(aluno)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    }

    function onSuccessCallback(data, status) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Aluno(a) cadastrado(a) com sucesso! Agora você pode adicionar refeições para ele(a).')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        $state.transitionTo('home.editar-aluno', {
            matricula: data.matricula
        }, {
            reload: true
        });
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

        // Carregar Cursos para seleção no cadastro do Aluno.
        cursoService.listarCursos()
            .success(function (data, status) {
                $scope.cursos = data;
            })
            .error(onErrorCallback);

        // Carregar os Campi para seleção no cadastro do Aluno.
        campusService.listarCampi()
            .success(function (data, status) {
                $scope.campi = data;
            })
            .error(onErrorCallback);

        // Carregar os Ano/Período para seleção no cadastro do Aluno.
        periodoService.listarPeriodo()
            .success(function (data, status) {
                $scope.periodos = data;
            })
            .error(onErrorCallback);
        
        // Carregar os Turma para seleção no cadastro do Aluno.
        turmaService.listarTurma()
            .success(function (data, status) {
                $scope.turmas = data;
            })
            .error(onErrorCallback);

        // Carregar os Turnos para seleção no cadastro do Aluno.
        turnoService.listarTurnos()
            .success(function (data, status) {
                $scope.turnos = data;
            })
            .error(onErrorCallback);        
    }

    // Inicializar listagem de cursos e campi.
    carregamentoInicial();
});