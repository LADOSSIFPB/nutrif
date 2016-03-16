angular.module("NutrifApp").controller("alunoCtrl", function ($scope, $stateParams, $state, alunoService, refeicaoService, diaRefeicaoService, diaService, cursoService) {

   $scope.tiposRefeicoes = [];
   $scope.dias = [];
   $scope.cursos = [];
   $scope.refeicoes = [];

   $scope.buscarAlunos = function (matricula) {
      $state.go("cadastro-aluno", {"matricula": matricula});
   }

   $scope.adicionarRefeicao = function (refeicao, aluno) {

      var encontrarRefeicao = function (element, index, array) {

          if (element.refeicao.id === refeicao.refeicao.id && element.dia.id === refeicao.dia.id) {

                  return true;
          }

          return false;

      }

      if ($scope.refeicoes.findIndex(encontrarRefeicao) > -1) {
          alert("Um aluno não pode fazer duas refeiões iguais no mesmo dia e esta refeição já foi cadastrada.");
          return;
      }

      var newRefeicao = {
         refeicao: {id :refeicao.refeicao.id},
         dia: refeicao.dia,
         aluno: aluno
      };

      diaRefeicaoService.cadastrarRefeicao(newRefeicao).success(function (data, status) {

         Materialize.toast('Refeição agendada com sucesso', 6000);
         $('#adicionar-refeicao').closeModal();

         delete $scope.refeicoes;
         delete refeicao.refeicao;
         delete refeicao.dia;

         carregarDiaRefeicaoAluno(aluno.matricula);

      }).error(function (data, status) {

         if (!data) {

            alert("Erro ao adicionar a refeição, tente novamente ou contate os administradores.");

         } else {

            alert(data.mensagem);

         }

      });

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
      $state.go("cadastro-aluno", {"matricula": ""});
   }

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

   var carregarTiposRefeicoes = function (){

      refeicaoService.listarRefeicoes().success(function (data, status){

         $scope.tiposRefeicoes = data;

      }).error(function (data, status){

         if (!data) {

            alert("Erro ao carregar as refeições, tente novamente ou contate os administradores.");

         } else {

            alert(data.mensagem);

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

   if (typeof $stateParams.matricula != "undefined"){

       alunoService.buscaAlunoPorMatricula($stateParams.matricula).success(function (data, status) {

          $scope.aluno = data;
          carregarCursos();
          carregarTiposRefeicoes();
          carregarDias();
          carregarDiaRefeicaoAluno($stateParams.matricula);

       }).error(function (data, status) {

          if (!data) {

             alert("Aluno não registrado, em caso de problemas, contate um administrador");

          }

       });
   }

});
