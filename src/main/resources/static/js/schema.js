angular.module('szop', []).controller('schema', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var tempSensorCounter = 0;
    var humidSensorCounter = 0;
    var sensorType = "temp";
    var img;
    var reasonableTimeToWaitForFileToLoad = 10000;
    var loaded = false;
    var schemaImg;

    $http.get('/schemas').
    then(function (response) {
        var schemasList = response.data;
        console.log(schemasList);
        $scope.schemaList = schemasList;
    });

    $http.get('/schema/128').
    then(function (response) {
        schemaImg = "data:image/jpeg;base64," + response.data.img;
        var data = {
            "img": schemaImg,
            "name": response.data.name
        };
        $scope.schema1 = data;
    });

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
        $("#schema-name").css("visibility", "visible");
        $("#schema-name").focus();
        $("#save-schema").css("visibility", "visible");
        $("#cancel-schema").css("visibility", "visible");
        $('#schema-img').attr('src', "");
    });

    $("#cancel-schema").click(function() {
        console.log("xxx");
        $("#not-editable-schema-name").css("display", "block");
        $("#select-file").css("visibility", "hidden");
        $("#schema-name").css("visibility", "hidden");
        $("#save-schema").css("visibility", "hidden");
        $("#cancel-schema").css("visibility", "hidden");
        $('#schema-img').attr('src', schemaImg);
    });

    $("#save-schema").click(function() {
        var schemaName = $("#schema-name").val();
        //var img = "sdfasfashfsgasfh";
        img = img.substring(img.indexOf(",") + 1);
        console.log(img);
        if (loaded === true) {
            var data = {
                "name": schemaName,
                "img": img
            };
            console.log(schemaName);

            $http.post('/schema', data).then(function () {
                console.log("posted");
            });
        }
    });

    $("#add-temp-sensor").click(function() {
        sensorType = "temp";
        $("#choose-sensor").css("visibility", "visible");
    });

    $("#add-humid-sensor").click(function() {
        sensorType = "humid";
        $("#choose-sensor").css("visibility", "visible");
    });

    $("#cancel-sensor").click(function() {
        $("#choose-sensor").css("visibility", "hidden");
    });

    function moveTempSensor( event ) {
        $("#temp-sensor" + tempSensorCounter).css({"margin-top": event.pageY - $('#schema-container').offset().top - 15 + "px", "margin-left": event.pageX - $('#schema-container').offset().left - 15 + "px"});
        //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
        console.log(event.pageX + " " + event.pageY);
    }

    function moveHumiditySensor( event ) {
        $("#humid-sensor" + humidSensorCounter).css({"margin-top": event.pageY - $('#schema-container').offset().top - 15 + "px", "margin-left": event.pageX - $('#schema-container').offset().left - 15 + "px"});
        //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
        console.log(event.pageX + " " + event.pageY);
    }

    $("#save-sensor").click(function() {
        $("#choose-sensor").css("visibility", "hidden");
        if (sensorType == "temp") {
            tempSensorCounter++;
            $("#schema-container").prepend("<div id='temp-sensor" + tempSensorCounter + "'></div>");
            $("#temp-sensor" + tempSensorCounter).css({
                "width": "30px", "height": "30px", "background-color": "red", "border-radius": "90px",
                "position": "absolute", "z-index": "100"
            });
            $("#schema-container").mousemove(moveTempSensor);
            $("#temp-sensor" + tempSensorCounter).click(function () {
                $("#schema-container").off("mousemove", moveTempSensor);
            })
        }
        if (sensorType == "humid") {
            humidSensorCounter++;
            $("#schema-container").prepend("<div id='humid-sensor" + humidSensorCounter + "'></div>");
            $("#humid-sensor" + humidSensorCounter).css({
                "width": "30px", "height": "30px", "background-color": "blue", "border-radius": "90px",
                "position": "absolute", "z-index": "100"
            });
            $("#schema-container").mousemove(moveHumiditySensor);
            $("#humid-sensor" + humidSensorCounter).click(function () {
                $("#schema-container").off("mousemove", moveHumiditySensor);
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

}]);