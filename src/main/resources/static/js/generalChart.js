angular.module('szop', []).controller('diagrams', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var minDate;
    var maxDate;
    var data;

    $scope.model = {
        min: 0,
        max: 0,
        dataTemp: [],
        dataHumid: []
    };

    function getData(type) {
        var minMax = [0, 0];
        $http.get('/sensors/user/1/data/' + type).then(function (response) {
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
                    }
                }, {
                    tickWidth: 1,
                    title: {
                        text: 'Humidity'
                    },
                    opposite: true
                }],
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}\n<br>{point.series.yAxis.axisTitle.textStr}</b><br>',
                    backgroundColor: 'rgba(0, 0, 0, 0.85)',
                    style: {
                        color: '#F0F0F0'
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

            var i = 0;
            var seriesOptions = [];
            $scope.model.dataTemp.forEach(function (entry) {
                console.log(entry[1].temps);
                seriesOptions[i] = {
                    name: entry[1].sensorID,
                    data: entry[1].temps,
                    yAxis: 0,
                    turboThreshold: Number.MAX_VALUE
                };
                options.series.push(seriesOptions[i]);
            });

        $scope.model.dataHumid.forEach(function (entry) {
            console.log(entry[1].temps);
            seriesOptions[i] = {
                name: entry[1].sensorID,
                data: entry[1].temps,
                yAxis: 1,
                turboThreshold: Number.MAX_VALUE
            };
            options.series.push(seriesOptions[i]);
        });

            console.log(options.series);
            chart = $('#container').highcharts(options);

    }

    getData("temp");
    getData("humid");

}]);
