'use client'

import React from 'react';
import Styles from './styles.module.css';
import DOMPurify from 'dompurify';

export default function Contact()
{
    async function handleSubmit(formData)
    {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                name: DOMPurify.sanitize(formData.get("personName")),
                email: DOMPurify.sanitize(formData.get("email")),
                body: DOMPurify.sanitize(formData.get("body"))
            })
        };
        await fetch("http://localhost:8080/sendEmail", requestOptions)
        .then((_) => (console.log("Sent the request")))
    }
    return (
        <div className="min-h-screen bg-green-50 grid grid-rows-[20px_1fr_20px] grid-cols-[20px_1fr_20px] items-center justify-items-center">
            <div className="col-start-2 row-start-2">
                <form action={handleSubmit} name="contactForm">
                    <h1 className="text-5xl font-bold">Get In Touch:</h1>
                    <br />
                    <p><label htmlFor='personName'>Your name:</label></p>
                    <input required className={Styles.inputField} type='text' id='personName' name='personName' size='10' />
                    <br />
                    <br />
                    <p><label htmlFor='emailField'>Your email address:</label></p>
                    <input required className={Styles.inputField} type='email' id='email' name='email' size='10' />
                    <br />
                    <br />
                    <p><label htmlFor='body'>Your message:</label></p>
                    <textarea required className={Styles.inputField} id="body" name="body"></textarea>
                    <br />
                    <br />
                    <input className={Styles.button} type="submit" value="Submit"></input>
                </form>
            </div>
        </div>
    )
}