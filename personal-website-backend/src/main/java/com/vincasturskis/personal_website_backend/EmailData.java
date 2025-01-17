package com.vincasturskis.personal_website_backend;

import java.beans.JavaBean;

import com.fasterxml.jackson.annotation.JsonProperty;

@JavaBean
public class EmailData
{
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("email")
    private String email;
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @JsonProperty("body")
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
