'use client';


import { useRouter } from "next/navigation";

export default function GameLayout({ children }: { children: React.ReactNode }) {
  const moves = 0;
  const router = useRouter();

  return (
    <div className="w-full h-full flex flex-col">
      
      {/* Barra sopra */}
      

      {/* Contenuto gioco */}
      <main className="flex-1 flex items-center justify-center pt-20">
        {children}
      </main>

    </div>
  );
}