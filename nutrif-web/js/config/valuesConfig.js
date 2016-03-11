angular.module("NutrifApp").value("config", {

	baseUrl: function() {

		var _externalUrl = "http://ladoss.com.br";
		var _internalUrl = "http://127.0.0.1";
		var _port = "8080";
		var _context = "/NutrIF_Sevice";

		var url = location.href;

		if (url.indexOf(_externalUrl) >= 0)
			return _externalUrl + ":" + _port + _context;
		else
			return _internalUrl + ":" + _port + _context;

	}

})
