/*
 *  Controlar ações de login para acesso do aluno.
 */
nutrifApp.controller('loginAlunoCtrl', function ($stateParams, $scope, $state, $mdToast, userService, alunoService) {

    $scope.aluno = {};
    $scope.aluno.matricula = $stateParams.matricula;

    $scope.fazerLogin = function (aluno) {
        alunoService.fazerLogin(aluno)
            .then(function(response) {
                $scope.aluno = response.data;
                
                // Cookie do usuário.
                userService.storeUser($scope.aluno);

                $state.go("pretensao.listar-pretensao");
            })
            .catch(function(data) {

                delete $scope.matricula;
            
                var _message = "";

                if (!data) {
                    _message = "Aluno inexistente."
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