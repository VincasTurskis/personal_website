'use client'

import React from 'react';
import Styles from './styles.module.css';

export default function Contact()
{
    function handleSubmit(formData)
    {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                name: formData.get("personName"),
                email: formData.get("email"),
                Message: formData.get("body")
            })
        };
        fetch("http://localhost:8080/sendEmail", requestOptions)
        .then((response) => (console.log("Sent the request")))
        console.log("You have submitted data\n:",
            `Name: ${formData.get("personName")}\n`,
            `Email: ${formData.get("email")}\n`,
            `Message: ${formData.get("body")}`);
    }
    return (
        <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center">
            <div className="row-start-2">
                <form action={handleSubmit}>
                    <p><label htmlFor='personName'>Your name:</label></p>
                    <input className={Styles.inputField} type='text' id='personName' name='personName' size='10' />
                    <br />
                    <p><label htmlFor='emailField'>Your email address:</label></p>
                    <input className={Styles.inputField} type='email' id='email' name='email' size='10' />
                    <br />
                    <p><label htmlFor='body'>Your message:</label></p>
                    <textarea className={Styles.inputField} id="body" name="body"></textarea>
                    <br />
                    <input className={Styles.button} type="submit" value="Submit"></input>
                </form>
            </div>
        </div>
    )
}