angular.module('NutrifApp').controller("pretensaoCtrl", function ($scope, $cookies, diaRefeicaoService, pretensaoService) {

	$scope.refeicoes = [];
	$scope.refeicaoSelecionada = {};

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

	$scope.abrirModalPretensao = function (refeicao) {
		
		$scope.refeicaoSelecionada = angular.copy(refeicao);

		delete refeicao.refeicao.horaInicio;
		delete refeicao.refeicao.horaFinal;
		delete refeicao.refeicao.horaPretensao;

		var _pretensao = new Object();
		_pretensao.confirmaPretensaoDia = new Object()
		_pretensao.confirmaPretensaoDia.diaRefeicao = angular.copy(refeicao); 
    	
		pretensaoService.verifyDiaRefeicao(_pretensao).success(function (data, status) {
			$('#confirmar-pretensao').openModal();
		}).error(function (data, status){
			if (!data) {
				Materialize.toast('Erro desconhecido<br/>Tente novamente ou informe um administrador.', 3000);
			} else {
				Materialize.toast('Erro: Solicite apenas refeições do dia seguinte.', 3000);
			}
		})
  	}

	carregarDiaRefeicaoAluno($cookies.getObject('user').matricula);

});
