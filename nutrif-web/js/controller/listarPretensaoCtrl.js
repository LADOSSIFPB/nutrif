angular.module("NutrifApp").controller("listarPretensaoCtrl", function ($scope,
    $mdToast, $mdDialog, pretensaoService, diaRefeicaoService, userService,
    arquivoService) {

    $scope.refeicoes = [];
    $scope.refeicaoSelecionada = {};
    $scope.pretensao = {};
    $scope.image = "img/icon/user-shape.svg";

    $scope.solicitarRefeicao = function (diaRefeicaoSelecionado) {

        var pretensaoRefeicao = {};
        var confirmaPretensaoDia = {};
        var diaRefeicao = {};

        diaRefeicao.id = diaRefeicaoSelecionado.id;
        confirmaPretensaoDia.diaRefeicao = diaRefeicao;
        pretensaoRefeicao.confirmaPretensaoDia = confirmaPretensaoDia;

        pretensaoService.verifyDiaRefeicao(pretensaoRefeicao)
            .success(function (data, status) {
                $mdDialog.show({
                    controller: confirmarPretensaoCtrl,
                    templateUrl: 'view/manager/modals/modal-confirmar-pretensao.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true,
                    fullscreen: false,
                    locals: {
                        pretensao: data
                    }
                }).then(function (code) {
                    $mdDialog.show({
                        controller: generateQrCtrl,
                        templateUrl: 'view/manager/modals/modal-show-qrcode.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        fullscreen: false,
                        locals: {
                            code: code,
                            pretensao: data
                        }
                    }).then(function (data) {}, function () {})
                }, function () {});
            })
            .error(onErrorCallback);
    }

    var carregarDiaRefeicaoAluno = function () {

        var matricula = userService.getUser().matricula;

        diaRefeicaoService.getAllVigentesByAlunoMatricula(matricula)
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

    // Imagem do perfil do aluno.
    var getImage = function () {

        arquivoService.getPerfilById(userService.getUser().id)
            .success(function (data, status) {

                $scope.image = data;
            })
            .error(onErrorImageCallback);
    }

    getImage();

    function onErrorImageCallback(data, status) {
        var _message = '';
        if (!data) {
            _message = 'Erro ao carregar imagem, tente novamente ou contate os administradores.'
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

function confirmarPretensaoCtrl(pretensao, $scope, $mdDialog, $mdToast, pretensaoService) {

    $scope.pretensao = pretensao;
    $scope.refeicao = pretensao.confirmaPretensaoDia.diaRefeicao;

    $scope.hide = function () {
        if (typeof ($scope.pretensao.keyAccess) != "undefined")
            $mdDialog.hide($scope.pretensao);
        else {
            pretensaoService.insertPretensao($scope.pretensao)
                .success(onSuccessCallback)
                .error(onErrorCallback);
        }
    };

    function onSuccessCallback(data, status) {

        $mdToast.show(
            $mdToast.simple()
            .textContent('Refeição solicitada com sucesso')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        $mdDialog.hide(data);
    }

    function onErrorCallback(data, status) {
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

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
};

function generateQrCtrl(loginService, userService, pretensao, code, $scope, $mdDialog, $state) {

    $scope.code = code.keyAccess;
    $scope.user = userService.getUser();
    $scope.refeicao = pretensao.confirmaPretensaoDia.diaRefeicao.dia.nome;
    $scope.dataRefeicao = pretensao.confirmaPretensaoDia.dataPretensao;

    // Imprimir qr-code e finalizar acesso do aluno.
    $scope.imprimir = function () {

        // Abrir gerenciador de impressão do sistema operacional.
        window.print();

        // Retornar ao login.
        this.finalizar();
    };

    // Finalizar acesso do aluno retornando ao Login.
    $scope.finalizar = function () {

        loginService.fazerLogout()
            .success(function (data, status) {

                $mdDialog.cancel();
                // Remover dados do usuário do cookie.
                userService.removeUser();
                $state.go("login.pretensao");

            }).error(onErrorCallback);
    };
};