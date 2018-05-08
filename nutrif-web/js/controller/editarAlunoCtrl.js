/*
 *  Controlar atualização do Aluno.
 */
nutrIFApp.controller('editarAlunoCtrl', function ($scope, $stateParams, $state, $mdDialog, toastUtil, alunoService, campusService, matriculaService, stringUtil) {

    $scope.aluno = {};

    $scope.selectedMatriculas = [];

    $scope.campi = [];
    $scope.matriculas = [];

    $scope.atualizarBasico = function () {
        // Aluno
        var _aluno = {};
        _aluno.id = $scope.aluno.id;
        _aluno.cpf = $scope.aluno.cpf;
        _aluno.nome = $scope.aluno.nome;
        _aluno.email = $scope.aluno.email;

        // Campus
        let _campus = $scope.aluno.campus;
        _aluno.campus = _campus;

        alunoService.atualizar(_aluno)
            .then(function (response) {
            
                toastUtil.showToast("Informações básicas do Aluno atualizadas com sucesso.");

                $state.transitionTo($state.current, $stateParams, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
            })
            .catch(onErrorCallback);
    }

    $scope.atualizarAcesso = function (aluno) {}

    $scope.adicionarMatricula = function () {

        let _idAluno = $scope.aluno.id;

        if (!(stringUtil.isEmpty(_idAluno))) {
            $state.transitionTo('administrador.adicionar-matricula', {
                id: _idAluno
            }, {
                reload: true,
                inherit: false,
                notify: true
            });
        } else {
            toastUtil.showToast("Impossível carregar os dados do Aluno para cadastrar a Matrícula.");
        }
    }


    $scope.removerMatricula = function () {

        var _selectedMatriculas = angular.copy($scope.selectedMatriculas);

        if (_selectedMatriculas.length === 0) {

            toastUtil.showToast("Antes de apagar, selecione alguma Matrícula.");
        } else {

            for (var matricula of _selectedMatriculas) {
                matriculaService.remover(matricula.id)
                    .then(function (response) {

                        toastUtil.showToast("Matrícula removida com sucesso.");

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

    function carregamentoInicial() {

        var _id = $stateParams.id;

        if (_id == '') {
            $state.transitionTo('administrador.listar-alunos', {
                reload: true
            });
        }

        alunoService.buscarPorId(_id)
            .then(function (response) {
                // Aluno 
                $scope.aluno = response.data;
            })
            .catch(onErrorCallback);

        campusService.listar()
            .then(function (response) {
                // Campi 
                $scope.campi = response.data;
            })
            .catch(onErrorCallback);

        matriculaService.getVigentesByAlunoId(_id)
            .then(function (response) {
                // Matrículas 
                $scope.matriculas = response.data;
            })
            .catch(onErrorCallback);
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    carregamentoInicial();
});