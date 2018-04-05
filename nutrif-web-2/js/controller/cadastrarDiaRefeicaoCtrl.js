/*
 *  Controlar inserção do Dia da Refeição.
 */
nutrIFApp.controller('cadastrarDiaRefeicaoCtrl', function ($scope, $stateParams, $state, matriculaService, diaRefeicaoService, diaService, refeicaoService, editalService, userService, toastUtil, stringUtil) {

    $scope.diaRefeicao = {};

    $scope.dias = [];
    $scope.refeicoes = [];
    $scope.editais = [];

    $scope.selectedDias = [];

    $scope.adicionar = function () {

        // Dia de refeição.
        let diaRefeicao = {};

        // Funcionário
        diaRefeicao.funcionario = {};
        diaRefeicao.funcionario.id = userService.getUser().id;

        // Matrícula
        diaRefeicao.matricula = {};
        diaRefeicao.matricula.id = $scope.diaRefeicao.matricula.id;

        // Refeição
        let refeicao = {};
        refeicao.id = $scope.diaRefeicao.refeicao.id;
        diaRefeicao.refeicao = refeicao;

        // Edital
        let edital = {};
        edital.id = $scope.diaRefeicao.edital.id;
        diaRefeicao.edital = edital;

        for (var selectedDia of $scope.selectedDias) {

            // Cópia do dia de refeição.    
            let _diaRefeicao = angular.copy(diaRefeicao);

            // Dia
            _diaRefeicao.dia = {};
            _diaRefeicao.dia.id = angular.copy(selectedDia.id);

            // Serviço para adicionar dia de refeição para o aluno.   
            diaRefeicaoService.cadastrar(_diaRefeicao)
                .then(function (response) {
                    // Dia de Refeição
                    let diaRefeicaoResponse = response.data;

                    $state.transitionTo('administrador.aditar-matricula', {
                        id: diaRefeicaoResponse.matricula.id
                    }, {
                        reload: true,
                        inherit: false,
                        notify: true
                    });
                    // Mensagem
                    toastUtil.showSuccessToast('Dia(s) de Refeição cadastrado(s) com sucesso.');
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
        }

    }

    $scope.toggle = function (item, list) {
        var idx = list.indexOf(item);
        if (idx > -1) {
            list.splice(idx, 1);
        } else {
            list.push(item);
        }
    };

    $scope.exists = function (item, list) {
        return list.indexOf(item) > -1;
    };

    function carregamentoInicial() {

        // Matrícula
        var _idMatricula = $stateParams.id;

        if (stringUtil.isEmpty(_idMatricula)) {

            $state.transitionTo('administrador.listar-alunos', {
                reload: true
            });
        } else {

            matriculaService.getById(_idMatricula)
                .then(function (response) {
                    // Matrícula 
                    let matricula = response.data;
                    $scope.diaRefeicao.matricula = matricula;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });

            diaService.listar()
                .then(function (response) {
                    // Dias 
                    let dias = response.data;
                    $scope.dias = dias;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });

            refeicaoService.listar()
                .then(function (response) {
                    // Refeições do campus. 
                    let refeicoes = response.data;
                    $scope.refeicoes = refeicoes;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });

            editalService.listarVigentes()
                .then(function (response) {
                    // Refeições do campus. 
                    let editais = response.data;
                    $scope.editais = editais;
                })
                .catch(function (error) {
                    toastUtil.showErrorToast(error);
                });
        }
    }

    // Inicializar listagem de cursos e campi.
    carregamentoInicial();
});