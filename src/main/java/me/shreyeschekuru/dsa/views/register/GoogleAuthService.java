package me.shreyeschekuru.dsa.views.register;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import me.shreyeschekuru.dsa.data.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;


@Service
public class GoogleAuthService {
    private static final String GOOGLE_CLIENT_ID = "41982409219-ik9l5h32k7rpfthlhrnaafs8fbuhdqen.apps.googleusercontent.com";
    private static final String GOOGLE_CLIENT_SECRET = "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";
    private static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/oauth2/callback/google";
    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String GOOGLE_USERINFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

    public String exchangeCodeForGoogleIdToken(String code) {
        GoogleTokenResponse tokenResponse = null;
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://oauth2.googleapis.com/token",
                    GOOGLE_CLIENT_ID,
                    GOOGLE_CLIENT_SECRET,
                    code,
                    GOOGLE_REDIRECT_URI
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tokenResponse.getIdToken();
    }

    public User getUserInfoFromGoogleIdToken(String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();

        GoogleIdToken googleIdToken = null;
        try {
            googleIdToken = verifier.verify(idToken);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            try {
                System.out.println("Google payload: " + payload.toPrettyString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String email = payload.getEmail();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String profilePicture = (String) payload.get("picture");

            return new User(firstName, lastName, email, profilePicture, true, false, "google");
        }

        return null;
    }

    public String getAuthorizationUrl() {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();

        GoogleAuthorizationCodeFlow flow = null;
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET,
                    Arrays.asList("openid", "profile", "email"))
                    .setAccessType("offline")
                    .setApprovalPrompt("force")
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return flow.newAuthorizationUrl().setRedirectUri(GOOGLE_REDIRECT_URI).build();
    }
}

