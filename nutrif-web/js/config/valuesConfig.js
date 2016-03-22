angular.module("NutrifApp").value("config", {

	baseUrl: function() {

		var _protocol = "http://"
		var _externalDNS = "localhost";
		var _externalIP = "localhost";
		var _internalIP = "127.0.0.1";
		var _port = "8080";
		var _context = "/NutrIF_Sevice";

		var url = location.href;

		if (url.indexOf(_externalDNS) >= 0 || url.indexOf(_externalIP) >= 0)
			return _protocol + _externalDNS + ":" + _port + _context;
		else
			return _protocol + _internalIP + ":" + _port + _context;

	}

})
