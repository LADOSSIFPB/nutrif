/*
 *  Controlar ações de verificação prévia do login para acesso de perfil de aluno.
 */
nutrifApp.controller("verificarAlunoCtrl", function (userService, alunoService, toastUtil, $state, $mdToast, $scope) {

    var ACESSO_CONFIRMADO = true;

    $scope.aluno = {};

    $scope.verificarAcesso = function (matricula) {

        alunoService.verificarAcesso(matricula)
            .then(function (response) {
                $scope.aluno = response.data;
                if ($scope.aluno.acesso == ACESSO_CONFIRMADO) {
                    // Direcionar para a tela com o campo de senha.
                    $state.transitionTo("login.aluno", {
                        matricula: $scope.aluno.matricula
                    });
                } else {
                    // Direcionar para a atualização dos dados.
                    $state.transitionTo("aluno.atualizar", {
                        matricula: $scope.aluno.matricula
                    });
                }
            })
            .catch(function (error) {
                delete $scope.matricula;
                
                return toastUtil.showToast(error);
            });
    }
});