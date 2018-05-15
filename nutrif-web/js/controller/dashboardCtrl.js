/*
 *  Controlar ações do Dashboard.
 */
nutrIFApp.controller('dashboardCtrl', function ($scope, $state, toastUtil, diaService, refeicaoService, pretensaoService, refeicaoRealizadaService, diaRefeicaoService) {

    // Dia de Refeição.
    $scope.diaRefeicao = {};

    // Campos Dia e Refeição para consulta.
    $scope.dias = [];
    $scope.refeicoes = [];
    
    // Mapas.
    $scope.mapaPretensao = {};
    $scope.mapaRefeicaoRealizada = {};
    $scope.mapaDiaRefeicao = {};

    $scope.buscar = function () {
        let diaRefeicao = $scope.diaRefeicao;
        
        buscarQuantidadePretensao(diaRefeicao);
        
        buscarQuantidadeRefeicoesDoDia(diaRefeicao);
        
        buscarQuantidadeRefeicoesRealizadas(diaRefeicao)
    }

    function buscarQuantidadePretensao(diaRefeicao) {
        pretensaoService.buscarQuantidade(diaRefeicao)
            .then(function (response) {
                $scope.mapaPretensao = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
    
    function buscarQuantidadeRefeicoesDoDia(diaRefeicao) {
        diaRefeicaoService.buscarQuantidade(diaRefeicao)
            .then(function (response) {
                $scope.mapaDiaRefeicao = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    function buscarQuantidadeRefeicoesRealizadas(diaRefeicao) {           
        refeicaoRealizadaService.buscarQuantidade(diaRefeicao)
            .then(function (response) {
                $scope.mapaRefeicaoRealizada = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }    

    function carregamentoInicial() {

        // Carregar Dias para seleção no Dashboasd.
        diaService.listar()
            .then(function (response) {
                $scope.dias = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });

        // Carregar Refeições para seleção no Dashboasd.
        refeicaoService.listar()
            .then(function (response) {
                $scope.refeicoes = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    // Inicializar listagem dos Dias e Refeições.
    carregamentoInicial();
});