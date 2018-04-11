/*
 *  Mapeamento dos serviço de Curso.
 */
nutrIFApp.factory("cursoService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/curso";    

    var _cadastrar = function (curso) {
        return $http.post(_path, curso);
    }
    
    var _listar = function (curso) {
        return $http.get(_path);
    };

    var _buscarPorNome = function (nome) {
        return $http.get(_path + "/nome/" + nome);
    };

    var _getById = function (id) {
        return $http.get(_path + "/" + id);
    };

    var _atualizar = function (curso) {
        return $http.put(_path, curso);
    };

    return {        
        cadastrar: _cadastrar,
        listar: _listar,
        buscarPorNome: _buscarPorNome,
        getById: _getById,
        atualizar: _atualizar
    };
});