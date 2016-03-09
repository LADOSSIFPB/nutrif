angular.module('NutrifApp').config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/main');

	$stateProvider
		.state('main', {
		    url: '/main',
		    templateUrl: '../view/home.html',
		    controller: 'pesquisaCtrl',
		    controllerAs: 'pesquisa'
	  	});

});
