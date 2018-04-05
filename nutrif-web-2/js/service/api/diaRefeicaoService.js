/*
 *  Mapeamento dos serviço de DiaRefeicao.
 */
nutrIFApp.factory("diaRefeicaoService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/diarefeicao";

    var _cadastrar = function (diaRefeicao) {
        return $http.post(_path, diaRefeicao)
    };
    
    var _atualizar = function(diaRefeicao){		
        return $http.put(_path, diaRefeicao)
	};
    
	var _listar = function(){		
        return $http.get(_path);
	};
    
    var _remover = function (idDiaRefeicao) {
        return $http.delete(_path + "/" + idDiaRefeicao)
    };   

    var _listarVigentesPorMatricula = function (numero) {
        return $http.get(_path + "/vigentes/matricula/numero/" + encodeURI(numero))
    };

    return {
        cadastrar: _cadastrar,
        atualizar: _atualizar,
        listar: _listar,
        remover: _remover,
        listarVigentesPorMatricula: _listarVigentesPorMatricula
    };
});