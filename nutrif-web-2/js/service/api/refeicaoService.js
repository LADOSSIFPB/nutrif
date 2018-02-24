/*
 *  Mapeamento dos serviço de Refeição.
 */
nutrIFApp.factory("refeicaoService", function($http, serviceCfg){

    var _path = serviceCfg.baseUrl() + "/refeicao";

    var _cadastrar = function (refeicao){
		return $http.post(_path, refeicao)
	};
    
    var _atualizar = function(refeicao){
		return $http.put(_path, refeicao)
	};
    
	var _listar = function(){
		return $http.get(_path);
	};
	
	var _buscarPorTipo = function (tipo){
		return $http.get(_path + "/tipo/" +tipo)
	};
	
	var _getById = function(id){
		return $http.get(_path + "/" + id);
	};	

	return {
		cadastrar: _cadastrar,
        atualizar: _atualizar,
        listar: _listar,
        buscarPorTipo: _buscarPorTipo,
        getById: _getById       
	};
});
