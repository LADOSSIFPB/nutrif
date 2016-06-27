angular.module('NutrifApp').run(function($rootScope, $state, userService) {

    // Register listener to watch route changes
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {

        var _user = userService.getUser();

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
                $state.go("home.entrada-alunos");
            }

            if (toState.module === 'admin' && !_user.roles.find(findRoleAdmin)) {
                event.preventDefault();
                $state.go("home.entrada-alunos");
            }

            if(toState.module === 'comensal' && !_user.roles.find(findRoleComensal)){
                event.preventDefault();
                $state.go("home.entrada-alunos");
            }

            if(toState.module != 'comensal' && _user.roles.find(findRoleComensal)){
                event.preventDefault();
                $state.go("pretensao.home");
            }

        } else {

            if (toState.module != 'non-logged') {
                event.preventDefault();
                $state.go("login.gerenciamento");
            }

        }

    });

});
