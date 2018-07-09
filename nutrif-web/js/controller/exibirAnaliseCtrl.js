/*
 *  Controlar ações da exibição de Gráficos.
 */
nutrIFApp.controller('exibirAnaliseCtrl', function ($scope, $state, toastUtil, extratoRefeicaoService) {

    // Chart
    $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
    $scope.series = ['Series A', 'Series B', 'Series C'];
    $scope.data = [
        [65, 59, 80, 81, 56, 55, 40],
        [28, 48, 40, 19, 86, 27, 90],
        [50, 48, 39, 69, 86, 77, 10]
    ];
    
    $scope.datasetOverride = [{
        yAxisID: 'y-axis-1'
    }, {
        yAxisID: 'y-axis-2'
    }];
    $scope.options = {
        scales: {
            yAxes: [
                {
                    id: 'y-axis-1',
                    type: 'linear',
                    display: true,
                    position: 'left'
                }
            ]
        }
    };
    
    // DX - Bar
    var populationData = [{
            arg: 1950,
            val: 2525778669
        }, {
            arg: 1960,
            val: 3026002942
        }, {
            arg: 1970,
            val: 3691172616
        }, {
            arg: 1980,
            val: 4449048798
        }, {
            arg: 1990,
            val: 5320816667
        }, {
            arg: 2000,
            val: 6127700428
        }, {
            arg: 2010,
            val: 6916183482
        }];
    
    $scope.chartOptionsBar = {
        dataSource: populationData,
        legend: {
            visible: false
        },
        series: {
            type: "bar"
        },
        argumentAxis: {
            tickInterval: 10,
            label: {
                format: {
                    type: "decimal"
                }
            }
        },
        title: "World Population by Decade"
    };
    
    // DX - Bubble
    var dataSourceBuble = [{
            total1: 9.5,
            total2: 168.8,
            total3: 127.2,
            older1: 2.4,
            older2: 8.8,
            older3: 40.1,
            perc1: 25.4,
            perc2: 5.3,
            perc3: 31.6,
            tag1: 'Sweden',
            tag2: 'Nigeria',
            tag3: 'Japan'
        }, {
            total1: 82.8,
            total2: 91.7,
            total3: 90.8,
            older1: 21.9,
            older2: 4.6,
            older3: 8.0,
            perc1: 26.7,
            perc2: 5.4,
            perc3: 8.9,
            tag1: 'Germany',
            tag2: 'Ethiopia',
            tag3: 'Viet Nam'
        }, {
            total1: 16.7,
            total2: 80.7,
            total3: 21.1,
            older1: 3.8,
            older2: 7.0,
            older3: 2.7,
            perc1: 22.8,
            perc2: 8.4,
            perc3: 12.9,
            tag1: 'Netherlands',
            tag2: 'Egypt',
            tag3: 'Sri Lanka'
        }, {
            total1: 62.8,
            total2: 52.4,
            total3: 96.7,
            older1: 14.4,
            older2: 4.0,
            older3: 5.9,
            perc1: 23.0,
            perc2: 7.8,
            perc3: 6.1,
            tag1: 'United Kingdom',
            tag2: 'South Africa',
            tag3: 'Philippines'
        }, {
            total1: 38.2,
            total2: 43.2,
            total3: 66.8,
            older1: 7.8,
            older2: 1.8,
            older3: 9.6,
            perc1: 20.4,
            perc2: 4.3,
            perc3: 13.7,
            tag1: 'Poland',
            tag2: 'Kenya',
            tag3: 'Thailand'
        }, {
            total1: 45.5,
            total3: 154.7,
            total4: 34.8,
            older1: 9.5,
            older3: 10.3,
            older4: 7.2,
            perc1: 21.1,
            perc3: 6.8,
            perc4: 20.8,
            tag1: 'Ukraine',
            tag3: 'Bangladesh',
            tag4: 'Canada'
        }, {
            total1: 143.2,
            total4: 120.8,
            older1: 26.5,
            older4: 11.0,
            perc1: 18.6,
            perc4: 9.5,
            tag1: 'Russian Federation',
            tag4: 'Mexico'
        }];
    $scope.chartOptionsBubble = {
        dataSource: dataSourceBuble,
        commonSeriesSettings: {
            type: 'bubble'
        },
        title: 'Correlation between Total Population and\n Population with Age over 60',
        tooltip: {
            enabled: true,
            location: "edge",
            customizeTooltip: function (arg) {
                return {
                    text: arg.point.tag + '<br/>Total Population: ' + arg.argumentText + 'M <br/>Population with Age over 60: ' + arg.valueText + 'M (' + arg.size + '%)'
                };
            }
        },
        argumentAxis: {
            label: {
                customizeText: function () {
                    return this.value + 'M';
                }
            },
            title: 'Total Population'
        },
        valueAxis: {
            label: {
                customizeText: function () {
                    return this.value + 'M';
                }
            },
            title: 'Population with Age over 60'
        },
        legend: {
            position: 'inside',
            horizontalAlignment: 'left',
            border: {
                visible: true
            }
        },
        palette: ["#00ced1", "#008000", "#ffd700", "#ff7f50"],
        onSeriesClick: function(e) {
            var series = e.target;
            if (series.isVisible()) {
                series.hide();
            } else {
                series.show();
            }
        },
        "export": {
            enabled: true
        },
        series: [{
            name: 'Europe',
            argumentField: 'total1',
            valueField: 'older1',
            sizeField: 'perc1',
            tagField:'tag1'
        }, {
            name: 'Africa',
            argumentField: 'total2',
            valueField: 'older2',
            sizeField: 'perc2',
            tagField: 'tag2'
        }, {
            name: 'Asia',
            argumentField: 'total3',
            valueField: 'older3',
            sizeField: 'perc3',
            tagField: 'tag3'
        }, {
            name: 'North America',
            argumentField: 'total4',
            valueField: 'older4',
            sizeField: 'perc4',
            tagField: 'tag4'
        }]
    };

    $scope.extratos = [];
    
    $scope.dataInicial;
    $scope.dataFinal;
    
    $scope.pesquisar = function () {
        
        // Data inicial e final.
        let dataInicial = Date.parse($scope.dataInicial);
        let dataFinal = Date.parse($scope.dataFinal);        
        
        extratoRefeicaoService.buscarByPeriodo(dataInicial, dataFinal)
            .then(function (response) {
                $scope.extratos = response.data;
                carregarExtrato();
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    };
    
    function carregarExtrato() {
    
        let almocos = [];
        let jantares = [];
        let datas = [];
        
        for(var extrato of $scope.extratos) {
            console.log(extrato);            
        }    
    }
});