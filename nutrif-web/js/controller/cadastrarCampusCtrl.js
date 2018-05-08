/*
 *  Controlar inserção do Campus.
 */
nutrIFApp.controller('cadastrarCampusCtrl', function ($scope, $state, toastUtil, campusService) {

    // Campus
    $scope.campus = {};

    // Enviar para o serviço de cadastro do Campus.
    $scope.adicionar = function () {

        let campus = $scope.campus;

        campusService.cadastrar(campus)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Campus cadastrado com sucesso.');

                // Redirecionamento            
                $state.transitionTo('administrador.listar-campi', {
                    reload: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
});