//angular.module('szop', []).controller('user', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    /**
     * The Sign-In client object.
     */
    var auth2;

    function initClient() {
        auth2 = gapi.auth2.init({
            clientId: '100008317632-rp63ps1kq4jaess9g0u5ltbsm6oh8e2u.apps.googleusercontent.com',
            fetch_basic_profile: true,
            scope: 'profile'
        }).then(function () {
            // Listen for sign-in state changes.
            gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

            // Handle the initial sign-in state.
            updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        });
    }

    function signInCallback() {
        console.log("call back");
        auth2 = gapi.auth2.init({
            client_id: '100008317632-rp63ps1kq4jaess9g0u5ltbsm6oh8e2u.apps.googleusercontent.com',
            fetch_basic_profile: true,
            scope: 'profile'
        });

        gapi.load('auth2', function () {
            console.log(gapi.auth2.currentUser);
            //        var profileData;
            var profile = auth2.currentUser.get().getBasicProfile();
            console.log("Profile: " + profile);
            var profileData = {
                userId: profile.getId(),
                name: profile.getName(),
                profilePic: profile.getImageUrl(),
                email: profile.getEmail(),
            };
            console.log("profiled Data");

            console.log('ID: ' + profile.getId());
            console.log('Full Name: ' + profile.getName());
            console.log('Given Name: ' + profile.getGivenName());
            console.log('Family Name: ' + profile.getFamilyName());
            console.log('Image URL: ' + profile.getImageUrl());
            console.log('Email: ' + profile.getEmail());

            console.log("before post");
            $http.post('/user', data)
                .then(function (data){
                    console.log("posted");
                }, function (err){
                    console.log(err);
                });

            console.log("after post");
        });
    }

    function updateSigninStatus(isSignedIn) {
        // When signin status changes, this function is called.
        // If the signin status is changed to signedIn, we make an API call.
        if (isSignedIn) {
            document.getElementById("login-butt").style.display = "none";
            document.getElementById("profile-butt").style.display = "block";
            document.getElementById("logout-butt").style.display = "block";
            document.getElementById("logout-butt").innerHTML = "Logout";
            //document.getElementById("login-butt").onclick = handleSignOutClick();
        }
        else {
            document.getElementById("logout-butt").style.display = "none";
            document.getElementById("profile-butt").style.display = "none";
            document.getElementById("login-butt").style.display = "block";
            document.getElementById("login-butt").innerHTML = "Login";
            //document.getElementById("login-butt").onclick = handleSignInClick();
        }
    }

    function handleSignInClick(event) {
        // Ideally the button should only show up after gapi.client.init finishes, so that this
        // handler won't be called before OAuth is initialized.
        gapi.auth2.getAuthInstance().signIn();
        ///console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
    }

    function handleSignOutClick(event) {
        gapi.auth2.getAuthInstance().signOut();
    }

    function handleClientLoad() {
        gapi.load('client:auth2', initClient);
    }

    function onSuccess(googleUser) {
        console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
        console.log("before");
        signInCallback();
        console.log("after");
    }

    function onFailure(error) {
        console.log(error);
    }

    function renderButton() {
        gapi.signin2.render('my-signin2', {
            'scope': 'profile email',
            'width': 240,
            'height': 50,
            'longtitle': true,
            'theme': 'dark',
            'onsuccess': onSuccess,
            'onfailure': onFailure
        });
    }

    function signOut() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('User signed out.');
        });
    }
//}]);