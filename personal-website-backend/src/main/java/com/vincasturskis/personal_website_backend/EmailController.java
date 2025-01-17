package com.vincasturskis.personal_website_backend;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.gmail.Gmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @PostMapping(
        value = "/sendEmail", consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = {"http://localhost:3000", "https://vincasturskis.netlify.app"})
    public String sendEmail(@RequestBody String entity) {
        
        try {
            System.out.println("Request received");
            Gmail service = GmailOAuthService.getGmailService();
            ObjectMapper objectMapper = new ObjectMapper();
            EmailData emailData = objectMapper.readValue(entity, EmailData.class);
            emailService.sendEmail(service, emailData);
            System.out.println(entity);
            return "Email Sent Successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}
