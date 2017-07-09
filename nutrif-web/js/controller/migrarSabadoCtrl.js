angular.module('NutrifApp').controller('migrarSabadoCtrl', function ($scope, $mdToast, $state, $window,
        diaRefeicaoService, userService, funcionarioService, campusService, eventoService, diaService) {


        $scope.campi = [];
        $scope.eventos = [];
        $scope.dias = [];
        $scope.idDia = 0;

        // Responsáveis
        this.selectedItem = null;
        this.searchText = null;
        this.autocompleteDemoRequireMatch = true;
        $scope.responsaveis = [];

        this.migrar = function (edital) {

            // Adicionar funcionário.
            edital.funcionario = {};
            edital.funcionario.id = userService.getUser().id;

            // Responsável
            edital.responsavel = this.selectedItem;

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
        this.buscarResponsaveis = function buscarResponsaveis(query) {

            var lowerCaseQuery = angular.lowercase(query);

            var results = this.listarFuncionario(lowerCaseQuery);

            return results || [];
        }

        // Consultar responsável no serviço.
        this.listarFuncionario = function listarFuncionario(query) {

            funcionarioService.getFuncionarioByNome(query)
                .success(onSuccessListarFuncionario)
                .error(onErrorCallback);

            return $scope.responsaveis;
        }

        function onSuccessListarFuncionario(data, status) {
            return $scope.responsaveis = data;
        }

        function transformChip(responsavel) {
            // If it is an object, it's already a known chip
            if (angular.isObject(responsavel)) {
                return responsavel;
            }
        }

        // Carregar os Campi para seleção no Edital
        function carregarCampi() {
            campusService.listarCampi()
                .success(function (data, status) {
                    $scope.campi = data;
                })
                .error(onErrorCallback);
        }

        // Carregar os Eventos para definir o tipo do Edital.
        function carregarEventos() {
            eventoService.listarEvento()
                .success(function (data, status) {
                    $scope.eventos = data;
                })
                .error(onErrorCallback);
        }

        // Carregar os dias da semana.
        function carregarDiasSemana() {
            diaService.listarDias()
                .success(function (data, status) {
                    $scope.dias = data;
                })
                .error(onErrorCallback);
        }

        carregarEventos();
        carregarCampi();
        carregarDiasSemana();

    })
    .config(function ($mdDateLocaleProvider) {
        // Example of a Spanish localization.
        $mdDateLocaleProvider.months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                                  'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
        $mdDateLocaleProvider.shortMonths = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
                                  'Jul', 'Ago', 'Sep', 'Out', 'Nov', 'Dez'];
        $mdDateLocaleProvider.days = ['Domingo', 'Segunda', 'Terça', 'Quarta',
                                'Quinta', 'Sexta', 'Sábado'];
        $mdDateLocaleProvider.shortDays = ['Do', 'Se', 'Te', 'Qua', 'Qui', 'Sex', 'Sab'];
        // Can change week display to start on Monday.
        $mdDateLocaleProvider.firstDayOfWeek = 1;
        // Optional.
        //$mdDateLocaleProvider.dates = [1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15,16,17,18,19,
        //                               20,21,22,23,24,25,26,27,28,29,30,31];
        // In addition to date display, date components also need localized messages
        // for aria-labels for screen-reader users.
        $mdDateLocaleProvider.weekNumberFormatter = function (weekNumber) {
            return 'Semana ' + weekNumber;
        };
        $mdDateLocaleProvider.msgCalendar = 'Calendario';
        $mdDateLocaleProvider.msgOpenCalendar = 'Abrir calendario';

    });