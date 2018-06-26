/*
 *  Controlar atualização do Campus.
 */
nutrIFApp.controller('editarCampusCtrl', function ($scope,
    $stateParams, $state, toastUtil, campusService) {

    $scope.campus = {};

    $scope.atualizar = function (refeicao) {

        let campus = $scope.campus;

        campusService.atualizar(campus)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Campus atualizado com sucesso.');

                // Redirecionamento
                $state.transitionTo('administrador.listar-campi', {
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
            campusService.getById(id)
                .then(function (response) {
                    // Refeição 
                    let campus = response.data;
                    
                    // Refeição - ng-model do formulário.
                    $scope.campus = campus;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                    redirecionarListagem();
                });
        }
    }

    /**
        Redirecionar para a página de listagem.
     */
    function redirecionarListagem() {
        $state.transitionTo('administrador.listar-campi', {
            reload: true
        });
    }

    carregar();
});