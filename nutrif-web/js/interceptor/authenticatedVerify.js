/*
 *  Verificar se usuário está logado e redirecionar requisição para a página correta.
 */
nutrIFApp.run(function ($rootScope, $state, userService, loginCfg) {
    
    let loginStates = ['inicio.login'];
    
    // This method reports if a state is one of the authorizes states
    let isLoginState = function (stateName) {
        var isLogin = false;
        loginStates.forEach(function (state) {
            if (stateName.indexOf(state) === 0) {
                isLogin = true;
            }
        });
        return isLogin;
    };

    // Register listener to watch route changes
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {

        let usuario = userService.getUser();
        let nameState = toState.name;

        if (usuario) {

            if (isLoginState(nameState)) {
                // Redirecionamento para a seção do administrador.
                event.preventDefault();
                $state.go(loginCfg.state[usuario.roles[0].nome]);
            }

        } else {

            // Verificar se a página solicitada já é o login.
            if (!isLoginState(nameState)) {
                console.log("inicio.login!");
                // Redirecionamento para o login do usuário.
                event.preventDefault();
                $state.go("inicio.login");
            }
        }
    });
});