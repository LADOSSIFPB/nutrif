/*
 *  Controlar entrada no Refeitório do Aluno através do QR-Code.
 */
nutrIFApp.controller('entrarRestauranteQrCodeCtrl', function ($scope, userService, pretensaoService, toastUtil, $mdToast, $state, $stateParams) {

    $scope.onSuccess = function (data) {
        pretensaoService.verifyChaveAcesso(data)
            .then(function (response) {
                // Acesso liberado.
                toastUtil.showSuccessToast("Entrada Liberada.");

                $state.transitionTo($state.current, $stateParams, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    };

    $scope.onError = function (error) {
        console.log(error);
    };

    $scope.onVideoError = function (error) {
        
        toastUtil.showToast("Problema ao iniciar a utilização da câmera. Ative permissões no navegador.");
        console.log(error);
    };
});