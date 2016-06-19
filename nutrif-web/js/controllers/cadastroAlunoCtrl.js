angular.module("NutrifApp").controller("cadastroAlunoCtrl", function ($scope, $state, alunoService, cursoService) {

  $scope.cursos = [];
    
  $scope.cadastrarAluno = function (newAluno) {

      alunoService.cadastrarAluno(newAluno).success(function (data, status) {
        
        $state.go("atualizar-aluno-form", {"matricula": data.matricula});
        Materialize.toast("Aluno cadastrado com sucesso<br/>Você agora pode adicionar refeições para ele.", 6000);
      
      }).error(function (data, status) {
        if (!data) {

            Materialize.toast("Erro ao cadastrar o aluno, tente novamente ou contate os administradores.", 6000);

         } else {

            Materialize.toast(data.mensagem, 6000);

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

});