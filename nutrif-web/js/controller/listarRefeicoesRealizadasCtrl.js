/*
 *  Controlar atualização do Aluno.
 */
nutrIFApp.controller('listarRefeicoesRealizadasCtrl', function ($scope, $stateParams, $state, $mdDialog, toastUtil, stringUtil, refeicaoRealizadaService, extratoRefeicaoService) {

    $scope.nomeOuMatricula = "";

    $scope.refeicoesRealizadas = [];

    $scope.extratoRefeicao = {};

    let _refeicoesRealizadas = [];

    $scope.pesquisar = function () {

        var _idExtratoRefeicao = $stateParams.idExtratoRefeicao;

        let _nomeOuMatricula = $scope.nomeOuMatricula;

        if (_nomeOuMatricula.length >= 3) {
            refeicaoRealizadaService.listByExtratoRefeicaoAluno(_idExtratoRefeicao, _nomeOuMatricula)
                .then(function (response) {
                    // Refeições realizadas por nome do Aluno.
                    $scope.refeicoesRealizadas = response.data;
                })
                .catch(onErrorCallback);

        } else if (_nomeOuMatricula.length == 0) {

            // Refeições realizadas cerragadas no início.
            $scope.refeicoesRealizadas = angular.copy(_refeicoesRealizadas);
        }
    }

    $scope.limparBusca = function () {

        $scope.nomeOuMatricula = "";

        // Refeições realizadas cerragadas no início.
        $scope.refeicoesRealizadas = angular.copy(_refeicoesRealizadas);
    };

    function carregamentoInicial() {

        var _idExtratoRefeicao = $stateParams.idExtratoRefeicao;

        if (_idExtratoRefeicao == '') {
            $state.transitionTo('administrador.listar-extratosrefeicoes', {
                reload: true
            });
        } else {

            extratoRefeicaoService.getById(_idExtratoRefeicao)
                .then(function (response) {
                    // Extrato da Refeição.
                    $scope.extratoRefeicao = response.data;
                })
                .catch(onErrorCallback);

            refeicaoRealizadaService.listByExtratoRefeicao(_idExtratoRefeicao)
                .then(function (response) {
                    // Refeições realizadas do Extrato da Refeição.
                    _refeicoesRealizadas = response.data;
                    $scope.refeicoesRealizadas = angular.copy(_refeicoesRealizadas);
                })
                .catch(onErrorCallback);
        }
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    carregamentoInicial();
});