angular.module("NutrifApp").controller("loginPretensaoCtrl", function (userService, alunoService, $state, $mdToast, $scope) {
    $scope.acesso = false;
    
    var ACESSO_CONFIRMADO = true;

    this.verificarAcesso = function (matricula) {

        alunoService.verificarAcesso(matricula)
            .success(function (data, status) {
                $scope.aluno = data;
                if ($scope.aluno.acesso == ACESSO_CONFIRMADO){
                    // Direcionar para a tela com o campo de senha.
                	 $state.go("pretensao.acesso-aluno")
                } else {
                    // Direcionar para a atualização dos dados.
                    $state.transitionTo("pretensao.atualizar-dados-aluno", {
                        matricula: $scope.aluno.matricula
                    });
                }
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
                return false;
            });
    }

   $scope.fazerLogin = function (aluno) {
        alunoService.fazerLogin(aluno)
            .success(function (data, status) {
                $scope.aluno = data;
                console.log(data);
                userService.storeUser($scope.aluno);
                
                if ($scope.aluno.acesso == true) {
                    $state.go("pretensao.listar-pretensao");
                } else {
                    $state.transitionTo("pretensao.atualizar-dados-aluno", {
                        matricula: $scope.aluno.matricula
                    });
                }
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