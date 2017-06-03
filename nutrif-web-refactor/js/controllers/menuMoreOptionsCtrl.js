/*
 * Menu flutuante para opções de listagem
 */
nutrifApp.controller('menuMoreOptionsCtrl', function ($mdDialog) {

    var originatorEv;

    this.openMenu = function ($mdMenu, ev) {
        originatorEv = ev;
        $mdMenu.open(ev);
    };
});