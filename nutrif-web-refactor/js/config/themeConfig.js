angular.module('NutrifApp').config(function($mdThemingProvider) {

    $mdThemingProvider.theme('default')

        .primaryPalette('green', {
            'default': '800',
            'hue-1': '600'})
        .accentPalette('lime')
        .warnPalette('red')
        .backgroundPalette('grey');

});
