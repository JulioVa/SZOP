function loadTempDiagram() {
    document.getElementById("temp-button").classList.add('black');
    document.getElementById("temp-button").classList.add('orange-text');
    document.getElementById("temp-button").classList.remove('orange');
    document.getElementById("temp-button").classList.remove('darken-3');
    document.getElementById("humidity-button").classList.add('orange');
    document.getElementById("humidity-button").classList.add('darken-3');
    document.getElementById("humidity-button").classList.remove('black');
    document.getElementById("humidity-button").classList.remove('orange-text');
    document.getElementById("diagram-will-be-here").innerHTML = "Temperature Diagram";
}

function loadHumidityDiagram() {
    document.getElementById("humidity-button").classList.add('black');
    document.getElementById("humidity-button").classList.add('orange-text');
    document.getElementById("humidity-button").classList.remove('orange');
    document.getElementById("humidity-button").classList.remove('darken-3');
    document.getElementById("temp-button").classList.add('orange');
    document.getElementById("temp-button").classList.add('darken-3');
    document.getElementById("temp-button").classList.remove('black');
    document.getElementById("temp-button").classList.remove('orange-text');
    document.getElementById("diagram-will-be-here").innerHTML = "Humidity Diagram";
}