angular.module("NutrifApp").factory("pretensaoService", function($http, config, userService){

    var _path = config.baseUrl() + "/pretensaorefeicao";

	var _verifyDiaRefeicao = function(refeicao){
		delete refeicao.refeicao.horaFinal;
        delete refeicao.refeicao.horaInicio;
        delete refeicao.refeicao.horaPretensao;
		var _pretensao = {confirmaPretensaoDia: {diaRefeicao: refeicao}};
		return $http.post(_path + "/diarefeicao/verificar", _pretensao);
	};

	var _insertPretensao = function(pretensao){
		delete pretensao.confirmaPretensaoDia.diaRefeicao.refeicao.horaFinal;
		delete pretensao.confirmaPretensaoDia.diaRefeicao.refeicao.horaInicio;
		delete pretensao.confirmaPretensaoDia.diaRefeicao.refeicao.horaPretensao;
		return $http.post(_path + "/inserir", pretensao);
	};

	var _verifyChaveAcesso = function(code){
		//TODO: var pretensao = {funcionario: userService.getUser(), keyAccess: code};
		var pretensao = {keyAccess: code};
		return $http.post(_path + "/chaveacesso/verificar", pretensao);
	};
	
	var _getQuantidadePretensao = function(diaRefeicao){
		
		return $http.post(_path + "/quantificar",diaRefeicao);
		
	}

    var _mapaRefeicao = function(peirodoPretensao){
		return $http.post(_path + "/mapa/consultar", peirodoPretensao);
	};

	return {
		verifyDiaRefeicao : _verifyDiaRefeicao,
		verifyChaveAcesso: _verifyChaveAcesso,
        mapaRefeicao: _mapaRefeicao,
		insertPretensao: _insertPretensao,
		getQuantidadePretensao:_getQuantidadePretensao
	};

});
