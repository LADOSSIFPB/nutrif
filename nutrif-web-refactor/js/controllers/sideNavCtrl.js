angular.module("NutrifApp").controller("sideNavCtrl", function (userService, $mdMedia, $mdSidenav, $state) {

    this.isOpened = true;
    this.typeUser = userService.getUserRole();

    this.teste = [];
    this.teste2 = [];

    this.logoutManager = function () {
        userService.removeUser();
        $state.go("login.gerenciamento");
    }

    this.title = $state.current.title;

    this.openOrCloseSideNav = function () {
        if($mdMedia('gt-md'))
        this.isOpened = !this.isOpened;
        else
        $mdSidenav('sideNav').toggle()
    }
});
