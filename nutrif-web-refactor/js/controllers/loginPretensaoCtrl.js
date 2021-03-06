angular.module("NutrifApp").controller("loginPretensaoCtrl", function (userService, alunoService, $state, $mdToast, $scope) {

    var ACESSO_CONFIRMADO = true;

    $scope.aluno = {};

    this.verificarAcesso = function (matricula) {

        alunoService.verificarAcesso(matricula)
            .success(function (data, status) {
                $scope.aluno = data;
                if ($scope.aluno.acesso == ACESSO_CONFIRMADO) {
                    // Direcionar para a tela com o campo de senha.
                    $state.transitionTo("login.acesso-aluno", {
                        matricula: $scope.aluno.matricula
                    });
                } else {
                    // Direcionar para a atualização dos dados.
                    $state.transitionTo("login.atualizar-dados-aluno", {
                        matricula: $scope.aluno.matricula
                    });
                }
            })
            .error(function (data, status) {
                delete $scope.matricula;
                var _message = "";

                if (!data) {
                    _message = "Ocorreu um erro na comunicação com o servidor, favor chamar o suporte."
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
                return false;
            });
    }

});