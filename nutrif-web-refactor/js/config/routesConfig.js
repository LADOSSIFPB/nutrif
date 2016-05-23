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
			module: 'non-logged'
	  	})

        .state('login.pretensao', {
		    url: '/pretensao',
		    templateUrl: 'view/login-pretensao.html',
			module: 'non-logged'
	  	})

        .state('home', {
		    url: '/inicio',
			title: 'Entrada de Alunos',
		    templateUrl: 'view/manager/home.html'
	  	});

});
