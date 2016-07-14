angular.module('NutrifApp').controller('entradaQrCtrl', function ($scope, userService, pretensaoService, $mdToast, $state, $stateParams) {

    $scope.onSuccess = function(data) {
        pretensaoService.verifyChaveAcesso(data)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    };

    $scope.onError = function(error) {
        console.log(error);
    };

    $scope.onVideoError = function(error) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Erro ao usar câmera, ative permissões no navegador ou reinicie a página.')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
        console.log(error);
    };

    function onSuccessCallback(data, status) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Entrada Liberada =D')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
        $state.transitionTo($state.current, $stateParams, {
            reload: true,
            inherit: false,
            notify: true
        });
    }

    function onErrorCallback(data, status) {
        var _message = '';
        if (!data) {
            _message = 'QRCode inválido'
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

        $state.transitionTo($state.current, $stateParams, {
            reload: true,
            inherit: false,
            notify: true
        });
    }
});
