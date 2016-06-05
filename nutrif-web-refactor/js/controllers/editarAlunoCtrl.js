angular.module('NutrifApp').controller('editarAlunoCtrl', function ($scope, alunoService, diaRefeicaoService, cursoService, $stateParams, $state, $mdToast, $mdDialog) {

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

    $scope.adicionaRefeicao = function () {
        $mdDialog.show({
            controller: adicionarRefeicaoCtrl,
            templateUrl: 'view/manager/modals/modal-confirmar-refeicao.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals : {
                refeicoes: $scope.refeicoes,
                aluno: $scope.aluno
            }
        }).then(function() {}, function() {});
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
            var _length = angular.copy(selected.length);
            var _selected = angular.copy(selected);
            for (var i = 0; i < _length; i++) {
                diaRefeicaoService.removerRefeicao(selected[i])
                    .success(function functionName() {
                        if (i === _length - 1) {
                            $state.transitionTo('home.editar-aluno', {reload: true});
                        }
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

function adicionarRefeicaoCtrl (refeicoes, aluno, $state, $stateParams, userService, $scope, $mdDialog, $mdToast, diaRefeicaoService, refeicaoService, diaService) {

    $scope.tiposRefeicao = [];
    $scope.dias = [];
    $scope.refeicoes = refeicoes;
    $scope.aluno = aluno;

    $scope.hide = function(refeicao) {
        $mdDialog.hide();
        refeicao.funcionario = userService.getUser();
        refeicao.aluno = $scope.aluno;
        diaRefeicaoService.cadastrarRefeicao(refeicao)
            .success(onSuccessCallback)
            .error(onErrorCallback);
    };

    function onSuccessCallback (data, status) {
        $mdToast.show(
            $mdToast.simple()
            .textContent('Refeição adicionada com sucesso')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );
        atualizarState();
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

    function atualizarState() {
        $state.transitionTo($state.current, $stateParams, {
            reload: true,
            inherit: false,
            notify: true
        });
    }

    function carregamentoInicial() {

        diaService.listarDias()
            .success(function (data, status) {
                $scope.dias = data;
            })
            .error(onErrorCallback);

        refeicaoService.listarRefeicoes()
            .success(function (data, status) {
                $scope.tiposRefeicao = data;
            })
            .error(onErrorCallback);
    }

    carregamentoInicial();

};
