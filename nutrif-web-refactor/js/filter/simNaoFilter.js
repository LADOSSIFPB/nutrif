nutrifApp.filter('yesNo', function() {
    return function(input) {
        return input ? 'Sim' : 'Não';
    }
});
