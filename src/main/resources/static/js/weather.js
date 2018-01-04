/* Does your browser support geolocation? */
if ("geolocation" in navigator) {
    $('.js-geolocation').show();
} else {
    $('.js-geolocation').hide();
}

function geolocationYourCity() {
    navigator.geolocation.getCurrentPosition(function(position) {
        loadWeather(position.coords.latitude+','+position.coords.longitude); //load weather using your lat/lng coordinates
        setTimeout(function(){
            var actualCity = document.getElementById("weatherCity").innerHTML;
            actualCity = actualCity.split(", ");
            actualCity = actualCity[0]
            //console.log(actualCity);
            /*$http.put('/user/city', actualCity).then(function () {
                console.log("city updated: " + actualCity);
            });*/

            var xhr = new XMLHttpRequest();
            xhr.open('PUT', '/user/city');
            xhr.send(actualCity);
        }, 1000);
    });
}

function loadWeather(location) {
    $.simpleWeather({
        location: location,
        woeid: '',
        unit: 'c',
        success: function(weather) {
            html = '<h1>Weather for</h1><h1 id="weatherCity">'+weather.city+', '+weather.region+':</h1>';
            html += '<h2><i class="icon-'+weather.code+'"></i> '+weather.temp+'&deg;'+weather.units.temp+'</h2>';
            html += '<ul><li class="currently">'+weather.currently+'</li>';
            html += '<li>'+weather.wind.direction+' '+weather.wind.speed+' '+weather.units.speed+'</li></ul>';

            $("#weather").html(html);
        },
        error: function(error) {
            $("#weather").html('<p>'+error+'</p>');
        }
    });
}