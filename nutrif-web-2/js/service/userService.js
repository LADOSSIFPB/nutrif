/*
 *  Controlar cookie do Usuário autenticado.
 */
nutrIFApp.factory('userService', function ($cookies) {
    
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

    return {
        // Método públicos.
        setUser: _setUser,
        getUser: _getUser,
        isLoggedIn: _isLoggedIn,
        removeUser: _removeUser
    };
});