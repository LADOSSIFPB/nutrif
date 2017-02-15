	angular.module('NutrifApp').factory('interceptorResponseAutorization', function ($q) {
	    return {

	        response: function (response) {
	            // Fluxo normal das respostas do servidor.
	            return response;
	        },
	        responseError: function (response) {

							// Verificar o retorno de erro da resposta para status: UNAUTHORIZED-401.
							var $ERRO_UNAUTHORIZED = 401;
							$status = response.status;

							if($status === $ERRO_UNAUTHORIZED){
									console.log("ResponseError: UNAUTHORIZED-401");
									// Redirecionar.
							}
	        }
	    };
	});
