<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="obj" class="com.controller.DataRestController"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SZOP</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/css/materialize.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="static/js/script.js" type="text/javascript"></script>
    <script src="static/js/diagrams.js" type="text/javascript"></script>
    <link href="static/css/style.css" type="text/css" rel="stylesheet">
</head>
<body>
<header>
    <div class="navbar-fixed">
        <nav>
            <div class="nav-wrapper grey darken-4">
                <a href="#" class="brand-logo nav-menu-logo">SZOP</a>
                <a href="#" data-activates="mobile-demo" class="button-collapse"><i
                        class="material-icons">menu</i></a>
                <ul class="left hide-on-med-and-down nav-menu-left">
                    <li><a href="static/schema.html">Schema</a></li>
                    <li><a href="static/diagrams.html">Diagrams</a></li>
                    <li><a href="static/sensors.html">Sensors</a></li>
                    <li><a href="static/alerts.html">Alerts</a></li>
                </ul>
                <ul class="right hide-on-med-and-down nav-menu-right">
                    <li><a href="profile.html">Profile</a></li>
                    <li><a href="#">Logout</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">
                    <li><a href="static/schema.html">Schema</a></li>
                    <li><a href="static/diagrams.html">Diagrams</a></li>
                    <li><a href="static/sensors.html">Sensors</a></li>
                    <li><a href="static/alerts.html">Alerts</a></li>
                    <li><a href="profile.html">Profile</a></li>
                    <li><a href="#">Logout</a></li>
                </ul>
            </div>
        </nav>
    </div>
    <main>
        <div class="container">
            <p><% String t = obj.getTemperature(); %></p>
        </div>
    </main>
</header>
</body>
</html>