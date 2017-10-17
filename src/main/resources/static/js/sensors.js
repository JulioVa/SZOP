angular.module('szop', []).controller('sensors', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var systemId = 1;

    $http.get('/system/' + systemId + '/sensors').
    then(function (response) {
        var sensorsList = response.data;
        var type;
        var lastUpdate;
        var formattedSensorsList = [];
        var selected = [];
        var counter = 1;
        var sensorIcon;
        var iconColor;
        sensorsList.forEach(function(entry) {
            if (entry.type == 1) {
                type = "Temperature";
                sensorIcon = "sensors-temp-icon";
                iconColor = "red";
            } else {
                type = "Humidity";
                sensorIcon = "sensors-humidity-icon";
                iconColor = "blue";
            }
            var date = new Date(entry.lastUpdate);
            var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
            var year = date.getFullYear();
            var month = months[date.getMonth()];
            var day = date.getDate();
            var hours = date.getHours();
            var minutes = "0" + date.getMinutes();
            var seconds = "0" + date.getSeconds();
            lastUpdate = day + ' ' + month + ' ' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);

            var formattedSensor = {
                sensorId: counter,
                name: entry.name,
                type: type,
                lastUpdate: lastUpdate,
                active: entry.active,
                checkboxId: entry.sensorId,
                icon: sensorIcon,
                iconColor: iconColor
            };

            counter++;
            /*var input = document.getElementsByTagName("input");
            var inputList = Array.prototype.slice.call(input);
            if (entry.isActive) {
                inputList[inputList.size-1].setAttribute("checked", "checked");
            } else {
                inputList[inputList.size-1].setAttribute("checked", "");
            }*/

            console.log(formattedSensor);

            formattedSensorsList.push(formattedSensor);
        });

        $scope.sensorsList = formattedSensorsList;
    });

}]);