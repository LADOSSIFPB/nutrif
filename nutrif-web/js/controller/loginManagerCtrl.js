/*
 *  Controlar ações de login para acesso de perfil de gerência.
 */
nutrifApp.controller("loginManagerCtrl", function (userService, $state, loginService, $mdToast) {

    this.funcionario = {};

    this.fazerLogin = function (funcionario) {
        loginService.fazerLogin(funcionario)            
            .then(function(response) {
                userService.storeUser(response.data);
                $state.go("home.entrada-alunos");
            })
            .catch(function(data) {
                delete funcionario.senha;
                var _message = "";

                if (!data) {
                    _message = "Usuário ou senha incorretos"
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
            });
    };
});