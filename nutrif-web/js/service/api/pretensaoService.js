/*
 *  Mapeamento dos serviço de Pretensão.
 */
nutrIFApp.factory("pretensaoService", function ($http, serviceCfg){

	var _path = serviceCfg.baseUrl() + "/pretensaorefeicao";

    var _buscarQuantidade = function (diaRefeicao) {

        return $http.post(_path + "/quantificar", diaRefeicao);
    }

    return {
        buscarQuantidade: _buscarQuantidade
    };
});