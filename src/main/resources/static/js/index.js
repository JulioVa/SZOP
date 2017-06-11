angular.module('szop', [])
    .controller('temp', function($scope, $http) {
        $http.get('/temp').success(function(data) {
            $scope.temp = data;
        })
    });