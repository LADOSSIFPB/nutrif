angular.module("NutrifApp").value("config", {

	chartColors: ['#DE3641', '#4BAE4F'],

	baseUrl: function() {

		var _protocol = "//"
		var _externalDNS = "ladoss.com.br";
		var _externalIP = "ladoss.com.br";
		var _internalIP = "ladoss.com.br";
		var _port = "8773";
		var _context = "/NutrIF_Service";

		var url = location.href;

		if (url.indexOf(_externalDNS) >= 0 || url.indexOf(_externalIP) >= 0)
			return _protocol + _externalDNS + ":" + _port + _context;
		else
			return _protocol + _internalIP + ":" + _port + _context;
	}

})
