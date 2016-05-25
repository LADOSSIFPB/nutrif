angular.module('NutrifApp').controller('cadastrarAlunoCtrl', function ($scope, $mdToast, cursoService, alunoService) {

    $scope.cursos = [];

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

    function carregarCursos () {
        cursoService.listarCursos()
            .success(function (data, status){
                $scope.cursos = data;
            })
            .error(function (data, status){
                alert("Houve um erro ao carregar os cursos. Contate um administrador.");
            });
    }

    carregarCursos();

});
