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

    // Chart
    $scope.series = [];
    $scope.labels = [];
    $scope.data = [];

    $scope.buscar = function () {
        
        let diaRefeicao = $scope.diaRefeicao;

        limparGrafico();
        
        // Extrair dados do Gráfico.
        buscarDia(diaRefeicao.dia.id);
        
        buscarQuantidadePretensao(diaRefeicao);

        buscarQuantidadeRefeicoesDoDia(diaRefeicao);

        buscarQuantidadeRefeicoesRealizadas(diaRefeicao)
    }
    
    function limparGrafico() {
        $scope.series = [];
        $scope.labels = [];
        $scope.data = [];
    }

    function buscarDia(idDia) {
        diaService.getById(idDia)
            .then(function (response) {
                let dia = response.data;
                $scope.labels.push(dia.nome);
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
    
    function buscarQuantidadePretensao(diaRefeicao) {
        pretensaoService.buscarQuantidade(diaRefeicao)
            .then(function (response) {
                let mapaPretensao = response.data;
            
                $scope.series[0] = "Pretensão";
                $scope.data[0] = [mapaPretensao.quantidade];

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
                
                $scope.series[1] = "Edital";
                $scope.data[1] = [mapaDiaRefeicao.quantidade];
            
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
                
                $scope.series[2] = "Refeições Realizadas";
                $scope.data[2] = [mapaRefeicaoRealizada.quantidade];
            
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