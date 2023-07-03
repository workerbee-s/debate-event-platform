package me.shreyeschekuru.dsa.views.register;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.shreyeschekuru.dsa.data.entity.User;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Service
public class GitHubAuthService {
    private static final String GITHUB_CLIENT_ID = "a06df777b3daf7181295";
    private static final String GITHUB_CLIENT_SECRET = "fb372aa76de9573beec26c8939bdb87a721c8c15";
    private static final String GITHUB_REDIRECT_URI = "http://localhost:8080/oauth2/callback/github";
    private static final String GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USERINFO_API_URL = "https://api.github.com/user";

    private static final String GITHUB_USEREMAILINFO_API_URL = "https://api.github.com/user/emails";

    public String getAuthorizationUrl() {
        String authorizationUrl = "https://github.com/login/oauth/authorize";
        authorizationUrl += "?client_id=" + GITHUB_CLIENT_ID;
        authorizationUrl += "&redirect_uri=" + GITHUB_REDIRECT_URI;
        authorizationUrl += "&scope=user:email read:user";

        System.out.println("authorizationUrl: " + authorizationUrl);
        return authorizationUrl;
    }

    public User getUserInfoFromToken(String code, String accessToken) {
        System.out.println("\n\nTrying to getUserInfoFromToken " + accessToken +"\n\n");
        if (accessToken != null) {
            System.out.println("Trying to fetch user as the token is not null : " + accessToken +"\n\n");
            User user = fetchUserInfo(accessToken);
            assert user != null;
            user.setoAuthProvider("github");
            return user;
        }
        return null;
    }

    public String exchangeCodeForAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("client_id", GITHUB_CLIENT_ID);
        requestBody.put("client_secret", GITHUB_CLIENT_SECRET);
        requestBody.put("code", code);
        requestBody.put("redirect_uri", GITHUB_REDIRECT_URI);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                GITHUB_ACCESS_TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            System.out.println("Response Body (GitHub): " + responseBody); // Add this line for debugging

            try {
                JsonNode responseJson = objectMapper.readTree(responseBody);
                if (responseJson.has("access_token")) {
                    String accessToken = responseJson.get("access_token").asText();
                    return accessToken;
                } else if (responseJson.has("error")){
                    String errorMessage = responseJson.get("error").asText();
                    System.out.println(errorMessage);
                    return errorMessage;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the error response
            String errorResponse = responseEntity.getBody();
            System.out.println("Error Response: " + errorResponse);
            // Extract the error details from the response
            String error = null;
            String errorDescription = null;
            try {
                String[] params = errorResponse.split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = keyValue[1];
                        if (key.equals("error")) {
                            error = value;
                        } else if (key.equals("error_description")) {
                            errorDescription = value;
                        }
                    }
                }
            } catch (Exception e) {
                // Handle the exception
            }
            // Handle the error accordingly
            System.out.println("Error: " + error);
            System.out.println("Error Description: " + errorDescription);
        }

        return null;
    }

    private User fetchUserInfo(String accessToken) {

        System.out.println("Trying to fetchUserInfo : " + accessToken +"\n\n");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        System.out.println("Request Headers: " + requestEntity.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                GITHUB_USERINFO_API_URL,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("Response Headers: " + responseHeaders);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            System.out.println("Response Body: " + responseBody); // Add this line for debugging

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode responseJson = objectMapper.readTree(responseBody);
                String fullName = responseJson.get("name").asText();
                String firstName = "";
                String lastName = "";
                String username = responseJson.get("login").asText();


                if (fullName != null && !fullName.isEmpty()) {
                    String[] nameParts = fullName.split(" ");
                    if (nameParts.length > 0) {
                        firstName = nameParts[0];
                    }
                    if (nameParts.length > 1) {
                        lastName = nameParts[1];
                    }
                }

                String email = fetchUserEmail(accessToken); //responseJson.get("email").asText();
                String profilePicture = responseJson.get("avatar_url").asText();
                User user = new User(firstName, lastName, email, profilePicture, true, false, "github");
                user.setUsername(username);
                return user;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String fetchUserEmail(String accessToken) {

        System.out.println("Trying to fetchUserEmailInfo : " + accessToken +"\n\n");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        System.out.println("Request Headers: " + requestEntity.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                GITHUB_USEREMAILINFO_API_URL,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("Response Headers: " + responseHeaders);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            System.out.println("Response Body: " + responseBody); // Add this line for debugging

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode responseJson = objectMapper.readTree(responseBody);
                return responseJson.get(0).get("email").asText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}


