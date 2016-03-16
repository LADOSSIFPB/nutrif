angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/main');

	$stateProvider

		.state('main', {
		    url: '/main',
		    templateUrl: '../view/main.html',
		    controller: 'loginCtrl'
	  	})

		.state('home', {
		    url: '/home',
		    templateUrl: '../view/home.html',
		    controller: 'pesquisaCtrl'
	  	})

	  	.state('equipe', {
		    url: '/equipe',
		    templateUrl: '../view/equipe.html'
	  	})

	  	.state('cadastro-aluno', {
	  		url: '/cadastro-aluno?matricula',
		    templateUrl: '../view/cadastrar-aluno.html',
		    controller: 'alunoCtrl'
	  	});
});
