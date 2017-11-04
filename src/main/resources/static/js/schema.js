angular.module('szop', []).controller('schema', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var tempSensorCounter = 0;
    var humidSensorCounter = 0;
    var sensorType = "temp";
    var img;
    var reasonableTimeToWaitForFileToLoad = 10000;
    var loaded = false;
    var schemaImg;
    var schemaX;
    var schemaY;
    var currentSchemaId;

    function getSchemasList() {
        $("#schemas").empty();
        $http.get('/schemas/id').
        then(function (response) {
            var schemasList = response.data;
            console.log(schemasList);
            $scope.schemaList = schemasList;
            //data-activates="schemas"

            schemasList.forEach(function (entry) {
                //<li><a href="#" class="black-text">{{schema.name}}</a></li>
                $("#schemas").append("<li><a href=\"#\" class='black-text schema-choice' id='" + "schema-choice" + entry.id + "'>" + entry.name + "</a></li>");
            });

            /* $("#schemas").attr("class", "dropdown-content");
             $("#choose-schema").attr("data-activates", "schemas");*/

            $(".schema-choice").click(function() {
                var schemaId = this.id;
                schemaId = schemaId.substring(schemaId.indexOf("schema-choice") + 13);
                console.log(schemaId);

                $('#schema-img').attr('src', "");
                $(".sensor-point").css("visibility", "hidden");

                getSchemaById(schemaId);
                /*$http.get('/schema/' + schemaId).
                then(function (response) {
                    schemaImg = "data:image/jpeg;base64," + response.data.img;
                    var data = {
                        "img": schemaImg,
                        "name": response.data.name
                    };
                    $scope.schema1 = data;
                });*/

                getSensorsBySchemaId(schemaId);
                /*$http.get('/schema/' + schemaId + '/sensors').then(function (response) {
                    var tempSensorsList = response.data;
                    tempSensorsList.forEach(function (entry) {
                        var sensorX = entry.schemaX;
                        var sensorY = entry.schemaY;
                        if (entry.type == 1) {
                            console.log("ccc");
                            $("#schema-container").prepend("<div class='sensor-point' id='temp-sensor" + entry.sensorId + "'></div>");
                            $("#temp-sensor" + entry.sensorId).css({
                                "width": "30px",
                                "height": "30px",
                                "background-color": "#f44336",
                                "background-image": "url('img/temp.png') no-repeat",
                                "border-radius": "90px",
                                "position": "absolute",
                                "z-index": "100",
                                "margin-top": sensorY - $('#schema-container').offset().top - 15 + "px",
                                "margin-left": sensorX - $('#schema-container').offset().left - 15 + "px"
                            });
                        } else if (entry.type == 2) {
                            $("#schema-container").prepend("<div class='sensor-point' id='humid-sensor" + entry.sensorId + "'></div>");
                            $("#humid-sensor" + entry.sensorId).css({
                                "width": "30px",
                                "height": "30px",
                                "background-color": "#2196f3",
                                "background-image": "background: url(\"../img/humidity.png\") no-repeat;",
                                "border-radius": "90px",
                                "position": "absolute",
                                "z-index": "100",
                                "margin-top": sensorY - $('#schema-container').offset().top - 15 + "px",
                                "margin-left": sensorX - $('#schema-container').offset().left - 15 + "px"
                            });
                        }
                    });
                });*/
                $('#schema-img').attr('src', schemaImg);
                $('#schema-img').css("opacity", "1");
            });
        });
    }

    function getSchemaById(schemaId) {
        currentSchemaId = schemaId;
        $http.get('/schema/' + schemaId).
        then(function (response) {
            schemaImg = "data:image/jpeg;base64," + response.data.img;
            var data = {
                "img": schemaImg,
                "name": response.data.name
            };
            $scope.schema1 = data;
        });
    }

    function getSensorsBySchemaId(schemaId) {
        $http.get('/schema/' + schemaId + '/sensors').then(function (response) {
            var tempSensorsList = response.data;
            tempSensorsList.forEach(function (entry) {
                var sensorX = entry.schemaX;
                var sensorY = entry.schemaY;
                if (entry.type == 1) {
                    console.log("ccc");
                    $("#schema-container").prepend("<div class='sensor-point' id='temp-sensor" + entry.sensorId + "'></div>");
                    $("#temp-sensor" + entry.sensorId).css({
                        "width": "30px",
                        "height": "30px",
                        "background-color": "#f44336",
                        "background-image": "url('../img/temp2.png')",
                        "border-radius": "90px",
                        "position": "absolute",
                        "z-index": "100",
                        "margin-top": sensorY - $('#schema-container').offset().top - 15 + "px",
                        "margin-left": sensorX - $('#schema-container').offset().left - 15 + "px"
                    });
                } else if (entry.type == 2) {
                    $("#schema-container").prepend("<div class='sensor-point' id='humid-sensor" + entry.sensorId + "'></div>");
                    $("#humid-sensor" + entry.sensorId).css({
                        "width": "30px",
                        "height": "30px",
                        "background-color": "#2196f3",
                        "background-image": "url('../img/humidity2.png')",
                        "border-radius": "90px",
                        "position": "absolute",
                        "z-index": "100",
                        "margin-top": sensorY - $('#schema-container').offset().top - 15 + "px",
                        "margin-left": sensorX - $('#schema-container').offset().left - 15 + "px"
                    });
                }
            });
        });
    }

    getSchemasList();
    getSchemaById(142);
    getSensorsBySchemaId(142);

    /*$("#choose-schema").click(function() {
     $http.get('/schemas').
     then(function (response) {
     var schemasList = response.data;
     console.log(schemasList);
     $scope.schemaList = schemasList;
     });
     });*/

    $("#add-new-schema").click(function() {
        console.log("xxx");
        $("#not-editable-schema-name").css("display", "none");
        $("#select-file").css("visibility", "visible");
        $("#select-file").val("");
        $("#schema-name").val("");
        $("#schema-name").css("visibility", "visible");
        $("#schema-name").focus();
        $("#save-schema").css("visibility", "visible");
        $("#cancel-schema").css("visibility", "visible");
        $('#schema-img').attr('src', "");
        $(".sensor-point").css("visibility", "hidden");
        $("#add-new-sensor-button").css("visibility", "hidden");
        $("#delete-schema").css("visibility", "hidden");
        $("#edit-schema").css("visibility", "hidden");
    });

    $("#cancel-schema").click(function() {
        console.log("xxx");
        $("#not-editable-schema-name").css("display", "block");
        $("#select-file").css("visibility", "hidden");
        $("#schema-name").css("visibility", "hidden");
        $("#save-schema").css("visibility", "hidden");
        $("#cancel-schema").css("visibility", "hidden");
        $('#schema-img').attr('src', schemaImg);
        $('#schema-img').css("opacity", "1");
        $(".sensor-point").css("visibility", "visible");
        $("#add-new-sensor-button").css("visibility", "visible");
        $("#delete-schema").css("visibility", "visible");
        $("#edit-schema").css("visibility", "visible");
    });

    $("#save-schema").click(function() {
        var schemaName = $("#schema-name").val();
        if (schemaName == "") {
            console.log("NO SCHEMA NAME!!!");
        }
        //var img = "sdfasfashfsgasfh";
        img = img.substring(img.indexOf(",") + 1);
        console.log(img);
        if (loaded === true) {
            var data = {
                "name": schemaName,
                "img": img
            };
            console.log(schemaName);

            $http.post('/schema', data).then(function successCallback(response) {
                console.log("posted");
                getSchemasList();
                currentSchemaId = response.data.id;
                console.log("current schema id: ", currentSchemaId);
                $("#select-file").css("visibility", "hidden");
                $("#schema-name").css("visibility", "hidden");
                $("#save-schema").css("visibility", "hidden");
                $("#cancel-schema").css("visibility", "hidden");
                $("#not-editable-schema-name").css("display", "block");
                $("#add-new-sensor-button").css("visibility", "visible");
                $("#delete-schema").css("visibility", "visible");
                $("#edit-schema").css("visibility", "visible");
                $('#schema-img').css("opacity", "1");

                $scope.schema1 = {
                    "img": schemaImg,
                    "name": schemaName
                };
            });
        }
    });

    $("#add-temp-sensor").click(function() {
        sensorType = "temp";
        console.log("temp");
        $http.get('/sensors/temperature').
        then(function (response) {
            var tempSensorsList = response.data;
            console.log(tempSensorsList);
            var formattedSensorsList = [];
            tempSensorsList.forEach(function(entry) {
                if (entry.schemaX == null) {
                    formattedSensorsList.push(entry);
                }
            });
            console.log(formattedSensorsList);
            if(formattedSensorsList.length == 0) {
                console.log("xxxxxxxxxxxxx");
                $("#save-sensor").css("visibility", "hidden");
                $("#sensor-alert").css("display", "block");
            } else {
                $("#save-sensor").css("visibility", "visible");
                $("#sensor-alert").css("display", "none");
            }
            $scope.sensors = formattedSensorsList;

            $scope.$watch( "sensors" , function(newSensors){
                if(newSensors.size != 0) {
                    $('input.choose-sensor-checkbox').on('change', function() {
                        $('input.choose-sensor-checkbox').not(this).prop('checked', false);
                    });
                }
            },true);
        });
        $("#choose-sensor").css("visibility", "visible");
    });

    $("#add-humid-sensor").click(function() {
        sensorType = "humid";
        $http.get('/sensors/humidity').
        then(function (response) {
            var humidSensorsList = response.data;
            var formattedSensorsList = [];
            humidSensorsList.forEach(function(entry) {
                if (entry.schemaX == null) {
                    formattedSensorsList.push(entry);
                }
            });
            console.log(formattedSensorsList);
            if(formattedSensorsList.length == 0) {
                $("#sensor-alert").css("display", "block");
                $("#save-sensor").css("visibility", "hidden");
            } else {
                $("#save-sensor").css("visibility", "visible");
                $("#sensor-alert").css("display", "none");
            }
            $scope.sensors = formattedSensorsList;

            $scope.$watch( "sensors" , function(newSensors){
                if(newSensors.size != 0) {
                    $('input.choose-sensor-checkbox').on('change', function() {
                        $('input.choose-sensor-checkbox').not(this).prop('checked', false);
                    });
                }
            },true);
        });
        $("#choose-sensor").css("visibility", "visible");
    });

    $("#cancel-sensor").click(function() {
        $("#save-sensor").css("visibility", "hidden");
        $("#choose-sensor").css("visibility", "hidden");
        $("#sensor-alert").css("display", "none");
    });

    function moveTempSensor( event ) {
        $("#temp-sensor" + tempSensorCounter).css({"margin-top": event.pageY - $('#schema-container').offset().top - 15 + "px", "margin-left": event.pageX - $('#schema-container').offset().left - 15 + "px"});
        //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
        schemaX = event.pageX;
        schemaY = event.pageY;
        console.log(event.pageX + " " + event.pageY);
    }

    function moveHumiditySensor( event ) {
        $("#humid-sensor" + humidSensorCounter).css({"margin-top": event.pageY - $('#schema-container').offset().top - 15 + "px", "margin-left": event.pageX - $('#schema-container').offset().left - 15 + "px"});
        //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
        schemaX = event.pageX;
        schemaY = event.pageY;
        console.log(event.pageX + " " + event.pageY);
    }

    $("#save-sensor").click(function() {
        var sensorId;
        $(':checked').each(function() {
            sensorId = this.id;
        });
        console.log("id " + sensorId);
        $("#sensor-alert").css("display", "none");
        $("#choose-sensor").css("visibility", "hidden");
        if (sensorType == "temp") {
            tempSensorCounter++;
            $("#schema-container").prepend("<div class='sensor-point' id='temp-sensor" + tempSensorCounter + "'></div>");
            $("#temp-sensor" + tempSensorCounter).css({
                "width": "30px", "height": "30px", "background-color": "#f44336",
                "background-image": "url('../img/temp2.png')", "border-radius": "90px",
                "position": "absolute", "z-index": "100"
            });
            $("#schema-container").mousemove(moveTempSensor);
            $("#temp-sensor" + tempSensorCounter).click(function () {
                $("#schema-container").off("mousemove", moveTempSensor);

                var data = {
                    "schemaX": schemaX,
                    "schemaY": schemaY,
                    "schemaId": currentSchemaId
                };
                console.log(data);

                $http.put('/system/1/sensors/' + sensorId, data).then(function () {
                    console.log("updated" + data);
                });

            });
        }
        if (sensorType == "humid") {
            humidSensorCounter++;
            $("#schema-container").prepend("<div class='sensor-point' id='humid-sensor" + humidSensorCounter + "'></div>");
            $("#humid-sensor" + humidSensorCounter).css({
                "width": "30px", "height": "30px", "background-color": "#2196f3",
                "background-image": "url('../img/humidity2.png')", "border-radius": "90px",
                "position": "absolute", "z-index": "100"
            });
            $("#schema-container").mousemove(moveHumiditySensor);
            $("#humid-sensor" + humidSensorCounter).click(function () {
                $("#schema-container").off("mousemove", moveHumiditySensor);

                var data = {
                    "schemaX": schemaX,
                    "schemaY": schemaY,
                    "schemaId": currentSchemaId
                };
                console.log(data);

                $http.put('/system/1/sensors/' + sensorId, data).then(function () {
                    console.log("updated" + data);
                });
            })
        }
    });

    /*$( "#schema-container" ).mousemove(function( event ) {
     $("#temp-sensor").css({"position": "relative", "margin-top": event.pageY - 190 + "px", "margin-left": event.pageX - 330 + "px"});
     //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
     console.log(event.pageX + " " + event.pageY);
     });*/

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#schema-img').attr('src', e.target.result);
                $("#schema-img").css("opacity", "0.5");
                img = e.target.result;
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#select-file").change(function(){
        readURL(this);
        $('#schema-img').css({"margin-top": "30px", "margin-left": "auto", "margin-right": "auto"});
    });

    setTimeout(function() {
        console.log(img);
        loaded = true;
    }, reasonableTimeToWaitForFileToLoad);

    /*$('input.choose-sensor-checkbox').on('change', function() {
        console.log("vvv");
        $('input.choose-sensor-checkbox').not(this).prop('checked', false);
    });*/

}]);