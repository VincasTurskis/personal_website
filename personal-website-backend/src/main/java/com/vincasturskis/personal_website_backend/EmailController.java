package com.vincasturskis.personal_website_backend;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EmailController {
    
    @PostMapping(
        value = "/sendEmail", consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public String sendEmail(@RequestBody String entity) {
        System.out.println("Request received:");
        System.out.println(entity);
        return entity;
    }
}
