/*
 *  Controlar atualização do Aluno.
 */
nutrIFApp.controller('listarRefeicoesRealizadasCtrl', function ($scope, $stateParams, $state, $mdDialog, toastUtil, stringUtil, refeicaoRealizadaService) {

    $scope.nomeOuMatricula = "";
    
    $scope.refeicoesRealizadas = [];

    $scope.pesquisar = function () {
        
        let nomeOuMatricula = $scope.nomeOuMatricula;
    }

    function carregamentoInicial() {

        var _idExtratoRefeicao = $stateParams.idExtratoRefeicao;

        if (_idExtratoRefeicao == '') {
            $state.transitionTo('administrador.listar-extratosrefeicoes', {
                reload: true
            });
        }

        refeicaoRealizadaService.listByExtratoRefeicao(_idExtratoRefeicao)
            .then(function (response) {
                // Aluno 
                $scope.refeicoesRealizadas = response.data;
            })
            .catch(onErrorCallback);
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    carregamentoInicial();
});