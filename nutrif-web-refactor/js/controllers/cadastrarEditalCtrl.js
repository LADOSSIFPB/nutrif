angular.module('NutrifApp').controller('cadastrarEditalCtrl', function ($scope, $mdToast, editalService, $state) {

    this.cadastrar = function (edital) {
        editalService.cadastrarEdital(edital)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    }

    function onSuccessCallback (data, status) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Aluno(a) cadastrado(a) com sucesso! Agora você pode adicionar refeições para ele(a).')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        $state.transitionTo('home.editar-aluno', {matricula: data.matricula}, {reload: true});
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
