angular.module('NutrifApp').controller('entradaAlunoCtrl', function ($scope, userService, diaRefeicaoService, $mdDialog) {

    $scope.refeicoes = [];
    $scope.refeicaoSelecionada = [];

    this.pesquisar = function (texto) {
        if(texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                diaRefeicaoService.buscaRefeicaoPorNome(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback);
            } else if (texto.length === 11) {
                diaRefeicaoService.buscaRefeicaoPorMatricula(texto)
                    .success(onSuccessCallback)
                    .error(onErrorCallback)
            }
        } else if (texto.length === 0) {
            $scope.refeicoes = [];
        }
    }

    function onSuccessCallback(data, status) {
        $scope.refeicoes = data;
    }

    function onErrorCallback(data, status) {
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

    $scope.limparBusca = limparBusca;

    function limparBusca () {
        $scope.texto = '';
        $scope.refeicoes = [];
    }

    this.confirmDialog = function(refeicao) {
        $mdDialog.show({
            controller: DialogController,
            templateUrl: 'view/manager/modals/modal-confirmar-entrada.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals : {
                refeicao: refeicao
            }
        }).then(limparBusca, function() {
            $scope.refeicaoSelecionada = [];
        });
    }
});

function DialogController($scope, $mdDialog, $mdToast, refeicao, refeicaoRealizadaService, userService) {

    $scope.refeicao = refeicao;

    var _refeicaoRealizada = {
        confirmaRefeicaoDia: {},
        inspetor: userService.getUser(),
    };

    _refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao = refeicao;

    $scope.hide = function() {
        $mdDialog.hide();
        refeicaoRealizadaService.inserirRefeicao(_refeicaoRealizada)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    };

    function onSuccessCallback (data, status) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Refeição realizada com sucesso')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
    }

    function onErrorCallback (data, status){
        var _message = '';
        if (!data) {
            _message = 'Erro ao registrar refeição, tente novamente ou contate os administradores.'
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

    $scope.cancel = function() {
        $mdDialog.cancel();
    };
}