angular.module('szop', []).controller('schema', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var sensorCounter = 0;
    var sensorType = "temp";
    var img;
    var loaded = false;
    var schemaImg;
    var schemaX;
    var schemaY;
    var currentSchemaId;
    var currentSensorId;
    var sensorsMap = new Map();
    var firstSchemaId;
    var userId;

    getCurrentUserId();
    setTimeout(function() {
        getFirstSchemaId();
        getSchemasList();
        chartHover();
        moveRemoveClick();
    }, 1000);


    function getCurrentUserId() {
        $http.get('/user/id').
        then(function (response) {
            userId = parseInt(response.data);
            console.log("id: " + userId);
        });
    }

    function getSchemasList() {
        $("#schemas").empty();

        $http.get('/schemas/id/' + userId).
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

                chartHover();
                moveRemoveClick();
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
                sensorsMap.set(entry.id, entry.sensorId);
                var sensorX = entry.schemaX;
                var sensorY = entry.schemaY;
                if (entry.type == 1) {
                    console.log("ccc");
                    $("#schema-container").prepend("<div class='sensor-point' id='sensorpoint" + entry.id + "'></div>");
                    $("#sensorpoint" + entry.id).css({
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
                    $("#schema-container").prepend("<div class='sensor-point' id='sensorpoint" + entry.id + "'></div>");
                    $("#sensorpoint" + entry.id).css({
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
                getLastValue(userId, entry.id);
            });
        });
    }

    function getFirstSchemaId() {
        $http.get('/schemas/id/' + userId).
        then(function (response) {
            var schemasList = response.data;
            console.log(schemasList[0].id);
            firstSchemaId = schemasList[0].id;
        });
    }
    setTimeout(function() {
        getSchemaById(firstSchemaId);
        getSensorsBySchemaId(firstSchemaId);
    }, 2000);

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
        $http.get('/sensors/temperature/user/' + userId).
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
        $http.get('/sensors/humidity/user/' + userId).
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
        $("#last-value-" + currentSensorId).remove();
        $("#sensor-details").css("visibility", "hidden");
        $("#save-sensor").css("visibility", "hidden");
        $("#sensorpoint" + sensorCounter).css({"margin-top": event.pageY - $('#schema-container').offset().top - 15 + "px", "margin-left": event.pageX - $('#schema-container').offset().left - 15 + "px"});
        //$("#temp-sensor").css({"position": "absolute", "margin-top": event.pageY + "px", "margin-left": event.pageX + "px"});
        schemaX = event.pageX;
        schemaY = event.pageY;
        console.log(event.pageX + " " + event.pageY);
    }

    $("#save-sensor").click(function() {
        var sensorId;
        $(':checked').each(function() {
            sensorId = this.id;
            console.log("xxxxxxxxxxxxxxxx " + sensorId)
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

            var systemId;
            $http.get('/sensor/' + sensorId).
            then(function (response) {
                systemId = response.data.systemId;

                console.log(systemId);

                $http.put('/system/' + systemId + '/sensors/' + sensorId, data).then(function () {
                    console.log("updated" + data);
                });

                chartHover();
                moveRemoveClick();
            });
        });
    });

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
                chartHover();
                moveRemoveClick();
            });
        });
    });

    function chartHover() {
        sensorCounter = currentSensorId;
        setTimeout(function() {
            $(".sensor-point").hover(function (event) {
                currentSensorId = event.target.id;
                currentSensorId = currentSensorId.substring(11);
                var posTop = $(this).offset().top;
                var posLeft = $(this).offset().left;
                showChart(posTop, posLeft);
                getData();
            });

            var timer;
            $(".sensor-point, #sensor-details").mouseleave(function() {
                timer = setTimeout(hideChart, 10);
            }).mouseenter(function() {
                clearTimeout(timer);
            });
        }, 3000);
    }

    function hideChart() {
        $("#sensor-details").css("visibility", "hidden");
    }

    function showChart(posTop, posLeft) {
        console.log(posTop + " " + posLeft);
        $("#sensor-details").css("visibility", "visible");
        $("#sensor-details").css("margin-left", posLeft + 20);
        $("#sensor-details").css("margin-top", posTop - 60);
    }

    function moveRemoveClick() {
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
    }

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
            $("#last-value-" + currentSensorId).remove();
        });
        setTimeout(function() {
            $(".sensor-point").css("visibility", "hidden");
            getSensorsBySchemaId(currentSchemaId);
            chartHover();
            moveRemoveClick();
        }, 1000);
    });

    $("#move-sensor").click(function () {
        $("#last-value-" + currentSensorId).remove();
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

            var systemId;
            $http.get('/sensor/' + currentSensorId).
            then(function (response) {
                systemId = response.data.systemId;

                console.log(systemId);

                $http.put('/system/' + systemId + '/sensors/' + currentSensorId, data).then(function () {
                    console.log("updated" + data);
                    getLastValue(userId, currentSensorId);
                    $("#last-value-" + currentSensorId).css("display", "block");
                });

                //$("#sensorpoint" + sensorCounter).unbind("click");

                chartHover();
                moveRemoveClick();
            });

        });
        $("#sensor-details").css("visibility", "visible");
    });

    setTimeout(function() {
        console.log(img);
        loaded = true;
    }, 15000);


    var data;

    $scope.model = {
        data: []
    };

    function getData() {
        var systemId;
        $http.get('/sensor/' + currentSensorId).
        then(function (response) {
            systemId = response.data.systemId;
            var sensId = response.data.sensorId;
            console.log(systemId);

            $http.get('/sensors/' + sensId + '/user/' + userId + '/data').then(function (response) {
                data = Object.entries(response.data);

                data.forEach(function (entry) {
                    entry[0] = parseInt(entry[0]);
                });

                console.log(data);

                $scope.model.data = data;
                createChart()
            });
        });
    }

    function getLastValue(userId, id) {
        $http.get('/sensors/' + id + '/user/' + userId + '/data/value').then(function (response) {
            console.log("value " + id + " " + response.data);
            var marginTop = $('#sensorpoint' + id).offset().top - $('#schema-container').offset().top - 20;
            var marginLeft = $('#sensorpoint' + id).offset().left - $('#schema-container').offset().left;
            console.log("margin" + marginTop + " " + marginLeft);
            $("#schema-container").prepend("<p id='last-value-" + id + "' style='margin-top:" + marginTop + "px; margin-left: " + marginLeft + "px; position: absolute; z-index: 103; text-shadow: 0px 0px 5px white'><strong>" + response.data + "</strong></p>");
        });
    }

    function createChart() {

        var options = {
            colors: ['#ff9800', '#90ee7e', '#f45b5b', '#7798BF', '#aaeeee', '#ff0066',
                '#eeaaee', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
            chart: {
                backgroundColor: '#455a64',
                type: 'line',
                plotBorderColor: '#606063'
            },
            title: {
                style: {
                    color: '#E0E0E3',
                    fontSize: '20px'
                },
                text: sensorsMap.get(parseInt(currentSensorId))
            },
            xAxis: {
                type: 'datetime',
                gridLineColor: '#707073',
                labels: {
                    style: {
                        color: '#E0E0E3'
                    }
                },
                lineColor: '#707073',
                minorGridLineColor: '#505053',
                tickColor: '#707073'
            },
            yAxis: [{
                gridLineColor: '#707073',
                labels: {
                    style: {
                        color: '#E0E0E3'
                    }
                },
                lineColor: '#707073',
                minorGridLineColor: '#505053',
                tickColor: '#707073',
                tickWidth: 1,
                title: {
                    text: 'Temperature (°C)',
                    style: {
                        color: '#A0A0A3'
                    }
                }
            }, {
                gridLineColor: '#707073',
                labels: {
                    style: {
                        color: '#E0E0E3'
                    }
                },
                lineColor: '#707073',
                minorGridLineColor: '#505053',
                tickColor: '#707073',
                tickWidth: 1,
                title: {
                    text: 'Humidity',
                    style: {
                        color: '#A0A0A3'
                    }
                },
                opposite: true
            }],
            tooltip: {
                backgroundColor: 'rgba(0, 0, 0, 0.85)',
                style: {
                    color: '#F0F0F0'
                }
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        color: '#B0B0B3'
                    },
                    marker: {
                        lineColor: '#333'
                    }
                },
                boxplot: {
                    fillColor: '#505053'
                },
                candlestick: {
                    lineColor: 'white'
                },
                errorbar: {
                    color: 'white'
                }
            },
            legend: {
                enabled: false,
                itemStyle: {
                    color: '#E0E0E3'
                },
                itemHoverStyle: {
                    color: '#FFF'
                },
                itemHiddenStyle: {
                    color: '#606063'
                }
            },
            credits: {
                style: {
                    color: '#666'
                }
            },
            labels: {
                style: {
                    color: '#707073'
                }
            },

            drilldown: {
                activeAxisLabelStyle: {
                    color: '#F0F0F3'
                },
                activeDataLabelStyle: {
                    color: '#F0F0F3'
                }
            },

            navigation: {
                buttonOptions: {
                    symbolStroke: '#DDDDDD',
                    theme: {
                        fill: '#505053'
                    }
                }
            },

            rangeSelector: {
                buttonTheme: {
                    fill: '#505053',
                    stroke: '#000000',
                    style: {
                        color: '#CCC'
                    },
                    states: {
                        hover: {
                            fill: '#707073',
                            stroke: '#000000',
                            style: {
                                color: 'white'
                            }
                        },
                        select: {
                            fill: '#000003',
                            stroke: '#000000',
                            style: {
                                color: 'white'
                            }
                        }
                    }
                },
                inputBoxBorderColor: '#505053',
                inputStyle: {
                    backgroundColor: '#333',
                    color: 'silver'
                },
                labelStyle: {
                    color: 'silver'
                }
            },

            navigator: {
                handles: {
                    backgroundColor: '#666',
                    borderColor: '#AAA'
                },
                outlineColor: '#CCC',
                maskFill: 'rgba(255,255,255,0.1)',
                series: {
                    color: '#7798BF',
                    lineColor: '#A6C7ED'
                },
                xAxis: {
                    gridLineColor: '#505053'
                }
            },

            scrollbar: {
                barBackgroundColor: '#808083',
                barBorderColor: '#808083',
                buttonArrowColor: '#CCC',
                buttonBackgroundColor: '#606063',
                buttonBorderColor: '#606063',
                rifleColor: '#FFF',
                trackBackgroundColor: '#404043',
                trackBorderColor: '#404043'
            },

            legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
            background2: '#505053',
            dataLabelsColor: '#B0B0B3',
            textColor: '#C0C0C0',
            contrastTextColor: '#F0F0F3',
            maskColor: 'rgba(255,255,255,0.3)',
            series: []

        };

        console.log($scope.model.data);

        var formattedData = [];

        $scope.model.data.forEach(function (entry) {
            formattedData.push(entry[1]);
        });

        console.log(formattedData);

        var seriesOptions = {
            data: formattedData,
            name: sensorsMap.get(parseInt(currentSensorId)),
            yAxis: 0,
            turboThreshold: Number.MAX_VALUE
        };
        options.series.push(seriesOptions);

        chart = $('#sensor-details').highcharts(options);
    }

}]);