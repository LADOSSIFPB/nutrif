angular.module("NutrifApp").controller("entradaAlunoCtrl", function (userService, $mdDialog) {

    this.pesquisar = function (texto) {
        if(texto.length > 2) {
            if (texto.match(/[a-zA-Z]/i) != null) {
                //Pesquisa por nome
                console.log("Nome: " + texto);
            } else if (texto.length === 11) {
                //Pesquisa por Matrícula
                console.log("Matrícula: " + texto);
            }
        }
    }

    this.showAdvanced = function(aluno) {
        $mdDialog.show({
            controller: DialogController,
            templateUrl: 'view/manager/modals/modal-confirmar-entrada.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals : {
                aluno: aluno
            }
        }).then(function(answer) {
            console.log(aluno.nome + " foi selecionado");
        }, function() {
            console.log("foi não");
        });
    }
});

function DialogController($scope, $mdDialog, aluno) {

    $scope.aluno = aluno;

    $scope.hide = function() {
        $mdDialog.hide();
    };

    $scope.cancel = function() {
        $mdDialog.cancel();
    };
}
