/**
 * Configuração da rota com ui-router.
 */
nutrIFApp.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

        // Rota padrão.
        $urlRouterProvider.otherwise("/inicio/login");

        // Estados        
        $stateProvider
            // Subrota - Inicio
            .state('inicio', {
                abstract: true,
                url: '/inicio',
                templateUrl: 'view/inicio.html'
            })

            // Login
            .state('inicio.login', {
                url: '/login',
                templateUrl: 'view/login.html',
                controller: 'loginCtrl',
                controllerAs: 'login'
            })

            // Subrota - Aluno
            .state('aluno', {
                abstract: true,
                data: {
                    label: 'Aluno'
                },
                url: '/aluno',
                templateUrl: 'view/aluno/aluno.html'
            })
    
            // Aluno - Home
            .state('aluno.home', {
                url: '/home',
                title: 'Aluno - Home',
                templateUrl: 'view/aluno/home.html',
                permissions: ['admin', 'aluno']
            })
    
            // Subrota - Inspetor
            .state('inspetor', {
                abstract: true,
                data: {
                    label: 'Inspetor'
                },
                url: '/inspetor',
                templateUrl: 'view/inspetor/aluno.html'
            })
    
            // Inspetor - Home
            .state('inspetor.home', {
                url: '/home',
                title: 'Inspetor - Home',
                templateUrl: 'view/inspetor/home.html',
                permissions: ['admin', 'inspetor']
            })

            // Subrota - Administrador
            .state('administrador', {
                abstract: true,
                controller: 'sideNavCtrl',
                controllerAs: 'sideNav',
                url: '/administrador',
                templateUrl: 'view/administrador/administrador.html'
            })

            /* Administrador - Home */
            .state('administrador.home', {
                url: '/home',
                title: 'Administrador - Home',
                templateUrl: 'view/administrador/home.html',
                permissions: ['admin']
            })

            /* Aluno */
            .state('administrador.adicionar-alunos', {
                url: '/adicionar/aluno',
                title: 'Adicionar Alunos',
                templateUrl: 'view/administrador/adicionar-alunos.html',
                controller: 'cadastrarAlunoCtrl',
                controllerAs: 'cadastrar',
                permissions: ['admin']
            })

            .state('administrador.listar-alunos', {
                url: '/listar/alunos',
                title: 'Listar Alunos',
                templateUrl: 'view/administrador/listar-alunos.html',
                controller: 'listarAlunosCtrl',
                controllerAs: 'listarAlunos',
                permissions: ['admin']
            })

            .state('administrador.editar-aluno', {
                url: '/editar/aluno/matricula/:matricula',
                title: 'Editar Aluno',
                templateUrl: 'view/administrador/editar-aluno.html',
                controller: 'editarAlunoCtrl',
                controllerAs: 'editar',
                permissions: ['admin']
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