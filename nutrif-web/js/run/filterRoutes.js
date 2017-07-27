/*
 *  Analisa as mudanças de rota e verificar os papeis de permissão de acesso
 *  para cada usuário.
 */
nutrifApp.run(function ($transitions, userService, userConst, arrayUtil) {

    // Perfis Administradores.
    var admins = [userConst.admin, userConst.inspetor];
        
    $transitions.onStart({}, function (t, n, i) {

        // Permissões da página acessada.
        var pagePermissions = t.to().permissions;
        
        // Usuário logado.
        var isLogged = userService.isLoggedIn();

        if (!isLogged || (isLogged && !userService.hasRoles(pagePermissions))) {

            // Verificar a página solicitada e encaminhar para login.
            if (!arrayUtil.findString(pagePermissions, userConst.nonLogged)) {                
                
                // Admin e inspetor
                if (arrayUtil.findOne(pagePermissions, admins)) {
                    return state.target('login.gerenciamento');
                }
                
                // Aluno                
                if (arrayUtil.findString(pagePermissions, userConst.aluno)) {
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