/*
 *  Controlar ações da Migração do Sábado Letivo.
 */
nutrifApp.controller('migrarSabadoLetivoCtrl', function ($scope, $state, $window,
        toastUtil, diaRefeicaoService, userService, editalService, funcionarioService, diaService) {
	
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
                .then(function (response) {
                    var message = 'Sábado Letivo Migrado com sucesso.';
                    toastUtil.showSuccessToast(message);
                    
                    $state.transitionTo('home.listar-edital');
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);

                    $state.transitionTo('home.listar-edital');
                });
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
                .then(function (response) {
                    $scope.editais = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });

            return $scope.editais;
        }

        function transformChip(edital) {
            // If it is an object, it's already a known chip
            if (angular.isObject(edital)) {
                return edital;
            }
        }

        // Carregar os dias da semana.
        function carregarDiasSemana() {
           diaService.listarDias()            
                .then(function (response) {
                    $scope.dias = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
        }

        carregarDiasSemana();    
})

		


