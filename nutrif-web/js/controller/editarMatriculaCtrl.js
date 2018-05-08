/*
 *  Controlar inserção do Aluno.
 */
nutrIFApp.controller('editarMatriculaCtrl', function ($scope, $stateParams, $state, matriculaService, alunoService, cursoService, turnoService, periodoService, turmaService, situacaoMatriculaService, diaRefeicaoService, toastUtil, stringUtil) {

    $scope.matricula = {};

    $scope.cursos = [];
    $scope.turnos = [];
    $scope.periodos = [];
    $scope.turmas = [];
    $scope.situacoes = [];

    $scope.selectedDiasRefeicao = [];

    $scope.diasRefeicao = [];

    $scope.atualizar = function () {

        let matricula = $scope.matricula;

        matriculaService.atualizar(matricula)
            .then(function (response) {

                // Mensagem
                toastUtil.showSuccessToast('Matrícula atualizada com sucesso. Caso necessário, inclua o(s) Dia(s) de Refeição.');

                $state.transitionTo($state.current, $stateParams, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
            })
            .catch(onErrorCallback);
    }

    $scope.removerDiaRefeicao = function (selected) {
        
        var _selectedDiasRefeicao = angular.copy($scope.selectedDiasRefeicao);

        if (_selectedDiasRefeicao.length === 0) {

            toastUtil.showToast("Antes de apagar, selecione algum Dia de Refeição.");
        } else {

            for (var diaRefeicao of _selectedDiasRefeicao) {
                diaRefeicaoService.remover(diaRefeicao.id)
                    .then(function (response) {

                        toastUtil.showToast("Dia de Refeição removido com sucesso.");

                        $state.transitionTo($state.current, $stateParams, {
                            reload: true,
                            inherit: false,
                            notify: true
                        });
                    })
                    .catch(onErrorCallback);
            }
        }
    }

    $scope.adicionarDiaRefeicao = function () {

        var _idMatricula = $scope.matricula.id;

        if (!(stringUtil.isEmpty(_idMatricula))) {
            $state.transitionTo('administrador.adicionar-diarefeicao', {
                id: _idMatricula
            }, {
                reload: true,
                inherit: false,
                notify: true
            });
        } else {
            toastUtil.showToast("Impossível carregar os dados da Matrícula para cadastrar o Dia de Refeição.");
        }
    }

    function carregamentoInicial() {

        // Matrícula
        var _idMatricula = $stateParams.id;

        if (stringUtil.isEmpty(_idMatricula)) {

            $state.transitionTo('administrador.listar-alunos', {
                reload: true
            });
        } else {

            matriculaService.getById(_idMatricula)
                .then(function (response) {
                    // Matrícula 
                    let matricula = response.data;
                    $scope.matricula = matricula;

                    // Dias de Refeição
                    diaRefeicaoService.listarVigentesPorMatricula(matricula.numero)
                        .then(function (response) {
                            $scope.diasRefeicao = response.data;
                        })
                        .catch(onErrorCallback);

                })
                .catch(onErrorCallback);

            // Curso
            cursoService.listar()
                .then(function (response) {
                    $scope.cursos = response.data;
                })
                .catch(onErrorCallback);

            // Turno
            turnoService.listar()
                .then(function (response) {
                    $scope.turnos = response.data;
                })
                .catch(onErrorCallback);

            // Periodo
            periodoService.listar()
                .then(function (response) {
                    $scope.periodos = response.data;
                })
                .catch(onErrorCallback);

            // Turma
            turmaService.listar()
                .then(function (response) {
                    $scope.turmas = response.data;
                })
                .catch(onErrorCallback);

            // Turma
            situacaoMatriculaService.listar()
                .then(function (response) {
                    $scope.situacoes = response.data;
                })
                .catch(onErrorCallback);
        }
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }
    
    // Inicializar listagem de cursos e campi.
    carregamentoInicial();
});