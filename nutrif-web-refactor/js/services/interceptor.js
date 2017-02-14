angular.module('NutrifApp').factory('Interceptor',function () {
		
	$inject = ['$q','$state'];

	function success(response) {
		return response;
	}

	function error(response) {
		if(response.status === 401) {
				$state.go('login.gerenciamento');
				return $q.reject(response);
		}
		else {
				return $q.reject(response);
		}
	}
	return function(promise) {
		return promise.then(success, error);
	}		
});