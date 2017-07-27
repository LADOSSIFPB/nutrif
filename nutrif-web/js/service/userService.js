nutrifApp.factory("userService", function ($cookies) {
    
    var _storeUser = function (user) {
        $cookies.putObject("user", user);
    };

    var _getUser = function () {
        return $cookies.getObject("user");
    };
    
    var _removeUser = function (user) {
        $cookies.remove("user");
    };

    var _isLoggedIn = function () {
        
        var isLogged = false;
        var user = _getUser();
        
        if (user != null || user != undefined) {
            isLogged = true;
        }
        
        return isLogged;
    }
    
    var _hasRoles = function (pageRoles) {
        
        var user = _getUser();
        var userRoles = user.roles;
        
        var found = false;
        for (var i = 0, j = userRoles.length; !found && i < j; i++) {
            if (pageRoles.indexOf(userRoles[i].nome) > -1) {
                found = true;
            }
        }
        return found;
    };

    var _getUserRole = function () {

        var $ADMINISTRADOR = 1;
        var $INSPETOR = 2;
        var $COMENSAL = 3;

        function findRoleAdmin(role) {
            return role.id === $ADMINISTRADOR;
        }

        function findRoleInspetor(role) {
            return role.id === $INSPETOR;
        }

        function findRoleComensal(role) {
            return role.id === $COMENSAL;
        }

        var _user = _getUser();

        if (_user) {
            if (_user.roles.find(findRoleInspetor)) {
                return {
                    id: $ADMINISTRADOR,
                    nome: 'Administrador'
                }
            }

            if (_user.roles.find(findRoleInspetor)) {
                return {
                    id: $INSPETOR,
                    nome: 'Inspetor'
                }
            }

            if (_user.roles.find(findRoleComensal)) {
                return {
                    id: $COMENSAL,
                    nome: 'Aluno'
                }
            }
        }
    }

    return {
        getUserRole: _getUserRole,
        removeUser: _removeUser,
        getUser: _getUser,
        storeUser: _storeUser,
        hasRoles: _hasRoles,
        isLoggedIn: _isLoggedIn
    };
});