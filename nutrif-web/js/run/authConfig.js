/*
 *  Adicionar a chave de autenticação nas requisições de usuários
 *  autenticados. O campo 'Authorization' receberá o valor de keyAuth.
 */
nutrifApp.run(function($rootScope, $state, $http, userService) {

    var _user = {}

    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        _user = userService.getUser();

        if (_user) {
            $http.defaults.headers.common['Authorization'] = _user.keyAuth;
        } else {
            $http.defaults.headers.common['Authorization'] = '';
        }

    });
});
