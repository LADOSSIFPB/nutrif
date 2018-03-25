/*
 *  Funções de gerenciamento do Toast.
 */
nutrIFApp.factory("toastUtil", function ($mdToast) {
    return {
        
        showErrorToast: function (error) {
            
            let mensagem = "Ocorreu um problema do NutrIF no Servidor, favor chamar o suporte.";
            let codigo = 0;
            
            if (error.data) {
                mensagem = error.data.mensagem;
                codigo = error.data.codigo;
            }

            $mdToast.show(
                $mdToast.simple()
                .textContent(mensagem)
                .position('top right')
                .action('Ok')
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