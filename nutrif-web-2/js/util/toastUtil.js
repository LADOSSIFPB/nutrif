/*
 *  Funções de gerenciamento do Toast.
 */
nutrIFApp.factory("toastUtil", function ($mdToast) {
    return {
        showErrorToast: function (error) {
            var message = "";
            
            if (!error.data) {
                message = "Ocorreu um problema do NutrIF no Servidor, favor chamar o suporte."
            } else {
                message = error.data.message;
            }

            $mdToast.show(
                $mdToast.simple()
                .textContent(message)
                .position('top right')
                .action('OK')
                .hideDelay(6000)
            );

            return false;
        },
        showSuccessToast: function (message="") {

            $mdToast.show(
                $mdToast.simple()
                .textContent(message)
                .position('top right')
                .action('OK')
                .hideDelay(6000)
            );

            return true;
        },
        showToast: function (message="") {

            $mdToast.show(
                $mdToast.simple()
                .textContent(message)
                .position('top right')
                .action('OK')
                .hideDelay(6000)
            );

            return true;
        }
    }
});