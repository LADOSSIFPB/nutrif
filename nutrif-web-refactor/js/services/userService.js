angular.module("NutrifApp").factory("userService", function($cookies, config){

    var _storeUser = function (user) {
        $cookies.putObject("user", user);
    };

    var _removeUser = function (user) {
        $cookies.remove("user");
    };

	var _getUser = function (){
		return $cookies.getObject("user");
	};

    var _getUserRole = function () {

        function findRoleAdmin(role) {
            console.log(role.nome);
            return role.id === 1;
        }

        function findRoleInspetor(role) {
            return role.id === 2;
        }

        function findRoleComensal(role) {
            return role.id === 3;
        }

        if (_getUser().roles.find(findRoleAdmin)) {
            return {id: 1, nome: 'Administrador'}
        }

        if (_getUser().roles.find(findRoleInspetor)) {
            return {id: 2, nome: 'Inspetor'}
        }

        if (_getUser().roles.find(findRoleComensal)) {
            return {id: 3, nome: 'Aluno'}
        }

    }

	return {
		getUserRole: _getUserRole,
        removeUser: _removeUser,
		getUser: _getUser,
        storeUser: _storeUser
	};

});
