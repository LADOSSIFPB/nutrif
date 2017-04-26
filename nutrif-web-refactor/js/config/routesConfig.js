angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/login/gerenciamento');

    $stateProvider
        .state('login', {
            url: '/login',
            abstract: true,
            templateUrl: 'view/login.html'
        })

        .state('login.gerenciamento', {
            url: '/gerenciamento',
            templateUrl: 'view/login-gerenciamento.html',
            controller: 'loginManagerCtrl',
            controllerAs: 'login',
            module: 'non-logged'
        })

        .state('login.pretensao', {
            url: '/pretensao',
            templateUrl: 'view/login-pretensao.html',
            controller: 'loginPretensaoCtrl',
            controllerAs: 'pretensao',
            module: 'non-logged'
        })

        .state('login.acesso-aluno', {
            url: '/acesso/aluno/:matricula',
            templateUrl: 'view/acesso-pretensao.html',
            controller: 'acessoAlunoCtrl',
            controllerAs: 'acesso',
            module: 'non-logged'
        })

        .state('login.atualizar-dados-aluno', {
            url: '/atualizar/aluno/:matricula',
            templateUrl: 'view/pretensao/atualizar-dados-aluno.html',
            title: 'Atualizar dados do aluno',
            controller: 'atualizarDadosAlunoCtrl',
            controllerAs: 'atualizar',
            module: 'non-logged'
        })

        .state('home', {
            url: '/inicio',
            abstract: true,
            templateUrl: 'view/manager/home.html'
        })

        /* Funcionário */
        .state('home.adicionar-funcionarios', {
            url: '/adicionar/funcionario',
            title: 'Adicionar Funcionarios',
            templateUrl: 'view/manager/admin/adicionar-funcionarios.html',
            controller: 'cadastrarFuncionarioCtrl',
            controllerAs: 'cadastrarFuncionarios',
            module: 'admin'
        })

        .state('home.listar-funcionarios', {
            url: '/listar/funcionarios',
            title: 'Listar Funcionarios',
            templateUrl: 'view/manager/admin/listar-funcionarios.html',
            controller: 'listarFuncionariosCtrl',
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
            url: '/perfilAluno/:matricula',
            title: 'Perfil Aluno',
            templateUrl: 'view/manager/admin/perfil-aluno.html',
            controller: 'webcamCtrl',
            module: 'admin'
        })

        /* Aluno */
        .state('home.adicionar-alunos', {
            url: '/adicionar',
            title: 'Adicionar Alunos',
            templateUrl: 'view/manager/admin/adicionar-alunos.html',
            controller: 'cadastrarAlunoCtrl',
            controllerAs: 'cadastrar',
            module: 'admin'
        })

        .state('home.listar-alunos', {
            url: '/listar',
            title: 'Listar Alunos',
            templateUrl: 'view/manager/admin/listar-alunos.html',
            controller: 'listarAlunosCtrl',
            controllerAs: 'listar',
            module: 'admin'
        })

        .state('home.editar-aluno', {
            url: '/editar/:matricula',
            title: 'Editar Aluno',
            templateUrl: 'view/manager/admin/editar-aluno.html',
            controller: 'editarAlunoCtrl',
            controllerAs: 'editar',
            module: 'admin'
        })

        .state('home.entrada-alunos', {
            url: '/entrada',
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
            url: '/listar/contemplados/edital/:id',
            title: 'Listar Contemplados do Edital',
            templateUrl: 'view/manager/admin/listar-contemplados-edital.html',
            controller: 'listarContempladosEditalCtrl',
            controllerAs: 'listarContempladosEdital',
            module: 'admin'
        })

         .state('home.listar-refeicao-realizada', {
            url: '/listar/refeicao/realizada/:id',
            title: 'Listar refeicoes realizadas',
            templateUrl: 'view/manager/admin/listar-refeicao-realizada.html',
            controller: 'listarRefeicaoRealizadaCtrl',
            controllerAs: 'listarRefeicaoRealizada',
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


        /* Pretensão */
        .state('pretensao', {
            url: '/pretensao',
            templateUrl: 'view/pretensao/pretensao-home.html',
            abstract: true
        })

        .state('pretensao.listar-pretensao', {
            url: '/listar',
            templateUrl: 'view/pretensao/listar-pretensao.html',
            title: 'Pretensão de Refeições',
            controller: 'listarPretensaoCtrl',
            controllerAs: 'listar',
            module: 'comensal'
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
        .state('home.migrar-sabado-letivo', {
            url: '/migrar/sabado/',
            title: 'Migrar Sábado Letivo',
            templateUrl: 'view/manager/admin/migrar-sabado-letivo.html',
            controller: 'migrarSabadoCtrl',
            controllerAs: 'migrarSabado',
            module: 'admin'
        })


        .state('home.migrar-sabado', {
            url: '/migrar/sabado2/',
            title: 'Migrar Sábado Letivo',
            templateUrl: 'view/manager/admin/migrar-sabado.html',
            controller: 'migrarSabadoLetivoCtrl',
            controllerAs: 'migrarSabado',
            module: 'admin'
        })

        /* Dashboard */
        .state('home.dashboard', {
            url: '/dashboard',
            title: 'Dashboard',
            templateUrl: 'view/manager/admin/dashboard.html',
            controller: 'dashboardCtrl',
            controllerAs: 'dashboard',
            module: 'admin'
        })
});