package com.vincasturskis.personal_website_backend;

import java.beans.JavaBean;

@JavaBean
public class EmailData
{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String email;
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailData()
    {
        name = "No Name Provided";
        email = "No Email Provided";
        body = "No Body Provided";
    }
}
