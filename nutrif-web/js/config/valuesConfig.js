angular.module("NutrifApp").value("config", {

	baseUrl: function() {

		var _protocol = "http://"
		var _externalDNS = "ladoss.com.br";
		var _externalIP = "200.129.68.181";
		var _internalIP = "192.168.1.245";
		var _port = "8773";
		var _context = "/NutrIF_Sevice";

		var url = location.href;

		if (url.indexOf(_externalDNS) >= 0 || url.indexOf(_externalIP) >= 0)
			return _protocol + _externalDNS + ":" + _port + _context;
		else
			return _protocol + _internalIP + ":" + _port + _context;

	}

})
