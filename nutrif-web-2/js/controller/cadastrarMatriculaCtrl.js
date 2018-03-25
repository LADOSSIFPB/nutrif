/*
 *  Controlar inserção do Aluno.
 */
nutrIFApp.controller('cadastrarMatriculaCtrl', function ($scope, $stateParams, $state, alunoService, cursoService, turnoService, periodoService, turmaService, toastUtil, stringUtil) {

    $scope.aluno = {};

    $scope.cursos = [];
    $scope.turnos = [];
    $scope.periodos = [];
    $scope.turmas = [];

    $scope.campi = [];

    $scope.adicionar = function () {

        var cpf = CPF.strip($scope.aluno.cpf);
        $scope.aluno.cpf = cpf;

        alunoService.cadastrar($scope.aluno)
            .then(function (response) {
                $state.transitionTo('administrador.editar-aluno', {
                    id: response.data.id
                }, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
                // Mensagem
                toastUtil.showSuccessToast('Aluno(a) cadastrado(a) com sucesso. Inclua o Aluno(a) em um curso e informe sua matrícula.');
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    function carregamentoInicial() {

        // Aluno
        var _id = $stateParams.id;

        if (stringUtil.isEmpty(_id)) {            
            $state.transitionTo('administrador.listar-alunos', {
                reload: true
            });        
        } else {
            
            alunoService.buscarPorId(_id)
                .then(function (response) {
                    // Aluno 
                    $scope.aluno = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });

            // Curso
            cursoService.listar()
                .then(function (response) {
                    $scope.cursos = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
            
            // Turno
            turnoService.listar()
                .then(function (response) {
                    $scope.turnos = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
            
            // Periodo
            periodoService.listar()
                .then(function (response) {
                    $scope.periodos = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
            
            // Turma
            turmaService.listar()
                .then(function (response) {
                    $scope.turmas = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
        }


    }

    // Inicializar listagem de cursos e campi.
    carregamentoInicial();
});