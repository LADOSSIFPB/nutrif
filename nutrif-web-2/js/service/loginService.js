/*
 *  Mapeamento dos serviços para Sala.
 */
nutrIFApp.factory("loginService", function ($http, serviceCfg) {

    var _path = serviceCfg.baseUrl();

    var _loginUsuario = function (usuario) {
        return $http.post(_path + '/login', usuario);
    };

    return {
        loginUsuario: _loginUsuario
    };
});