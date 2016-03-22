angular.module('NutrifApp').run(function($rootScope, $state, $cookies) {
    // Register listener to watch route changes
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {

        var _user = $cookies.getObject("user");

        function findRoleAdmin(role) {
            return role.nome === 'admin';
        }

        function findRoleInspetor(role) {
            return role.nome === 'inspetor';
        }

        if (_user) {

            if (toState.module === 'non-logged') {
                event.preventDefault();
                $state.go("home");
            }

            if (toState.module === 'admin' && !_user.roles.find(findRoleAdmin)) {
                console.log("bloqueado");
                event.preventDefault();
                $state.go("home");
            }

            /* Adiocionar para onde redirecionar, pois é o ultimo tipo de usuário
            if(toState.module === 'inspetor' && !_user.roles.find(findRoleInspetor)){
                event.preventDefault();
                return;
            }*/

        } else {

            if (toState.module != 'non-logged') {
                event.preventDefault();
                $state.go("main");
            }

        }

    });

});
