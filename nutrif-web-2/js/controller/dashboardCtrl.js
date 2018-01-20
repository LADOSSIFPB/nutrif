/*
 *  Controlar ações da listagem da Refeição.
 */
nutrIFApp.controller('dashboardCtrl', function (
    $scope, $timeout, $state, $interval, $mdExpansionPanelGroup, $mdExpansionPanel, toastUtil, diaService, refeicaoService, pretensaoService, refeicaoRealizadaService, diaRefeicaoService) {

    // Campos Dia e Refeição para consulta.
    $scope.dias = [];
    $scope.refeicoes = [];

    $scope.dataHoje = new Date();
    $scope.diaRefeicao = {};

    // Mapas.
    $scope.mapaPretensao = {};
    $scope.mapaRefeicaoRealizada = {};
    $scope.mapaDiaRefeicao = {};

    $scope.refeicoesRealizadas = [];
    $scope.diaRefeicoes = [];

    // Carregar os dias da semana.
    $scope.carregarDia = function () {
        diaService.listarDias()
            .then(function (response) {
                $scope.dias = response.data;
            })
            .catch(onErrorCallback);
    }

    // Carregar os tipos das refeições.
    $scope.carregarRefeicao = function () {
        refeicaoService.listarRefeicoes()
            .then(function (response) {
                $scope.refeicoes = response.data;
            })
            .catch(onErrorCallback);
    }

    // Iniciar a quantidade nos mapas.
    $scope.initMapas = function () {

        $scope.mapaRefeicaoRealizada.quantidade = 0;
        $scope.mapaPretensao.quantidade = 0;
        $scope.mapaDiaRefeicao.quantidade = 0;

        $scope.mapaRefeicaoRealizada.data = new Date();
        $scope.mapaPretensao.data = new Date();
        $scope.mapaDiaRefeicao.data = new Date();
    }

    function onErrorCallback(error) {
        toastUtil.showErrorToast(error);
    }

    var getQuantidadePretensao = function () {

        pretensaoService.getQuantidadePretensao($scope.diaRefeicao)
            .then(function (response) {
                $scope.mapaPretensao = response.data;
            })
            .catch(onErrorCallback);

    }

    var getQuantidadeRefeicoesRealizadas = function () {

        refeicaoRealizadaService.getQuantidadeRefeicoesRealizadas($scope.diaRefeicao)
            .then(function (response) {
                $scope.mapaRefeicaoRealizada = response.data;
            })
            .catch(onErrorCallback);
    }

    var getQuantidadeRefeicoesDoDia = function () {

        diaRefeicaoService.getQuantidadeRefeicoes($scope.diaRefeicao)
            .then(function (response) {
                $scope.mapaDiaRefeicao = response.data;
            })
            .catch(onErrorCallback);
    }

    // Lista de refeições realizadas.
    var getMapaRefeicaoRealizadaByDiaRefeicao = function () {

        var idDia = $scope.diaRefeicao.dia.id;
        var idRefeicao = $scope.diaRefeicao.refeicao.id;

        refeicaoRealizadaService.getMapaRefeicaoRealizadaByDiaRefeicao(idDia, idRefeicao)
            .then(function (response) {
                $scope.refeicoesRealizadas = response.data.lista;
            })
            .catch(onErrorCallback);
    }

    // Lista de refeições para o dia.
    var getAllDiaRefeicaoByDiaAndRefeicao = function () {

        var idDia = $scope.diaRefeicao.dia.id;
        var idRefeicao = $scope.diaRefeicao.refeicao.id;

        diaRefeicaoService.getAllByDiaAndRefeicao(idDia, idRefeicao)
            .then(function (response) {
                $scope.diaRefeicoes = response.data;
            })
            .catch(onErrorCallback);
    }

    $scope.consulta = function () {
        getQuantidadePretensao();
        getQuantidadeRefeicoesRealizadas();
        getQuantidadeRefeicoesDoDia();

        getMapaRefeicaoRealizadaByDiaRefeicao();
        getAllDiaRefeicaoByDiaAndRefeicao();
    }

    // Carregamento inicial.
    $scope.carregarDia();
    $scope.carregarRefeicao();
    $scope.initMapas();

    /* Panel expandido
     */
    // Async 
    $mdExpansionPanel().waitFor('refeicaoRealizadaExpPanel').then(function (instance) {
        console.log("Foi: " + instance.isOpen());
    });

    // Ordenamento
    $scope.orderByNome = "confirmaRefeicaoDia.diaRefeicao.aluno.nome";

    $scope.logOrder = function (order) {
        console.log('order: ', order);
    };

    /*
     *   Método que faz com que a informação seja atualizada periodicamente, parando de atualizala, quando o usuário muda de página, onde seu controller é alterado.
     */
    this.consultaAutomatica = function () {
        var timer;
        timer = $interval(function () {
            var currentCtrl = $state.current.controller;
            if (currentCtrl === "dashboardCtrl") {
                $scope.consulta();
            } else {
                console.log(currentCtrl);
                $interval.cancel(timer);
            }
        }, 10000);
    }
});