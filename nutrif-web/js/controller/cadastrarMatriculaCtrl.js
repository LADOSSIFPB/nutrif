/*
 *  Controlar inserção do Aluno.
 */
nutrIFApp.controller('cadastrarMatriculaCtrl', function ($scope, $stateParams, $state, matriculaService, alunoService, cursoService, turnoService, periodoService, turmaService, toastUtil, stringUtil) {

    $scope.matricula = {};

    $scope.cursos = [];
    $scope.turnos = [];
    $scope.periodos = [];
    $scope.turmas = [];

    $scope.selected = [];

    $scope.adicionar = function () {
        
        let aluno = {};
        aluno.id = $scope.matricula.aluno.id;
        
        let curso = {};
        curso.id = $scope.matricula.curso.id;
        
        let turno = {};
        turno.id = $scope.matricula.turno.id;
        
        let periodo = {};
        periodo.id = $scope.matricula.periodo.id;
        
        let turma = {};
        turma.id = $scope.matricula.turma.id;
        
        let matricula = {};
        matricula.numero = $scope.matricula.numero;
        matricula.aluno = aluno;
        matricula.curso = curso;
        matricula.turno = turno;
        matricula.periodo = periodo;
        matricula.turma = turma;
        
        matriculaService.cadastrar(matricula)
            .then(function (response) {
                
                let matriculaResponse = response.data;
            
                $state.transitionTo('administrador.aditar-matricula', {
                    id: matriculaResponse.id
                }, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
            
                // Mensagem
                toastUtil.showSuccessToast('Matrícula cadastrada com sucesso. Inclua o(s) Dia(s) de Refeição.');
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
                    $scope.matricula.aluno = response.data;
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