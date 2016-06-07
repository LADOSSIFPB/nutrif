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
			module: 'non-logged'
	  	})

        .state('home', {
		    url: '/inicio',
			abstract: true,
		    templateUrl: 'view/manager/home.html'
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
	  	});
});
