/*
 *  Mapeamento dos serviço de ExtratoRefeico.
 */
nutrIFApp.factory("extratoRefeicaoService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/extratorefeicao";    
    
    var _listar = function () {
        return $http.get(_path);
    };

    var _getById = function (id) {
        return $http.get(_path + "/" + id);
    };
    
    var _buscarByPeriodo = function (dataInicial, dataFinal) {
        return $http.get(_path + "/inicio/" + dataInicial + "/fim/" + dataFinal);
    };   
    
    return {
        listar: _listar,
        getById: _getById,
        buscarByPeriodo: _buscarByPeriodo
    };
});