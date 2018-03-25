/*
 *  Controlar inserção do Aluno.
 */
nutrIFApp.controller('cadastrarAlunoCtrl', function ($scope, $state, alunoService, campusService, toastUtil) {

    $scope.aluno = {};
    $scope.campi = [];

    $scope.adicionar = function () {
        
        var cpf = CPF.strip($scope.aluno.cpf);
        $scope.aluno.cpf = cpf;
        
        alunoService.cadastrar($scope.aluno)
            .then(function (response) {
                $state.transitionTo('administrador.editar-aluno', {
                    id: response.data.id
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