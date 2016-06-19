angular.module("NutrifApp").controller("listarPretensaoCtrl", function ($scope, pretensaoService, diaRefeicaoService, userService, $mdToast, $mdDialog) {

    $scope.refeicoes = [];
    $scope.refeicaoSelecionada = {};
    $scope.pretensao = {};

    $scope.solicitarRefeicao = function (refeicao) {
        pretensaoService.verifyDiaRefeicao(refeicao)
            .success(function (data, status) {
                $mdDialog.show({
                    controller: confirmarPretensaoCtrl,
                    templateUrl: 'view/manager/modals/modal-confirmar-pretensao.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose:true,
                    fullscreen: false,
                    locals : {
                        pretensao: data
                    }
                }).then(function(code) {
                    $mdDialog.show({
                        controller: generateQrCtrl,
                        templateUrl: 'view/manager/modals/modal-show-qrcode.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose:true,
                        fullscreen: false,
                        locals : {
                            code: code
                        }
                    }).then(function(data) {

                    }, function() {})
                }, function() {});
            })
            .error(onErrorCallback);
    }

    var carregarDiaRefeicaoAluno = function () {

        var _matricula = userService.getUser().matricula;

        diaRefeicaoService.listaRefeicaoPorMatricula(_matricula)
            .success(function (data, status) {
                $scope.refeicoes = data;
            })
            .error(onErrorCallback);
    }

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Houve um problema, favor chamar administradores.'
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


    carregarDiaRefeicaoAluno();

});

function confirmarPretensaoCtrl (pretensao, $scope, $mdDialog, $mdToast, pretensaoService) {

    $scope.pretensao = pretensao;
    $scope.refeicao = pretensao.confirmaPretensaoDia.diaRefeicao;

    $scope.hide = function() {
        if (typeof($scope.pretensao.keyAccess) != "undefined")
            $mdDialog.hide($scope.pretensao);
        else {
            pretensaoService.insertPretensao($scope.pretensao)
                .success(onSuccessCallback)
                .error(onErrorCallback);
        }
    };

    function onSuccessCallback (data, status) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Refeição solicitada com sucesso')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        $mdDialog.hide(data);
    }

    function onErrorCallback (data, status){
        var _message = '';
        if (!data) {
            _message = 'Erro no servidor, por favor chamar administração ou suporte.'
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

};

function generateQrCtrl (code, $scope, $mdDialog) {

    $scope.code = code.keyAccess;

    $scope.hide = function() {
        $mdDialog.hide();
    };

    $scope.cancel = function() {
        $mdDialog.cancel();
    };

};
