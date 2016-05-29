angular.module('NutrifApp').controller('editarAlunoCtrl', function ($scope, alunoService, diaRefeicaoService, $stateParams, $state, $mdToast) {

    $scope.selected = [];
    $scope.refeicoes = [];
    $scope.alunoCopy = {};

    function carregamentoInicial() {
        var _matricula = $stateParams.matricula;

        if (_matricula == ''){
            $state.transitionTo('home.listar-alunos', {reload: true});
        }
        alunoService.buscaAlunoPorMatricula(_matricula)
            .success(function (data, status) {
                $scope.aluno = data;
                $scope.alunoCopy = angular.copy($scope.aluno);
            })
            .error(onErrorCallback);

        diaRefeicaoService.listaRefeicaoPorMatricula(_matricula)
            .success(function (data, status) {
                $scope.refeicoes = data;
            })
            .error(onErrorCallback);
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

        $state.transitionTo('home.listar-alunos', {reload: true});
    }

    carregamentoInicial();

});
