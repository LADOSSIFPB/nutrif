angular.module('NutrifApp').controller("geralCtrl", function ($scope, $cookies, $state) {

    this.estaLogado = function () {
        if ($cookies.getObject('user')) {
            return true;
        } else {
            return false;
        }
    }

    this.logout = function () {
        $cookies.remove("user");
        $state.go("main");
    }

});
