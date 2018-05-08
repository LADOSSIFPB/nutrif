/*
 *  Controlar ações do menu.
 */
nutrIFApp.controller('SideNavMenuCtrl', function ($scope, $timeout, $rootScope, $state, $location, $mdSidenav, menu) {

    var vm = this;

    // Functions for menu-link and menu-toggle (Menu Expansivo)
    vm.isOpen = isOpen;
    vm.toggleOpen = toggleOpen;
    vm.autoFocusContent = false;
    vm.menu = menu;

    vm.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };

    function isOpen(section) {
        return menu.isSectionSelected(section);
    }

    function toggleOpen(section) {
        menu.toggleSelectSection(section);
    }

    // Controlar o Sidenav.
    $scope.close = function () {
        // Component lookup should always be available since we are not using `ng-if`
        $mdSidenav('left').close()
            .then(function () {
                console.log("close LEFT is done");
            });

    };
});