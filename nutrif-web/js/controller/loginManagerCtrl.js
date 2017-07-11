/*
 *  Controlar ações de login para acesso de perfil de gerência.
 */
nutrifApp.controller("loginManagerCtrl", function (toastUtil, userService, $state, loginService, $mdToast) {

    this.funcionario = {};

    this.fazerLogin = function (funcionario) {
        loginService.fazerLogin(funcionario)            
            .then(function(response) {
                // Cookie do usuário.
                userService.storeUser(response.data);
                $state.go("home.entrada-alunos");
            })
            .catch(function(error) {
                delete funcionario.senha;                
                toastUtil.showErrorToast(error);
            });
    };
});