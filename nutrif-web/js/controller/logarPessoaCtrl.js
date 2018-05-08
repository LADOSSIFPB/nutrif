/*
 *  Controlar ações de autenticação para acesso.
 */
nutrIFApp.controller('logarPessoaCtrl', function ($stateParams, $scope, $state, pessoaService, userService, toastUtil) {

    $scope.pessoa = {};
    
    $scope.login = function () {
        
        let pessoa = $scope.pessoa;
        
        pessoaService.login(pessoa)
            .then(function (response) {
                
                // Cookie do usuário.
                let usuarioLogin = response.data;
            
                let usuario = {};
                usuario.id = usuarioLogin.id;
                usuario.keyAuth = usuarioLogin.keyAuth;
            
                // Identificação do campus do usuário logado.
                let campus = {};
                campus.id = usuarioLogin.campus.id;
                usuario.campus = campus;
            
                userService.setUser(usuario);

                // Redirecionamento com identificador do Campus
                $state.transitionTo("administrador.home");
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
});