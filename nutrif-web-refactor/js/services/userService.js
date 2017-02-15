angular.module("NutrifApp").factory("userService", function($cookies){

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

        var $ADMINISTRADOR = 1;
        var $INSPETOR = 2;
        var $COMENSAL = 3;

        function findRoleAdmin(role) {
            return role.id === $ADMINISTRADOR;
        }

        function findRoleInspetor(role) {
            return role.id === $INSPETOR;
        }

        function findRoleComensal(role) {
            return role.id === $COMENSAL;
        }

        if (_getUser().roles.find(findRoleAdmin)) {
            return {id: $ADMINISTRADOR, nome: 'Administrador'}
        }

        if (_getUser().roles.find(findRoleInspetor)) {
            return {id: $INSPETOR, nome: 'Inspetor'}
        }

        if (_getUser().roles.find(findRoleComensal)) {
            return {id: $COMENSAL, nome: 'Aluno'}
        }
    }

    return {
    		getUserRole: _getUserRole,
            removeUser: _removeUser,
    		getUser: _getUser,
            storeUser: _storeUser
    };
});
