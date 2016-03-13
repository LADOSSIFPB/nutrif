angular.module("NutrifApp").controller("alunoCtrl", function ($scope, alunoService, refeicaoService, diaService, cursoService) {

   $scope.buscarAlunos = function (matricula) {

      alunoService.buscaAlunoPorMatricula(matricula).success(function (data, status) {

         $scope.aluno = data;

      }).error(function (data, status) {

         if (!data) {
               
            alert("Aluno não registrado, em caso de problemas, contate um administrador");
            
         }

      });

   }

   $scope.tiposRefeicoes = [];
   $scope.dias = []; 
   $scope.cursos = [];
   $scope.refeicoes = [];

   $scope.adicionarRefeicao = function (refeicao, aluno) {
      
      refeicao.aluno = {};
      refeicao.aluno = aluno;

      $scope.refeicoes.push(angular.copy(refeicao));

      delete refeicao.dia;
      delete refeicao.refeicao;
      delete refeicao.aluno;

   }

   $scope.removerRefeicao = function (refeicao) {
      var index = $scope.refeicoes.indexOf(refeicao);

      if (index > -1) {
         $scope.refeicoes.splice(index, 1);
      }

   }

   $scope.modalAdicionarRefeicao = function () {
      $('#adicionar-refeicao').openModal();  
   }

   $scope.pesquisarNovamente = function (){
      delete $scope.aluno;
      $scope.refeicoes = [];
   }

   var carregarRefeicoes = function (){
   		
      refeicaoService.listarRefeicoes().success(function (data, status){
   			
         $scope.tiposRefeicoes = data;
   		
      }).error(function (data, status){
   			
         if (!data) {
   			   
            alert("Erro no refeições, tente novamente ou contate os administradores.");
   			
         } else {
   				
            alert(data.message);
   			
         }
      });
   }

   var carregarDias = function (){

   	diaService.listarDias().success(function (data, status){
   			
         $scope.dias = data;
   		
      }).error(function (data, status){
   			
         if (!data){
   			
         	alert("Erro ao carregar dias, tente novamente ou contate os administradores.");
   			
         } else {
   			
           	alert(data.message);
   			
         }
   	});
   }

   var carregarCursos = function (){

      cursoService.listarCursos().success(function (data, status){

      	$scope.cursos = data;

      }).error(function (data, status){

      	if (!data){

            alert("Erro ao carregar cursos, tente novamente ou contate os administradores.");

         } else {

            alert(data.message);

         }
      });
   }

   carregarCursos();
   carregarRefeicoes();
   carregarDias();

});
