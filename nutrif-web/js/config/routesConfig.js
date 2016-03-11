angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/main');

	$stateProvider

		.state('main', {
		    url: '/main',
		    templateUrl: '../view/home.html',
		    controller: 'pesquisaCtrl',
		    controllerAs: 'pesquisa'
	  	})

	  	.state('cadastro-aluno', {
	  		url: '/cadastro-aluno',
		    templateUrl: '../view/cadastrar-aluno.html',
		    controller: 'alunoCtrl',
		    controllerAs: 'alunoCtrl'
	  	});
});
