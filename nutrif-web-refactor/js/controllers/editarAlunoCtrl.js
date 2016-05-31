angular.module('NutrifApp').controller('editarAlunoCtrl', function ($scope, alunoService, diaRefeicaoService, cursoService, $stateParams, $state, $mdToast) {

    $scope.selected = [];
    $scope.cursos = [];
    $scope.refeicoes = [];
    $scope.alunoCopy = {};

    $scope.atualizar = function (aluno) {
        alunoService.atualizarAluno(aluno)
            .success(function (data, status) {
                $scope.aluno = data;
                $scope.alunoCopy = angular.copy($scope.aluno);
                $state.transitionTo('home.editar-aluno', {matricula: aluno.matricula}, {reload: true});
                $mdToast.show(
                    $mdToast.simple()
                    .textContent('Aluno atualizado com sucesso!')
                    .position('top right')
                    .action('OK')
                    .hideDelay(6000)
                );
            })
            .error(onErrorCallback);
    }

    $scope.apagarAlunos = function (selected) {
        if (selected.length === 0) {
            $mdToast.show(
                $mdToast.simple()
                .textContent('Antes de apagar, selecione alguma refeição')
                .position('top right')
                .action('OK')
                .hideDelay(6000)
            );
        } else {
            var _length = selected.length;
            for (var i = 0; i < _length; i++) {
                diaRefeicaoService.removerRefeicao(selected[i])
                    .success(function functionName() {
                        $scope.refeicoes.splice($scope.refeicoes.indexOf(selected[i]));
                    })
                    .error(function (data, status) {

                        $mdToast.show(
                            $mdToast.simple()
                            .textContent('Erro ao apagar uma refeição')
                            .position('top right')
                            .action('OK')
                            .hideDelay(6000)
                        );
                    });
            }
        }
    }

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
            .error(onErrorLoadCallback);

        diaRefeicaoService.listaRefeicaoPorMatricula(_matricula)
            .success(function (data, status) {
                $scope.refeicoes = data;
            })
            .error(onErrorLoadCallback);

        cursoService.listarCursos()
            .success(function (data, status){
                $scope.cursos = data;
            })
            .error(onErrorLoadCallback);
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

    function onErrorLoadCallback(data, status) {
        onErrorCallback(data, status);
        $state.transitionTo('home.listar-alunos', {reload: true});
    }

    carregamentoInicial();

});
