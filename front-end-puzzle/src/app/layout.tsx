
import "./globals.css";
import Provider from "@/components/Provider";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="it">
      <Provider>
        <body className="h-screen w-screen overflow-hidden bg-gradient-to-br from-blue-900 via-indigo-900 to-black">
          <div className="h-full w-full flex items-center justify-center p-2 sm:p-4">
            <div className="w-full h-full max-w-[1700px] bg-slate-900/80 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 flex flex-col overflow-hidden">
              {children}
            </div>
          </div>
        </body>
      </Provider>
    </html>
  );
}