angular.module('NutrifApp').controller("loginCtrl", function ($scope, loginService, $state, $cookies) {

    $scope.fazerLogin = function (funcionario) {

        loginService.fazerLogin(funcionario).success(function (data, status){

            $cookies.putObject('user', data);
            $state.go("home");

        }).error(function (data, status) {

            delete funcionario.senha;

            if (!data) {
               Materialize.toast('Usuário ou senha incorretos', 6000);
            } else {
                Materialize.toast(data.mensagem, 6000);
            }

        });
    }
});
