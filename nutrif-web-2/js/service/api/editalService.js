/*
 *  Mapeamento dos serviço de Edital.
 */
nutrIFApp.factory("editalService", function($http, config){

	var _path = config.baseUrl() + "/edital";

	var _cadastrarEdital = function (edital){
		return $http.post(_path + "/inserir", edital)
	};

	var _listarEdital = function (){
		return $http.get(_path + "/listar/")
	};

	var _listarEditalVigentes = function (){
		return $http.get(_path + "/listar/vigentes")
	};
	
	var _buscarEditalPorNome = function (nome){
		return $http.get(_path + "/listar/nome/" + encodeURI(nome))
	};
	
	var _getEditalById = function (id){
		return $http.get(_path + "/id/" + encodeURI(id))
	};
	
	var _atualizarEdital = function (edital){
		return $http.post(_path + "/atualizar", edital)
	};
	
	var _removerEdital = function (id){
		return $http.get(_path + "/remover/id/"+ id)
	};

	return {
		cadastrarEdital: _cadastrarEdital,
		listarEditalVigentes: _listarEditalVigentes,
		listarEdital: _listarEdital,
		buscarEditalPorNome: _buscarEditalPorNome,
		getEditalById: _getEditalById,
		atualizarEdital: _atualizarEdital,
		removerEdital: _removerEdital
	};
});
