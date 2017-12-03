package com;

import com.database.model.User;
import com.database.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class Authentication {

    private static final Logger LOGGER = LogManager.getLogger(Authentication.class);

    private static final String CLIENT_ID = "100008317632-rp63ps1kq4jaess9g0u5ltbsm6oh8e2u.apps.googleusercontent.com";

    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    private static final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), jacksonFactory)
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

    public static String authenticate(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            LOGGER.info(name);
            LOGGER.info(pictureUrl);
            LOGGER.info(email);

            // save to db
            if (UserService.findUserByEmail(email) == null) {
                User user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setProfilePic(pictureUrl);
                UserService.save(user);
            }
            return email;
        } else {
            System.out.println("Invalid ID token.");
        }
        return "";
    }
}
