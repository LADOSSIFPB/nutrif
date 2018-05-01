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

    var _buscarDiaRefeicaoPorNome = function (nome) {
        return $http.get(_path + "/aluno/nome/" + encodeURI(nome) + "/entrada")
    };

    var _buscarDiaRefeicaoPorMatricula = function (numero) {
        return $http.get(_path + "/matricula/numero/" + encodeURI(numero) + "/entrada")
    };
    
    var _buscarPorId = function (id){
		return $http.get(_path + "/" + encodeURI(id))
	};
    
    return {
        cadastrar: _cadastrar,
        atualizar: _atualizar,
        listar: _listar,
        remover: _remover,
        listarVigentesPorMatricula: _listarVigentesPorMatricula,
        buscarDiaRefeicaoPorNome: _buscarDiaRefeicaoPorNome,
        buscarDiaRefeicaoPorMatricula: _buscarDiaRefeicaoPorMatricula,
        buscarPorId: _buscarPorId
    };
});