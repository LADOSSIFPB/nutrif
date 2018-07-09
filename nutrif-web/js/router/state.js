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
                controller: 'logarPessoaCtrl',
                controllerAs: 'logarPessoa'
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
                permissions: ['aluno']
            })
    
            // Subrota - Inspetor
            .state('inspetor', {
                abstract: true,
                data: {
                    label: 'Inspetor'
                },
                controller: 'sideNavCtrl',
                controllerAs: 'sideNav',
                url: '/inspetor',
                templateUrl: 'view/inspetor/inspetor.html'
            })
    
            /* Inspetor - Home */
            .state('inspetor.home', {
                url: '/home',
                title: 'Inspetor - Home',
                templateUrl: 'view/inspetor/home.html',
                permissions: ['inspetor']
            })
    
            /* Inspetor - Entrada de Aluno */
            .state('inspetor.listar-entradarefeitorio', {
                url: '/listar/entradarefeitorio',
                title: 'Listar os Alunos para Entrada do Refeitório',
                templateUrl: 'view/inspetor/listar-entradarefeitorio.html',
                controller: 'listarEntradaRefeitorioCtrl',
                controllerAs: 'listarEntradaRefeitorio',
                permissions: ['inspetor']
            })

            // Subrota - Administrador
            .state('administrador', {
                abstract: true,
                data: {
                    label: 'Administrador'
                },
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

            /* Administrador - Aluno */
            .state('administrador.adicionar-aluno', {
                url: '/adicionar/aluno',
                title: 'Adicionar Aluno',
                templateUrl: 'view/administrador/adicionar-aluno.html',
                controller: 'cadastrarAlunoCtrl',
                controllerAs: 'cadastrarAluno',
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
                url: '/editar/aluno/:id',
                title: 'Editar Aluno',
                templateUrl: 'view/administrador/editar-aluno.html',
                controller: 'editarAlunoCtrl',
                controllerAs: 'editarAluno',
                permissions: ['admin']
            })
    
            /* Administrador - Funcionário */
            .state('administrador.adicionar-funcionario', {
                url: '/adicionar/funcionario',
                title: 'Adicionar Funcionário',
                templateUrl: 'view/administrador/adicionar-funcionario.html',
                controller: 'cadastrarFuncionarioCtrl',
                controllerAs: 'cadastrarFuncionario',
                permissions: ['admin']
            })

            .state('administrador.listar-funcionarios', {
                url: '/listar/funcionarios',
                title: 'Listar Funcionários',
                templateUrl: 'view/administrador/listar-funcionarios.html',
                controller: 'listarFuncionariosCtrl',
                controllerAs: 'listarFuncionarios',
                permissions: ['admin']
            })

            .state('administrador.editar-funcionario', {
                url: '/editar/funcionario/:id',
                title: 'Editar Funcionário',
                templateUrl: 'view/administrador/editar-funcionario.html',
                controller: 'editarFuncionarioCtrl',
                controllerAs: 'editarFuncionario',
                permissions: ['admin']
            })
    
            /* Administrador - Matrícula */
            .state('administrador.adicionar-matricula', {
                url: '/adicionar/matricula/aluno/:id',
                title: 'Adicionar Matrícula',
                templateUrl: 'view/administrador/adicionar-matricula.html',
                controller: 'cadastrarMatriculaCtrl',
                controllerAs: 'cadastrarMatricula',
                permissions: ['admin']
            })
    
            .state('administrador.aditar-matricula', {
                url: '/editar/matricula/:id',
                title: 'Editar Matrícula',
                templateUrl: 'view/administrador/editar-matricula.html',
                controller: 'editarMatriculaCtrl',
                controllerAs: 'aditarMatricula',
                permissions: ['admin']
            })
            
            /* Administrador - Dia Refeição */
            .state('administrador.adicionar-diarefeicao', {
                url: '/adicionar/diarefeicao/matricula/:id',
                title: 'Adicionar Dia de Refeição',
                templateUrl: 'view/administrador/adicionar-diarefeicao.html',
                controller: 'cadastrarDiaRefeicaoCtrl',
                controllerAs: 'cadastrarDiaRefeicao',
                permissions: ['admin']
            })
    
            /* Administrador - Refeição */
            .state('administrador.adicionar-refeicao', {
                url: '/adicionar/refeicao',
                title: 'Adicionar Refeição',
                templateUrl: 'view/administrador/adicionar-refeicao.html',
                controller: 'cadastrarRefeicaoCtrl',
                controllerAs: 'cadastrarRefeicao',
                permissions: ['admin']
            })

            .state('administrador.listar-refeicoes', {
                url: '/listar/refeicoes',
                title: 'Listar Refeições',
                templateUrl: 'view/administrador/listar-refeicoes.html',
                controller: 'listarRefeicoesCtrl',
                controllerAs: 'listarRefeicoes',
                permissions: ['admin']
            })

            .state('administrador.editar-refeicao', {
                url: '/editar/refeicao/:id',
                title: 'Editar refeicao',
                templateUrl: 'view/administrador/editar-refeicao.html',
                controller: 'editarRefeicaoCtrl',
                controllerAs: 'editarRefeicao',
                permissions: ['admin']
            })
    
            /* Administrador - Campus */
            .state('administrador.adicionar-campus', {
                url: '/adicionar/campus',
                title: 'Adicionar Campus',
                templateUrl: 'view/administrador/adicionar-campus.html',
                controller: 'cadastrarCampusCtrl',
                controllerAs: 'cadastrarCampus',
                permissions: ['admin']
            })

            .state('administrador.listar-campi', {
                url: '/listar/campi',
                title: 'Listar Campi',
                templateUrl: 'view/administrador/listar-campi.html',
                controller: 'listarCampiCtrl',
                controllerAs: 'listarCampi',
                permissions: ['admin']
            })

            .state('administrador.editar-campus', {
                url: '/editar/campus/:id',
                title: 'Editar Campus',
                templateUrl: 'view/administrador/editar-campus.html',
                controller: 'editarCampusCtrl',
                controllerAs: 'editarCampus',
                permissions: ['admin']
            })
    
            /* Administrador - Curso */
            .state('administrador.adicionar-curso', {
                url: '/adicionar/curso',
                title: 'Adicionar Curso',
                templateUrl: 'view/administrador/adicionar-curso.html',
                controller: 'cadastrarCursoCtrl',
                controllerAs: 'cadastrarCurso',
                permissions: ['admin']
            })

            .state('administrador.listar-cursos', {
                url: '/listar/cursos',
                title: 'Listar Cursos',
                templateUrl: 'view/administrador/listar-cursos.html',
                controller: 'listarCursosCtrl',
                controllerAs: 'listarCursos',
                permissions: ['admin']
            })

            .state('administrador.editar-curso', {
                url: '/editar/curso/:id',
                title: 'Editar Curso',
                templateUrl: 'view/administrador/editar-curso.html',
                controller: 'editarCursoCtrl',
                controllerAs: 'editarCurso',
                permissions: ['admin']
            })
    
            /* Administrador - Edital */
            .state('administrador.adicionar-edital', {
                url: '/adicionar/edital',
                title: 'Adicionar Edital',
                templateUrl: 'view/administrador/adicionar-edital.html',
                controller: 'cadastrarEditalCtrl',
                controllerAs: 'cadastrarEdital',
                permissions: ['admin']
            })
    
            .state('administrador.listar-editais', {
                url: '/listar/editais',
                title: 'Listar Editais',
                templateUrl: 'view/administrador/listar-editais.html',
                controller: 'listarEditaisCtrl',
                controllerAs: 'listarEditais',
                permissions: ['admin']
            })
            
            .state('administrador.editar-edital', {
                url: '/editar/edital/:id',
                title: 'Editar Edital',
                templateUrl: 'view/administrador/editar-edital.html',
                controller: 'editarEditalCtrl',
                controllerAs: 'editarEdital',
                permissions: ['admin']
            })
    
            /* Administrador - Entrada de Aluno */
            .state('administrador.listar-entradarefeitorio', {
                url: '/listar/entradarefeitorio',
                title: 'Listar os Alunos para Entrada do Refeitório',
                templateUrl: 'view/inspetor/listar-entradarefeitorio.html',
                controller: 'listarEntradaRefeitorioCtrl',
                controllerAs: 'listarEntradaRefeitorio',
                permissions: ['admin', 'inspetor']
            })
    
            /* Administrador - Extratos das Refeições */    
            .state('administrador.listar-extratosrefeicoes', {
                url: '/listar/extratosrefeicoes',
                title: 'Listar Extratos das Refeições',
                templateUrl: 'view/administrador/listar-extratosrefeicoes.html',
                controller: 'listarExtratosRefeicoesCtrl',
                controllerAs: 'listarExtratosRefeicoes',
                permissions: ['admin']
            })
    
            /* Administrador - Refeições Realizadas */    
            .state('administrador.listar-refeicoesrealizadas', {
                url: '/listar/refeicoesrealizadas/extratorefeicao/:idExtratoRefeicao',
                title: 'Listar Refeições Realizadas',
                templateUrl: 'view/administrador/listar-refeicoesrealizadas.html',
                controller: 'listarRefeicoesRealizadasCtrl',
                controllerAs: 'listarRefeicoesRealizadas',
                permissions: ['admin']
            })
    
            /* Administrador - Dashboard e Estatísticas */
            .state('administrador.dashboard', {
                url: '/dashboard',
                title: 'Dashboard',
                templateUrl: 'view/administrador/dashboard.html',
                controller: 'dashboardCtrl',
                controllerAs: 'dashboard',
                permissions: ['admin']
            })    
    
            .state('administrador.exibir-analise', {
                url: '/exibir/analise',
                title: 'Dashboard',
                templateUrl: 'view/administrador/exibir-analise.html',
                controller: 'exibirAnaliseCtrl',
                controllerAs: 'exibirAnalise',
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