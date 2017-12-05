angular.module('NutrifApp').controller('editarAlunoCtrl', function ($scope,
    $stateParams, $state, $mdToast, $mdDialog,
    alunoService, diaRefeicaoService, cursoService, editalService, campusService, turnoService, turmaService, periodoService) {

    $scope.selected = [];
    $scope.cursos = [];
    $scope.editais = [];
    $scope.refeicoes = [];
    $scope.diaRefeicaoHistorico = [];
    $scope.turmas = [];
    $scope.turnos = [];
    $scope.periodos = [];

    var _campi = $scope.campi;

    $scope.alunoCopy = {};

    $scope.atualizarBasico = function (aluno) {
        alunoService.atualizarBasico(aluno)
            .success(function (data, status) {
                $scope.aluno = data;
                $scope.alunoCopy = angular.copy($scope.aluno);
                $state.transitionTo('home.editar-aluno', {
                    matricula: aluno.matricula
                }, {
                    reload: true
                });
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

    $scope.atualizarAcesso = function (aluno) {

        // Adicionar Acesso do Aluno.
        if (aluno.senha == aluno.resenha) {

            delete aluno.resenha;

            alunoService.atualizarAcesso(aluno)
                .success(function (data, status) {})
                .error(onErrorCallback);

        } else {

            var data = {};
            data.mensagem = "A senha de confirmação não está correta.";
            onErrorCallback(data);
        }
    }

    $scope.adicionarRefeicao = function () {
        $mdDialog.show({
            controller: adicionarRefeicaoCtrl,
            templateUrl: 'view/manager/modals/modal-confirmar-refeicao.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true,
            fullscreen: false,
            locals: {
                aluno: $scope.aluno
            }
        }).then(function () {}, function () {});
    }

    $scope.removerRefeicao = function (selected) {
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
                        if (i >= _length) {
                            $state.transitionTo($state.current, $stateParams, {
                                reload: true,
                                inherit: false,
                                notify: true
                            });
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

        if (_matricula == '') {
            $state.transitionTo('home.listar-alunos', {
                reload: true
            });
        }

        alunoService.buscaAlunoPorMatricula(_matricula)
            .success(function (data, status) {
                $scope.aluno = data;
                $scope.alunoCopy = angular.copy($scope.aluno);
            })
            .error(onErrorLoadCallback);

        diaRefeicaoService.getAllVigentesByAlunoMatricula(_matricula)
            .success(function (data, status) {
                $scope.refeicoes = data;
            })
            .error(onErrorLoadCallback);

        diaRefeicaoService.listarHistoricoRefeicaoPorMatricula(_matricula)
            .success(function (data, status) {
                $scope.diaRefeicaoHistorico = data;
                //Agrupar todos de um mesmo edital e criar propriedade side.
            })
            .error(onErrorLoadCallback);

        cursoService.listarCursos()
            .success(function (data, status) {
                $scope.cursos = data;
            })
            .error(onErrorLoadCallback);

        campusService.listarCampi()
            .success(function (data, status) {
                $scope.campi = data;
            })
            .error(onErrorCallback);

        turmaService.listarTurma()
            .success(function (data, status) {
                $scope.turmas = data;
            })
            .error(onErrorCallback);

        turnoService.listarTurnos()
            .success(function (data, status) {
                $scope.turnos = data;
            })
            .error(onErrorCallback);

        periodoService.listarPeriodo()
            .success(function (data, status) {
                $scope.periodos = data;
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
    }

    function onErrorLoadCallback(data, status) {
        onErrorCallback(data, status);
        $state.transitionTo('home.listar-alunos', {
            reload: true
        });
    }

    carregamentoInicial();

    $scope.perfilAluno = function () {
        $state.transitionTo('home.perfil-aluno', {
            matricula: $scope.aluno.matricula
        }, {
            reload: true
        });
    }

})

// Controller Adicionar Refeição.
function adicionarRefeicaoCtrl($scope, $mdDialog, $mdToast, $state, $stateParams, userService,
    editalService, diaRefeicaoService, refeicaoService, diaService, aluno) {

    $scope.editais = [];
    $scope.refeicoes = [];
    $scope.dias = [];
    $scope.selectedDias = [];
    $scope.aluno = aluno;

    $scope.adicionarDiaRefeicao = function (diaRefeicao) {

        // Dia de refeição.
        diaRefeicao.funcionario = {};
        diaRefeicao.funcionario.id = userService.getUser().id;
        diaRefeicao.aluno = {};
        diaRefeicao.aluno.id = $scope.aluno.id;

        for (var selectedDia of $scope.selectedDias) {
            // Cópia do dia de refeição.    
            var _diaRefeicao = angular.copy(diaRefeicao);
            // Dia
            _diaRefeicao.dia = {};
            _diaRefeicao.dia.id = angular.copy(selectedDia.id);
            // Serviço para adicionar dia de refeição para o aluno.   
            diaRefeicaoService.cadastrarRefeicao(_diaRefeicao)
                .success(onSuccessCallback)
                .error(onErrorCallback);
        }
    };

    $scope.toggle = function (item, list) {
        var idx = list.indexOf(item);
        if (idx > -1) {
            list.splice(idx, 1);
        } else {
            list.push(item);
        }
    };

    $scope.exists = function (item, list) {
        return list.indexOf(item) > -1;
    };

    function onSuccessCallback(data, status) {
        // Toast de sucesso.
        $mdToast.show(
            $mdToast.simple()
            .textContent('Refeição adicionada com sucesso')
            .position('top right')
            .action('OK')
            .hideDelay(6000)
        );

        // Fechar caixa de diálogo.
        $mdDialog.hide();

        // Recarregar a página.
        $state.transitionTo($state.current, $stateParams, {
            reload: true,
            inherit: false,
            notify: true
        });
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

    // Carregar itens do modal de inserção do Dia da Refeção.
    function carregamentoInicial() {

        editalService.listarEditalVigentes()
            .success(function (data, status) {
                $scope.editais = data;
            })
            .error(onErrorCallback);

        diaService.listarDias()
            .success(function (data, status) {
                $scope.dias = data;
            })
            .error(onErrorCallback);

        refeicaoService.listarRefeicoes()
            .success(function (data, status) {
                $scope.refeicoes = data;
            })
            .error(onErrorCallback);
    }

    carregamentoInicial();
};