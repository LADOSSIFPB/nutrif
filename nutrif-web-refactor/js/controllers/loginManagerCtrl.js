angular.module("NutrifApp").controller("loginManagerCtrl", function (userService, $state, loginService, $mdToast) {

    this.funcionario = {};

    this.fazerLogin = function (funcionario) {
        loginService.fazerLogin(funcionario)

            .success(function (data, status) {
                userService.storeUser(data);
                $state.go("home.entrada-alunos");
            })

            .error(function (data, status) {

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
