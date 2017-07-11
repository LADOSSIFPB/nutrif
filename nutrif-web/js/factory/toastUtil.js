/*
 *  Funções de gerenciamento do Toast.
 */
nutrifApp.factory("toastUtil", function ($mdToast) {
    return {
        showErrorToast: function (error) {
            var message = "";

            if (!error.data) {
                message = "Ocorreu um erro na splicitação ao servidor, favor chamar o suporte."
            } else {
                message = error.data.mensagem
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
        }
    }
});