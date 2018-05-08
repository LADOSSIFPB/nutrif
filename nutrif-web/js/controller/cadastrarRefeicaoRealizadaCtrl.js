/*
 *  Controlar inserção da Refeição Realizada.
 */
nutrIFApp.controller('cadastrarRefeicaoRealizadaCtrl', function ($scope, $rootScope, $mdDialog, $state, $stateParams, idDiaRefeicao, refeicaoRealizadaService, diaRefeicaoService, userService, toastUtil) {

    $scope.diaRefeicao = {};

    $scope.adicionar = function () {

        // Dados da refeição realizada.
        let refeicaoRealizada = {
            confirmaRefeicaoDia: {},
            inspetor: {}
        };
        
        refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao = {};
        refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao.id = $scope.diaRefeicao.id;
        refeicaoRealizada.inspetor.id = userService.getUser().id;

        refeicaoRealizadaService.cadastrar(refeicaoRealizada)
            .then(function (response) {
                toastUtil.showSuccessToast("Refeição Realizada cadastrada com sucesso.");
                $mdDialog.hide();
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    $scope.fechar = function () {
        $mdDialog.cancel();
    }

    function carregamentoInicial() {

        // Dia de Refeição.
        let _idDiaRefeicao = idDiaRefeicao;

        diaRefeicaoService.buscarPorId(_idDiaRefeicao)
            .then(function (response) {
                // Dia de Refeição selecionada para registrar Realização.
                $scope.diaRefeicao = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
                $mdDialog.cancel();
            });
    }

    // Inicializar listagem de cursos e campi.
    carregamentoInicial();
});