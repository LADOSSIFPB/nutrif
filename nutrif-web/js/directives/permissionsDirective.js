nutrifApp.directive('permission', function(userService) {
   return {
       restrict: 'A',
       scope: {
          permission: '='
       },
 
       link: function (scope, elem, attrs) {
            scope.$watch(userService.isLoggedIn, function() {
                if (!userService.hasRoles(scope.permission)) {
                    elem.addClass('hide');
                }
            });                
       }
   }
});