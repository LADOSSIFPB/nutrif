/*
 *  Controlar inserção do Dia da Refeição.
 */
nutrIFApp.controller('listarEntradaRefeitorioCtrl', function ($scope, $stateParams, $state, diaRefeicaoService, toastUtil, stringUtil) {

    var TAM_MINIMO_MATRICULA = 11;
    var TAM_MAXIMO_MATRICULA = 13;
    var TAM_MIN_BUSCA_NOME = 3;

    $scope.diasRefeicao = [];

    $scope.nomeOuMatricula = "";

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

    $scope.limparBusca = function () {
        $scope.nomeOuMatricula = "";
        
        // Limpar dia de refeição listados anteriormente.
        $scope.diasRefeicao = [];
    };
});