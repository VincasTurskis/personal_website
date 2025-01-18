'use client'

import React from 'react';
import Styles from './styles.module.css';
import DOMPurify from 'dompurify';
import Snackbar from '@mui/material/Snackbar';
import SnackbarContent from '@mui/material/SnackbarContent';


export default function Contact()
{
    const [openSuccess, setOpenSuccess] = React.useState(false);
    const [openFail, setOpenFail] = React.useState(false);
    const vertical = 'top'
    const horizontal = 'right';
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
            .then((response) => {
                if(response.ok)
                {
                    setOpenSuccess(true);
                }
                else
                {
                    setOpenFail(true);
                }
            });
    }
    function handleToastClose()
    {
        setOpenSuccess(false);
        setOpenFail(false);
    }
    return (
        <div>
            <Snackbar
                anchorOrigin={{ vertical, horizontal }}
                autoHideDuration={4000}
                open = {openSuccess}
                onClose={handleToastClose}
                key="toastSuccess"
            >
                <SnackbarContent style = {{
                    backgroundColor: '#15803d',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
                message = {<p>Form submitted successfully!</p>}
                />
            </Snackbar>

            <Snackbar
                anchorOrigin={{ vertical, horizontal }}
                autoHideDuration={8000}
                open = {openFail}
                onClose={handleToastClose}
                key="toastFail"
            >
                <SnackbarContent style = {{
                    backgroundColor: '#dc2626',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
                message = {<p>Something went wrong</p>}
                />
            </Snackbar>
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
        </div>
    )
}