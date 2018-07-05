  angular.module('NutrifApp').controller('entradaAlunoCtrl', function ($scope, $mdDialog,
      $mdToast, userService, diaRefeicaoService) {

      var TAM_MINIMO_MATRICULA = 11;
      var TAM_MAXIMO_MATRICULA = 13;
      var TAM_MIN_BUSCA_NOME = 3;

      var mensagemToast;

      $scope.refeicoes = [];
      $scope.refeicaoSelecionada = [];

      this.pesquisar = function (texto) {

          if (texto.length > TAM_MIN_BUSCA_NOME) {

              if (texto.match(/[a-zA-Z]/i) != null) {

                  diaRefeicaoService.buscaRefeicaoPorNome(texto)
                      .success(onSuccessCallback)
                      .error(onErrorCallback);

              } else if (texto.match(/^\d+$/) &&
                  (texto.length >= TAM_MINIMO_MATRICULA) && (texto.length <= TAM_MAXIMO_MATRICULA)) {

                  diaRefeicaoService.buscaRefeicaoPorMatricula(texto)
                      .success(onSuccessCallback)
                      .error(onErrorCallback)
              }

          } else if (texto.length === 0) {
              $scope.refeicoes = [];
          }
      }

      function onSuccessCallback(data, status) {
          if (status == 200) {
              $mdToast.hide(mensagemToast);
              $scope.refeicoes = data;
          }
      }

      function onErrorCallback(data, status) {

          // Limpar dia de refeição listados anteriormente.
          $scope.refeicoes = [];

          // Mensagem de erro.
          var _message = '';

          if (!data) {

              _message = 'Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.';

          } else {

              _message = data.mensagem;
          }

          mensagemToast = $mdToast.show(
              $mdToast.simple()
              .textContent(_message)
              .position('top right')
              .action('OK')
              .hideDelay(6000)
          );
      }

      $scope.limparBusca = limparBusca;

      function limparBusca() {
          $scope.texto = '';
          $scope.refeicoes = [];
      }

      this.confirmDialog = function (refeicao) {
          $mdDialog.show({
              controller: DialogController,
              templateUrl: 'view/manager/modals/modal-confirmar-entrada.html',
              parent: angular.element(document.body),
              clickOutsideToClose: true,
              fullscreen: false,
              locals: {
                  refeicao: refeicao
              }
          }).then(limparBusca, function () {
              $scope.refeicaoSelecionada = [];
          });
      }
  });

  function DialogController($scope, $mdDialog, $mdToast, refeicao,
      userService, refeicaoRealizadaService, pretensaoService, arquivoService) {

      $scope.refeicao = refeicao;

      var refeicaoRealizada = {
          confirmaRefeicaoDia: {},
          inspetor: {}
      };

      $scope.hide = function () {

          // CPF
          var cpf = CPF.strip(refeicao.aluno.cpf);
          
          // Dados da refeição realizada.
          refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao = {
              aluno: {
                  cpf: cpf
              }
          };
          refeicaoRealizada.confirmaRefeicaoDia.diaRefeicao.id = refeicao.id;
          refeicaoRealizada.inspetor.id = userService.getUser().id;         

          // Esconder modal.
          $mdDialog.hide();

          // Serviço
          refeicaoRealizadaService.inserirRefeicao(refeicaoRealizada)
              .success(onSuccessCallback)
              .error(onErrorCallback);
      };

      function onSuccessCallback(data, status) {
          $mdToast.show(
              $mdToast.simple()
              .textContent('Refeição realizada com sucesso')
              .position('top right')
              .action('OK')
              .hideDelay(6000)
          );
      }

      function onErrorCallback(data, status) {
          var _message = '';

          if (!data) {
              _message = 'Erro no servidor, por favor chamar administração ou suporte.';
          } else {
              _message = data.mensagem;
          }

          $mdToast.show(
              $mdToast.simple()
              .textContent(_message)
              .position('top right')
              .action('OK')
              .hideDelay(6000)
          );
      }

      // Imagem do perfil do aluno.
      var getImage = function () {

          arquivoService.getPerfilById(refeicao.aluno.id)
              .success(function (data, status) {

                  $scope.image = data;
              })
              .error(onErrorCallback);
      }

      var getPretensao = function () {

          pretensaoService.pretensaoRefeicaoByDiaRefeicao(refeicao.id)
              .success(function (data, status) {

                  $scope.pretensaoRefeicao = data;
              })
              .error(onErrorCallback);
      }

      $scope.cancel = function () {
          $mdDialog.cancel();
      };

      getImage();
      getPretensao();
  }