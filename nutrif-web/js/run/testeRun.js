/*
 *  Analisa as mudanças de rota e verificar os papeis de permissão de acesso
 *  para cada usuário.
 */
nutrifApp.run(function ($transitions, $rootScope, userService) {

    var nonLogged = 'non-logged';
    var admin = 'admin';
    var aluno = 'aluno';
    var inspetor = 'inspetor';
    var admins = [admin, inspetor];
    
    /**
     * @description determine if an array contains one or more items from another array.
     * @param {array} haystack the array to search.
     * @param {array} arr the array providing items to check for in the haystack.
     * @return {boolean} true|false if haystack contains at least one item from arr.
     */
    var findOne = function (haystack, arr) {
        return arr.some(function (v) {
            return haystack.indexOf(v) >= 0;
        });
    };
    
    var findWord = function (words, word) {
        return words.indexOf(word) >= 0;
    };
    
    $transitions.onStart({}, function (t, n, i) {

        var permissions = t.to().permissions;
        console.log('In start > permissions: ' + permissions);

        var state = t.router.stateService;
        var isLogged = userService.isLoggedIn();

        if (!isLogged || (isLogged && !userService.hasRoles(permissions))) {

            // Verificar a página solicitada e encaminhar para login.
            console.log('Usuário não está logado ou não possui permissão.');
            if (!findWord(permissions, nonLogged)) {                
                
                // Admin e inspetor
                if (findOne(permissions, admins)) {
                    return state.target('login.gerenciamento');
                }
                
                // Aluno                
                if (findWord(permissions, aluno)) {
                    return state.target('login.verificar-aluno');    
                }                
            }   
        }

        return true;
    });
    $transitions.onFinish({}, () => {
        console.log('In finish')
    });
});