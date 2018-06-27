/*
 *  Controlar inserção da Refeição.
 */
nutrIFApp.controller('cadastrarCursoCtrl', function ($scope,
    $mdToast, $state, toastUtil, cursoService, campusService, nivelService) {

    $scope.campi = [];
    $scope.niveis = [];

    // Enviar para o serviço de cadastro do Curso.
    $scope.adicionar = function () {

        // Enviar para o serviço de cadastro de curso.            
        cursoService.cadastrar(curso)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Curso cadastrado com sucesso.');

                // Redirecionamento            
                $state.transitionTo('administrador.listar-cursos', {
                    reload: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    function carregamentoInicial() {

        // Carregar Campus para seleção no cadastro do Curso.
        campusService.listar()
            .then(function(response) {
                $scope.campi = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
        
        // Carregar Níveis para seleção no cadastro do Curso.
        nivelService.listar()
            .then(function(response) {
                $scope.niveis = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    // Inicializar listagem dos campi e níveis.
    carregamentoInicial();
});