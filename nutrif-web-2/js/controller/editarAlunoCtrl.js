/*
 *  Controlar atualização do Aluno.
 */
nutrIFApp.controller('editarAlunoCtrl', function ($scope,
    $stateParams, $state, $mdDialog, toastUtil, alunoService, campusService, matriculaService) {

    $scope.aluno = {};

    $scope.selected = [];

    $scope.campi = [];
    $scope.matriculas = [];

    $scope.atualizarBasico = function (aluno) {}

    $scope.atualizarAcesso = function (aluno) {}

    $scope.adicionar = function () {}

    $scope.remover = function (selected) {}

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

        matriculaService.getByAlunoId(_id)
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