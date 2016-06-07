angular.module("NutrifApp").factory("pretensaoService", function($http, config){

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
		var pretensao = {keyAccess: code};
		return $http.post(_path + "/chaveacesso/verificar", pretensao);
	};

	return {
		verifyDiaRefeicao : _verifyDiaRefeicao,
		verifyChaveAcesso: _verifyChaveAcesso,
		insertPretensao: _insertPretensao
	};

});
