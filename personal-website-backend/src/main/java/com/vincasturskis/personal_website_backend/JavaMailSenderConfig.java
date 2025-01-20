package com.vincasturskis.personal_website_backend;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderConfig {

    // I'm using environment variables for this, because the hosting service (Render) is too complicated to set up with secrets.
    // If someone hacks this, they'll get access to a throwaway gmail account.
    public static final String USERNAME_ENV_VAR = "USERNAME";
    public static final String PASSWORD_ENV_VAR = "PASSWORD";

    @Bean
    JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(System.getenv(USERNAME_ENV_VAR));
        mailSender.setPassword(System.getenv(PASSWORD_ENV_VAR));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.debug", "true"); // Optional: Enable for debugging

        return mailSender;
    }
}
