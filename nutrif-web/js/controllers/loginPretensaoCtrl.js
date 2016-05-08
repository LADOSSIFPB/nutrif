angular.module('NutrifApp').controller("loginPretensaoCtrl", function ($scope, $cookies, $state, alunoService) {

  $scope.buscarAluno = function (aluno) {
    alunoService.buscaAlunoPorMatricula(aluno.matricula).success(function (data, status) {
      $cookies.putObject('user', data);
      $state.go("lista-pretensao");
    }).error(function (data, status) {
      if (!data) {
        Materialize.toast('Aluno não cadastrado,<br/>tente novamente ou contate<br/>um administrador', 3000);
      }
    });
  }

});
