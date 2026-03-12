'use client';

export default function GameLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="w-full h-full">
      <main className="h-full w-full">{children}</main>
    </div>
  );
}