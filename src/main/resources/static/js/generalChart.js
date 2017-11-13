angular.module('szop', []).controller('diagrams', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var minDate;
    var maxDate;
    var data;

    $http.get('/sensors/28-000008fe8820/user/1/system/1/data').
    then(function (response) {
        var keys = (Object.keys(response.data)).sort();
        //console.log(keys[0]);
        minDate = keys[0];
        maxDate = keys[keys.length - 1];

       /* const map = new Map();
        Object.keys(response.data).forEach(key => {
            map.set(key, response.data[key]);
    });*/

        data = Object.entries(response.data);

        console.log(data);
    });

    function createChart() {
        chart = $('#container').highcharts(options);
        chart.xAxis.setExtremes(new Date().getTime(), new Date().setHours(new Date().getHours()+1));

    }

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
            min: 1510573067000,
            max: 1510659467000
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
            name: 'Sensor1',
            data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
            pointStart: 1510573067000,
            pointInterval: 1000 * 3600
        }, {
            yAxis: 0,
            name: 'Sensor2',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8],
            pointStart: 1510573067000,
            pointInterval: 1000 * 3600
        }, {
            yAxis: 1,
            name: 'Sensor3',
            data: [3.0, 6.49, 1.5, 10.5, 15.4, 10.5, 14.2, 15.5, 14.3, 13.3, 13.9, 6.6],
            pointStart: 1510573067000,
            pointInterval: 1000 * 3600
        }]


    };

    setTimeout(function() {
        console.log(minDate + " " + maxDate);
        createChart();
    }, 3000);
}]);
