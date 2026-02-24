'use client';

import { isHmrRefresh } from "next/dist/server/app-render/work-unit-async-storage.external";
import Link from "next/link";

interface ControlLink{
    href: string;
    label: string;
}


// bottoni:
// pausa tempo, reaset tempo, preview, impostazioni, fulldestra: tempo, mosse fatte, quit

// tempo solonella tabella riferimentoreset qppena si cambia

export default function ControlBar() {
    return(
        <nav className=" bg-zinc-900 border-b border-zinc-800">
            <div></div>
        </nav>
    )
}