/**
 * Interceptador para Respostas (Response) HTTP: verificação da autorização do usuário.
 */
nutrIFApp.config(['$httpProvider', function ($httpProvider) {
    // Adicionar interceptadores:
    $httpProvider.interceptors.push(function ($q, $injector, userService) {
        return {
            
            response: function (response) {
                // Fluxo normal das respostas do servidor.
                return response || $q.when(response);
            },

            responseError: function (response) {

                // Verificar o retorno de erro da resposta para status: UNAUTHORIZED-401.
                var $ERRO_UNAUTHORIZED = 401;
                $status = response.status;

                if ($status === $ERRO_UNAUTHORIZED) {

                    // Remove dados do usuário do cookie.
                    userService.removeUser();

                    // Redireciona para o Login.
                    $injector.get('$state').go('inicio.login');
                }

                return $q.reject(response);
            }
        };
    });
}]);