/*
 *  Configuração de conexão de serviço.
 */
nutrIFApp.value("serviceCfg", {

	baseUrl: function() {

		var _protocol = "//"
		var _hostAddress = "127.0.0.1";
		var _port = "3000";
		var _context = "";
        
		return _protocol + _hostAddress + ":" + _port + _context;
	}

})