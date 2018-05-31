/*
 *  Controlar ações de autenticação para acesso.
 */
nutrIFApp.controller('logarPessoaCtrl', function ($stateParams, $scope, $state, pessoaService, userService, toastUtil, loginCfg) {

    $scope.pessoa = {};
    
    $scope.login = function () {
        
        let pessoa = $scope.pessoa;
        
        pessoaService.login(pessoa)
            .then(function (response) {
                
                // Cookie do usuário.
                let usuarioLogin = response.data;
            
                let usuario = {};
                usuario.id = usuarioLogin.id;
                usuario.nome = usuarioLogin.nome;
                usuario.keyAuth = usuarioLogin.keyAuth;
                usuario.roles = usuarioLogin.roles;
            
                // Identificação do campus do usuário logado.
                let campus = {};
                campus.id = usuarioLogin.campus.id;
                usuario.campus = campus;
            
                userService.setUser(usuario);
            
                // Redirecionamento com identificador do Campus
                $state.transitionTo(loginCfg.state[usuarioLogin.roles[0].nome]);
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
});