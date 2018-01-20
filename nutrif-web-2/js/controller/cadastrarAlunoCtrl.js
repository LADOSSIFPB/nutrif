/*
 *  Controlar inserção do Aluno.
 */
nutrIFApp.controller('cadastrarAlunoCtrl', function ($scope, $mdToast, $state, cursoService, alunoService, campusService, periodoService, turnoService, turmaService) {

    $scope.cursos = [];
    $scope.campi = [];
    $scope.periodos = [];
    $scope.turmas = [];
    $scope.turnos = [];

    this.cadastrar = function (aluno) {
        alunoService.cadastrarAluno(aluno)
            .then(function(response) {
                // Mensagem
                $mdToast.show(
                    $mdToast.simple()
                    .textContent('Aluno(a) cadastrado(a) com sucesso! Agora você pode adicionar refeições para ele(a).')
                    .position('top right')
                    .action('OK')
                    .hideDelay(6000)
                );

                // Redirecionamento
                $state.transitionTo('home.editar-aluno', {
                    matricula: response.data.matricula
                }, {
                    reload: true
                });
            })
            .error(onErrorCallback(data));
    }

    function onErrorCallback(data) {
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
            .then(function(response) {
                $scope.cursos = response.data;
            })
            .catch(onErrorCallback(data));

        // Carregar os Campi para seleção no cadastro do Aluno.
        campusService.listarCampi()
            .then(function(response) {
                $scope.campi = response.data;
            })
            .catch(onErrorCallback(data));

        // Carregar os Ano/Período para seleção no cadastro do Aluno.
        periodoService.listarPeriodo()
            .then(function(response) {
                $scope.periodos = response.data;
            })
            .catch(onErrorCallback(data));
        
        // Carregar os Turma para seleção no cadastro do Aluno.
        turmaService.listarTurma()
            .then(function(response) {
                $scope.turmas = response.data;
            })
            .catch(onErrorCallback(data));

        // Carregar os Turnos para seleção no cadastro do Aluno.
        turnoService.listarTurnos()
            .then(function(response) {
                $scope.turnos = response.data;
            })
            .catch(onErrorCallback(data));
    }

    // Inicializar listagem de cursos e campi.
    carregamentoInicial();
});