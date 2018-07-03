nutrIFApp.filter('relativeDate',['$filter', function($filter) {
    return function(relation, format) {
        let date = new Date();
        date.setDate(date.getDate() + relation || 0);
        return $filter('date')(date, format || 'yyyy-MM-dd');
    }
}]);