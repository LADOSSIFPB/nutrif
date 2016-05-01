angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/login');

	$stateProvider

		.state('login', {
		    url: '/login',
		    templateUrl: 'view/login.html',
		    controller: 'loginCtrl',
			module: 'non-logged'
	  	})

		.state('entrada-aluno', {
		    url: '/entrada-aluno',
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
		    controller: 'atualizaAlunoCtrl',
			module: 'admin'
	  	})

	  	.state('atualizar-aluno-form', {
	  		url: '/atualizar-aluno/form/:matricula',
		    templateUrl: 'view/atualizar-aluno-form.html',
		    controller: 'atualizaAlunoCtrl',
			module: 'admin'
	  	})

		.state('cadastro-aluno', {
	  		url: '/cadastro-aluno',
		    templateUrl: 'view/cadastro-aluno.html',
		    controller: 'cadastroAlunoCtrl',
			module: 'admin'
	  	})

	  	.state('pretensao-home', {
	  		url: '/pretensao-home',
		    templateUrl: 'view/pretensao-home.html',
			module: 'non-logged'
	  	});
});
