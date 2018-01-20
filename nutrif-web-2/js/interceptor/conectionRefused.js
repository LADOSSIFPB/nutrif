/**
 * Interceptador para requisições sem sucesso. Host sem alcance.
 */
nutrIFApp.config(function ($httpProvider) {
    $httpProvider.interceptors.push(function ($q, $injector) {
        return {
            responseError: function (rejection) {

                if (rejection.status <= 0) {

                    console.log("Conexão recusada");

                    var mdToast = $injector.get("$mdToast");

                    var toast = mdToast.simple()
                        .textContent('Não foi possível estabelecer conexão com o servidor.')
                        .position('top right')
                        .action('Ok')
                        .hideDelay(10000);

                    mdToast.show(toast);
                }
                return $q.reject(rejection);
            }
        };
    });
});