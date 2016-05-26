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

		.state('home.adicionar-alunos', {
		    url: '/adicionar',
			title: 'Adicionar Alunos',
		    templateUrl: 'view/manager/admin/adicionar-alunos.html',
            controller: 'cadastrarAlunoCtrl',
            controllerAs: 'cadastrar',
			module: 'admin'
	  	});
});
