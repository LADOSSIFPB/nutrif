/*
 *  Controlar inserção do Aluno.
 */
nutrIFApp.controller('cadastrarAlunoCtrl', function ($scope, $mdToast, $state, alunoService, campusService, toastUtil) {

    $scope.aluno;
    $scope.campi = [];

    $scope.adicionar = function () {
        alunoService.cadastrarAluno($scope.aluno)
            .then(function (response) {
                $state.transitionTo('home.editar-aluno', {
                    matricula: response.data.matricula
                }, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
                // Mensagem
                toastUtil.showSuccessToast('Aluno(a) cadastrado(a) com sucesso. Inclua o Aluno(a) em um curso e informe sua matrícula.');
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
    
    function carregamentoInicial() {

        // Carregar Cursos para seleção no cadastro do Aluno.
        campusService.listar()
            .then(function(response) {
                $scope.campi = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    // Inicializar listagem de cursos e campi.
    carregamentoInicial();
});