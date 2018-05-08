/*
 *  Controlar ações da listagem dos Campi.
 */
nutrIFApp.controller('listarCursosCtrl', function ($scope, $state, toastUtil, cursoService) {

    $scope.nome = "";
    $scope.cursos = [];

    $scope.limparBusca = function () {
        $scope.nome = "";
        $scope.cursos = [];
    };

    $scope.pesquisar = function (){
        
        let nome = $scope.nome;
        
        if(nome.length >= 3) {
            if (nome.match(/[a-zA-Z]/i) != null) {
            	
                cursoService.buscarPorNome(nome)
                    .then(function (response) {
                        $scope.cursos = response.data;
                    })
                    .catch(function (error) {
                        toastUtil.showErrorToast(error);
                    });
            }
        } else if (nome.length === 0) {
            $scope.campi = [];
        }
    };

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
});
