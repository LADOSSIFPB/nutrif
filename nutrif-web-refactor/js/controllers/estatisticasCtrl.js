angular.module('NutrifApp').controller('estatisticasCtrl', function ($scope, pretensaoService, refeicaoRealizadaService) {

    $scope.periodoPretensao = {
        dataInicio: Date.parse('-6').toString('yyyy-MM-dd'),
        dataFim: Date.parse('today').toString('yyyy-MM-dd')
    };

    $scope.pretensaoChart = {
        labels: [],
        series: ['Almoço', 'Jantar'],
        data: []
    };

    $scope.refeicaoRealizadaChart = {
        labels: [],
        series: ['Almoço', 'Jantar'],
        data: []
    };

    function carregaGraficoPretensao (periodoPretensao) {

        var _periodoPretensao = angular.copy(periodoPretensao);
        _periodoPretensao.refeicao = {id: 1};
        var _dadosPretensaoAlmoco = [];
        var _dadosPretensaoJantar = [];

        pretensaoService.mapaRefeicao(_periodoPretensao)

            .success(function (data, status){
                for (var mapaPretensao in data) {
                    $scope.pretensaoChart.labels.push(mapaPretensao.data);
                    _dadosPretensaoAlmoco.push(mapaPretensao.quantidade);
                }

                $scope.pretensaoChart.data.push(_dadosPretensaoAlmoco)
                _periodoPretensao.refeicao = {id: 2};

                pretensaoService.mapaRefeicao(_periodoPretensao)
                    .success(function (data, status){
                        for (var mapaPretensao in data) {
                            $scope.pretensaoChart.labels.push(mapaPretensao.data);
                            _dadosPretensaoJantar.push(mapaPretensao.quantidade);
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
                for (var mapaPretensao in data) {
                    $scope.refeicaoRealizadaChart.labels.push(mapaPretensao.data);
                    _dadosPretensaoAlmoco.push(mapaPretensao.quantidade);
                }

                $scope.refeicaoRealizadaChart.data.push(_dadosPretensaoAlmoco)
                _periodoPretensao.refeicao = {id: 2};

                pretensaoService.mapaRefeicao(_periodoPretensao)
                    .success(function (data, status){
                        for (var mapaPretensao in data) {
                            $scope.refeicaoRealizadaChart.labels.push(mapaPretensao.data);
                            _dadosPretensaoJantar.push(mapaPretensao.quantidade);
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
    }

});
