package com.vincasturskis.personal_website_backend;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> sendEmail(@RequestBody String entity) {
        try {
            System.out.println("Request received");
            ObjectMapper objectMapper = new ObjectMapper();
            EmailData emailData = objectMapper.readValue(entity, EmailData.class);
            emailService.sendEmail(emailData);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
