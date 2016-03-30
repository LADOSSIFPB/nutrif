angular.module("NutrifApp").controller("alunoCtrl", function ($scope, $stateParams, $state, alunoService, refeicaoService, diaRefeicaoService, diaService, cursoService) {

   $scope.tiposRefeicoes = [];
   $scope.dias = [];
   $scope.cursos = [];
   $scope.refeicoes = [];

   $scope.buscarAlunos = function (matricula) {
      $state.go("atualizar-aluno-form", {"matricula": matricula});
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

   $scope.modalCadastroAluno = function () {
      $('#cadastrar-aluno-form').openModal();
   };

   $scope.cadastrarAluno = function (newAluno) {

      $('#cadastrar-aluno-form').closeModal();

      alunoService.cadastrarAluno(newAluno).success(function (data, status) {
        $state.go("atualizar-aluno-form", {"matricula": data.matricula});
      }).error(function (data, status) {
        if (!data) {

            Materialize.toast("Erro ao cadastrar o aluno, tente novamente ou contate os administradores.", 6000);

         } else {

            Materialize.toast(data.mensagem, 6000);

         }
      });
   }

   $scope.modalRemoverRefeicao = function (refeicao) {
      $scope.refeicaoSelecionada = refeicao;
      $('#confirmar-remocao-refeicao').openModal();
   };

   $scope.removerRefeicao = function (refeicao) {

      $('#confirmar-remocao-refeicao').closeModal();
      delete refeicao.refeicao.horaInicio;
      delete refeicao.refeicao.horaFinal;

      diaRefeicaoService.removerRefeicao(refeicao).success(function(data, status){

          carregarDiaRefeicaoAluno(refeicao.aluno.matricula);
          Materialize.toast('Refeição removida com sucesso', 6000);

      }).error(function(data, status){

          if (!data) {

            Materialize.toast("Erro ao remover a refeição, tente novamente ou contate os administradores.", 6000);

         } else {

            Materialize.toast(data.mensagem, 6000);

         }

      });

   }

   $scope.modalAdicionarRefeicao = function () {
      $('#adicionar-refeicao').openModal();
   }

   $scope.pesquisarNovamente = function (){
      delete $scope.aluno;
      delete $scope.refeicoes;
      $state.go("atualizar-aluno");
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

   if (typeof $stateParams.matricula != "undefined") {

       alunoService.buscaAlunoPorMatricula($stateParams.matricula).success(function (data, status) {

          $scope.aluno = data;
          carregarTiposRefeicoes();
          carregarDias();
          carregarDiaRefeicaoAluno($stateParams.matricula);

       }).error(function (data, status) {

          if (!data) {

              Materialize.toast('Aluno não cadastrado,<br/>tente novamente ou contate<br/>um administrador', 3000);

          }

       });
   }

   carregarCursos();

});
