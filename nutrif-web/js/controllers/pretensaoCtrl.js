angular.module('NutrifApp').controller("pretensaoCtrl", function ($scope, $cookies, diaRefeicaoService) {

	$scope.refeicoes = [];

	var carregarDiaRefeicaoAluno = function (matricula) {
		diaRefeicaoService.listaRefeicaoPorMatricula(matricula).success(function (data, status) {
			$scope.refeicoes = data;
		}).error(function (data, status){
			if (!data) {
				alert("Erro ao carregar as refeições desse aluno, tente novamente ou contate os administradores.");
			} else {
				alert(data.mensagem);
			}
		});
	}

	carregarDiaRefeicaoAluno($cookies.getObject('user').matricula);

});
