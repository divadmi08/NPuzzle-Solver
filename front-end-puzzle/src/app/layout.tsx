import Providers from "@/components/providers";
import "./globals.css";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="it">
      <Providers>
        <body className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-900 via-indigo-900 to-black">
          <div className="w-[90vw] max-w-4xl h-[70vh] bg-slate-900/80 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 flex items-center justify-center">
            {children}
          </div>
        </body>
      </Providers>
    </html>
  );
}