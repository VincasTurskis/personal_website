'use client'

import React from 'react';
import { useRouter } from 'next/navigation';

const pages = {
  'About Me':'aboutMe',
  'About This Website':"/aboutThisWebsite",
  'Contact':"/contact",
};
export default function NavTest() {
    const router = useRouter();

    const handleRouting = (route) => {
        console.log('Route:', route);
        router.push(route);
    };
    
    return (
        <div>
            {Object.entries(pages).map(([page, route]) => (
                <button key={page} type='button' onClick={() => handleRouting(route)}>{page}</button>
                )
            )}
        </div>
    )
}