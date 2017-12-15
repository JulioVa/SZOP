angular.module('szop', []).controller('diagrams', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var minDate;
    var maxDate;
    var data;
    var userId;

    $scope.model = {
        min: 0,
        max: 0,
        dataTemp: [],
        dataHumid: []
    };

    getCurrentUserId();

    function getCurrentUserId() {
        $http.get('/user/id').
        then(function (response) {
            userId = response.data;
            console.log("id: " + userId);
        });
    }

    function getData(type) {
        var minMax = [0, 0];
        $http.get('/sensors/user/' + parseInt(userId) + '/data/' + type).then(function (response) {
            var keys = (Object.keys(response.data)).sort();
            minDate = keys[0];
            maxDate = keys[keys.length - 1];

            data = Object.entries(response.data);

            data.forEach(function (entry) {
                console.log(entry);
            });

            if (type === "temp") {
                $scope.model.dataTemp = data;
            } else {
                $scope.model.dataHumid = data;
            }

            createChart()
        });

        //return minMax;
    }

    function createChart() {

            var options = {
                colors: ['#7cb5ec', '#f7a35c', '#90ee7e', '#7798BF', '#aaeeee', '#ff0066',
                    '#eeaaee', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
                chart: {
                    backgroundColor: null,
                    type: 'line'
                },
                title: {
                    style: {
                        fontSize: '16px',
                        fontWeight: 'bold',
                        textTransform: 'uppercase'
                    },
                    text: 'Temperature/humidity chart'
                },
                xAxis: {
                    type: 'datetime'
                },
                yAxis: [{
                    labels: {
                        style: {
                            fontSize: '12px'
                        }
                    },
                    tickWidth: 1,
                    title: {
                        text: 'Temperature (°C)'
                    },
                    align: 'left',
                    opposite: false
                }, {
                    tickWidth: 1,
                    title: {
                        text: 'Humidity (%)'
                    },
                    opposite: true
                }],
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}\n<br>{point.series.yAxis.axisTitle.textStr}</b><br>',
                    backgroundColor: 'rgba(0, 0, 0, 0.85)',
                    style: {
                        color: '#F0F0F0'
                    },
                    shared: false,
                    split: true
                },

                legend: {
                    enabled: true,
                    maxHeight: 100,
                    labelFormatter: function() {
                        var pos;
                        if (this.options.stack == "Temperature") {
                            pos = "left";
                        } else {
                            pos = "right";
                        }
                        return '<div class="' + this.name + '-arrow" style="float:' + pos + '; clear: both"></div>' + this.name +'<br/><span style="font-size:10px; color:#ababaa">' + this.options.stack + '</span>';
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

                plotOptions: {
                    series: {
                        marker: {
                            enabled: false
                        },
                        dataGrouping: {
                            enabled: false
                        }
                    }
                },

                rangeSelector: {
                    selected: 0,
                    allButtonsEnabled: true,
                    buttons: [{
                        type: 'hour',
                        count: 24,
                        text: 'Day'
                    }, {
                        type: 'day',
                        count: 7,
                        text: 'Week'
                    }, {
                        type: 'all',
                        text: 'All'
                    }],
                    inputStyle: {
                        backgroundColor: '#F5F5F5'
                    }
                },

                navigator: {
                    outlineColor: '#CCC',
                    maskFill: 'rgba(255,255,255,0.1)',
                    series: {
                        color: '#7798BF',
                        lineColor: '#A6C7ED'
                    }
                },

                legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
                background2: '#505053',
                dataLabelsColor: '#B0B0B3',
                textColor: '#C0C0C0',
                contrastTextColor: '#F0F0F3',
                maskColor: 'rgba(255,255,255,0.3)',
                series: []
            };

            var i = 0;
            var seriesOptions = [];
            $scope.model.dataTemp.forEach(function (entry) {
                console.log(entry[1].temps);
                seriesOptions[i] = {
                    name: entry[1].sensorID,
                    data: entry[1].temps,
                    yAxis: 0,
                    turboThreshold: Number.MAX_VALUE,
                    stack: 'Temperature',
                    color: entry[1].color
                };
                options.series.push(seriesOptions[i]);
            });

        $scope.model.dataHumid.forEach(function (entry) {
            console.log(entry[1].temps);
            seriesOptions[i] = {
                name: entry[1].sensorID,
                data: entry[1].temps,
                yAxis: 1,
                turboThreshold: Number.MAX_VALUE,
                stack: 'Humidity',
                color: entry[1].color
            };
            options.series.push(seriesOptions[i]);
        });

            console.log(options.series);
            chart = $('#container').highcharts('StockChart', options).highcharts();

    }

    setTimeout(function() {
        getData("temp");
        getData("humid");
    }, 2000);

    setInterval(function(){
        getData("temp");
        getData("humid");
    }, 315000);

}]);
