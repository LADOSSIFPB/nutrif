/*
 *  Mapeamento dos serviço de Campus.
 */
nutrIFApp.factory("campusService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/campus";

    var _cadastrar = function (campus){
		return $http.post(_path, campus)
	};
    
    var _listar = function () {
        return $http.get(_path);
    }

    var _buscarPorCidade = function (cidade){
		return $http.get(_path + "/cidade/" + encodeURI(cidade))
	};
    
    var _getById = function (id) {
        return $http.get(_path + "/" + encodeURI(id));
    }

    return {
        cadastrar: _cadastrar,
        listar: _listar,
        getById: _getById,
        buscarPorCidade: _buscarPorCidade
    };
});