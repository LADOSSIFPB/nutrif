angular.module('NutrifApp').config(['$provide', function($provide) {
     var DEFAULT_TIMEZONE = 'UTC';

     $provide.decorator('dateFilter', ['$delegate', '$injector', function($delegate, $injector) {
       var oldDelegate = $delegate;

       var standardDateFilterInterceptor = function(date, format, timezone) {
         if(angular.isUndefined(timezone)) {
           timezone = DEFAULT_TIMEZONE;
         }
         return oldDelegate.apply(this, [date, format, timezone]);
       };

       return standardDateFilterInterceptor;
     }]);
}]);