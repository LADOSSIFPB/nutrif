/*
 *  Controlar ações do SideNav.
 */
nutrIFApp.controller("sideNavCtrl", function ($mdMedia, $mdSidenav, $state, $scope, userService) {

    this.isOpened = true;

    this.title = $state.current.title;

    this.logout = function () {
        userService.removeUser();
        $state.go("inicio.login");
    }

    this.openOrCloseSideNav = function () {
        console.log("Close menu!")
        if ($mdMedia('gt-md'))
            this.isOpened = !this.isOpened;
        else
            $mdSidenav('sideNav').toggle()
    }
});