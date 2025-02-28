"use client";

import Image from "next/image";
import Login from "./components/LoginComponent";
import { useRouter } from "next/navigation";

export default function Home() {
  const router = useRouter();

  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
      <main className="flex flex-col gap-8 row-start-2 items-center sm:items-start">
        <h1 className="text-2xl font-bold">Bem vindo ao Sistema de Matrículas</h1>
        <div className="p-4 rounded-xl ring w-full">
          <h1 className="text-xl font-semibold mb-4">Login</h1>
          <Login />
        </div>
      </main>
    </div>
  );
}
