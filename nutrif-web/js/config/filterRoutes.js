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

        if (_user) {

            if (toState.module === 'non-logged') {
                event.preventDefault();
                $state.go("entrada-aluno");
            }

            if (toState.module === 'admin' && !_user.roles.find(findRoleAdmin)) {
                console.log("bloqueado");
                event.preventDefault();
                $state.go("entrada-aluno");
            }

            /* Adiocionar para onde redirecionar, pois é o ultimo tipo de usuário
            if(toState.module === 'inspetor' && !_user.roles.find(findRoleInspetor)){
                event.preventDefault();
                return;
            }*/

        } else {

            if (toState.module != 'non-logged') {
                event.preventDefault();
                $state.go("login");
            }

        }

    });

});
