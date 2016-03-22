angular.module('NutrifApp').controller("geralCtrl", function ($scope, $cookies, $state) {

    this.estaLogado = function () {
        if ($cookies.getObject('user')) {
            return true;
        } else {
            return false;
        }
    }

    this.getUserRole = function () {

        function findRoleAdmin(role) {
            return role.id === 1;
        }

        function findRoleInspetor(role) {
            return role.id === 2;
        }

        if ($cookies.getObject('user').roles.find(findRoleAdmin)) {
            return "Administrador"
        }

        if ($cookies.getObject('user').roles.find(findRoleInspetor)) {
            return "Inspetor"
        }

    }

    this.logout = function () {
        $cookies.remove("user");
        $state.go("main");
    }

});
