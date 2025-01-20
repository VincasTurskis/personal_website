package com.vincasturskis.personal_website_backend;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message.RecipientType;

@Service
public class EmailServiceImpl implements EmailService {

    private final String RECEIVER_EMAIL = "vincas.turskis@gmail.com";
    //private final String SUBJECT = "New Message From Personal Website";
    private final String SENDER_EMAIL = "personalwebsitevincasturskis@gmail.com";

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    private static MimeMessage createEmailMessage(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
    public void sendEmail(EmailData data) throws Exception
    {
        String emailBody = 
                "A person has filled out the contact form on the personal website\n\n"+
                "Name: " + data.getName() +"\n\n"+
                "Email: " + data.getEmail() + "\n\n"+
                "Message body:\n"+
                data.getBody();
        
        String subject = "(Personal Website) New Message From \"" + data.getName() + "\" (" + data.getEmail() + ")";
        MimeMessage message = createEmailMessage(RECEIVER_EMAIL, SENDER_EMAIL, subject, emailBody);

        mailSender.send(message);

    }
}
