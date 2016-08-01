angular.module("NutrifApp").value("config", {

	chartColors: ['#DE3641', '#4BAE4F'],

	baseUrl: function() {

		var _protocol = "http://"
		var _externalDNS = "localhost";
		var _externalIP = "localhost";
		var _internalIP = "localhost";
		var _port = "8080";
		var _context = "/NutrIF_Service";

		var url = location.href;

		if (url.indexOf(_externalDNS) >= 0 || url.indexOf(_externalIP) >= 0)
			return _protocol + _externalDNS + ":" + _port + _context;
		else
			return _protocol + _internalIP + ":" + _port + _context;
	}

})
