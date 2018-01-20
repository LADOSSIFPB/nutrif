/*
 *  Mapeamento dos serviço de Login para Pessoa.
 */
nutrIFApp.factory("loginService", function ($http, config) {

    var _path = config.baseUrl() + "/pessoa";

    var _fazerLogin = function (pessoa) {
        return $http.post(_path + "/login", pessoa);
    };

    var _fazerLogout = function () {
        return $http.post(_path + "/logout");
    };

    return {
        fazerLogin: _fazerLogin,
        fazerLogout: _fazerLogout
    };
});