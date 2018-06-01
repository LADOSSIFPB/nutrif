/*
 *  Controlar cookie do Usuário autenticado.
 */
nutrIFApp.factory('userService', function ($cookies) {

    let states = {
        admin: "administrador.home",
        inspetor: "inspetor.home",
        aluno: "aluno.home"
    };

    var _setUser = function (user) {
        $cookies.putObject("user", user);
    };

    var _getUser = function () {
        return $cookies.getObject("user");
    };

    var _getUserHome = function () {
        return states[_getUser().roles[0].nome];
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
        getUserHome: _getUserHome,
        isLoggedIn: _isLoggedIn,
        removeUser: _removeUser
    };
});
