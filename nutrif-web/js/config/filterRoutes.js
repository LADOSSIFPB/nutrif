angular.module('NutrifApp').run(function($rootScope, $state, $cookies) {
    // Register listener to watch route changes
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {

        var _user = $cookies.getObject("user");

        function findRoleAdmin(role) {
            return role.id === 1;
        }

        function findRoleInspetor(role) {
            return role.id === 2;
        }

        function findRoleComensal(role) {
            return role.id === 3;
        }

        if (_user) {

            if (toState.module === 'non-logged') {
                event.preventDefault();
                $state.go("entrada-aluno");
            }

            if (toState.module === 'admin' && !_user.roles.find(findRoleAdmin)) {
                event.preventDefault();
                $state.go("entrada-aluno");
            }

            if(toState.module === 'comensal' && !_user.roles.find(findRoleComensal)){
                event.preventDefault();
                $state.go("entrada-aluno");
            }

            if(toState.module != 'comensal' && _user.roles.find(findRoleComensal)){
                event.preventDefault();
                $state.go("lista-pretensao");
            }

        } else {

            if (toState.module != 'non-logged') {
                event.preventDefault();
                $state.go("login");
            }

        }

    });

});
