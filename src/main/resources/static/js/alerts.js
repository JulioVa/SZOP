angular.module('szop', []).controller('alerts', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var userId;

    function getCurrentUserId() {
        $http.get('/user/id').
        then(function (response) {
            userId = response.data;
            console.log("id: " + userId);
        });
    }

    function getAllAlerts() {
        $http.get('/alerts/all').
        then(function (response) {
            var alertsList = response.data;
            var type;
            var formattedAlertsList = [];
            var counter = 1;
            var iconColor;
            alertsList.forEach(function(entry) {
                $http.get('/sensor/' + entry.sensorId).
                then(function (sensorResponse) {
                    console.log(sensorResponse);
                    if (sensorResponse.data.type == 1) {
                        type = "Temperature";
                        iconColor = "red";
                    } else {
                        type = "Humidity";
                        iconColor = "blue";
                    }
                    var formattedAlert = {
                        type: type,
                        greaterLower: entry.greaterLower + " than " + entry.value,
                        iconColor: iconColor,
                        sensorName: sensorResponse.data.name,
                        sensorId: entry.sensorId,
                        alertId: entry.id,
                        active: entry.active
                    };

                    console.log(formattedAlert);

                    formattedAlertsList.push(formattedAlert);
                });
                counter++;
            });

            $scope.alertsList = formattedAlertsList;
        });
    }

    getAllAlerts();
    getCurrentUserId();

    setTimeout(function() {
        getSensorsList();
    }, 2000);

    function getSensorsList() {
        $("#sensors-list").empty();
        $('select').material_select('destroy');

        $http.get('/user/' + userId + '/sensors/id').
        then(function (response) {
            var sensorsList = response.data;
            console.log(sensorsList);
            $scope.sensorsList = sensorsList;

            sensorsList.forEach(function (entry) {
                $("#sensors-list").append("<option value='" + entry.id + "'>" + entry.name + "</option>");
            });

            $('select').material_select();
        })}

    $("#add-new-alert-button").click(function () {
        $("#new-alert").css("visibility", "visible");
        $("#alert-value").val("");
    });

    $("#cancel-alert").click(function () {
        $("#new-alert").css("visibility", "hidden");
    });

    $("#save-alert").click(function () {
        var sensorId = $("#sensors-list").val();
        var value = $("#alert-value").val();
        var greaterLower = $("#greater-lower").val();

        if (value == "") {
            console.log("value cannot be null");
            $("#alert-value").css("border-bottom", "1px solid #EA1C1C");
            $("#alert-value").css("box-shadow", "0 1px 0 0 #EA1C1C");
        } else {
            $("#new-alert").css("visibility", "hidden");
            var data = {
                "userId": userId,
                "sensorId": sensorId,
                "greaterLower": greaterLower,
                "value": value
            };
            console.log(data);

            $http.post('/alert', data).then(function successCallback(response) {
                console.log("posted");
            });

            setTimeout(function() {
                getAllAlerts();
                setTimeout(function() {
                    removeAlert();
                    changeIsActive();
                }, 2000);
            }, 2000);
        }
    });

    $("#alert-value").click(function () {
        if ($(this).is(':focus')) {
            $("#alert-value:focus").css("border-bottom", "1px solid orange");
            $("#alert-value:focus").css("box-shadow", "0 1px 0 0 orange");
        }
    });

    function removeAlert() {
        $(".remove-alert").click(function () {
            var alertId = this.id;
            console.log(alertId);
            $http.delete('/alert/' + alertId).then(function () {
                getAllAlerts();
                setTimeout(function() {
                    removeAlert();
                    changeIsActive();
                }, 2000);
            })
        })
    }

    function changeIsActive() {
        $(".is-active").click(function () {
            var alertId = this.id;
            alertId = alertId.substring(10);
            console.log(alertId);
            $http.put('/alert/' + alertId + '/active').then(function () {
                console.log("updated");
            })
        })
    }

    setTimeout(function() {
        removeAlert();
        changeIsActive();
    }, 2000);

}]);