nutrifApp.config(function ($routeProvider, $locationProvider) {    
    
    // Remover a exclamação (!) da URL
    var SEM_PREFIXO = '';
    $locationProvider.hashPrefix(SEM_PREFIXO);
    
    // Atualizar os módulos passados no app.js adicionando o 'ui.router'. 
    // Mover todas as rotas já definidas no router.js para o arquivo state.js.
    // Verificar o modelo utilizado para o $stateProvider presente no state.js que é diferente.
    // Não esquecer de importar no index o script state.js .
    // Mudar no index.html o atributo ng-view para o ui-view.    
});