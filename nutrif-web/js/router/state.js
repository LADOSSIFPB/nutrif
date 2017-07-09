nutrifApp.config(function ($stateProvider, $urlRouterProvider) {

        // Rota padrão.
        $urlRouterProvider.otherwise("/login/gerenciamento");

        // Estados
        $stateProvider
            // Subrota - Login
            .state('login', {
                abstract: true,
                url: '/login',
                templateUrl: 'view/login.html'
            })

            // Login Gerência
            .state('login.gerenciamento', {
                url: '/gerenciamento',
                templateUrl: 'view/login-gerenciamento.html',
                controller: 'loginManagerCtrl',
                controllerAs: 'loginGerenciamento',
                module: 'non-logged'
            })

            // Login Aluno
            .state('login.aluno', {
                url: '/aluno/matricula/:matricula',
                templateUrl: 'view/login-aluno.html',
                controller: 'loginAlunoCtrl',
                controllerAs: 'loginAluno',
                module: 'non-logged'
            })

            // Verificação do Aluno.
            .state('login.verificar-aluno', {
                url: '/aluno/verificar',
                templateUrl: 'view/verificar-aluno.html',
                controller: 'verificarAlunoCtrl',
                controllerAs: 'verificarAluno',
                module: 'non-logged'
            })
    
            // Subrota - Aluno
            .state('aluno', {
                abstract: true,
                url: '/aluno',                
                templateUrl: 'view/aluno/aluno.html'
            })

            .state('aluno.atualizar', {
                url: '/atualizar/:matricula',
                templateUrl: 'view/aluno/atualizar-aluno.html',
                title: 'Atualizar dados do aluno',
                controller: 'atualizarAlunoCtrl',
                controllerAs: 'atualizarAluno',
                module: 'non-logged'
            })

    })
    //take all whitespace out of string
    .filter('nospace', function () {
        return function (value) {
            return (!value) ? '' : value.replace(/ /g, '');
        };
    })
    //replace uppercase to regular case
    .filter('humanizeDoc', function () {
        return function (doc) {
            if (!doc) return;
            if (doc.type === 'directive') {
                return doc.name.replace(/([A-Z])/g, function ($1) {
                    return '-' + $1.toLowerCase();
                });
            }

            return doc.label || doc.name;
        }
    });