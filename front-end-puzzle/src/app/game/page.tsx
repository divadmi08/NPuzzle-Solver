import Table from "@/components/Table";
import Link from "next/link";

export default function Home({nTabelle}: {nTabelle: number}) {
  const pages = [
    { href: "/game", label: "Gioca" },
    { href: "/settings", label: "Impostazioni" },
  ];



  return (
    <main className="flex flex-col items-center justify-center text-white space-y-12">
      <Table nTabelle={nTabelle} />
    </main>
  );
}