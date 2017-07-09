/*
 *  Converter valores booleanos (true ou false) para Sim ou Não nos DataTables.
 */
nutrifApp.filter('yesNo', function() {
    return function(input) {
        return input ? 'Sim' : 'Não';
    }
});