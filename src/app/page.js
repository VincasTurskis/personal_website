'use client'

import Image from "next/image";
import NavBar from "./navBar";
import NavTest from "./navTest";
import { useRouter, redirect } from 'next/navigation';

export default function Home() {

  return (
    redirect("/contact")
  );
}
