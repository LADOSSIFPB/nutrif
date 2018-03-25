/*
 *  Mapeamento dos serviço da Matrícula.
 */
nutrIFApp.factory("matriculaService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/matricula";

    var _cadastrar = function (campus){
		return $http.post(_path, campus)
	};
    
    var _listar = function () {
        return $http.get(_path);
    }
    
    var _getById = function (id) {
        return $http.get(_path + "/" + id);
    }
    
    var _getByAlunoId = function (id) {
        return $http.get(_path + "/aluno/" + id);
    }

    return {
        cadastrar: _cadastrar,
        listar: _listar,
        getById: _getById,
        getByAlunoId: _getByAlunoId
    };
});