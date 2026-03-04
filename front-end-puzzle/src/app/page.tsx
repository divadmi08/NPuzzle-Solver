import Link from "next/link";

export default function Home() {
  const pages = [
    { href: "/game", label: "Gioca" },
    { href: "/settings", label: "Impostazioni" },
  ];

  return (
    <main className="flex flex-col items-center justify-center text-white space-y-12">
      
      <h1 className="text-6xl font-extrabold tracking-wide bg-gradient-to-r from-cyan-400 to-blue-500 bg-clip-text text-transparent">
        GAME HUB
      </h1>

      <div className="flex flex-col space-y-6 w-64">
        {pages.map((page) => (
          <Link
            key={page.href}
            href={page.href}
            className="text-center py-4 rounded-xl bg-blue-600 hover:bg-blue-500 transition-all duration-300 text-xl font-semibold shadow-lg hover:scale-105 active:scale-95"
          >
            {page.label}
          </Link>
        ))}
      </div>
    </main>
  );
}