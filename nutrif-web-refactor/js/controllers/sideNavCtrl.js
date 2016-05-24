angular.module("NutrifApp").controller("sideNavCtrl", function ($scope, $mdMedia, $mdSidenav, $state, $mdDialog) {

    this.isOpened = true;

    this.teste = [];

    this.teste2 = [{
        "nome": "José Renan Silva Luciano",
        "matricula": "20131004015",
        "refeicao": "Almoço",
        "dia": "Terça Feira"
    },
    {
        "nome": "Rhavy Maia Guedes",
        "matricula": "20131004016",
        "refeicao": "Almoço",
        "dia": "Terça Feira"
    },
    {
        "nome": "Marcelo José Siqueira Coutinho de Almeida",
        "matricula": "20131004017",
        "refeicao": "Almoço",
        "dia": "Terça Feira"
    },
    {
        "nome": "Juan Lira Barros e Barros",
        "matricula": "20131004018",
        "refeicao": "Almoço",
        "dia": "Terça Feira"
    },
    {
        "nome": "José Henrique da Silveira Lima",
        "matricula": "20131004019",
        "refeicao": "Almoço",
        "dia": "Terça Feira"
    },
    {
        "nome": "Thomas Eduardo da Silva",
        "matricula": "20131004020",
        "refeicao": "Almoço",
        "dia": "Terça Feira"
    }];

    this.title = $state.current.title;

    this.openOrCloseSideNav = function () {
        if($mdMedia('gt-md'))
        this.isOpened = !this.isOpened;
        else
        $mdSidenav('sideNav').toggle()
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
        console.log("foi")
        $mdDialog.hide();
    };

    $scope.cancel = function() {
        console.log("foi não")
        $mdDialog.cancel();
    };
}
