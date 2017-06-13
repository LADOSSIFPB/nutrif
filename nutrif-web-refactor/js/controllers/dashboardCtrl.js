angular.module('NutrifApp').controller('dashboardCtrl', function (
    $scope, $mdToast, $timeout, $state, $interval, $mdExpansionPanelGroup, $mdExpansionPanel, diaService, refeicaoService, pretensaoService, refeicaoRealizadaService, diaRefeicaoService) {

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
            .success(function (data, status) {
                $scope.dias = data;
            })
            .error(onErrorCallback);
    }

    // Carregar os tipos das refeições.
    $scope.carregarRefeicao = function () {
        refeicaoService.listarRefeicoes()
            .success(function (data, status) {
                $scope.refeicoes = data;
            })
            .error(onErrorCallback);
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

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Não foram encontrados cursos'
        } else {
            _message = data.mensagem
        }

        $mdToast.show(
            $mdToast.simple()
            .textContent(_message)
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
    }

    var getQuantidadePretensao = function () {

        pretensaoService.getQuantidadePretensao($scope.diaRefeicao)
            .success(function (data, status) {
                $scope.mapaPretensao = data;
            })
            .error(onErrorCallback);

    }

    var getQuantidadeRefeicoesRealizadas = function () {

        refeicaoRealizadaService.getQuantidadeRefeicoesRealizadas($scope.diaRefeicao).
        success(function (data, status) {
                $scope.mapaRefeicaoRealizada = data;
            })
            .error(onErrorCallback);
    }

    var getQuantidadeRefeicoesDoDia = function () {

        diaRefeicaoService.getQuantidadeRefeicoes($scope.diaRefeicao).
        success(function (data, status) {
                $scope.mapaDiaRefeicao = data;
            })
            .error(onErrorCallback);
    }

    // Lista de refeições realizadas.
    var getMapaRefeicaoRealizadaByDiaRefeicao = function () {

        var idDia = $scope.diaRefeicao.dia.id;
        var idRefeicao = $scope.diaRefeicao.refeicao.id;

        refeicaoRealizadaService.getMapaRefeicaoRealizadaByDiaRefeicao(idDia, idRefeicao).
        success(function (data, status) {
                $scope.refeicoesRealizadas = data.lista;
            })
            .error(onErrorCallback);
    }

    // Lista de refeições para o dia.
    var getAllDiaRefeicaoByDiaAndRefeicao = function () {

        var idDia = $scope.diaRefeicao.dia.id;
        var idRefeicao = $scope.diaRefeicao.refeicao.id;

        diaRefeicaoService.getAllByDiaAndRefeicao(idDia, idRefeicao).
        success(function (data, status) {
                $scope.diaRefeicoes = data;
            })
            .error(onErrorCallback);
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