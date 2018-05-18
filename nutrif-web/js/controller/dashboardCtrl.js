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

    $scope.labels = [];
    $scope.data = [];

    $scope.buscar = function () {
        let diaRefeicao = $scope.diaRefeicao;

        limparGrafico();
        
        buscarQuantidadePretensao(diaRefeicao);

        buscarQuantidadeRefeicoesDoDia(diaRefeicao);

        buscarQuantidadeRefeicoesRealizadas(diaRefeicao)
    }
    
    function limparGrafico() {
        $scope.labels = [];
        $scope.data = [];
    }

    function buscarQuantidadePretensao(diaRefeicao) {
        pretensaoService.buscarQuantidade(diaRefeicao)
            .then(function (response) {
                let mapaPretensao = response.data;

                $scope.labels.push("Pretensão");
                $scope.data.push(mapaPretensao.quantidade);

                $scope.mapaPretensao = mapaPretensao;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    function buscarQuantidadeRefeicoesDoDia(diaRefeicao) {
        diaRefeicaoService.buscarQuantidade(diaRefeicao)
            .then(function (response) {
                let mapaDiaRefeicao = response.data;
            
                $scope.labels.push("Edital");
                $scope.data.push(mapaDiaRefeicao.quantidade);
            
                $scope.mapaDiaRefeicao = mapaDiaRefeicao;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    function buscarQuantidadeRefeicoesRealizadas(diaRefeicao) {
        refeicaoRealizadaService.buscarQuantidade(diaRefeicao)
            .then(function (response) {
                let mapaRefeicaoRealizada = response.data;
            
                $scope.labels.push("Realizadas");
                $scope.data.push(mapaRefeicaoRealizada.quantidade);
            
                $scope.mapaRefeicaoRealizada = mapaRefeicaoRealizada;
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