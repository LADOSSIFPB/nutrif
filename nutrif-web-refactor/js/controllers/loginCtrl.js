angular.module("NutrifApp").controller("loginCtrl", function ($scope, funcionarioApiService) {

    this.funcionario = {};

    this.fazerLogin = function (funcionario) {
        funcionarioApiService.loginFuncionario(funcionario)
            .success(onSuccessLogin)
            .error(onErrorLogin);
    };

    function onSuccessLogin (data, status) {

    };

    function onErrorLogin (data, status) {

        delete $scope.funcionario.senha;

        if (!data) {
            Materialize.toast('Usuário ou senha incorretos', 6000);
        } else {
            Materialize.toast(data.mensagem, 6000);
        }

    };
});
