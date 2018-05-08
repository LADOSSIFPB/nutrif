/*
 *  Controlar ações do menu.
 */
nutrIFApp.controller('SideNavToolbarCtrl', function ($scope, $timeout, $rootScope, $state, $location, $mdSidenav, userService, pessoaService) {

    var vm = this;
    
    vm.user = {};
    
    function carregamentoInicial() {
        
        let user = userService.getUser();
        let _idUser = user.id;
        
        // Carregar dados do Usuário logado.
        pessoaService.getById(_idUser)
            .then(function(response) {
                vm.user = response.data;
            })
            .catch(function (error) {
                toastUtil.showErrorToast(error);
            });
    }

    // Inicializar listagem dos campi e níveis.
    carregamentoInicial();   
});