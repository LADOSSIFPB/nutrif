/**
 * Editar do Edital.
 */
nutrIFApp.controller('editarEditalCtrl', function ($scope, $stateParams, $state, toastUtil, arrayUtil, dateTimeUtil, campusService, funcionarioService, editalService, eventoService, userService) {

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

    // Enviar para o serviço de atualização do Edital.
    $scope.atualizar = function () {

        let edital = $scope.edital;

        edital.dataInicial = Date.parse(edital.dataInicial);
        edital.dataFinal = Date.parse(edital.dataFinal);

        edital.responsavel = {};
        edital.responsavel.id = $scope.responsavelSelecionado.id;

        edital.funcionario = {};
        edital.funcionario.id = userService.getUser().id;

        editalService.atualizar(edital)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Edital atualizado com sucesso.');

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

        let id = $stateParams.id;

        if (id <= 0) {
            redirecionarListagem();
        } else {
            
            // Recuperar Edital para edição.
            editalService.getById(id)
                .then(function (response) {
                    $scope.edital = response.data;
                    let edital = $scope.edital;
                
                    // Data/hora de início e fim do Edital.
                    let dataInicial = dateTimeUtil.toDate(edital.dataInicial);
                    let dataFinal = dateTimeUtil.toDate(edital.dataFinal);
                    
                    $scope.edital.dataInicial = dataInicial;
                    $scope.edital.dataFinal = dataFinal;                  
                
                    // Responsável
                    let responsavel = edital.responsavel;
                    $scope.responsavelSelecionado = responsavel;
                    $scope.nomeResponsavel = responsavel.nome;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });            
            
            // Carregar Cursos para seleção no cadastro do Edital.
            campusService.listar()
                .then(function (response) {
                    $scope.campi = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });

            // Carregar Eventos para seleção no cadastro do Edital.
            eventoService.listar()
                .then(function (response) {
                    $scope.eventos = response.data;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
        }
    }

    /**
        Redirecionar para a página de listagem.
     */
    function redirecionarListagem() {
        $state.transitionTo('administrador.listar-editais', {
            reload: true
        });
    }
    
    // Inicializar listagem dos campi e níveis.
    carregamentoInicial();
});