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
			title: 'Perfil aluno',
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

		.state('home.estatisticas', {
		    url: '/estatisticas',
			title: 'Estatisticas',
		    templateUrl: 'view/manager/admin/estatisticas.html',
            controller: 'estatisticasCtrl',
            controllerAs: 'estatisticas',
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
	  	});
});
