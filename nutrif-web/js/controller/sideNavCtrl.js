/*
 *  Controlar ações do SideNav.
 */
nutrifApp.controller("sideNavCtrl", function ($mdMedia, $mdSidenav, $state, $scope, toastUtil, userService, loginService) {

    this.isOpened = true;

    this.title = $state.current.title;

    // Usuário
    this.typeUser = userService.getUserRole();
    this.user = userService.getUser();

    this.logoutManager = function () {

        loginService.fazerLogout()
            .then(function (response) {
                // Remover Usuário Logado;
                userService.removeUser();
            
                // Redirecionar para a página de login.
                $state.go("login.gerenciamento");
            })
            .catch(function (error) {
                // Problema no serviço de logout.
                toastUtil.showErrorToast(error);
                
                // Remover Usuário Logado;
                userService.removeUser();

                // Redirecionar para a página de login.
                $state.go("login.gerenciamento");
            });
    }

    this.logoutAluno = function () {
        userService.removeUser();
        $state.go("login.pretensao");
    }

    this.openOrCloseSideNav = function () {
        if ($mdMedia('gt-md'))
            this.isOpened = !this.isOpened;
        else
            $mdSidenav('sideNav').toggle()
    }
});