angular.module("NutrifApp").controller('acessoAlunoCtrl', function ($stateParams, $scope, $state, $mdToast, userService, alunoService) {

    $scope.aluno = {};
    $scope.aluno.matricula = $stateParams.matricula;

    $scope.fazerLogin = function (aluno) {
        alunoService.fazerLogin(aluno)
            .success(function (data, status) {
                $scope.aluno = data;

                userService.storeUser($scope.aluno);

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