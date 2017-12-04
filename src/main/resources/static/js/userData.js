var auth2;

window.onLoadCallback = function() {
    function indexInitClient() {
        console.log("yyyyy");
        gapi.load('auth2', function () {
            auth2 = gapi.auth2.init({
                clientId: '1024855410706-fnspfn6v8g2f1lsqjc27k1jvfu5lkltp.apps.googleusercontent.com',
                fetch_basic_profile: true,
                scope: 'profile'
            }).then(function () {
                console.log("xxxxx");
                // Handle the initial sign-in state.
                upSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
            });
        });
    }


    function upSigninStatus(isSignedIn) {
        console.log("before if signed in");
        if (isSignedIn) {
            console.log("if signed in");
            document.getElementById("logout-butt").innerHTML = "Logout";

            /*$http.get('/user/loggedin').then(function (response) {
             var email = response.data.email;
             )};*/


            auth2 = gapi.auth2.init({
                client_id: '1024855410706-fnspfn6v8g2f1lsqjc27k1jvfu5lkltp.apps.googleusercontent.com',
                fetch_basic_profile: true,
                scope: 'profile'
            });

            gapi.load('auth2', function () {
                var profile = auth2.currentUser.get().getBasicProfile();
                var id_token = auth2.currentUser.get().getAuthResponse().id_token;
                var xhr = new XMLHttpRequest();
                xhr.open('POST', 'https://localhost:8090/user/token');
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onload = function() {
                    console.log('Signed in as: ' + xhr.responseText);
                };
                xhr.send(id_token);
                var image = document.createElement("img");
                image.src = profile.getImageUrl();
                image.setAttribute("height", "50px");
                image.setAttribute("width", "50px");
                image.setAttribute("border-radius", "5px 10px 15px 20px");
                image.setAttribute("alt", "profile-pic");
                document.getElementById("profile-pic").appendChild(image);

                document.getElementById("name").innerHTML = profile.getName();
            });
        }
        else {
            console.log("User isn't signed in");
            location.href = "login.html";
        }
    }
    indexInitClient();
};


function logoutFunction(){
    document.getElementById("logout-butt").onclick = handleSignOutClick();
    location.href = "logout.html";
}