angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/main');

	$stateProvider

		.state('main', {
		    url: '/main',
		    templateUrl: 'view/main.html',
		    controller: 'loginCtrl',
			module: 'public'
	  	})

		.state('home', {
		    url: '/home',
		    templateUrl: 'view/home.html',
		    controller: 'pesquisaCtrl',
			module: 'private'
	  	})

	  	.state('equipe', {
		    url: '/equipe',
		    templateUrl: 'view/equipe.html',
			module: 'public'
	  	})

	  	.state('cadastro-aluno', {
	  		url: '/cadastro-aluno?matricula',
		    templateUrl: 'view/cadastrar-aluno.html',
		    controller: 'alunoCtrl',
			module: 'private'
	  	});
});
