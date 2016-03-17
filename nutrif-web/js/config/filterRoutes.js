angular.module('NutrifApp').run(function($rootScope, $state, $cookies) {
    // Register listener to watch route changes
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {

        var _user = $cookies.getObject("user");

        if (toState.name != "main" && toState.name != "equipe") {
            if (!_user) {
                event.preventDefault();
                $state.go("main");
            }
        } else {
            if (toState.name === "main" && _user) {
                event.preventDefault();
                $state.go("home");
            }
        }

    });

});
