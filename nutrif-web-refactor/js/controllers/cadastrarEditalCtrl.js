/**
 * Cadastro do Edital.
 */
angular.module('NutrifApp').controller('cadastrarEditalCtrl', function ($scope, $mdToast, editalService, userService, $state) {

    this.cadastrar = function (edital) {
		
		// Adicionar funcionário.
		edital.funcionario = {};
		edital.funcionario.id = userService.getUser().id;
		
		// Enviar para o serviço de cadastro de Edital.
		editalService.cadastrarEdital(edital)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    }

    function onSuccessCallback (data, status) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Edital(a) cadastrado(a) com sucesso!')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
    }

    function onErrorCallback (data, status) {
        var _message = '';

        if (!data) {
            _message = 'Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.'
        } else {
            _message = data.mensagem
        }

        $mdToast.show(
            $mdToast.simple()
            .textContent(_message)
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
    }
});
