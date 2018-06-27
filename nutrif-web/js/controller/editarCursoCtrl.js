/*
 *  Controlar atualização do Curso.
 */
nutrIFApp.controller('editarCursoCtrl', function ($scope,
    $stateParams, $state, toastUtil, cursoService, campusService, nivelService) {

    $scope.curso = {};

    $scope.campi = [];
    $scope.niveis = [];

    $scope.atualizar = function () {

        let curso = $scope.curso;

        cursoService.atualizar(curso)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Curso atualizado com sucesso.');

                // Redirecionamento
                $state.transitionTo('administrador.listar-cursos', {
                    reload: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    /**
        Carregar os dados iniciais.
     */
    function carregar() {

        let id = $stateParams.id;

        if (id <= 0) {
            redirecionarListagem();
        } else {

            cursoService.getById(id)
                .then(function (response) {
                    // Refeição 
                    let curso = response.data;

                    // Curso - ng-model do formulário.
                    $scope.curso = curso;
                
                    carregarComplementar();
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                    redirecionarListagem();
                });
        }
    }

    function carregarComplementar() {
        
        // Carregar Campus para seleção no cadastro do Curso.
        campusService.listar()
            .then(function (response) {
                $scope.campi = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });

        // Carregar Níveis para seleção no cadastro do Curso.
        nivelService.listar()
            .then(function (response) {
                $scope.niveis = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    /**
        Redirecionar para a página de listagem.
     */
    function redirecionarListagem() {
        $state.transitionTo('administrador.listar-curso', {
            reload: true
        });
    }

    carregar();
});