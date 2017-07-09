/*
 *  Funções de gerenciamento do Toast.
 */
nutrifApp.factory("toastUtil", function ($mdToast) {
    return {
        showToast: function (error) {
            var _message = "";

            if (!error.data) {
                _message = "Ocorreu um erro na comunicação com o servidor, favor chamar o suporte."
            } else {
                _message = error.data.mensagem
            }

            $mdToast.show(
                $mdToast.simple()
                .textContent(_message)
                .position('top right')
                .action('OK')
                .hideDelay(6000)
            );

            return false;
        },
        doSomethingElse: function () {
            //Do something else here
        }
    }
});