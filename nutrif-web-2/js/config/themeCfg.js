/*
 *  Configuração do Tema para Angular Material.
 */
nutrIFApp.config(function($mdThemingProvider) {

    $mdThemingProvider.theme('default')
        .primaryPalette('blue-grey', {
            'default': '800',
            'hue-1': '600'})
        .accentPalette('lime')
        .warnPalette('red')
        .backgroundPalette('grey');

});