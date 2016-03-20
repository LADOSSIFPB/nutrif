angular.module("NutrifApp").value("config", {

	baseUrl: function() {

		var _externalDNS = "http://ladoss.com.br";
		var _externalIP = "http://200.129.68.181";
		var _internalIP = "http://192.168.2.245";
		var _port = "8773";
		var _context = "/NutrIF_Sevice";

		var url = location.href;

		if (url.indexOf(_externalDNS) >= 0 || url.indexOf(_externalIP) >= 0)
			return _externalDNS + ":" + _port + _context;
		else
			return _internalUrl + ":" + _port + _context;

	}

})
