angular.module('NutrifApp').controller("geralCtrl", function ($scope, $cookies, $state) {

    this.estaLogado = function () {
        if ($cookies.getObject('user')) {
            return true;
        } else {
            return false;
        }
    }

    this.getUser = function () {
        return $cookies.getObject('user');
    }

    this.getUserRole = function () {

        function findRoleAdmin(role) {
            return role.id === 1;
        }

        function findRoleInspetor(role) {
            return role.id === 2;
        }

        function findRoleComensal(role) {
            return role.id === 3;
        }

        if ($cookies.getObject('user').roles.find(findRoleAdmin)) {
            return "Administrador"
        }

        if ($cookies.getObject('user').roles.find(findRoleInspetor)) {
            return "Inspetor"
        }

        if ($cookies.getObject('user').roles.find(findRoleComensal)) {
            return "Aluno"
        }

    }

    this.logout = function () {
        
        var _state = "login";

        if (this.getUserRole() === 'Aluno')
            _state = "pretensao-home";

        $cookies.remove("user");
        $state.go(_state);
    }

});
