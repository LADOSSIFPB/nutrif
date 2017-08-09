/*
 *  Controlar ações de login para acesso de perfil de gerência.
 */
nutrifApp.controller("loginManagerCtrl", function ($scope, $state, toastUtil, loginService, userService) {

    $scope.funcionario = {};

    $scope.fazerLogin = function (funcionario) {
        loginService.fazerLogin(funcionario)            
            .then(function(response) {
                // Cookie do usuário.
                userService.storeUser(response.data);
            
                // Redirecionamento para a tela da entrada no Refeitório.
                $state.go("home.entrar-restaurante");
            })
            .catch(function(error) {
                delete $scope.funcionario.senha;                
                toastUtil.showErrorToast(error);
            });
    };
});