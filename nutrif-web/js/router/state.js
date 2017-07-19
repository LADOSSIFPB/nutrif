nutrifApp.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

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
                controllerAs: 'loginManager',
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

            // Subrota - Gerenciamento
            .state('home', {
                url: '/inicio',
                abstract: true,
                controller: 'sideNavCtrl',
                controllerAs: 'sideNav',
                templateUrl: 'view/manager/home.html'
            })

            /* Funcionário */
            .state('home.listar-funcionarios', {
                url: '/listar/funcionarios',
                title: 'Listar Funcionários',
                templateUrl: 'view/manager/admin/listar-funcionarios.html',
                controller: 'listarFuncionariosCtrl',
                controllerAs: 'listarFuncionarios',
                module: 'admin'
            })
            .state('home.adicionar-funcionarios', {
                url: '/adicionar/funcionario',
                title: 'Adicionar Funcionarios',
                templateUrl: 'view/manager/admin/adicionar-funcionarios.html',
                controller: 'cadastrarFuncionarioCtrl',
                controllerAs: 'cadastrarFuncionarios',
                module: 'admin'
            })
            .state('home.editar-funcionario', {
                url: '/editar/funcionario/:id',
                title: 'Editar Funcionario',
                templateUrl: 'view/manager/admin/editar-funcionario.html',
                controller: 'editarFuncionarioCtrl',
                controllerAs: 'editar',
                module: 'admin'
            })

            .state('home.perfil-aluno', {
                url: '/perfil/aluno/matricula/:matricula',
                title: 'Perfil Aluno',
                templateUrl: 'view/manager/admin/perfil-aluno.html',
                controller: 'webcamCtrl',
                module: 'admin'
            })

            /* Aluno */
            .state('home.adicionar-alunos', {
                url: '/adicionar/aluno',
                title: 'Adicionar Alunos',
                templateUrl: 'view/manager/admin/adicionar-alunos.html',
                controller: 'cadastrarAlunoCtrl',
                controllerAs: 'cadastrar',
                module: 'admin'
            })

            .state('home.listar-alunos', {
                url: '/listar/aluno',
                title: 'Listar Alunos',
                templateUrl: 'view/manager/admin/listar-alunos.html',
                controller: 'listarAlunosCtrl',
                controllerAs: 'listarAlunos',
                module: 'admin'
            })

            .state('home.editar-aluno', {
                url: '/editar/aluno/matricula/:matricula',
                title: 'Editar Aluno',
                templateUrl: 'view/manager/admin/editar-aluno.html',
                controller: 'editarAlunoCtrl',
                controllerAs: 'editar',
                module: 'admin'
            })

            .state('home.entrada-alunos', {
                url: '/entrada/aluno',
                title: 'Entrada de Alunos',
                templateUrl: 'view/manager/entrada-alunos.html',
                controller: 'entradaAlunoCtrl',
                controllerAs: 'entrada',
                module: 'inspetor'
            })

            .state('home.qrcode', {
                url: '/qrcode',
                title: 'Entrada de Alunos',
                templateUrl: 'view/manager/entrada-qrcode.html',
                controller: 'entradaQrCtrl',
                controllerAs: 'qr',
                module: 'inspetor'
            })

            /* Edital*/
            .state('home.adicionar-edital', {
                url: '/adicionar/edital',
                title: 'Adicionar Edital',
                templateUrl: 'view/manager/admin/adicionar-edital.html',
                controller: 'cadastrarEditalCtrl',
                controllerAs: 'cadastrarEdital',
                module: 'admin'
            })

            .state('home.listar-edital', {
                url: '/listar/edital',
                title: 'Listar Edital',
                templateUrl: 'view/manager/admin/listar-edital.html',
                controller: 'listarEditalCtrl',
                controllerAs: 'listarEdital',
                module: 'admin'
            })

            .state('home.editar-edital', {
                url: '/editar/edital/:id',
                title: 'Editar Edital',
                templateUrl: 'view/manager/admin/editar-edital.html',
                controller: 'editarEditalCtrl',
                controllerAs: 'editarEdital',
                module: 'admin'
            })

            .state('home.listar-contemplados-edital', {
                url: '/listar/alunos/edital/:id',
                title: 'Listar Contemplados do Edital',
                templateUrl: 'view/manager/admin/listar-contemplados-edital.html',
                controller: 'listarContempladosEditalCtrl',
                controllerAs: 'listarContempladosEdital',
                module: 'admin'
            })

            .state('home.detalhar-refeicaorealizada-aluno-edital', {
                url: '/detalhar/refeicoesrealizadas/edital/:idEdital/aluno/:matricula',
                title: 'Detalhar Refeições Realizadas do Aluno para o Edital',
                templateUrl: 'view/manager/admin/detalhar-refeicaorealizada-aluno-edital.html',
                controller: 'detalharRefeicaoRealizadaAlunoEditalCtrl',
                controllerAs: 'detalharRefeicaoRealizadaAlunoEdital',
                module: 'admin'
            })

            /* Curso */
            .state('home.listar-cursos', {
                url: '/listar/curso',
                title: 'Listar Curso',
                templateUrl: 'view/manager/admin/listar-cursos.html',
                controller: 'listarCursoCtrl',
                controllerAs: 'listarCurso',
                module: 'admin'
            })

            .state('home.adicionar-cursos', {
                url: '/adicionar/curso',
                title: 'Adicionar curso',
                templateUrl: 'view/manager/admin/adicionar-curso.html',
                controller: 'cadastrarCursoCtrl',
                controllerAs: 'cadastrarCurso',
                module: 'admin'
            })

            .state('home.editar-curso', {
                url: '/editar/curso/:id',
                title: 'Editar Curso',
                templateUrl: 'view/manager/admin/editar-curso.html',
                controller: 'editarCursoCtrl',
                controllerAs: 'editar',
                module: 'admin'
            })

            /* Evento */
            .state('home.listar-eventos', {
                url: '/listar/evento',
                title: 'Listar Evento',
                templateUrl: 'view/manager/admin/listar-eventos.html',
                controller: 'listarEventoCtrl',
                controllerAs: 'listarEvento',
                module: 'admin'
            })

            .state('home.adicionar-eventos', {
                url: '/adicionar/evento',
                title: 'Adicionar Evento',
                templateUrl: 'view/manager/admin/adicionar-evento.html',
                controller: 'cadastrarEventoCtrl',
                controllerAs: 'cadastrarEvento',
                module: 'admin'
            })

            .state('home.editar-evento', {
                url: '/editar/evento/:id',
                title: 'Editar Evento',
                templateUrl: 'view/manager/admin/editar-evento.html',
                controller: 'editarEventoCtrl',
                controllerAs: 'editar',
                module: 'admin'
            })

            /* Refeição */
            .state('home.adicionar-refeicao', {
                url: '/adicionar/refeicao',
                title: 'Adicionar Refeição',
                templateUrl: 'view/manager/admin/adicionar-refeicao.html',
                controller: 'cadastrarRefeicaoCtrl',
                controllerAs: 'cadastrarRefeicao',
                module: 'admin'
            })

            .state('home.listar-refeicoes', {
                url: '/listar/refeicao',
                title: 'Listar Refeição',
                templateUrl: 'view/manager/admin/listar-refeicoes.html',
                controller: 'listarRefeicaoCtrl',
                controllerAs: 'listarRefeicao',
                module: 'admin'
            })

            .state('home.editar-refeicao', {
                url: '/editar/refeicao/:id',
                title: 'Editar Refeicao',
                templateUrl: 'view/manager/admin/editar-refeicao.html',
                controller: 'editarRefeicaoCtrl',
                controllerAs: 'editarRefeicao',
                module: 'admin'
            })

            /* Migrar Sábado Letivo */
            .state('home.migrar-sabado', {
                url: '/migrar/sabado/',
                title: 'Migrar Sábado Letivo',
                templateUrl: 'view/manager/admin/migrar-sabado-letivo.html',
                controller: 'migrarSabadoLetivoCtrl',
                controllerAs: 'migrarSabado',
                module: 'admin'
            })

            /* Dashboard e Estatística */
            .state('home.dashboard', {
                url: '/dashboard',
                title: 'Dashboard',
                templateUrl: 'view/manager/admin/dashboard.html',
                controller: 'dashboardCtrl',
                controllerAs: 'dashboard',
                module: 'admin'
            })
    
            .state('home.estatisticas', {
                url: '/estatisticas',
                title: 'Estatisticas',
                templateUrl: 'view/manager/admin/estatisticas.html',
                controller: 'estatisticasCtrl',
                controllerAs: 'estatisticas',
                module: 'admin'
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