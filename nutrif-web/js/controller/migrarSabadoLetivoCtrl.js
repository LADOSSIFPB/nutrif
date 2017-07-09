angular.module('NutrifApp').controller('migrarSabadoLetivoCtrl', function ($scope, $mdToast, $state, $window,
        diaRefeicaoService, userService, editalService, funcionarioService, diaService) {
	
	
        $scope.dias = [];
        $scope.idDia = 0;

        // Responsáveis
        this.selectedItem = null;
        this.searchText = null;
        this.autocompleteDemoRequireMatch = true;
        $scope.editais = [];
        $scope.edital = {};


        this.migrar = function () {

            edital = this.selectedItem;
            
            // Enviar para o serviço de que migra o dia para o sabado letivo.
            diaRefeicaoService.migrarSabadoLetivo($scope.idDia, edital)
                .success(onSuccessCallback)
                .error(onErrorCallback);
        }

        function onSuccessCallback(data, status) {

            $mdToast.show(
                $mdToast.simple()
                .textContent('Sabado letivo migrado com sucesso!')
                .position('top right')
                .action('OK')
                .hideDelay(6000)
            );

            $state.transitionTo('home.listar-edital');
        }

        function onErrorCallback(data, status) {
            var _message = '';

            if (!data) {
                _message = 'Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.'
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

            $state.transitionTo('home.listar-edital');
        }

        // Selecionar responsável pelo Edital.
        this.buscarEditais = function buscarEditais(query) {

            var lowerCaseQuery = angular.lowercase(query);

            var results = this.listarEdital(lowerCaseQuery);

            return results || [];
        }

        // Consultar responsável no serviço.
        this.listarEdital = function listarEdital(query) {

            editalService.buscarEditalPorNome(query)
                .success(onSuccessListarFuncionario)
                .error(onErrorCallback);

            return $scope.editais;
        }

        function onSuccessListarFuncionario(data, status) {
            return $scope.editais = data;
        }

        function transformChip(edital) {
            // If it is an object, it's already a known chip
            if (angular.isObject(edital)) {
                console.log("edital" + edital);
                return edital;
            }
        }


        // Carregar os dias da semana.
        function carregarDiasSemana() {
           diaService.listarDias()
                .success(function (data, status) {
                    $scope.dias = data;
                })
                .error(function (data, status) {
                    alert("Houve um problema ao carregar os Eventos.");
                });
        }

        carregarDiasSemana();
    
})

		


