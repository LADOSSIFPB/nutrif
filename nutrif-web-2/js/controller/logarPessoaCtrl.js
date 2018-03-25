/*
 *  Controlar ações de autenticação para acesso.
 */
nutrIFApp.controller('logarPessoaCtrl', function ($stateParams, $scope, $state, pessoaService, userService, toastUtil) {

    $scope.pessoa = {};
    
    $scope.login = function () {

        // Redirecionamento temporário: Trecho abaixo deverá ser ativado.
        // Identificação do campus do usuário logado.
        // $state.transitionTo("administrador.home", {id: 1});
        let pessoa = $scope.pessoa;
        
        pessoaService.login(pessoa)
            .then(function (response) {
                
                // Cookie do usuário.
                let usuarioLogin = response.data;
                let authdata = btoa(usuarioLogin.token + ':unused');
                    
                userService.setUser(authdata);

                // Redirecionamento com identificador do Campus
                $state.transitionTo("administrador.home", {idCampus: 1});
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
});