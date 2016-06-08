angular.module("NutrifApp").controller("loginPretensaoCtrl", function (userService, $state, alunoService, $mdToast) {

    this.fazerLogin = function (matricula) {
        alunoService.buscaAlunoPorMatricula(matricula)

            .success(function (data, status) {
                userService.storeUser(data);
                $state.go("pretensao.listar-pretensao");
            })

            .error(function (data, status) {

                delete $scope.matricula;
                var _message = "";

                if (!data) {
                    _message = "Aluno inexistente"
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