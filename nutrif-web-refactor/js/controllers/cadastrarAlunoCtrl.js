angular.module('NutrifApp').controller('cadastrarAlunoCtrl', function ($scope, $mdToast,
  $state, cursoService, alunoService, campusService) {

    $scope.cursos = [];
    $scope.campi = [];

    this.cadastrar = function (aluno) {
      alunoService.cadastrarAluno(aluno)
      .success(onSuccessCallback)
      .error(onErrorCallback);
    }

    function onSuccessCallback (data, status) {
      $mdToast.show(
        $mdToast.simple()
        .textContent('Aluno(a) cadastrado(a) com sucesso! Agora você pode adicionar refeições para ele(a).')
        .position('top right')
        .action('OK')
        .hideDelay(6000)
      );

      $state.transitionTo('home.editar-aluno', {matricula: data.matricula}, {reload: true});
    }

    function onErrorCallback (data, status) {
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

    // Carregar Cursos para seleção no cadastro do Aluno.
    function carregarCursos () {
      cursoService.listarCursos()
      .success(function (data, status){
        $scope.cursos = data;
      })
      .error(function (data, status){
        alert("Houve um erro ao carregar os cursos. Contate um administrador.");
      });
    }

    // Carregar os Campi para seleção no cadastro do Aluno.
    function carregarCampi () {
      campusService.listarCampi()
      .success(function (data, status){
        $scope.campi = data;
      })
      .error(function (data, status){
        alert("Houve um problema ao carregar os Campus.");
      });
    }

    // Inicializar listagem de cursos e campi.
    carregarCursos();
    carregarCampi();
  });
