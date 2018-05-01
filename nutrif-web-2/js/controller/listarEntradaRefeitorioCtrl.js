/*
 *  Controlar inserção do Dia da Refeição.
 */
nutrIFApp.controller('listarEntradaRefeitorioCtrl', function ($scope, $stateParams, $state, $mdDialog, diaRefeicaoService, toastUtil, stringUtil) {

    var TAM_MINIMO_MATRICULA = 11;
    var TAM_MAXIMO_MATRICULA = 13;
    var TAM_MIN_BUSCA_NOME = 3;

    $scope.diasRefeicao = [];

    $scope.nomeOuMatricula = "";

    $scope.selectedDiasRefeicao = [];

    $scope.pesquisar = function () {

        let nomeOuMatricula = $scope.nomeOuMatricula;

        if (nomeOuMatricula.length > TAM_MIN_BUSCA_NOME) {

            if (nomeOuMatricula.match(/[a-zA-Z]/i) != null) {
                // Buscar através do nome do Aluno.
                diaRefeicaoService.buscarDiaRefeicaoPorNome(nomeOuMatricula)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);

            } else if (nomeOuMatricula.match(/^\d+$/) &&
                (nomeOuMatricula.length >= TAM_MINIMO_MATRICULA) && (nomeOuMatricula.length <= TAM_MAXIMO_MATRICULA)) {
                // Buscar através do número da Matrícula.
                diaRefeicaoService.buscarDiaRefeicaoPorMatricula(nomeOuMatricula)
                    .then(onSuccessCallback)
                    .catch(onErrorCallback);
            }

        } else if (nomeOuMatricula.length == 0) {
            $scope.diasRefeicao = [];
        }
    }

    function onSuccessCallback(response) {
        // Dias de refeição encontrados.
        $scope.diasRefeicao = response.data;
    }

    function onErrorCallback(error) {

        // Limpar dia de refeição listados anteriormente.
        $scope.diasRefeicao = [];

        // Mensagem de Erro.
        toastUtil.showErrorToast(error);
    }

    // Adicionar Dia de Refeição.
    $scope.adicionarRefeicaoRealizada = function (diaRefeicao) {

        let dialog = {
            controller: 'cadastrarRefeicaoRealizadaCtrl',
            controllerAs: 'cadastrarRefeicaoRealizada',
            templateUrl: 'view/inspetor/modals/adicionar-refeicaorealizada.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            fullscreen: false,
            locals: {
                idDiaRefeicao: diaRefeicao.id
            }
        };

        $mdDialog.show(dialog)
            .then(function(response) {})
            .catch(function (error) {}) 
            .finally(limparBusca, function () {
                $scope.selectedDiasRefeicao = [];
            });
    }

    $scope.limparBusca = function () {
        $scope.nomeOuMatricula = "";

        // Limpar dia de refeição listados anteriormente.
        $scope.diasRefeicao = [];
    };

    let limparBusca = $scope.limparBusca;
});