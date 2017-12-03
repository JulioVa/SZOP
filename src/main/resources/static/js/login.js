    /**
     * The Sign-In client object.
     */
    var auth2;
    //var signedStatus;

    function initClient() {
        //console.log("init");
        auth2 = gapi.auth2.init({
            clientId: '100008317632-rp63ps1kq4jaess9g0u5ltbsm6oh8e2u.apps.googleusercontent.com',
            fetch_basic_profile: true,
            scope: 'profile'
        }).then(function () {
            // Listen for sign-in state changes.
            gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

            //signedStatus = gapi.auth2.getAuthInstance().isSignedIn.get();
            // Handle the initial sign-in state.
            updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        });
    }

    function signInCallback(googleUser) {
        console.log("signInCallback");
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("xxxxx " + id_token);

        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'https://localhost:8090/user/token');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            console.log('Signed in as: ' + xhr.responseText);
        };
        xhr.send(id_token);
    }

    function updateSigninStatus(isSignedIn) {
        //console.log("updateSigninStatus");
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
        }
    }

    function handleSignInClick(event) {
        //console.log("handleSignInClick");
        // Ideally the button should only show up after gapi.client.init finishes, so that this
        // handler won't be called before OAuth is initialized.
        gapi.auth2.getAuthInstance().signIn();
        console.log("xxxxxx");
        location.href = "/";
    }

    function handleSignOutClick(event) {
        gapi.auth2.getAuthInstance().signOut();
    }

    function handleClientLoad() {
        //console.log("handleClientLoad");
        gapi.load('client:auth2', initClient);
    }

    function onSuccess(googleUser) {
        console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
        location.href = "/";
    }

    function onFailure(error) {
        console.log(error);
    }

    function renderButton() {
        //console.log("renderButton");
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

    /*function getSignedStatus() {
        initClient();
        return this.signedStatus;
    }*/

    function signOut() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('User signed out.');
        });
    }
