/**
 * Cadastro do Edital.
 */
nutrIFApp.controller('cadastrarEditalCtrl', function ($scope,
    $mdToast, $state, toastUtil, arrayUtil, campusService, funcionarioService, editalService, eventoService, userService) {

    // Edital
    $scope.edital = {};
    
    // Campi
    $scope.campi = [];
    
    // Eventos
    $scope.eventos = [];
    
    // Auto-complete
    $scope.responsaveis = [];
    $scope.nomeResponsavel = "";
    $scope.responsavelSelecionado = null;
    
    // Enviar para o serviço de cadastro do Edital.
    $scope.adicionar = function () {
        
        let edital = $scope.edital;

        edital.dataInicial = Date.parse(edital.dataInicial);
        edital.dataFinal = Date.parse(edital.dataFinal);
        
        edital.responsavel = $scope.responsavelSelecionado;
        
        edital.funcionario = {};
        edital.funcionario.id = userService.getUser().id;

        editalService.cadastrar(edital)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Edital cadastrado com sucesso.');

                // Redirecionamento            
                $state.transitionTo('administrador.listar-editais', {
                    reload: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    };

    $scope.pesquisarReponsavelNome = function (nome) {
        return funcionarioService.listByNome(nome)
            .then(function (result) {

                if (!arrayUtil.isEmpty(result.data)) {
                    return result.data;
                } else {
                    return [];
                }

            }).catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    };
    
    function carregamentoInicial() {

        // Carregar Cursos para seleção no cadastro do Edital.
        campusService.listar()
            .then(function(response) {
                $scope.campi = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
        
        // Carregar Eventos para seleção no cadastro do Edital.
        eventoService.listar()
            .then(function(response) {
                $scope.eventos = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    // Inicializar listagem dos campi e níveis.
    carregamentoInicial();
});