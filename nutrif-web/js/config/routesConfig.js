angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/main');

	$stateProvider

		.state('main', {
		    url: '/main',
		    templateUrl: 'view/login.html',
		    controller: 'loginCtrl',
			module: 'non-logged'
	  	})

		.state('home', {
		    url: '/home',
		    templateUrl: 'view/entrada-aluno.html',
		    controller: 'pesquisaCtrl',
			module: 'inspetor'
	  	})

	  	.state('equipe', {
		    url: '/equipe',
		    templateUrl: 'view/equipe.html',
			module: 'public'
	  	})

	  	.state('atualizar-aluno', {
	  		url: '/atualizar-aluno',
		    templateUrl: 'view/atualizar-aluno.html',
		    controller: 'alunoCtrl',
			module: 'admin'
	  	})

	  	.state('atualizar-aluno-form', {
	  		url: '/atualizar-aluno/form/:matricula',
		    templateUrl: 'view/atualizar-aluno-form.html',
		    controller: 'alunoCtrl',
			module: 'admin'
	  	})

		.state('cadastro-aluno', {
	  		url: '/cadastro-aluno',
		    templateUrl: 'view/cadastro-aluno.html',
		    controller: 'alunoCtrl',
			module: 'admin'
	  	});
});
