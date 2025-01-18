package com.vincasturskis.personal_website_backend;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes; 
import com.google.api.client.http.HttpTransport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.net.URLDecoder;
public class GmailOAuthService {

    private static final String CLIENT_SECRET_PATH = "/etc/secrets/auth/credentials.json";
    private static final String APPLICATION_NAME = "Gmail API Java Sender";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "/etc/secrets/tokens";

    private static Credential createCredential(GoogleAuthorizationCodeFlow flow) throws Exception
    {
        String redirectUri = "http://localhost";
        String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri).set("prompt", "consent").build();
        System.out.println("Go to the following URL to authorize the app:");
        System.out.println(authorizationUrl);

        // Capture the authorization code manually
        System.out.println("Enter the authorization code:");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String code = scanner.nextLine();

        String decodedCode = URLDecoder.decode(code, "UTF-8");
        // Exchange the code for tokens and store them
        TokenResponse tokenResponse = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

        System.out.println("Access token: " + tokenResponse.getAccessToken());
        System.out.println("Refresh token: " + tokenResponse.getRefreshToken());
        // Create a Credential object using the TokenResponse
        Credential credential = flow.createAndStoreCredential(tokenResponse, "me");
        scanner.close();
        return credential;

    }

    public static Gmail getGmailService() throws Exception {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        // Load client secrets
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            httpTransport, 
            JSON_FACTORY, 
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(CLIENT_SECRET_PATH))),
            Collections.singletonList(GmailScopes.GMAIL_SEND)
        )
        .setAccessType("offline")
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))) // Store tokens
        .build();

        // Authorize
        Credential credential = flow.loadCredential("me");
        if (credential == null) {
            // If not, guide the user through the authorization process
            credential = createCredential(flow);
        }
        if (credential.getAccessToken() == null || credential.getExpiresInSeconds() <= 60) {
            // Refresh the access token if it's expired
            boolean result = credential.refreshToken();
            if (result) {
                System.out.println("Access token refreshed successfully.");
            } else {
                System.out.println("Failed to refresh access token.");
                // Clear the stored tokens directory
                File tokensDir = new File(TOKENS_DIRECTORY_PATH);
                if (tokensDir.exists()) {
                    for (File file : tokensDir.listFiles()) {
                        file.delete();
                    }
                }
                credential = createCredential(flow);
            }
        }
        System.out.println("Access Token Expires In: " + credential.getExpiresInSeconds() + " seconds");
        // Return an authenticated Gmail service instance
        return new Gmail.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JSON_FACTORY,
            credential
        ).setApplicationName(APPLICATION_NAME).build();
    }

}
