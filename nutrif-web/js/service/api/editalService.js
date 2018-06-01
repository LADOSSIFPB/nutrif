/*
 *  Mapeamento dos serviço de Edital.
 */
nutrIFApp.factory("editalService", function($http, serviceCfg){

    var _path = serviceCfg.baseUrl() + "/edital";

	var _cadastrar = function (edital){
		return $http.post(_path, edital)
	};

	var _listar = function (){
		return $http.get(_path)
	};

	var _listarVigentes = function (){
		return $http.get(_path + "/vigentes")
	};
	
	var _buscarPorNome = function (nome){
		return $http.get(_path + "/nome/" + encodeURI(nome))
	};
    
    var _buscarVigentesPorNome = function (nome){
		return $http.get(_path + "/vigentes/nome/" + encodeURI(nome))
	};
	
	var _getById = function (id){
		return $http.get(_path + "/" + encodeURI(id))
	};
	
	var _atualizar = function (edital){
		return $http.put(_path, edital)
	};
	
	var _remover = function (id){
		return $http.delete(_path + "/" + id)
	};

	return {
		cadastrar: _cadastrar,
        listar: _listar,
        listarVigentes: _listarVigentes,
        buscarVigentesPorNome: _buscarVigentesPorNome,
        buscarPorNome: _buscarPorNome,
        getById: _getById,
        atualizar: _atualizar,
        remover: _remover
	};
});
