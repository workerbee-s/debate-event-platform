package me.shreyeschekuru.dsa.data.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {
    @Value("${mailgun.apiKey}")
    private String mailgunApiKey;

    @Value("${mailgun.domain}")
    private String mailgunDomain;

    public void sendRegistrationEmail(String recipientEmail, String recipientName) {
        String from = "Debate Platorm <connect@shreyeschekuru.me>";
        String to = recipientName + " <" + recipientEmail + ">";
        String template = "dsa-welcome-aboard"; // Replace with the name of your Mailgun template

        String mailgunUrl = "https://api.mailgun.net/v3/" + mailgunDomain + "/messages";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("api", mailgunApiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("from", from);
        map.add("to", to);
        map.add("template", template);
        map.add("recipientName", recipientName);
        map.add("h:X-Mailgun-Variables", "{\"recipientName\": \"" + recipientName + "\"}");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                mailgunUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Email sent successfully!");
        } else {
            System.out.println("Failed to send email. Response: " + responseEntity.getBody());
        }
    }
}
