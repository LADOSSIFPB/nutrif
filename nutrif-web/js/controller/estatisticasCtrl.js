/*
 *  Controlar ações da Estatística.
 */
nutrifApp.controller('estatisticasCtrl', function ($scope, config, toastUtil, pretensaoService, refeicaoRealizadaService) {

    $scope.periodoPretensao = {
        dataInicio: Date.parse('-6'),
        dataFim: Date.parse('today')
    };

    $scope.listaPretensao = [];
    $scope.listaRefeicoes = [];

    function carregaGraficoPretensao(periodoPretensao) {

        var _periodoPretensao = angular.copy(periodoPretensao);
        _periodoPretensao.refeicao = {
            id: 1
        };
        var _dadosPretensaoAlmoco = [];
        var _dadosPretensaoJantar = [];
        var _listaPretensaoAlmoco = [];
        var _listaPretensaoJantar = [];

        pretensaoService.mapaRefeicao(_periodoPretensao)
            .then(function (response) {
                
                var data = response.data;
            
                for (var i = 0; i < data.length; i++) {
                    $scope.pretensaoChart.labels.push(moment(data[i].data).locale("pt-br").format('DD/MM'));
                    _dadosPretensaoAlmoco.push(data[i].quantidade);
                    _listaPretensaoAlmoco.push(data[i].lista);
                }

                $scope.pretensaoChart.data.push(_dadosPretensaoAlmoco)
                $scope.pretensaoChart.refeicoes.push(_listaPretensaoAlmoco)
                _periodoPretensao.refeicao = {
                    id: 2
                };

                pretensaoService.mapaRefeicao(_periodoPretensao)
                    .then(function (response) {
                
                        var data = response.data;
                    
                        for (var i = 0; i < data.length; i++) {
                            _dadosPretensaoJantar.push(data[i].quantidade);
                            _listaPretensaoJantar.push(data[i].lista);
                        }

                        $scope.pretensaoChart.data.push(_dadosPretensaoJantar);
                        $scope.pretensaoChart.refeicoes.push(_listaPretensaoJantar)
                    })
                    .catch(function (error) {
                        toastUtil.showErrorToast(error);
                    });

            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    function carregaGraficoRefeicaoRealizada(periodoPretensao) {

        var _periodoPretensao = angular.copy(periodoPretensao);
        _periodoPretensao.refeicao = {
            id: 1
        };
        var _dadosPretensaoAlmoco = [];
        var _dadosPretensaoJantar = [];
        var _listaRefeicaoAlmoco = [];
        var _listaRefeicaoJantar = [];

        refeicaoRealizadaService.mapaRefeicao(_periodoPretensao)
            .then(function (response) {
                
                var data = response.data;
                    
                for (var i = 0; i < data.length; i++) {
                    $scope.refeicaoRealizadaChart.labels.push(moment(data[i].data).locale("pt-br").format('DD/MM'));
                    _dadosPretensaoAlmoco.push(data[i].quantidade);
                    _listaRefeicaoAlmoco.push(data[i].lista);
                }

                $scope.refeicaoRealizadaChart.data.push(_dadosPretensaoAlmoco)
                $scope.refeicaoRealizadaChart.refeicoes.push(_listaRefeicaoAlmoco);
                _periodoPretensao.refeicao = {
                    id: 2
                };

                refeicaoRealizadaService.mapaRefeicao(_periodoPretensao)
                    .then(function (response) {
                
                        var data = response.data;
                    
                        for (var i = 0; i < data.length; i++) {
                            _dadosPretensaoJantar.push(data[i].quantidade);
                            _listaRefeicaoJantar.push(data[i].lista);
                        }

                        $scope.refeicaoRealizadaChart.data.push(_dadosPretensaoJantar)
                        $scope.refeicaoRealizadaChart.refeicoes.push(_listaRefeicaoJantar);
                    })
                    .catch(function (error) {
                        toastUtil.showErrorToast(error);
                    });

            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    };


    $scope.carregaGraficos = function () {

        $scope.pretensaoChart = {
            colors: config.chartColors,
            labels: [],
            series: ['Almoço', 'Jantar'],
            data: [],
            refeicoes: []
        };

        $scope.refeicaoRealizadaChart = {
            colors: config.chartColors,
            labels: [],
            series: ['Almoço', 'Jantar'],
            data: [],
            refeicoes: []
        };

        carregaGraficoPretensao($scope.periodoPretensao);
        carregaGraficoRefeicaoRealizada($scope.periodoPretensao);
    };

    $scope.onClickRefeicao = function (points, evt) {
        $scope.listaPretensao = [];
        $scope.listaRefeicoes = $scope.refeicaoRealizadaChart.refeicoes[0][points[0]._index];
        $scope.listaRefeicoes.push.apply($scope.listaRefeicoes, $scope.refeicaoRealizadaChart.refeicoes[1][points[0]._index]);
        console.log($scope.listaRefeicoes);
    };

    $scope.onClickPretensao = function (points, evt) {
        console.log($scope.pretensaoChart.refeicoes[0][points[0]._index]);
        $scope.listaRefeicoes = [];
        $scope.listaPretensao = $scope.pretensaoChart.refeicoes[0][points[0]._index];
        $scope.listaPretensao.push.apply($scope.listaPretensao, $scope.pretensaoChart.refeicoes[1][points[0]._index]);
    };

    $scope.carregaGraficos();

});