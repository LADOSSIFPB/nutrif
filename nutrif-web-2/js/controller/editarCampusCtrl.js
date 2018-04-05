/*
 *  Controlar atualização do Campus.
 */
nutrIFApp.controller('editarCampusCtrl', function ($scope,
    $stateParams, $state, toastUtil, dateTimeUtil, refeicaoService) {

    $scope.refeicao = {};

    $scope.atualizar = function (refeicao) {

        let refeicao = $scope.refeicao;

        refeicao.horaInicio = Date.parse(refeicao.horaInicio);
        refeicao.horaFinal = Date.parse(refeicao.horaFinal);
        refeicao.horaPrevisaoPretensao = Date.parse(refeicao.horaPrevisaoPretensao);

        refeicaoService.atualizar(refeicao)
            .then(function (response) {
                // Mensagem
                toastUtil.showSuccessToast('Refeição atualizada com sucesso.');

                // Redirecionamento
                $state.transitionTo('administrador.listar-refeicoes', {
                    reload: true
                });
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    /**
        Carregar os dados iniciais.
     */
    function carregar() {

        let id = $stateParams.id;

        if (id <= 0) {
            redirecionarListagem();
        } else {
            refeicaoService.getById(id)
                .then(function (response) {
                    // Refeição 
                    let refeicao = response.data;

                    // Hora de início e fim da apresentação
                    let horaInicio = dateTimeUtil.timeToDate(refeicao.horaInicio);
                    let horaFinal = dateTimeUtil.timeToDate(refeicao.horaFinal);
                    let horaPrevisaoPretensao = dateTimeUtil.timeToDate(refeicao.horaPrevisaoPretensao);

                    refeicao.horaInicio = horaInicio;
                    refeicao.horaFinal = horaFinal;
                    refeicao.horaPrevisaoPretensao = horaPrevisaoPretensao;
                    
                    // Refeição - ng-model do formulário.
                    $scope.refeicao = refeicao;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                    redirecionarListagem();
                });
        }
    }

    /**
        Redirecionar para a página de listagem.
     */
    function redirecionarListagem() {
        $state.transitionTo('administrador.listar-refeicoes', {
            reload: true
        });
    }

    carregar();
});