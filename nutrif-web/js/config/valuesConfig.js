angular.module("NutrifApp").value("config", {

	baseUrl: function() {

		var _externalUrl = "http://ladoss.com.br";
		var _internalUrl = "http://127.0.0.1";
		var _port = "8096";
		var _context = "/NutrIF_Service";

		var url = location.href;

		if (url.indexOf(_externalUrl) >= 0)
			return _externalUrl + ":" + _port + _context;
		else
			return _internalUrl + ":" + _port + _context;

	}

})
