angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/main');

	$stateProvider

		.state('main', {
		    url: '/main',
		    templateUrl: '../view/home.html',
		    controller: 'pesquisaCtrl'
	  	})

	  	.state('cadastro-aluno', {
	  		url: '/cadastro-aluno?matricula',
		    templateUrl: '../view/cadastrar-aluno.html',
		    controller: 'alunoCtrl'
	  	});
});
