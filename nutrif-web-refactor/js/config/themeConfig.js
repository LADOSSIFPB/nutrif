angular.module('NutrifApp').config(function($mdThemingProvider) {

    $mdThemingProvider.theme('default')

        .primaryPalette('green', {
            'default': '800',
            'hue-1': '600'})
        .accentPalette('lime')
        .warnPalette('red')
        .backgroundPalette('grey');

});

angular.module('NutrifApp').config(function (ChartJsProvider) {
    ChartJsProvider.setOptions({ colors : ['#DCDCDC', '#46BFBD', '#FDB45C', '#4D5360'] });
});
