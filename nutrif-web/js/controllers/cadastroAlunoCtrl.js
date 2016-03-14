angular.module("NutrifApp").controller("alunoCtrl", function ($scope, alunoService, refeicaoService, diaRefeicaoService, diaService, cursoService) {

   $scope.tiposRefeicoes = [];
   $scope.dias = []; 
   $scope.cursos = [];
   $scope.refeicoes = [];

   $scope.buscarAlunos = function (matricula) {

      alunoService.buscaAlunoPorMatricula(matricula).success(function (data, status) {

         $scope.aluno = data;
         carregarCursos();
         carregarTiposRefeicoes();
         carregarDias();
         carregarDiaRefeicaoAluno(matricula);

      }).error(function (data, status) {

         if (!data) {
               
            alert("Aluno não registrado, em caso de problemas, contate um administrador");
            
         }

      });

   }

   $scope.adicionarRefeicao = function (refeicao, aluno) {
   
      var newRefeicao = {
         refeicao: {id :refeicao.refeicao.id},
         dia: refeicao.dia,
         aluno: aluno
      };

      diaRefeicaoService.cadastrarRefeicao(newRefeicao).success(function (data, status) {

         Materialize.toast('Refeição agendada com sucesso', 6000);

      }).error(function (data, status) {

         if (!data) {
               
            alert("Erro ao adicionar a refeição, tente novamente ou contate os administradores.");
            
         } else {
               
            alert(data.message);
            
         }

      });

      delete $scope.refeicoes;
      carregarDiaRefeicaoAluno(aluno.matricula);
      delete $scope.refeicao;

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
      delete $scope.refeicoes;
   }

   var carregarDiaRefeicaoAluno = function (matricula) {
      
      diaRefeicaoService.listaRefeicaoPorMatricula(matricula).success(function (data, status) {
         
         $scope.refeicoes = data;
      
      }).error(function (data, status){

         if (!data) {
               
            alert("Erro ao carregar as refeições desse aluno, tente novamente ou contate os administradores.");
            
         } else {
               
            alert(data.message);
            
         }

      });
   }

   var carregarTiposRefeicoes = function (){
   		
      refeicaoService.listarRefeicoes().success(function (data, status){
   			
         $scope.tiposRefeicoes = data;
   		
      }).error(function (data, status){
   			
         if (!data) {
   			   
            alert("Erro ao carregar as refeições, tente novamente ou contate os administradores.");
   			
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
});
