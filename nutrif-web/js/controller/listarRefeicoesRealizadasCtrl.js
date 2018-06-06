/*
 *  Controlar atualização do Aluno.
 */
nutrIFApp.controller('listarRefeicoesRealizadasCtrl', function ($scope, $stateParams, $state, $mdDialog, toastUtil, alunoService stringUtil) {

    $scope.nomeOuMatricula = "";
    
    $scope.refeicoesrealizadas = [];

    $scope.pesquisar = function () {
        
        let nomeOuMatricula = $scope.nomeOuMatricula;
    }

    $scope.atualizarAcesso = function (aluno) {}

    function carregamentoInicial() {

        var _idExtratoRefeicao = $stateParams.idExtratoRefeicao;

        if (_idExtratoRefeicao == '') {
            $state.transitionTo('administrador.listar-extratosrefeicoes', {
                reload: true
            });
        }

        alunoService.buscarPorId(_id)
            .then(function (response) {
                // Aluno 
                $scope.aluno = response.data;
            })
            .catch(onErrorCallback);
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    carregamentoInicial();
});