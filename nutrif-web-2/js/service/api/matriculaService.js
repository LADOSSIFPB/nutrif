/*
 *  Mapeamento dos serviço da Matrícula.
 */
nutrIFApp.factory("matriculaService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/matricula";

    var _cadastrar = function (matricula){
		return $http.post(_path, matricula)
	};
    
    var _atualizar = function (matricula){
		return $http.put(_path, matricula)
	};
    
    var _remover = function (id) {
        return $http.delete(_path + "/" + id);
    }
    
    var _listar = function () {
        return $http.get(_path);
    }
    
    var _getById = function (id) {
        return $http.get(_path + "/" + id);
    }
    
    var _getByAlunoId = function (id) {
        return $http.get(_path + "/aluno/" + id);
    }
    
    var _getVigentesByAlunoId = function (id) {
        return $http.get(_path + "/aluno/" + id + "/vigentes");
    }

    return {
        cadastrar: _cadastrar,
        atualizar: _atualizar,
        remover: _remover,
        listar: _listar,
        getById: _getById,
        getByAlunoId: _getByAlunoId,
        getVigentesByAlunoId: _getVigentesByAlunoId
    };
});