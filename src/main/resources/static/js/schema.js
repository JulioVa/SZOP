angular.module('szop', []).controller('schema', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var sensorCounter = 0;
    var sensorType = "temp";
    var img;
    var reasonableTimeToWaitForFileToLoad = 10000;
    var loaded = false;
    var schemaImg;
    var schemaX;
    var schemaY;
    var currentSchemaId;
    var currentSensorId;
    var firstSchemaId;
    var userId = 1;

    function getSchemasList() {
        $("#schemas").empty();
        $http.get('/schemas/id').
        then(function (response) {
            var schemasList = response.data;
            console.log(schemasList);
            $scope.schemaList = schemasList;
            //data-activates="schemas"

            schemasList.forEach(function (entry) {
                $("#schemas").append("<li><a href=\"#\" class='black-text schema-choice' id='" + "schema-choice" + entry.id + "'>" + entry.name + "</a></li>");
            });

            $(".schema-choice").click(function() {
                var schemaId = this.id;
                schemaId = schemaId.substring(schemaId.indexOf("schema-choice") + 13);
                console.log(schemaId);

                $('#schema-img').attr('src', "");
                $(".sensor-point").css("visibility", "hidden");

                getSchemaById(schemaId);
                getSensorsBySchemaId(schemaId);

                $('#schema-img').attr('src', schemaImg);
                $('#schema-img').css("opacity", "1");

                setTimeout(function() {
                    $(".sensor-point").hover(function () {
                        var posTop = $(this).offset().top;
                        var posLeft = $(this).offset().left;
                        showChart(posTop, posLeft);
                    });
                    var timer;
                    $(".sensor-point, #sensor-details").mouseleave(function() {
                        timer = setTimeout(hideChart, 10);
                    }).mouseenter(function() {
                        clearTimeout(timer);
                    });
                }, 2000);

                setTimeout(function() {
                    $(".sensor-point").click(function (event) {
                        currentSensorId = event.target.id;
                        currentSensorId = currentSensorId.substring(11);
                        console.log("current sensor id" + currentSensorId);
                        var posTop = $(this).offset().top;
                        var posLeft = $(this).offset().left;
                        showMoveRemove(posTop, posLeft);
                    });

                    var timer;
                    $(".sensor-point, #move-remove").mouseleave(function() {
                        timer = setTimeout(hideMoveRemove, 10);
                    }).mouseenter(function() {
                        clearTimeout(timer);
                    });
                }, 3000);
            });
        }, function () {
            $scope.schema1 = null;
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
                    $("#schema-container").prepend("<div class='sensor-point' id='sensorpoint" + entry.sensorId + "'></div>");
                    $("#sensorpoint" + entry.sensorId).css({
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
                    $("#schema-container").prepend("<div class='sensor-point' id='sensorpoint" + entry.sensorId + "'></div>");
                    $("#sensorpoint" + entry.sensorId).css({
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

    function getFirstSchemaId() {
        $http.get('/schemas/id').
        then(function (response) {
            var schemasList = response.data;
            console.log(schemasList[0].id);
            firstSchemaId = schemasList[0].id;
        });
    }

    getFirstSchemaId();
    getSchemasList();

    setTimeout(function() {
        getSchemaById(firstSchemaId);
        getSensorsBySchemaId(firstSchemaId);
    }, 2000);

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
        $("#no-name-alert-button").unbind("click");
        var schemaName = $("#schema-name").val();
        if ($("#select-file").val() == "") {
            $("#no-image-alert").css("visibility", "visible");
        } else if (schemaName == "") {
            console.log("NO SCHEMA NAME!!!");
            $("#schema-name2").val("");
            $("#no-name-alert").css("visibility", "visible");
            $("#no-name-alert-button").click(function() {
                var schemaName = $("#schema-name2").val();
                $("#no-name-alert").css("visibility", "hidden");
                console.log("nnnnn");
                saveSchema(schemaName);
            });
        } else {
            saveSchema(schemaName);
        }
        //var img = "sdfasfashfsgasfh";
    });

    $("#no-image-alert-button").click(function() {
        $("#no-image-alert").css("visibility", "hidden");
    });

    function saveSchema(schemaName) {
        img = img.substring(img.indexOf(",") + 1);
        console.log(img);
        if (loaded === true) {
            var data = {
                "name": schemaName,
                "img": img,
                "userId": userId
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
    }

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

    function moveSensor( event ) {
        $("#sensor-details").css("visibility", "hidden");
        $("#save-sensor").css("visibility", "hidden");
        $("#sensorpoint" + sensorCounter).css({"margin-top": event.pageY - $('#schema-container').offset().top - 15 + "px", "margin-left": event.pageX - $('#schema-container').offset().left - 15 + "px"});
        //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
        schemaX = event.pageX;
        schemaY = event.pageY;
        console.log(event.pageX + " " + event.pageY);
    }

   /* function moveHumiditySensor( event ) {
        $("#save-sensor").css("visibility", "hidden");
        $("#sensorpoint" + humidSensorCounter).css({"margin-top": event.pageY - $('#schema-container').offset().top - 15 + "px", "margin-left": event.pageX - $('#schema-container').offset().left - 15 + "px"});
        //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
        schemaX = event.pageX;
        schemaY = event.pageY;
        console.log(event.pageX + " " + event.pageY);
    }*/

    $("#save-sensor").click(function() {
        var sensorId;
        $(':checked').each(function() {
            sensorId = this.id;
        });
        console.log("id " + sensorId);
        $("#sensor-alert").css("display", "none");
        $("#choose-sensor").css("visibility", "hidden");
        $("#save-sensor").css("visibility", "hidden");
        if (sensorType == "temp") {
            sensorCounter = sensorId;
            $("#schema-container").prepend("<div class='sensor-point' id='sensorpoint" + sensorCounter + "'></div>");
            $("#sensorpoint" + sensorCounter).css({
                "width": "30px", "height": "30px", "background-color": "#f44336",
                "background-image": "url('../img/temp2.png')", "border-radius": "90px",
                "position": "absolute", "z-index": "100"
            });
                    }
        if (sensorType == "humid") {
            sensorCounter = sensorId;
            $("#schema-container").prepend("<div class='sensor-point' id='sensorpoint" + sensorCounter + "'></div>");
            $("#sensorpoint" + sensorCounter).css({
                "width": "30px", "height": "30px", "background-color": "#2196f3",
                "background-image": "url('../img/humidity2.png')", "border-radius": "90px",
                "position": "absolute", "z-index": "100"
            });
        }

        $("#schema-container").mousemove(moveSensor);
        $("#sensorpoint" + sensorCounter).click(function () {
            $("#schema-container").off("mousemove", moveSensor);

            var data = {
                "schemaX": schemaX,
                "schemaY": schemaY,
                "schemaId": currentSchemaId
            };
            console.log(data);

            $http.put('/system/1/sensors/' + sensorId, data).then(function () {
                console.log("updated" + data);
            });

            //$("#sensorpoint" + sensorCounter).unbind("click");

            setTimeout(function() {
                $(".sensor-point").hover(function () {
                    var posTop = $(this).offset().top;
                    var posLeft = $(this).offset().left;
                    showChart(posTop, posLeft);
                });

                var timer;
                $(".sensor-point, #sensor-details").mouseleave(function() {
                    timer = setTimeout(hideChart, 10);
                }).mouseenter(function() {
                    clearTimeout(timer);
                });
            }, 1000);

            setTimeout(function() {
                $(".sensor-point").click(function (event) {
                    currentSensorId = event.target.id;
                    currentSensorId = currentSensorId.substring(11);
                    console.log("current sensor id" + currentSensorId);
                    var posTop = $(this).offset().top;
                    var posLeft = $(this).offset().left;
                    showMoveRemove(posTop, posLeft);
                });

                var timer;
                $(".sensor-point, #move-remove").mouseleave(function() {
                    timer = setTimeout(hideMoveRemove, 10);
                }).mouseenter(function() {
                    clearTimeout(timer);
                });
            }, 3000);

        });
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
    
    $("#not-editable-schema-name").hover(function () {
        console.log("edit?");
    });

    $("#not-editable-schema-name").click(function (event) {
        $("#not-editable-schema-name").css("display", "none");
        $("#schema-name").css("visibility", "visible");
        $("#schema-name").val($scope.schema1.name);

        $("#schema-name").blur(function () {
            $scope.schema1.name = $("#schema-name").val();
            $("#schema-name").css("visibility", "hidden");
            $("#not-editable-schema-name").css("display", "block");
            var data = {
                "name": $scope.schema1.name
            };
            console.log(data);

            $http.put('/schema/' + currentSchemaId, data).then(function () {
                console.log("updated" + data);
                getSchemasList();
            });
        })
    });

    $("#delete-schema").click(function () {
        $http.put('/sensors/schema/' + currentSchemaId + '/unbind').then(function () {
            $http.delete('/schema/' + currentSchemaId).then(function () {
                console.log("deleted");
                $(".sensor-point").css("display", "none");
                getFirstSchemaId();
                getSchemasList();
                setTimeout(function() {
                    getSchemaById(firstSchemaId);
                    getSensorsBySchemaId(firstSchemaId);
                    //getSchemasList();
                }, 2000);
                $('#schema-img').attr('src', schemaImg);

                setTimeout(function() {
                    $(".sensor-point").hover(function () {
                        var posTop = $(this).offset().top;
                        var posLeft = $(this).offset().left;
                        showChart(posTop, posLeft);
                    });

                    var timer;
                    $(".sensor-point, #sensor-details").mouseleave(function() {
                        timer = setTimeout(hideChart, 10);
                    }).mouseenter(function() {
                        clearTimeout(timer);
                    });
                }, 3000);

                setTimeout(function() {
                    $(".sensor-point").click(function (event) {
                        currentSensorId = event.target.id;
                        currentSensorId = currentSensorId.substring(11);
                        console.log("current sensor id" + currentSensorId);
                        var posTop = $(this).offset().top;
                        var posLeft = $(this).offset().left;
                        showMoveRemove(posTop, posLeft);
                    });

                    var timer;
                    $(".sensor-point, #move-remove").mouseleave(function() {
                        timer = setTimeout(hideMoveRemove, 10);
                    }).mouseenter(function() {
                        clearTimeout(timer);
                    });
                }, 3000);
            });
        });
    });

    setTimeout(function() {
        $(".sensor-point").hover(function () {
            var posTop = $(this).offset().top;
            var posLeft = $(this).offset().left;
            showChart(posTop, posLeft);
        });

        var timer;
        $(".sensor-point, #sensor-details").mouseleave(function() {
            timer = setTimeout(hideChart, 10);
        }).mouseenter(function() {
            clearTimeout(timer);
        });
    }, 3000);

    function hideChart() {
        $("#sensor-details").css("visibility", "hidden");
    }

    function showChart(posTop, posLeft) {
        console.log(posTop + " " + posLeft);
        $("#sensor-details").css("visibility", "visible");
        $("#sensor-details").css("margin-left", posLeft + 20);
        $("#sensor-details").css("margin-top", posTop - 60);
    }

    setTimeout(function() {
        $(".sensor-point").click(function (event) {
            currentSensorId = event.target.id;
            currentSensorId = currentSensorId.substring(11);
            console.log("current sensor id" + currentSensorId);
            var posTop = $(this).offset().top;
            var posLeft = $(this).offset().left;
            showMoveRemove(posTop, posLeft);
        });

        var timer;
        $(".sensor-point, #move-remove").mouseleave(function() {
            timer = setTimeout(hideMoveRemove, 10);
        }).mouseenter(function() {
            clearTimeout(timer);
        });
    }, 3000);

    function showMoveRemove(posTop, posLeft) {
        console.log(posTop + " " + posLeft);
        $("#move-remove").css("visibility", "visible");
        $("#move-remove").css("margin-left", posLeft - 190);
        $("#move-remove").css("margin-top", posTop - 60);
    }

    function hideMoveRemove() {
        $("#move-remove").css("visibility", "hidden");
    }

    $("#remove-sensor").click(function () {
        $http.put('/sensors/' + currentSensorId + '/unbind').then(function () {
            console.log("removed");
        });
        setTimeout(function() {
            $(".sensor-point").css("visibility", "hidden");
            getSensorsBySchemaId(currentSchemaId);

            setTimeout(function() {
                $(".sensor-point").hover(function () {
                    var posTop = $(this).offset().top;
                    var posLeft = $(this).offset().left;
                    showChart(posTop, posLeft);
                });

                var timer;
                $(".sensor-point, #sensor-details").mouseleave(function() {
                    timer = setTimeout(hideChart, 10);
                }).mouseenter(function() {
                    clearTimeout(timer);
                });
            }, 3000);

            setTimeout(function() {
                $(".sensor-point").click(function (event) {
                    currentSensorId = event.target.id;
                    currentSensorId = currentSensorId.substring(11);
                    console.log("current sensor id" + currentSensorId);
                    var posTop = $(this).offset().top;
                    var posLeft = $(this).offset().left;
                    showMoveRemove(posTop, posLeft);
                });

                var timer;
                $(".sensor-point, #move-remove").mouseleave(function() {
                    timer = setTimeout(hideMoveRemove, 10);
                }).mouseenter(function() {
                    clearTimeout(timer);
                });
            }, 3000);
        }, 1000);
    });

    $("#move-sensor").click(function () {
        $("#move-remove").css("visibility", "hidden");
        $("#sensor-details").css("visibility", "hidden");
        sensorCounter = currentSensorId;
        $("#sensorpoint" + currentSensorId).unbind('mouseenter mouseleave');
        $("#schema-container").mousemove(moveSensor);
        $("#sensorpoint" + currentSensorId).click(function () {
            /*$("#sensor-details").css("visibility", "hidden");
            $("#sensor-details").css("display", "none");*/
            $("#schema-container").off("mousemove", moveSensor);

            var data = {
                "schemaX": schemaX,
                "schemaY": schemaY,
                "schemaId": currentSchemaId
            };
            console.log(data);

            $http.put('/system/1/sensors/' + currentSensorId, data).then(function () {
                console.log("updated" + data);
            });

            //$("#sensorpoint" + sensorCounter).unbind("click");

            setTimeout(function() {
                $(".sensor-point").hover(function () {
                    var posTop = $(this).offset().top;
                    var posLeft = $(this).offset().left;
                    showChart(posTop, posLeft);
                });

                var timer;
                $(".sensor-point, #sensor-details").mouseleave(function() {
                    timer = setTimeout(hideChart, 10);
                }).mouseenter(function() {
                    clearTimeout(timer);
                });
            }, 1000);

            setTimeout(function() {
                $(".sensor-point").click(function (event) {
                    currentSensorId = event.target.id;
                    currentSensorId = currentSensorId.substring(11);
                    console.log("current sensor id" + currentSensorId);
                    var posTop = $(this).offset().top;
                    var posLeft = $(this).offset().left;
                    showMoveRemove(posTop, posLeft);
                });

                var timer;
                $(".sensor-point, #move-remove").mouseleave(function() {
                    timer = setTimeout(hideMoveRemove, 10);
                }).mouseenter(function() {
                    clearTimeout(timer);
                });
            }, 3000);

        });
        $("#sensor-details").css("visibility", "visible");
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