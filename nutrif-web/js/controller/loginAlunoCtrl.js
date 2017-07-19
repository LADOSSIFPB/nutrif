/*
 *  Controlar ações de login para acesso do aluno.
 */
nutrifApp.controller('loginAlunoCtrl', function ($stateParams, $scope, $state, toastUtil, userService, alunoService) {

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
            .catch(function(error) {
                
                delete $scope.aluno.senha;
            
                toastUtil.showErrorToast(error);
            });
    };
});