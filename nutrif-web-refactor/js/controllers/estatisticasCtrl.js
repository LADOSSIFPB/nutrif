angular.module('NutrifApp').controller('estatisticasCtrl', function ($scope, config, pretensaoService, refeicaoRealizadaService) {

    $scope.periodoPretensao = {
        dataInicio: Date.parse('-6'),
        dataFim: Date.parse('today')
    };

    function carregaGraficoPretensao (periodoPretensao) {

        var _periodoPretensao = angular.copy(periodoPretensao);
        _periodoPretensao.refeicao = {id: 1};
        var _dadosPretensaoAlmoco = [];
        var _dadosPretensaoJantar = [];

        pretensaoService.mapaRefeicao(_periodoPretensao)

            .success(function (data, status){
                for (var i = 0; i < data.length; i++) {
                    $scope.pretensaoChart.labels.push(moment(data[i].data).locale("pt-br").format('DD/MM'));
                    _dadosPretensaoAlmoco.push(data[i].quantidade);
                }

                $scope.pretensaoChart.data.push(_dadosPretensaoAlmoco)
                _periodoPretensao.refeicao = {id: 2};

                pretensaoService.mapaRefeicao(_periodoPretensao)
                    .success(function (data, status){
                        for (var i = 0; i < data.length; i++) {
                            _dadosPretensaoJantar.push(data[i].quantidade);
                        }

                        $scope.pretensaoChart.data.push(_dadosPretensaoJantar)
                    })
                    .error(function (data, status){
                        alert("Houve um erro ao carregar os gráficos. Contate um administrador.");
                    });

            })

            .error(function (data, status){
                alert("Houve um erro ao carregar os gráficos. Contate um administrador.");
            });
    }

    function carregaGraficoRefeicaoRealizada (periodoPretensao) {

        var _periodoPretensao = angular.copy(periodoPretensao);
        _periodoPretensao.refeicao = {id: 1};
        var _dadosPretensaoAlmoco = [];
        var _dadosPretensaoJantar = [];

        refeicaoRealizadaService.mapaRefeicao(_periodoPretensao)

            .success(function (data, status){
                for (var i = 0; i < data.length; i++) {
                    $scope.refeicaoRealizadaChart.labels.push(moment(data[i].data).locale("pt-br").format('DD/MM'));
                    _dadosPretensaoAlmoco.push(data[i].quantidade);
                }

                $scope.refeicaoRealizadaChart.data.push(_dadosPretensaoAlmoco)
                _periodoPretensao.refeicao = {id: 2};

                refeicaoRealizadaService.mapaRefeicao(_periodoPretensao)
                    .success(function (data, status){
                        for (var i = 0; i < data.length; i++) {
                            _dadosPretensaoJantar.push(data[i].quantidade);
                        }

                        $scope.refeicaoRealizadaChart.data.push(_dadosPretensaoJantar)
                    })
                    .error(function (data, status){
                        alert("Houve um erro ao carregar os gráficos. Contate um administrador.");
                    });

            })

            .error(function (data, status){
                alert("Houve um erro ao carregar os gráficos. Contate um administrador.");
            });
    };
    
    
    $scope.carregaGraficos = function () {
        
        $scope.pretensaoChart = {
            colors: config.chartColors,
            labels: [],
            series: ['Almoço', 'Jantar'],
            data: []
        };
    
        $scope.refeicaoRealizadaChart = {
            colors: config.chartColors,
            labels: [],
            series: ['Almoço', 'Jantar'],
            data: []
        };
        
        carregaGraficoPretensao($scope.periodoPretensao);
        carregaGraficoRefeicaoRealizada($scope.periodoPretensao);
    };
    
    $scope.onClick = function (points, evt) {
        console.log(points[0].value); // 0 -> Series A, 1 -> Series B
    };
    
    $scope.carregaGraficos();

});
