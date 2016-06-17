angular.module('NutrifApp').controller('listarFuncionariosCtrl', function ($scope, $mdToast, funcionarioService) {

 
    $scope.funcionarios = [];

    
    $scope.getAll = function (){
    	funcionarioService.getAll()
    	.success(onSuccessCallback)
		.error(onErrorCallback);
    };

    function onSuccessCallback(data, status) {
        $scope.funcionarios = data;
    }

    function onErrorCallback(data, status) {
        var _message = '';

        if (!data) {
            _message = 'Não foram encontrados funcionários com esses dados'
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

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };
    

});
