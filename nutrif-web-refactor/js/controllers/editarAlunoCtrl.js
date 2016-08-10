angular.module('NutrifApp').controller('editarAlunoCtrl', function ($scope, alunoService, diaRefeicaoService, cursoService, editalService, $stateParams, $state, $mdToast, $mdDialog) {

  $scope.selected = [];
  $scope.cursos = [];
  $scope.editais = [];
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

  $scope.perfilAluno= function(){
    $state.transitionTo('home.perfil-aluno', {matricula: $scope.aluno.matricula}, {reload: true});
  }

});

// Controller Adicionar Refeição.
function adicionarRefeicaoCtrl (refeicoes, aluno, $state, $stateParams, userService, editalService, $scope, $mdDialog, $mdToast, diaRefeicaoService, refeicaoService, diaService) {

  $scope.editais = [];
  $scope.tiposRefeicao = [];
  $scope.dias = [];
  $scope.refeicoes = refeicoes;
  $scope.aluno = aluno;

  $scope.hide = function(refeicao) {

    var encontrarRefeicao = function (element, index, array) {
      if (element.refeicao.id === parseInt(refeicao.refeicao.id)
        && element.dia.id === parseInt(refeicao.dia.id)) {
        return true;
      }
      return false;
    }

    if ($scope.refeicoes.findIndex(encontrarRefeicao) > -1) {
      $mdToast.show(
        $mdToast.simple()
        .textContent('Um aluno não pode possuir duas refeições iguais no mesmo dia')
        .position('top right')
        .action('OK')
        .hideDelay(6000)
      );
    } else {
      $mdDialog.hide();
      refeicao.funcionario = {};
      refeicao.funcionario.id = userService.getUser().id;
      refeicao.aluno = $scope.aluno;
      diaRefeicaoService.cadastrarRefeicao(refeicao)
      .success(onSuccessCallback)
      .error(onErrorCallback);
    }
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
      $scope.tiposRefeicao = data;
    })
    .error(onErrorCallback);
  }

  carregamentoInicial();
};
