/*
 *  Mapeamento dos serviços para Pessoa.
 */
nutrIFApp.factory("pessoaService", function ($http, serviceCfg) {

    var _path = serviceCfg.baseUrl() + "/pessoa";

    var _login = function (pessoa) {
        return $http.post(_path + '/login', pessoa);
    };
    
    var _getById = function (id) {
        return $http.get(_path + "/" + encodeURI(id));
    };

    return {
        login: _login,
        getById: _getById
    };
});