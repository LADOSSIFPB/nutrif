angular.module('NutrifApp').config(['$httpProvider', function ($httpProvider) {
			$httpProvider.interceptors.push('Interceptor');		
}]);