package com.vincasturskis.personal_website_backend;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message.RecipientType;

import com.google.api.services.gmail.model.Message;

@Component
public class EmailServiceImpl implements EmailService {

    private final String RECEIVER_EMAIL = "vincas.turskis@gmail.com";
    //private final String SUBJECT = "New Message From Personal Website";
    private final String SENDER_EMAIL = "personalwebsitevincasturskis@gmail.com";

    private static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
    private static Message createMessageWithEmail(MimeMessage email) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        String encodedEmail = java.util.Base64.getUrlEncoder().encodeToString(buffer.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    
    public void sendEmail(Gmail service, EmailData data) throws Exception
    {
        String emailBody = 
                "A person has filled out the contact form on the personal website\n\n"+
                "Name: " + data.getName() +"\n\n"+
                "Email: " + data.getEmail() + "\n\n"+
                "Message body:\n"+
                data.getBody();
        
        String subject = "(Personal Website) New Message From \"" + data.getName() + "\" (" + data.getEmail() + ")";
        MimeMessage email = createEmail(RECEIVER_EMAIL, SENDER_EMAIL, subject, emailBody);
        Message gmailMessage = createMessageWithEmail(email);
        try
        {
            service.users().messages().send("me", gmailMessage).execute();
        }
        catch (GoogleJsonResponseException e) {
            System.out.println("Google API Error: " + e.getDetails());
        }
    }
}
