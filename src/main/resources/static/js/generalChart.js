angular.module('szop', []).controller('diagrams', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var minDate;
    var maxDate;
    var data;

    $scope.model = {
        min: 0,
        max: 0,
        data: []
    };

    function getData() {
        var minMax = [0, 0];
        $http.get('/sensors/28-000008fe8820/user/1/system/1/data').then(function (response) {
            var keys = (Object.keys(response.data)).sort();
            //console.log(keys[0]);
            minDate = keys[0];
            maxDate = keys[keys.length - 1];

            /* const map = new Map();
             Object.keys(response.data).forEach(key => {
             map.set(key, response.data[key]);
             });*/

            data = Object.entries(response.data);

            //console.log(data);

            data.forEach(function (entry) {
                //console.log(entry);
                entry[0] = parseInt(entry[0]);
            });

            $scope.model.data = data.sort();

            minMax[0] = minDate;
            minMax[1] = maxDate;

            console.log(minMax);
            console.log(keys[0]);

            createChart(minMax)
        });

        return minMax;
    }

    function createChart(minMax) {

        $scope.model.min = parseInt(minMax[0]);
        $scope.model.max = parseInt(minMax[1]);

        //console.log(min + " " + max);


            console.log("scope " + $scope.model.min);
            var xxx = $scope.model.min;
            console.log(xxx);
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
                    type: 'datetime', //For time series, x-axis labels will be time
                    labels: {
                        //You can format the label according to your need
                        //format: '{value:%H:%m:%s}'
                    },
                    min: $scope.model.min,
                    max: $scope.model.max
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
                series: [{
                    yAxis: 0,
                    name: 'Sensor1'
                   /* data: $scope.model.data*/

                    /*pointStart: $scope.model.min,
                    pointInterval: 1000 * 3600 / 12*/
                }/*, {
                    yAxis: 0,
                    name: 'Sensor2',
                    data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8],
                    /!*pointStart: $scope.model.min,
                    pointInterval: 1000 * 3600 / 12*!/
                }, {
                    yAxis: 1,
                    name: 'Sensor3',
                    data: [3.0, 6.49, 1.5, 10.5, 15.4, 10.5, 14.2, 15.5, 14.3, 13.3, 13.9, 6.6],
                    /!*pointStart: $scope.model.min,
                    pointInterval: 1000 * 3600 / 12*!/
                }*/]
            };

            console.log("min " + options.xAxis.min.type + " max " + options.xAxis.max);
            //console.log(options.series[0].data);
            options.series[0].data = $scope.model.data;
            chart = $('#container').highcharts(options);


        //chart.xAxis.setExtremes(new Date().getTime(), new Date().setHours(new Date().getHours()+1));
    }


    function getInput(_callback){
        var m = getData();
        _callback(m);
    }

    function generateChart(minMax){
        createChart(minMax);
    }

    getData();

}]);
