var auth2;

window.onLoadCallback = function() {
    function indexInitClient() {
        gapi.load('auth2', function () {
            auth2 = gapi.auth2.init({
                clientId: '100008317632-rp63ps1kq4jaess9g0u5ltbsm6oh8e2u.apps.googleusercontent.com',
                fetch_basic_profile: true,
                scope: 'profile'
            }).then(function () {
                // Handle the initial sign-in state.
                upSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
            });
        });
    }


    function upSigninStatus(isSignedIn) {
        if (isSignedIn) {
            document.getElementById("logout-butt").innerHTML = "Logout";

            auth2 = gapi.auth2.init({
                client_id: '100008317632-rp63ps1kq4jaess9g0u5ltbsm6oh8e2u.apps.googleusercontent.com',
                fetch_basic_profile: true,
                scope: 'profile'
            });

            gapi.load('auth2', function () {
                var profile = auth2.currentUser.get().getBasicProfile();
                var id_token = auth2.currentUser.get().getAuthResponse().id_token;
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/user/token');
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