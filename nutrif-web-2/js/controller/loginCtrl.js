/*
 *  Controlar ações de autenticação para acesso.
 */
nutrIFApp.controller('loginCtrl', function ($stateParams, $scope, $state, loginService, userService, toastUtil) {

    $scope.loginUsuario = function (usuario) {

        // Redirecionamento temporário: Trecho abaixo deverá ser ativado.
        $state.go("administrador.home");

        /*
        loginService.loginUsuario(usuario)
            .then(function (response) {
                // Cookie do usuário.
                var usuarioLogin = response.data;
                var authdata = btoa(usuarioLogin.token + ':unused');
                    
                userService.setUser(authdata);

                // Redirecionamento a parte administrativa.
                $state.go("administrador.home");
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
        */
    }
});