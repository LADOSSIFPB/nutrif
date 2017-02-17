	angular.module('NutrifApp').factory('interceptorResponseAutorization', function ($q,$injector,userService) {
	    return {

	       response: function (response) {
	            // Fluxo normal das respostas do servidor.
	                return response || $q.when(response);
	        },

			responseError: function (response) {
						
				// Verificar o retorno de erro da resposta para status: UNAUTHORIZED-401.
				var $ERRO_UNAUTHORIZED = 401;
				$status = response.status;

				if($status === $ERRO_UNAUTHORIZED){
					console.log("ResponseError: UNAUTHORIZED-401");
					// Redirecionar: falta configurar o retorno
					
					// Remove dados do usuário do cookie.
			        userService.removeUser();
					$injector.get('$state').go('login.gerenciamento');
					
					return;
				}

				return $q.reject(response);
	        }
	    };
	});
