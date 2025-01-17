package com.vincasturskis.personal_website_backend;

import com.google.api.services.gmail.Gmail;

public interface EmailService {
    public void sendEmail(Gmail service, EmailData data) throws Exception;
}


