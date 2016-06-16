angular.module('NutrifApp').controller('cadastrarFuncionarioCtrl', function ($scope, $mdToast, funcionarioService, $state) {

    this.cadastrar = function (funcionario) {
        funcionarioService.cadastrarFuncionario(funcionario)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    }

    function onSuccessCallback (data, status) {
        $mdToast.show( 
            $mdToast.simple()
            .textContent('Funcionario(a) cadastrado(a) com sucesso!')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        $state.transitionTo('home');
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
