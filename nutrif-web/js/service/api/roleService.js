/*
 *  Mapeamento dos serviço de Funcionário.
 */
nutrIFApp.factory("roleService", function($http, serviceCfg) {

    var _path = serviceCfg.baseUrl() + "/role";

	var _listar = function(){
		return $http.get(_path);
	}

	return {
		listar: _listar
	};
});
