angular.module('NutrifApp').config(['$httpProvider', function ($httpProvider) {
			// Adicionar interceptadores:
			$httpProvider.interceptors.push('interceptorResponseAutorization');
}]);
