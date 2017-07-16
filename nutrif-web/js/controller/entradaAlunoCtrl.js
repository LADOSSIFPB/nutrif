/*
 *  Controlar entrada no Refeitório do Aluno.
 */
nutrifApp.controller('entradaAlunoCtrl', function ($scope, $mdDialog,
    $mdToast, toastUtil, userService, diaRefeicaoService) {

    var TAM_MINIMO_MATRICULA = 11;
    var TAM_MAXIMO_MATRICULA = 13;
    var TAM_MIN_BUSCA_NOME = 3;

    var mensagemToast;

    $scope.refeicoes = [];
    $scope.refeicaoSelecionada = [];

    $scope.pesquisarDiaReeficao = function (texto) {

        if (texto.length > TAM_MIN_BUSCA_NOME) {

            if (texto.match(/[a-zA-Z]/i) != null) {

                diaRefeicaoService.buscaRefeicaoPorNome(texto)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);

            } else if (texto.match(/^\d+$/) &&
                (texto.length >= TAM_MINIMO_MATRICULA) && (texto.length <= TAM_MAXIMO_MATRICULA)) {

                diaRefeicaoService.buscaRefeicaoPorMatricula(texto)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);
            }

        } else if (texto.length === 0) {
            $scope.refeicoes = [];
        }
    }

    function onSuccessCallback(response) {

        var status = response.status;

        if (status == 200) {
            $scope.refeicoes = response.data;
        }
    }

    function onErrorCallback(error) {

        // Limpar dia de refeição listados anteriormente.
        $scope.refeicoes = [];

        // Mensagem de erro.
        return toastUtil.showErrorToast(error);
    }

    $scope.limparNomeMatricula = function () {
        $scope.texto = '';
        $scope.refeicoes = [];
    }

    this.confirmDialog = function (refeicao) {
        $mdDialog.show({
            controller: DialogController,
            templateUrl: 'view/manager/modals/modal-confirmar-entrada.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            fullscreen: false,
            locals: {
                refeicao: refeicao
            }
        }).then(limparNomeMatricula, function () {
            $scope.refeicaoSelecionada = [];
        });
    }
});

function DialogController($scope, $mdDialog, $mdToast, refeicao,
    toastUtil, userService, refeicaoRealizadaService, pretensaoService, arquivoService) {

    $scope.refeicao = refeicao;

    var refeicaoRealizada = {
        confirmaRefeicaoDia: {},
        inspetor: {}
    };

    $scope.hide = function () {

        // Dados da refeição realizada.
        refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao = {};
        refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao.id = refeicao.id;
        refeicaoRealizada.inspetor.id = userService.getUser().id;

        // Esconder modal.
        $mdDialog.hide();

        // Serviço
        refeicaoRealizadaService.inserirRefeicao(refeicaoRealizada)
            .then(function (response) {
                // Mensagem
                var message = "Refeição realizada com sucesso";
                // Toast.
                toastUtil.showSuccessToast(message);
            })
            .catch(onErrorCallback);
    };

    function onErrorCallback(error) {
        // Mensagem de erro.
        return toastUtil.showErrorToast(error);
    }

    // Imagem do perfil do aluno.
    var getImage = function () {

        arquivoService.getPerfilById(refeicao.aluno.id)
            .then(function (response) {
                // Imagem do Perfil do aluno.
                $scope.image = response.data;
            })
            .catch(onErrorCallback);
    }

    var getPretensao = function () {

        pretensaoService.pretensaoRefeicaoByDiaRefeicao(refeicao.id)
            .then(function (response) {
                // Pretensão do aluno.
                $scope.pretensaoRefeicao = response.data;
            })
            .catch(onErrorCallback);
    }

    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    getImage();
    getPretensao();
}