/*
 *  Controlar cookie do Usuário autenticado.
 */
nutrIFApp.factory('userService', function ($cookies) {

    var adminRoles = ['admin', 'atendente'];
    var otherRoles = ['visitante'];

    var roles = {
        guest: ['visitante'],
        member: ['gerente','atendente'],
        admin: ['administrador']
    }
    
    var _setUser = function (user) {
        $cookies.putObject("user", user);
    };

    var _getUser = function () {
        return $cookies.getObject("user");
    };

    var _isLoggedIn = function () {

        var isLogged = false;
        var user = _getUser();

        if (user != null || user != undefined) {
            isLogged = true;
        }

        return isLogged;
    }

    var _removeUser = function (user) {

        var isRemoved = false;

        if (_isLoggedIn()) {
            $cookies.remove("user");
            isRemoved = true;
        }

        return isRemoved;
    };

    var _validateRoleAdmin = function () {
        return _.contains(adminRoles, currentUser.role);
    };

    var validateRoleOther = function () {
        return _.contains(otherRoles, currentUser.role);
    };

    return {
        // Método públicos.
        setUser: _setUser,
        getUser: _getUser,
        isLoggedIn: _isLoggedIn,
        removeUser: _removeUser,
        validateRoleAdmin: _validateRoleAdmin,
        validateRoleOther: _validateRoleAdmin
    };
});