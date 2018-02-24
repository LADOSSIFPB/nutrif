/*
 *  Controlar inserção da Refeição.
 */
nutrIFApp.controller('cadastrarRefeicaoCtrl', function ($scope, $state, toastUtil, refeicaoService) {

    // Refeição
    $scope.refeicao = {};

    // Enviar para o serviço de cadastro de refeição.
    $scope.adicionar = function () {

        let refeicao = $scope.refeicao;

        refeicao.horaInicio = Date.parse(refeicao.horaInicio);
        refeicao.horaFinal = Date.parse(refeicao.horaFinal);
        refeicao.horaPrevisaoPretensao = Date.parse(refeicao.horaPrevisaoPretensao);

        refeicaoService.cadastrar(refeicao)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Refeição cadastrada com sucesso.');

                // Redirecionamento            
                $state.transitionTo('administrador.listar-refeicoes', {
                    reload: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }
});