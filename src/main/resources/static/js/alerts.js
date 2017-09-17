angular.module('szop', []).controller('alerts', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var userId = 1;

    $http.get('/user/' + userId + '/alerts').
    then(function (response) {
        var alertsList = response.data;
        var type;
        var formattedAlertsList = [];
        var counter = 1;
        alertsList.forEach(function(entry) {
            if (entry.type == 1) {
                type = "Temperature"
            } else {
                type = "Humidity";
            }

            var formattedAlert = {
                alertId: counter,
                //name: entry.name,
                type: type,
                greaterLower: entry.greaterLower + " than " + entry.value
            };

            console.log(formattedAlert);

            formattedAlertsList.push(formattedAlert);
            counter++;
        });

        $scope.alertsList = formattedAlertsList;
    });

}]);