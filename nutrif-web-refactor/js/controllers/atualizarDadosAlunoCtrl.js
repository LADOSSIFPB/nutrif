angular.module('NutrifApp').controller('atualizarDadosAlunoCtrl', function ($scope, $mdToast,
  $state, cursoService, alunoService, turnoService, turmaService, campusService,$stateParams) {
	
	$scope.aluno = {};
	$scope.turnos = [];
	$scope.turmas = [];
	
	this.atualizar = function (aluno) {
	      alunoService.atualizarCadastro(aluno)
	      .success(onSuccessCallback)
	      .error(onErrorCallback);
	    }
	
		function onSuccessCallback (data, status) {
		      $mdToast.show(
		        $mdToast.simple()
		        .textContent('Aluno(a) atualizado(a) com sucesso!')
		        .position('top right')
		        .action('OK')
		        .hideDelay(6000)
		      );
		      
		      $state.go("pretensao.listar-pretensao");
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
		
	   function carregamentoInicial(){
		   
		   var _matricula = $stateParams.matricula;
		   console.log(_matricula);

		   alunoService.buscaAlunoPorMatricula(_matricula)
           .success(function (data, status) {
        	   $scope.aluno = data;
        	   console.log(data);
           })
           .error(onErrorCallback);
		   
		  
		   turnoService.listarTurnos()
           .success(function (data, status) {
        	   $scope.turnos = data;
           })
           .error(onErrorCallback);
		   
		   turmaService.listarTurma()
           .success(function (data, status) {
        	   $scope.turmas = data;
           })
           .error(onErrorCallback);
		   
           
		   
           
	   }
	   
	   carregamentoInicial();
	    
	
});