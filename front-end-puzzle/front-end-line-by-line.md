# Frontend Line-by-Line (src/)

Questo file analizza il frontend riga per riga. Ogni sezione e' raggiungibile tramite ancora.

## Indice
1. [src/app/layout.tsx](#src-app-layout-tsx)
2. [src/app/page.tsx](#src-app-page-tsx)
3. [src/app/game/layout.tsx](#src-app-game-layout-tsx)
4. [src/app/game/page.tsx](#src-app-game-page-tsx)
5. [src/app/custom/page.tsx](#src-app-custom-page-tsx)
6. [src/app/settings/page.tsx](#src-app-settings-page-tsx)
7. [src/app/globals.css](#src-app-globals-css)
8. [src/app/favicon.ico](#src-app-favicon-ico)
9. [src/components/Provider.tsx](#src-components-provider-tsx)
10. [src/utils/cn.ts](#src-utils-cn-ts)
11. [src/features/puzzle/types/puzzle.ts](#src-features-puzzle-types-puzzle-ts)
12. [src/features/puzzle/constants/puzzle.ts](#src-features-puzzle-constants-puzzle-ts)
13. [src/features/puzzle/utils/puzzle.ts](#src-features-puzzle-utils-puzzle-ts)
14. [src/features/puzzle/hooks/usePlayback.ts](#src-features-puzzle-hooks-useplayback-ts)
15. [src/features/puzzle/store/usePuzzleStore.ts](#src-features-puzzle-store-usepuzzlestore-ts)
16. [src/features/puzzle/store/puzzleSelectors.ts](#src-features-puzzle-store-puzzleselectors-ts)
17. [src/features/puzzle/api/solverApi.ts](#src-features-puzzle-api-solverapi-ts)
18. [src/features/puzzle/components/GameClient.tsx](#src-features-puzzle-components-gameclient-tsx)
19. [src/features/puzzle/components/GamePlayNavbar.tsx](#src-features-puzzle-components-gameplaynavbar-tsx)
20. [src/features/puzzle/components/PuzzleGrid.tsx](#src-features-puzzle-components-puzzlegrid-tsx)
21. [src/features/puzzle/components/Controls.tsx](#src-features-puzzle-components-controls-tsx)
22. [src/features/puzzle/components/Header.tsx](#src-features-puzzle-components-header-tsx)
23. [src/features/puzzle/components/MoveInfo.tsx](#src-features-puzzle-components-moveinfo-tsx)
24. [src/features/puzzle/components/MoveList.tsx](#src-features-puzzle-components-movelist-tsx)
25. [src/features/puzzle/components/ProgressBar.tsx](#src-features-puzzle-components-progressbar-tsx)
26. [src/features/puzzle/components/SpeedControl.tsx](#src-features-puzzle-components-speedcontrol-tsx)
27. [src/features/puzzle/components/StatesPreview.tsx](#src-features-puzzle-components-statespreview-tsx)
28. [src/features/puzzle/components/Tile.tsx](#src-features-puzzle-components-tile-tsx)
29. [src/features/puzzle/components/Footer.tsx](#src-features-puzzle-components-footer-tsx)

<a id="src-app-layout-tsx"></a>
## src/app/layout.tsx

### Import

#### Codice
```typescript
import "./globals.css";
import Provider from "@/components/Provider";

```

#### Spiegazione
Linea 2: Importa i CSS globali dell'app.
Linea 3: Importa il componente Provider (wrapper per i children).
Linea 4: Riga vuota per separare import e funzione.

### Componente: RootLayout

#### Codice
```typescript
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
```

#### Spiegazione
Linea 5: Definisce il componente RootLayout esportato di default.
Linea 6: Estrae la prop children dalla firma della funzione.
Linea 7: Apre il tipo della prop children.
Linea 8: Tipizza children come React.ReactNode.
Linea 9: Chiude la definizione del tipo props.
Linea 10: Inizia il return del JSX.
Linea 11: Apre il tag html e imposta la lingua su italiano.
Linea 12: Avvolge il body con Provider.
Linea 13: Apre il body con classi Tailwind per full screen e gradiente.
Linea 14: Apre un contenitore centrato con padding responsive.
Linea 15: Apre il pannello principale con max width, blur e bordi.
Linea 16: Renderizza i children dentro il pannello.
Linea 17: Chiude il pannello principale.
Linea 18: Chiude il contenitore esterno.
Linea 19: Chiude il body.
Linea 20: Chiude Provider.
Linea 21: Chiude il tag html.
Linea 22: Chiude il return.
Linea 23: Chiude la funzione RootLayout.

<a id="src-app-page-tsx"></a>
## src/app/page.tsx

### Import

#### Codice
```typescript
import Link from "next/link";

```

#### Spiegazione
Linea 1: Importa il componente Link di Next.js per la navigazione client-side.
Linea 2: Riga vuota per separare import e funzione.

### Componente: Home

#### Codice
```typescript
export default function Home() {
```

#### Spiegazione
Linea 3: Definisce il componente Home esportato di default.

### Costante: pages

#### Codice
```typescript
  const pages = [
    { href: "/game", label: "Gioca" },
    { href: "/custom", label: "Tabella Manuale" },
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
```

#### Spiegazione
Linea 4: Crea un array di pagine con route e label.
Linea 5: Definisce il link alla pagina di gioco.
Linea 6: Definisce il link alla tabella manuale.
Linea 7: Definisce il link alla pagina impostazioni.
Linea 8: Chiude l'array pages.
Linea 9: Riga vuota per separare dati e JSX.
Linea 10: Inizia il return del JSX.
Linea 11: Apre il main con layout centrato e testo bianco.
Linea 12: Riga vuota per separare header e lista.
Linea 13: Apre il titolo principale con stile gradient.
Linea 14: Inserisce il testo "GAME HUB".
Linea 15: Chiude il tag h1.
Linea 16: Riga vuota per separare titolo e pulsanti.
Linea 17: Apre il contenitore dei link con spaziatura verticale.
Linea 18: Mappa l'array pages in una lista di Link.
Linea 19: Apre il tag Link.
Linea 20: Usa l'href come key per React.
Linea 21: Imposta l'href del link.
Linea 22: Imposta classi Tailwind per stile bottone.
Linea 23: Chiude il tag di apertura del Link.
Linea 24: Renderizza la label del link.
Linea 25: Chiude il Link.
Linea 26: Chiude la map.
Linea 27: Chiude il contenitore dei link.
Linea 28: Chiude il main.
Linea 29: Chiude il return.
Linea 30: Chiude la funzione Home.

<a id="src-app-game-layout-tsx"></a>
## src/app/game/layout.tsx

### Direttive

#### Codice
```typescript
'use client';

```

#### Spiegazione
Linea 1: Direttiva client per usare hook e state nel layout.
Linea 2: Riga vuota per leggibilita'.

### Componente: GameLayout

#### Codice
```typescript
export default function GameLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="w-full h-full">
      <main className="h-full w-full">{children}</main>
    </div>
  );
}
```

#### Spiegazione
Linea 3: Definisce il layout della route /game.
Linea 4: Inizia il return del JSX.
Linea 5: Apre un wrapper full size.
Linea 6: Inserisce il main e renderizza i children.
Linea 7: Chiude il wrapper.
Linea 8: Chiude il return.
Linea 9: Chiude la funzione GameLayout.

<a id="src-app-game-page-tsx"></a>
## src/app/game/page.tsx

### Import

#### Codice
```typescript
import GameClient from "@/features/puzzle/components/GameClient";

```

#### Spiegazione
Linea 1: Importa GameClient che contiene la UI del gioco.
Linea 2: Riga vuota per separare import e funzione.

### Componente: Page

#### Codice
```typescript
export default function Page() {
  return <GameClient />;
}
```

#### Spiegazione
Linea 3: Definisce il componente Page.
Linea 4: Renderizza GameClient.
Linea 5: Chiude la funzione Page.

<a id="src-app-custom-page-tsx"></a>
## src/app/custom/page.tsx

### Direttive

#### Codice
```typescript
"use client";

```

#### Spiegazione
Linea 1: Direttiva client per usare hook e stato nel componente.
Linea 2: Riga vuota per leggibilita'.

### Import

#### Codice
```typescript
import Link from 'next/link';
import { useMemo, useState } from 'react';
import type { GridSize } from '@/features/puzzle/types/puzzle';
import { useSetCustomBoard, useSetSolutionMovesFromApi } from '@/features/puzzle/store/puzzleSelectors';
import { useRouter } from 'next/navigation';
import { postSolvePuzzle } from '@/features/puzzle/api/solverApi';

```

#### Spiegazione
Linea 3: Importa Link per navigazione client-side.
Linea 4: Importa useMemo e useState da React.
Linea 5: Importa il tipo GridSize.
Linea 6: Importa selector per impostare tabella custom e mosse soluzione.
Linea 7: Importa useRouter per navigazione programmata.
Linea 8: Importa la funzione API per risolvere il puzzle.
Linea 9: Riga vuota per separare import e funzioni helper.

### Funzione: createEmptyGrid

#### Codice
```typescript
function createEmptyGrid(size: GridSize): number[][] {
  return Array.from({ length: size }, () => Array.from({ length: size }, () => 0));
}

```

#### Spiegazione
Linea 10: Dichiarazione helper createEmptyGrid (non usata nel file).
Linea 11: Ritorna una matrice size x size inizializzata a 0.
Linea 12: Chiude createEmptyGrid.
Linea 13: Riga vuota per separare helper.

### Funzione: parseCell

#### Codice
```typescript
function parseCell(value: string): number {
```

#### Spiegazione
Linea 14: Dichiarazione helper parseCell per convertire stringa in numero.

### Costante: num

#### Codice
```typescript
  const num = Number(value.trim());
  return Number.isNaN(num) ? -1 : num;
}

```

#### Spiegazione
Linea 15: Converte il valore in numero dopo trim.
Linea 16: Ritorna -1 se NaN, altrimenti il numero.
Linea 17: Chiude parseCell.
Linea 18: Riga vuota per separare helper.

### Funzione: validateGridValues

#### Codice
```typescript
function validateGridValues(values: number[], size: GridSize): string | null {
```

#### Spiegazione
Linea 19: Dichiarazione helper validateGridValues.

### Costante: maxValue

#### Codice
```typescript
  const maxValue = size * size - 1;

  if (values.some((v) => v < 0 || v > maxValue)) {
    return `I valori devono essere compresi tra 0 e ${maxValue}.`;
  }

```

#### Spiegazione
Linea 20: Calcola il valore massimo consentito (size^2 - 1).
Linea 21: Riga vuota per separare blocco.
Linea 22: Verifica che tutti i valori siano nel range 0..maxValue.
Linea 23: Ritorna messaggio di errore sul range.
Linea 24: Chiude il blocco if del range.
Linea 25: Riga vuota per separare blocco.

### Costante: uniq

#### Codice
```typescript
  const uniq = new Set(values);
  if (uniq.size !== values.length) {
    return 'Ogni numero deve comparire una sola volta.';
  }

  for (let n = 0; n <= maxValue; n++) {
    if (!uniq.has(n)) {
      return `Manca il numero ${n}.`;
    }
  }

  return null;
}

```

#### Spiegazione
Linea 26: Crea un Set per controllare duplicati.
Linea 27: Confronta dimensione Set con array.
Linea 28: Ritorna errore se ci sono duplicati.
Linea 29: Chiude il blocco if duplicati.
Linea 30: Riga vuota per separare blocco.
Linea 31: Ciclo su tutti i numeri richiesti.
Linea 32: Se un numero manca, ritorna errore.
Linea 33: Messaggio di errore per numero mancante.
Linea 34: Chiude l'if di numero mancante.
Linea 35: Chiude il ciclo for.
Linea 36: Riga vuota per separare blocco.
Linea 37: Ritorna null se non ci sono errori.
Linea 38: Chiude validateGridValues.
Linea 39: Riga vuota per separare helper.

### Funzione: toMatrix

#### Codice
```typescript
function toMatrix(values: number[], size: GridSize): number[][] {
```

#### Spiegazione
Linea 40: Dichiarazione helper toMatrix.

### Costante: matrix

#### Codice
```typescript
  const matrix: number[][] = [];
  for (let r = 0; r < size; r++) {
    matrix.push(values.slice(r * size, (r + 1) * size));
  }
  return matrix;
}

```

#### Spiegazione
Linea 41: Inizializza la matrice di output.
Linea 42: Ciclo sulle righe.
Linea 43: Aggiunge una slice di size elementi per ogni riga.
Linea 44: Chiude il ciclo for.
Linea 45: Ritorna la matrice.
Linea 46: Chiude toMatrix.
Linea 47: Riga vuota per separare dalla componente principale.

### Componente: CustomBoardPage

#### Codice
```typescript
export default function CustomBoardPage() {
```

#### Spiegazione
Linea 48: Definisce il componente CustomBoardPage.

### Costante: router

#### Codice
```typescript
  const router = useRouter();
```

#### Spiegazione
Linea 49: Inizializza il router per la navigazione.

### Costante: setCustomBoard

#### Codice
```typescript
  const setCustomBoard = useSetCustomBoard();
```

#### Spiegazione
Linea 50: Recupera l'azione per impostare tabella custom.

### Costante: setSolutionMovesFromApi

#### Codice
```typescript
  const setSolutionMovesFromApi = useSetSolutionMovesFromApi();

```

#### Spiegazione
Linea 51: Recupera l'azione per impostare mosse da API.
Linea 52: Riga vuota per separare stato e azioni.

### Sezione

#### Codice
```typescript
  const [gridSize, setGridSize] = useState<GridSize>(4);
```

#### Spiegazione
Linea 53: Stato della dimensione griglia (default 4).

### Sezione

#### Codice
```typescript
  const [cellValues, setCellValues] = useState<string[]>(Array.from({ length: 16 }, (_, i) => String(i)));
```

#### Spiegazione
Linea 54: Stato dei valori celle (default 0..15).

### Sezione

#### Codice
```typescript
  const [isSubmitting, setIsSubmitting] = useState(false);
```

#### Spiegazione
Linea 55: Stato di invio in corso.

### Sezione

#### Codice
```typescript
  const [feedback, setFeedback] = useState<string | null>(null);
```

#### Spiegazione
Linea 56: Stato messaggi di feedback.

### Sezione

#### Codice
```typescript
  const [error, setError] = useState<string | null>(null);

```

#### Spiegazione
Linea 57: Stato per errore bloccante (overlay).
Linea 58: Riga vuota per separare calcoli derivati.

### Costante: maxValue

#### Codice
```typescript
  const maxValue = useMemo(() => gridSize * gridSize - 1, [gridSize]);

```

#### Spiegazione
Linea 59: Calcola maxValue con useMemo in base alla dimensione.
Linea 60: Riga vuota per separare handler.

### Costante: handleSizeChange

#### Codice
```typescript
  const handleSizeChange = (size: GridSize) => {
    setGridSize(size);
    setCellValues(Array.from({ length: size * size }, (_, i) => String(i)));
    setFeedback(null);
  };

```

#### Spiegazione
Linea 61: Handler per cambio dimensione griglia.
Linea 62: Aggiorna la dimensione.
Linea 63: Rigenera l'array dei valori celle.
Linea 64: Reset del feedback.
Linea 65: Chiude handleSizeChange.
Linea 66: Riga vuota per separare handler.

### Costante: updateCell

#### Codice
```typescript
  const updateCell = (index: number, value: string) => {
    setCellValues((prev) => {
```

#### Spiegazione
Linea 67: Handler per aggiornare una singola cella.
Linea 68: Aggiorna lo stato cellValues con funzione.

### Costante: next

#### Codice
```typescript
      const next = [...prev];
      next[index] = value;
      return next;
    });
  };

```

#### Spiegazione
Linea 69: Clona l'array precedente.
Linea 70: Aggiorna l'indice richiesto.
Linea 71: Ritorna il nuovo array.
Linea 72: Chiude setCellValues.
Linea 73: Chiude updateCell.
Linea 74: Riga vuota per separare handler.

### Costante: buildGrid

#### Codice
```typescript
  const buildGrid = (): number[][] | null => {
```

#### Spiegazione
Linea 75: Handler per costruire la griglia validata.

### Costante: numbers

#### Codice
```typescript
    const numbers = cellValues.map(parseCell);
```

#### Spiegazione
Linea 76: Converte i valori stringa in numeri.

### Costante: error

#### Codice
```typescript
    const error = validateGridValues(numbers, gridSize);
    if (error) {
      setFeedback(error);
      return null;
    }
    return toMatrix(numbers, gridSize);
  };

```

#### Spiegazione
Linea 77: Valida la griglia.
Linea 78: Se c'e' un errore, lo mostra.
Linea 79: Imposta il feedback con l'errore.
Linea 80: Ritorna null per bloccare il flusso.
Linea 81: Chiude l'if di errore.
Linea 82: Ritorna la matrice validata.
Linea 83: Chiude buildGrid.
Linea 84: Riga vuota per separare handler.

### Costante: handleUseBoard

#### Codice
```typescript
  const handleUseBoard = () => {
```

#### Spiegazione
Linea 85: Handler per usare la tabella senza API.

### Costante: grid

#### Codice
```typescript
    const grid = buildGrid();
    if (!grid) return;

    setCustomBoard(gridSize, grid);
    setFeedback('Tabella salvata. Ora puoi giocare o avviare la soluzione.');
    router.push('/game');
  };

```

#### Spiegazione
Linea 86: Costruisce la griglia.
Linea 87: Esce se la griglia non e' valida.
Linea 88: Riga vuota per separare side effect.
Linea 89: Salva la griglia nello store.
Linea 90: Imposta un messaggio di feedback.
Linea 91: Naviga alla pagina /game.
Linea 92: Chiude handleUseBoard.
Linea 93: Riga vuota per separare handler.

### Costante: handleResolveWithApi

#### Codice
```typescript
  const handleResolveWithApi = async () => {
```

#### Spiegazione
Linea 94: Handler async per risolvere via API.

### Costante: grid

#### Codice
```typescript
    const grid = buildGrid();
    if (!grid) return;

    setIsSubmitting(true);
    setFeedback('Invio richiesta API in corso...');

    try {
      setCustomBoard(gridSize, grid);
```

#### Spiegazione
Linea 95: Costruisce la griglia.
Linea 96: Esce se non valida.
Linea 97: Riga vuota per separare stato.
Linea 98: Imposta isSubmitting a true.
Linea 99: Mostra feedback di invio API.
Linea 100: Riga vuota per separare try/catch.
Linea 101: Inizia il blocco try.
Linea 102: Salva la griglia nello store.

### Costante: moves

#### Codice
```typescript
      const moves = await postSolvePuzzle(grid);

      setSolutionMovesFromApi(moves);
      setFeedback(`Soluzione caricata: ${moves.length} mosse.`);
      router.push('/game');
    } catch (err) {
      // Verifica se Ã¨ un errore di puzzle non risolvibile
      if (err instanceof Error && (err.message.includes('500') || err.message.includes('not solvable'))) {
        setError('âŒ Tabella non risolvibile\n\nLa tabella inserita non puÃ² essere risolta. Verifica i numeri e riprova!');
      } else {
        setFeedback('API non disponibile o risposta non valida. La tabella Ã¨ stata salvata comunque.');
      }
    } finally {
      setIsSubmitting(false);
    }
  };

```

#### Spiegazione
Linea 103: Chiama l'API di solve e attende le mosse.
Linea 104: Riga vuota per separare side effect.
Linea 105: Salva le mosse nello store e passa in replay.
Linea 106: Mostra feedback con numero di mosse.
Linea 107: Naviga alla pagina /game.
Linea 108: Passa al blocco catch.
Linea 109: Commento: controllo errore puzzle non risolvibile.
Linea 110: Se errore contiene 500 o not solvable, usa errore bloccante.
Linea 111: Imposta il testo di errore per puzzle non risolvibile.
Linea 112: Altrimenti, entra nel ramo else.
Linea 113: Feedback soft se API non disponibile.
Linea 114: Chiude il blocco else.
Linea 115: Entra nel blocco finally.
Linea 116: Resetta isSubmitting.
Linea 117: Chiude finally.
Linea 118: Chiude handleResolveWithApi.
Linea 119: Riga vuota per separare calcoli UI.

### Costante: cellsCount

#### Codice
```typescript
  const cellsCount = gridSize * gridSize;
```

#### Spiegazione
Linea 120: Calcola il numero di celle.

### Costante: boardMaxWidth

#### Codice
```typescript
  const boardMaxWidth = gridSize === 3 ? 360 : 460;

  return (
    <main className="h-full w-full overflow-y-auto px-3 py-3 sm:px-5 sm:py-5 text-white">
      <div className="h-full w-full rounded-2xl border border-slate-700 bg-slate-900/80 p-4 sm:p-6 shadow-2xl flex flex-col">
        <div className="flex flex-col items-center justify-center gap-3 text-center">
          <h1 className="text-2xl sm:text-3xl font-bold">Tabella Manuale</h1>
          <div className="flex flex-wrap items-center justify-center gap-2">
            <Link
              href="/"
              className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-sm font-semibold hover:bg-gray-700"
            >
              Home
            </Link>
            <Link
              href="/game"
              className="rounded-lg border border-cyan-500/60 bg-cyan-700 px-3 py-1.5 text-sm font-semibold hover:bg-cyan-600"
            >
              Vai al Gioco
            </Link>
          </div>
        </div>

        <div className="mt-5 min-h-0 flex-1 grid grid-cols-1 xl:grid-cols-3 gap-4">
          <section className="w-full rounded-xl border border-slate-700 bg-slate-800/70 p-4 xl:col-span-1 xl:overflow-y-auto">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Configurazione</h2>
            <div className="mt-3 flex gap-2">
              <button
                onClick={() => handleSizeChange(3)}
                className={`rounded-lg border px-3 py-2 text-sm font-semibold ${
                  gridSize === 3 ? 'border-emerald-400/70 bg-emerald-700' : 'border-slate-600 bg-slate-700'
                }`}
              >
                3 x 3
              </button>
              <button
                onClick={() => handleSizeChange(4)}
                className={`rounded-lg border px-3 py-2 text-sm font-semibold ${
                  gridSize === 4 ? 'border-emerald-400/70 bg-emerald-700' : 'border-slate-600 bg-slate-700'
                }`}
              >
                4 x 4
              </button>
            </div>

            <p className="mt-4 text-xs text-slate-400">
              Inserisci tutti i numeri da 0 a {maxValue} una sola volta. Lo 0 e la casella vuota.
            </p>

            <div className="mt-4 flex flex-col gap-2">
              <button
                onClick={handleUseBoard}
                disabled={isSubmitting}
                className="rounded-lg border border-sky-400/60 bg-sky-700 px-4 py-2 text-sm font-semibold hover:bg-sky-600 disabled:opacity-60"
              >
                Usa Questa Tabella
              </button>
              <button
                onClick={handleResolveWithApi}
                disabled={isSubmitting}
                className="rounded-lg border border-orange-400/60 bg-orange-600 px-4 py-2 text-sm font-semibold hover:bg-orange-500 disabled:opacity-60"
              >
                {isSubmitting ? 'Richiesta API...' : 'Risolvi Con API'}
              </button>
            </div>
          </section>

          <section className="w-full rounded-xl border border-slate-700 bg-slate-800/70 p-4 flex min-h-0 flex-col xl:col-span-2">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Posizione Numeri</h2>
            <div className="mt-4 min-h-0 flex-1 flex items-center justify-center">
              <div
                className="mx-auto grid w-full gap-2"
                style={{
                  gridTemplateColumns: `repeat(${gridSize}, minmax(0, 1fr))`,
                  maxWidth: `min(${boardMaxWidth}px, calc(100vh - 290px))`,
                }}
              >
                {Array.from({ length: cellsCount }).map((_, index) => (
                  <input
                    key={index}
```

#### Spiegazione
Linea 121: Imposta larghezza massima board in base alla dimensione.
Linea 122: Riga vuota per separare JSX.
Linea 123: Inizia il return del JSX.
Linea 124: Apre il main con padding e scroll.
Linea 125: Apre il contenitore principale con stile card.
Linea 126: Apre il blocco header pagina.
Linea 127: Titolo della pagina.
Linea 128: Apre il contenitore dei link.
Linea 129: Apre il Link verso home.
Linea 130: Imposta href per home.
Linea 131: Imposta classi Tailwind.
Linea 132: Chiude il tag Link.
Linea 133: Testo del link Home.
Linea 134: Chiude il Link.
Linea 135: Apre il Link verso /game.
Linea 136: Imposta href verso gioco.
Linea 137: Imposta classi Tailwind.
Linea 138: Chiude il tag Link.
Linea 139: Testo del link Vai al Gioco.
Linea 140: Chiude il Link.
Linea 141: Chiude il contenitore link.
Linea 142: Chiude il blocco header.
Linea 143: Riga vuota per separare layout.
Linea 144: Apre la griglia principale della pagina.
Linea 145: Apre sezione Configurazione.
Linea 146: Titolo della sezione.
Linea 147: Apre il contenitore dei bottoni size.
Linea 148: Apre il bottone per 3x3.
Linea 149: onClick imposta size 3.
Linea 150: Inizia classe con ternario per stato attivo.
Linea 151: Se size e' 3 usa stile attivo, altrimenti inattivo.
Linea 152: Chiude il template literal della classe.
Linea 153: Chiude il tag di apertura button.
Linea 154: Testo "3 x 3".
Linea 155: Chiude il bottone 3x3.
Linea 156: Apre il bottone per 4x4.
Linea 157: onClick imposta size 4.
Linea 158: Inizia classe con ternario per stato attivo.
Linea 159: Se size e' 4 usa stile attivo, altrimenti inattivo.
Linea 160: Chiude il template literal della classe.
Linea 161: Chiude il tag di apertura button.
Linea 162: Testo "4 x 4".
Linea 163: Chiude il bottone 4x4.
Linea 164: Chiude il contenitore bottoni size.
Linea 165: Riga vuota per separare testo.
Linea 166: Apre paragrafo istruzioni.
Linea 167: Testo con range valido e significato dello zero.
Linea 168: Chiude il paragrafo.
Linea 169: Riga vuota per separare bottoni azione.
Linea 170: Apre contenitore bottoni azione.
Linea 171: Apre bottone "Usa Questa Tabella".
Linea 172: onClick usa la tabella locale.
Linea 173: Disabilita se in submitting.
Linea 174: Classi per bottone.
Linea 175: Chiude il tag di apertura button.
Linea 176: Testo del bottone.
Linea 177: Chiude bottone.
Linea 178: Apre bottone "Risolvi Con API".
Linea 179: onClick chiama la risoluzione via API.
Linea 180: Disabilita se in submitting.
Linea 181: Classi per bottone.
Linea 182: Chiude il tag di apertura button.
Linea 183: Testo condizionale in base a isSubmitting.
Linea 184: Chiude bottone.
Linea 185: Chiude contenitore bottoni.
Linea 186: Chiude sezione Configurazione.
Linea 187: Riga vuota per separare sezione grid.
Linea 188: Apre sezione Posizione Numeri.
Linea 189: Titolo della sezione.
Linea 190: Apre contenitore per la griglia input.
Linea 191: Apre il wrapper della griglia.
Linea 192: Classi per layout grid.
Linea 193: Apre l'oggetto style.
Linea 194: Imposta le colonne in base a gridSize.
Linea 195: Imposta maxWidth in base all'altezza viewport.
Linea 196: Chiude l'oggetto style.
Linea 197: Chiude il tag div.
Linea 198: Mappa le celle per creare input numerici.
Linea 199: Apre input per una cella.
Linea 200: Imposta key su index.

### Sezione

#### Codice
```typescript
                    type="number"
                    min={0}
                    max={maxValue}
                    value={cellValues[index] ?? ''}
                    onChange={(e) => updateCell(index, e.target.value)}
                    className="no-spinner aspect-square w-full rounded-lg border border-slate-600 bg-slate-900 px-2 text-center text-lg sm:text-xl font-semibold text-white outline-none focus:border-cyan-400"
                  />
                ))}
              </div>
            </div>

            {feedback && (
              <p className="mt-4 rounded-lg border border-slate-700 bg-slate-900/70 px-3 py-2 text-sm text-slate-200">
                {feedback}
              </p>
            )}
          </section>
        </div>
      </div>

      {error && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">
            <div className="text-4xl sm:text-5xl mb-2">âŒ</div>
            <div className="text-lg sm:text-xl font-bold text-red-400 whitespace-pre-wrap mb-4">{error}</div>
            <button
              onClick={() => setError(null)}
              className="mt-4 px-6 py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg transition-colors"
            >
              OK
            </button>
          </div>
        </div>
      )}
    </main>
  );
}
```

#### Spiegazione
Linea 201: Tipo input number.
Linea 202: Minimo 0.
Linea 203: Massimo maxValue.
Linea 204: Value collegato a cellValues.
Linea 205: onChange aggiorna il valore cella.
Linea 206: Classi per stile input.
Linea 207: Chiude input.
Linea 208: Chiude la map delle celle.
Linea 209: Chiude il wrapper della griglia.
Linea 210: Chiude contenitore della griglia.
Linea 211: Riga vuota per separare feedback.
Linea 212: Render condizionale del feedback.
Linea 213: Apre paragrafo feedback.
Linea 214: Inserisce il testo feedback.
Linea 215: Chiude paragrafo.
Linea 216: Chiude condizionale feedback.
Linea 217: Chiude sezione Posizione Numeri.
Linea 218: Chiude la griglia principale.
Linea 219: Chiude il contenitore principale.
Linea 220: Riga vuota per separare overlay errore.
Linea 221: Render condizionale overlay errore.
Linea 222: Apre overlay full screen.
Linea 223: Apre card dell'errore.
Linea 224: Icona errore (simbolo) in grande.
Linea 225: Testo errore formattato.
Linea 226: Apre bottone di chiusura.
Linea 227: onClick resetta l'errore.
Linea 228: Classi per bottone.
Linea 229: Chiude il tag di apertura button.
Linea 230: Testo del bottone OK.
Linea 231: Chiude bottone.
Linea 232: Chiude card errore.
Linea 233: Chiude overlay.
Linea 234: Chiude condizionale errore.
Linea 235: Chiude il main.
Linea 236: Chiude il return.
Linea 237: Chiude la funzione CustomBoardPage.

<a id="src-app-settings-page-tsx"></a>
## src/app/settings/page.tsx

### Direttive

#### Codice
```typescript
"use client";

```

#### Spiegazione
Linea 1: Direttiva client per usare hook e stato.
Linea 2: Riga vuota per separare import e codice.

### Import

#### Codice
```typescript
import Link from 'next/link';
import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';
import type { ColorPaletteMode } from '@/features/puzzle/types/puzzle';
import {
  useColorPaletteMode,
  useMusicEnabled,
  useSetColorPaletteMode,
  useThemeMode,
  useToggleMusicEnabled,
  useToggleThemeMode,
} from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 3: Importa Link di Next.js.
Linea 4: Importa le label delle palette colori.
Linea 5: Importa il tipo ColorPaletteMode.
Linea 6: Importa selector e azioni dallo store.
Linea 7: Seleziona lo stato colorPaletteMode.
Linea 8: Seleziona lo stato musicEnabled.
Linea 9: Importa azione per impostare palette.
Linea 10: Seleziona lo stato themeMode.
Linea 11: Importa toggle musica.
Linea 12: Importa toggle tema.
Linea 13: Chiude l'import multiplo.
Linea 14: Riga vuota per separare costanti.

### Costante: COLOR_MODES

#### Codice
```typescript
const COLOR_MODES: ColorPaletteMode[] = [
  'default',
  'protanopia',
  'deuteranopia',
  'tritanopia',
  'achromatopsia',
];

```

#### Spiegazione
Linea 15: Definisce l'elenco delle palette disponibili.
Linea 16: Aggiunge modalita' standard.
Linea 17: Aggiunge protanopia.
Linea 18: Aggiunge deuteranopia.
Linea 19: Aggiunge tritanopia.
Linea 20: Aggiunge achromatopsia.
Linea 21: Chiude array COLOR_MODES.
Linea 22: Riga vuota per separare componente.

### Componente: SettingsPage

#### Codice
```typescript
export default function SettingsPage() {
```

#### Spiegazione
Linea 23: Definisce il componente SettingsPage.

### Costante: themeMode

#### Codice
```typescript
  const themeMode = useThemeMode();
```

#### Spiegazione
Linea 24: Legge il tema corrente dallo store.

### Costante: toggleThemeMode

#### Codice
```typescript
  const toggleThemeMode = useToggleThemeMode();
```

#### Spiegazione
Linea 25: Prende l'azione per toggle tema.

### Costante: colorPaletteMode

#### Codice
```typescript
  const colorPaletteMode = useColorPaletteMode();
```

#### Spiegazione
Linea 26: Legge la palette corrente.

### Costante: setColorPaletteMode

#### Codice
```typescript
  const setColorPaletteMode = useSetColorPaletteMode();
```

#### Spiegazione
Linea 27: Prende l'azione per impostare palette.

### Costante: musicEnabled

#### Codice
```typescript
  const musicEnabled = useMusicEnabled();
```

#### Spiegazione
Linea 28: Legge lo stato musica.

### Costante: toggleMusicEnabled

#### Codice
```typescript
  const toggleMusicEnabled = useToggleMusicEnabled();

  return (
    <main className="w-full h-full overflow-y-auto px-4 py-5 sm:px-6 sm:py-8 text-white">
      <div className="mx-auto w-full max-w-3xl rounded-2xl border border-slate-700 bg-slate-900/80 p-5 sm:p-7 shadow-2xl">
        <div className="flex flex-wrap items-center justify-between gap-3">
          <h1 className="text-2xl sm:text-3xl font-bold">Impostazioni</h1>
          <Link
            href="/game"
            className="rounded-lg border border-cyan-500/60 bg-cyan-700 px-3 py-1.5 text-sm font-semibold hover:bg-cyan-600"
          >
            Torna al Gioco
          </Link>
        </div>

        <div className="mt-6 space-y-5">
          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Tema</h2>
            <p className="mt-1 text-xs text-slate-400">Mantieni il tema scuro o attiva quello chiaro.</p>
            <button
              onClick={toggleThemeMode}
              className="mt-3 rounded-lg border border-sky-400/60 bg-sky-700 px-4 py-2 text-sm font-semibold hover:bg-sky-600"
            >
              {themeMode === 'dark' ? 'Passa a Modalita Chiara' : 'Passa a Modalita Scura'}
            </button>
          </section>

          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Colori Tabella</h2>
            <p className="mt-1 text-xs text-slate-400">Scegli il profilo: standard o uno dei principali tipi di daltonismo.</p>
            <div className="mt-3 flex flex-wrap gap-2">
              {COLOR_MODES.map((mode) => (
                <button
                  key={mode}
                  onClick={() => setColorPaletteMode(mode)}
                  className={`rounded-lg border px-3 py-2 text-sm font-semibold ${
                    colorPaletteMode === mode
                      ? 'border-emerald-400/70 bg-emerald-700'
                      : 'border-slate-600 bg-slate-700'
                  }`}
                >
                  {COLOR_PALETTE_LABELS[mode]}
                </button>
              ))}
            </div>
          </section>

          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">
            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Musica</h2>
            <p className="mt-1 text-xs text-slate-400">Attiva o disattiva la musica del gioco.</p>
            <button
              onClick={toggleMusicEnabled}
              className="mt-3 rounded-lg border border-violet-400/60 bg-violet-700 px-4 py-2 text-sm font-semibold hover:bg-violet-600"
            >
              {musicEnabled ? 'Disattiva Musica' : 'Attiva Musica'}
            </button>
          </section>
        </div>
      </div>
    </main>
  );
}
```

#### Spiegazione
Linea 29: Prende l'azione per toggle musica.
Linea 30: Riga vuota per separare JSX.
Linea 31: Inizia il return del JSX.
Linea 32: Apre main con scroll e padding.
Linea 33: Apre card centrale con stile.
Linea 34: Apre header con titolo e link.
Linea 35: Titolo "Impostazioni".
Linea 36: Apre Link verso /game.
Linea 37: Imposta href.
Linea 38: Classi del bottone.
Linea 39: Chiude il tag di apertura Link.
Linea 40: Testo del link.
Linea 41: Chiude Link.
Linea 42: Chiude header.
Linea 43: Riga vuota per separare sezioni.
Linea 44: Apre contenitore sezioni con spacing.
Linea 45: Apre sezione tema.
Linea 46: Titolo sezione tema.
Linea 47: Descrizione breve del tema.
Linea 48: Apre bottone toggle tema.
Linea 49: onClick richiama toggleThemeMode.
Linea 50: Classi bottone.
Linea 51: Chiude tag apertura bottone.
Linea 52: Testo bottone in base al tema.
Linea 53: Chiude bottone.
Linea 54: Chiude sezione tema.
Linea 55: Riga vuota per separare sezioni.
Linea 56: Apre sezione colori tabella.
Linea 57: Titolo sezione palette.
Linea 58: Descrizione palette.
Linea 59: Apre contenitore dei bottoni palette.
Linea 60: Mappa le modalita' colore.
Linea 61: Apre bottone per una modalita'.
Linea 62: key per React.
Linea 63: onClick imposta la modalita'.
Linea 64: Inizia className con ternario.
Linea 65: Se attiva usa stile evidenziato.
Linea 66: Stringa stile attivo.
Linea 67: Stringa stile inattivo.
Linea 68: Chiude il template literal.
Linea 69: Chiude il tag di apertura button.
Linea 70: Renderizza la label della modalita'.
Linea 71: Chiude bottone.
Linea 72: Chiude map.
Linea 73: Chiude contenitore bottoni.
Linea 74: Chiude sezione colori.
Linea 75: Riga vuota per separare sezioni.
Linea 76: Apre sezione musica.
Linea 77: Titolo sezione musica.
Linea 78: Descrizione musica.
Linea 79: Apre bottone toggle musica.
Linea 80: onClick richiama toggleMusicEnabled.
Linea 81: Classi bottone.
Linea 82: Chiude tag apertura bottone.
Linea 83: Testo bottone in base allo stato musica.
Linea 84: Chiude bottone.
Linea 85: Chiude sezione musica.
Linea 86: Chiude contenitore sezioni.
Linea 87: Chiude card.
Linea 88: Chiude main.
Linea 89: Chiude return.
Linea 90: Chiude la funzione SettingsPage.

<a id="src-app-globals-css"></a>
## src/app/globals.css

### Regola: @import "tailwindcss";

#### Codice
```css
@import "tailwindcss";

```

#### Spiegazione
Linea 1: Importa le base utilities di Tailwind CSS.
Linea 2: Riga vuota per separare blocchi.

### Blocco: :root

#### Codice
```css
:root {
  --background: #ffffff;
  --foreground: #171717;
}

```

#### Spiegazione
Linea 3: Apre il selettore :root.
Linea 4: Definisce la variabile --background chiara.
Linea 5: Definisce la variabile --foreground scura.
Linea 6: Chiude :root.
Linea 7: Riga vuota per separare blocchi.

### Regola: @theme inline {

#### Codice
```css
@theme inline {
  --color-background: var(--background);
  --color-foreground: var(--foreground);
  --font-sans: var(--font-geist-sans);
  --font-mono: var(--font-geist-mono);
}

```

#### Spiegazione
Linea 8: Apre il blocco @theme inline per Tailwind.
Linea 9: Mappa --color-background su --background.
Linea 10: Mappa --color-foreground su --foreground.
Linea 11: Mappa font sans su Geist.
Linea 12: Mappa font mono su Geist mono.
Linea 13: Chiude @theme inline.
Linea 14: Riga vuota per separare blocchi.

### Regola: @media (prefers-color-scheme: dark) {

#### Codice
```css
@media (prefers-color-scheme: dark) {
```

#### Spiegazione
Linea 15: Apre media query prefers-color-scheme: dark.

### Blocco: :root

#### Codice
```css
  :root {
    --background: #0a0a0a;
    --foreground: #ededed;
  }
}

```

#### Spiegazione
Linea 16: Apre :root in modalita' scura.
Linea 17: Imposta background scuro.
Linea 18: Imposta foreground chiaro.
Linea 19: Chiude :root.
Linea 20: Chiude media query.
Linea 21: Riga vuota per separare blocchi.

### Blocco: body

#### Codice
```css
body {
  /* background: var(--background);
  color: var(--foreground); */
  font-family: Arial, Helvetica, sans-serif;
}

input[type='number']::-webkit-outer-spin-button,
```

#### Spiegazione
Linea 22: Apre stile body.
Linea 23: Commento: proprietà background e color disabilitate.
Linea 24: Commento: chiusura del blocco commentato.
Linea 25: Imposta font-family di default.
Linea 26: Chiude stile body.
Linea 27: Riga vuota per separare blocchi.
Linea 28: Selettore per rimuovere spinner numerici (WebKit).

### Blocco: input[type='number']::-webkit-inner-spin-button

#### Codice
```css
input[type='number']::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

```

#### Spiegazione
Linea 29: Selettore per spinner interno WebKit.
Linea 30: Disattiva l'aspetto nativo.
Linea 31: Rimuove margine predefinito.
Linea 32: Chiude il blocco spinner WebKit.
Linea 33: Riga vuota per separare blocchi.

### Blocco: input[type='number']

#### Codice
```css
input[type='number'] {
  appearance: textfield;
  -moz-appearance: textfield;
}

```

#### Spiegazione
Linea 34: Selettore per input number.
Linea 35: Imposta appearance a textfield.
Linea 36: Imposta -moz-appearance a textfield.
Linea 37: Chiude il blocco input number.
Linea 38: Riga vuota per separare blocchi.

### Blocco: select.no-native-arrow

#### Codice
```css
select.no-native-arrow {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  background-image: none;
}

```

#### Spiegazione
Linea 39: Selettore per select con classe no-native-arrow.
Linea 40: Disattiva appearance standard.
Linea 41: Disattiva appearance WebKit.
Linea 42: Disattiva appearance Firefox.
Linea 43: Rimuove immagine di background della freccia.
Linea 44: Chiude il blocco select.
Linea 45: Riga vuota per separare blocchi.

### Blocco: select.no-native-arrow::-ms-expand

#### Codice
```css
select.no-native-arrow::-ms-expand {
  display: none;
}
```

#### Spiegazione
Linea 46: Selettore per la freccia in IE/Edge legacy.
Linea 47: Nasconde la freccia nativa.
Linea 48: Chiude il blocco select legacy.

<a id="src-app-favicon-ico"></a>
## src/app/favicon.ico
File binario (icona). Non e' un file di testo, quindi non e' possibile una spiegazione riga per riga.

<a id="src-components-provider-tsx"></a>
## src/components/Provider.tsx

### Direttive

#### Codice
```typescript
'use client';

```

#### Spiegazione
Linea 1: Direttiva client per abilitare componenti client.
Linea 2: Riga vuota per separare.

### Componente: Providers

#### Codice
```typescript
export default function Providers({
  children,
}: {
  children: React.ReactNode;
}) {
  return <>{children}</>;
}
```

#### Spiegazione
Linea 3: Definisce il componente Providers.
Linea 4: Estrae children dalle props.
Linea 5: Apre il tipo delle props.
Linea 6: Tipizza children come React.ReactNode.
Linea 7: Chiude la definizione delle props.
Linea 8: Ritorna i children senza wrapper aggiuntivi.
Linea 9: Chiude la funzione Providers.

<a id="src-utils-cn-ts"></a>
## src/utils/cn.ts

### Import

#### Codice
```typescript
import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

```

#### Spiegazione
Linea 1: Importa clsx e il tipo ClassValue.
Linea 2: Importa twMerge per unire classi Tailwind.
Linea 3: Riga vuota per separare import e funzione.

### Funzione: cn

#### Codice
```typescript
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}
```

#### Spiegazione
Linea 4: Definisce helper cn per unire classi.
Linea 5: Ritorna classi combinate con clsx e twMerge.
Linea 6: Chiude la funzione cn.

<a id="src-features-puzzle-types-puzzle-ts"></a>
## src/features/puzzle/types/puzzle.ts

### Sezione

#### Codice
```typescript
export type Grid = number[][];
export type GridSize = 3 | 4;
export type ThemeMode = 'dark' | 'light';
export type ColorPaletteMode =
  | 'default'
  | 'protanopia'
  | 'deuteranopia'
  | 'tritanopia'
  | 'achromatopsia';

export type Direction = 'SINISTRA' | 'SOTTO' | 'SOPRA' | 'DESTRA';

export interface DirectionInfo {
  icon: string;
  en: string;
}

export interface PuzzleConfig {
  gridSize: GridSize;
  initialGrid: Grid;
  moves: Direction[];
}
```

#### Spiegazione
Linea 1: Definisce il tipo Grid come matrice di numeri.
Linea 2: Definisce GridSize come 3 o 4.
Linea 3: Definisce ThemeMode come dark o light.
Linea 4: Inizia la definizione di ColorPaletteMode.
Linea 5: Aggiunge modalita' default.
Linea 6: Aggiunge protanopia.
Linea 7: Aggiunge deuteranopia.
Linea 8: Aggiunge tritanopia.
Linea 9: Aggiunge achromatopsia.
Linea 10: Riga vuota per separare tipi.
Linea 11: Definisce Direction per le quattro direzioni.
Linea 12: Riga vuota per separare interfacce.
Linea 13: Inizia l'interfaccia DirectionInfo.
Linea 14: Campo icon (stringa).
Linea 15: Campo en (etichetta inglese).
Linea 16: Chiude l'interfaccia DirectionInfo.
Linea 17: Riga vuota per separare interfacce.
Linea 18: Inizia l'interfaccia PuzzleConfig.
Linea 19: Campo gridSize.
Linea 20: Campo initialGrid.
Linea 21: Campo moves.
Linea 22: Chiude l'interfaccia PuzzleConfig.

<a id="src-features-puzzle-constants-puzzle-ts"></a>
## src/features/puzzle/constants/puzzle.ts

### Import

#### Codice
```typescript
import type { ColorPaletteMode, Direction, DirectionInfo, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';

```

#### Spiegazione
Linea 1: Importa tipi usati nelle costanti.
Linea 2: Riga vuota per separare.

### Costante: INITIAL_GRID_4X4

#### Codice
```typescript
const INITIAL_GRID_4X4: Grid = [
  [7, 2, 0, 8],
  [3, 11, 14, 5],
  [6, 15, 10, 1],
  [9, 12, 13, 4],
];

```

#### Spiegazione
Linea 3: Inizia la griglia iniziale 4x4.
Linea 4: Riga 0 della griglia 4x4.
Linea 5: Riga 1 della griglia 4x4.
Linea 6: Riga 2 della griglia 4x4.
Linea 7: Riga 3 della griglia 4x4.
Linea 8: Chiude la griglia iniziale 4x4.
Linea 9: Riga vuota per separare.

### Costante: MOVES_4X4

#### Codice
```typescript
const MOVES_4X4: Direction[] = [
  'DESTRA', 'SOTTO', 'SOTTO', 'SINISTRA', 'SINISTRA', 'SOTTO',
  'DESTRA', 'SOPRA', 'SOPRA', 'DESTRA', 'SOTTO', 'SOTTO',
  'SINISTRA', 'SOPRA', 'SINISTRA', 'SOPRA', 'DESTRA', 'DESTRA',
  'SOTTO', 'SINISTRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',
  'DESTRA', 'SOPRA', 'SOPRA', 'SOPRA', 'DESTRA', 'SOTTO',
  'SINISTRA', 'SOPRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',
  'DESTRA', 'SOPRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',
  'DESTRA', 'SOPRA', 'SINISTRA', 'SOTTO', 'SOTTO', 'DESTRA',
  'SOTTO', 'DESTRA',
];

```

#### Spiegazione
Linea 10: Inizia la sequenza di mosse per 4x4.
Linea 11: Prima parte delle mosse 4x4.
Linea 12: Seconda parte delle mosse 4x4.
Linea 13: Terza parte delle mosse 4x4.
Linea 14: Quarta parte delle mosse 4x4.
Linea 15: Quinta parte delle mosse 4x4.
Linea 16: Sesta parte delle mosse 4x4.
Linea 17: Settima parte delle mosse 4x4.
Linea 18: Ottava parte delle mosse 4x4.
Linea 19: Ultime mosse 4x4.
Linea 20: Chiude l'array MOVES_4X4.
Linea 21: Riga vuota per separare.

### Costante: INITIAL_GRID_3X3

#### Codice
```typescript
const INITIAL_GRID_3X3: Grid = [
  [1, 2, 3],
  [7, 0, 6],
  [5, 4, 8],
];

```

#### Spiegazione
Linea 22: Inizia la griglia iniziale 3x3.
Linea 23: Riga 0 della griglia 3x3.
Linea 24: Riga 1 della griglia 3x3.
Linea 25: Riga 2 della griglia 3x3.
Linea 26: Chiude la griglia 3x3.
Linea 27: Riga vuota per separare.

### Costante: MOVES_3X3

#### Codice
```typescript
const MOVES_3X3: Direction[] = [
  'SOTTO',
  'SINISTRA',
  'SOPRA',
  'DESTRA',
  'SOTTO',
  'DESTRA',
];

```

#### Spiegazione
Linea 28: Inizia la sequenza di mosse 3x3.
Linea 29: Mossa 1.
Linea 30: Mossa 2.
Linea 31: Mossa 3.
Linea 32: Mossa 4.
Linea 33: Mossa 5.
Linea 34: Mossa 6.
Linea 35: Chiude l'array MOVES_3X3.
Linea 36: Riga vuota per separare.

### Costante: PUZZLE_CONFIGS

#### Codice
```typescript
export const PUZZLE_CONFIGS: Record<GridSize, PuzzleConfig> = {
  3: {
    gridSize: 3,
    initialGrid: INITIAL_GRID_3X3,
    moves: MOVES_3X3,
  },
  4: {
    gridSize: 4,
    initialGrid: INITIAL_GRID_4X4,
    moves: MOVES_4X4,
  },
};

```

#### Spiegazione
Linea 37: Espone le configurazioni per 3x3 e 4x4.
Linea 38: Inizia config per 3x3.
Linea 39: Imposta gridSize=3.
Linea 40: Imposta initialGrid 3x3.
Linea 41: Imposta moves 3x3.
Linea 42: Chiude config 3x3.
Linea 43: Inizia config per 4x4.
Linea 44: Imposta gridSize=4.
Linea 45: Imposta initialGrid 4x4.
Linea 46: Imposta moves 4x4.
Linea 47: Chiude config 4x4.
Linea 48: Chiude PUZZLE_CONFIGS.
Linea 49: Riga vuota per separare.

### Costante: DEFAULT_GRID_SIZE

#### Codice
```typescript
export const DEFAULT_GRID_SIZE: GridSize = 4;

```

#### Spiegazione
Linea 50: Definisce la dimensione di default (4).
Linea 51: Riga vuota per separare.

### Costante: DIRECTION_LABELS

#### Codice
```typescript
export const DIRECTION_LABELS: Record<Direction, DirectionInfo> = {
  SINISTRA: { icon: 'â†', en: 'LEFT' },
  DESTRA:   { icon: 'â†’', en: 'RIGHT' },
  SOPRA:    { icon: 'â†‘', en: 'UP' },
  SOTTO:    { icon: 'â†“', en: 'DOWN' },
};

```

#### Spiegazione
Linea 52: Inizia la mappa etichette direzioni.
Linea 53: Etichetta per SINISTRA con icona e inglese.
Linea 54: Etichetta per DESTRA con icona e inglese.
Linea 55: Etichetta per SOPRA con icona e inglese.
Linea 56: Etichetta per SOTTO con icona e inglese.
Linea 57: Chiude DIRECTION_LABELS.
Linea 58: Riga vuota per separare.

### Costante: DIRECTION_DELTAS

#### Codice
```typescript
export const DIRECTION_DELTAS: Record<Direction, [number, number]> = {
  SINISTRA: [0, -1],
  DESTRA:   [0,  1],
  SOPRA:    [-1, 0],
  SOTTO:    [ 1, 0],
};

```

#### Spiegazione
Linea 59: Inizia la mappa dei delta per direzioni.
Linea 60: Delta per SINISTRA.
Linea 61: Delta per DESTRA.
Linea 62: Delta per SOPRA.
Linea 63: Delta per SOTTO.
Linea 64: Chiude DIRECTION_DELTAS.
Linea 65: Riga vuota per separare.

### Costante: TILE_COLORS

#### Codice
```typescript
export const TILE_COLORS: Record<number, string> = {
  1:  'from-blue-500 to-blue-600',
  2:  'from-emerald-500 to-emerald-600',
  3:  'from-amber-500 to-amber-600',
  4:  'from-purple-500 to-purple-600',
  5:  'from-rose-500 to-rose-600',
  6:  'from-cyan-500 to-cyan-600',
  7:  'from-orange-500 to-orange-600',
  8:  'from-indigo-500 to-indigo-600',
  9:  'from-teal-500 to-teal-600',
  10: 'from-pink-500 to-pink-600',
  11: 'from-lime-500 to-lime-600',
  12: 'from-fuchsia-500 to-fuchsia-600',
  13: 'from-sky-500 to-sky-600',
  14: 'from-red-500 to-red-600',
  15: 'from-violet-500 to-violet-600',
};

```

#### Spiegazione
Linea 66: Inizia la palette colori standard.
Linea 67: Colore tile 1.
Linea 68: Colore tile 2.
Linea 69: Colore tile 3.
Linea 70: Colore tile 4.
Linea 71: Colore tile 5.
Linea 72: Colore tile 6.
Linea 73: Colore tile 7.
Linea 74: Colore tile 8.
Linea 75: Colore tile 9.
Linea 76: Colore tile 10.
Linea 77: Colore tile 11.
Linea 78: Colore tile 12.
Linea 79: Colore tile 13.
Linea 80: Colore tile 14.
Linea 81: Colore tile 15.
Linea 82: Chiude TILE_COLORS.
Linea 83: Riga vuota per separare.

### Costante: TILE_COLORS_PROTANOPIA

#### Codice
```typescript
export const TILE_COLORS_PROTANOPIA: Record<number, string> = {
  1: 'from-sky-500 to-sky-600',
  2: 'from-cyan-500 to-cyan-600',
  3: 'from-yellow-500 to-yellow-600',
  4: 'from-violet-500 to-violet-600',
  5: 'from-slate-500 to-slate-600',
  6: 'from-indigo-500 to-indigo-600',
  7: 'from-amber-500 to-amber-600',
  8: 'from-blue-500 to-blue-600',
  9: 'from-emerald-500 to-emerald-600',
  10: 'from-zinc-500 to-zinc-600',
  11: 'from-lime-500 to-lime-600',
  12: 'from-orange-500 to-orange-600',
  13: 'from-fuchsia-500 to-fuchsia-600',
  14: 'from-stone-500 to-stone-600',
  15: 'from-teal-500 to-teal-600',
};

```

#### Spiegazione
Linea 84: Inizia la palette protanopia.
Linea 85: Colore tile 1 protanopia.
Linea 86: Colore tile 2 protanopia.
Linea 87: Colore tile 3 protanopia.
Linea 88: Colore tile 4 protanopia.
Linea 89: Colore tile 5 protanopia.
Linea 90: Colore tile 6 protanopia.
Linea 91: Colore tile 7 protanopia.
Linea 92: Colore tile 8 protanopia.
Linea 93: Colore tile 9 protanopia.
Linea 94: Colore tile 10 protanopia.
Linea 95: Colore tile 11 protanopia.
Linea 96: Colore tile 12 protanopia.
Linea 97: Colore tile 13 protanopia.
Linea 98: Colore tile 14 protanopia.
Linea 99: Colore tile 15 protanopia.
Linea 100: Chiude TILE_COLORS_PROTANOPIA.
Linea 101: Riga vuota per separare.

### Costante: TILE_COLORS_DEUTERANOPIA

#### Codice
```typescript
export const TILE_COLORS_DEUTERANOPIA: Record<number, string> = {
  1: 'from-blue-500 to-blue-600',
  2: 'from-cyan-500 to-cyan-600',
  3: 'from-amber-500 to-amber-600',
  4: 'from-purple-500 to-purple-600',
  5: 'from-zinc-500 to-zinc-600',
  6: 'from-sky-500 to-sky-600',
  7: 'from-yellow-500 to-yellow-600',
  8: 'from-indigo-500 to-indigo-600',
  9: 'from-teal-500 to-teal-600',
  10: 'from-rose-500 to-rose-600',
  11: 'from-lime-500 to-lime-600',
  12: 'from-fuchsia-500 to-fuchsia-600',
  13: 'from-orange-500 to-orange-600',
  14: 'from-stone-500 to-stone-600',
  15: 'from-emerald-500 to-emerald-600',
};

```

#### Spiegazione
Linea 102: Inizia la palette deuteranopia.
Linea 103: Colore tile 1 deuteranopia.
Linea 104: Colore tile 2 deuteranopia.
Linea 105: Colore tile 3 deuteranopia.
Linea 106: Colore tile 4 deuteranopia.
Linea 107: Colore tile 5 deuteranopia.
Linea 108: Colore tile 6 deuteranopia.
Linea 109: Colore tile 7 deuteranopia.
Linea 110: Colore tile 8 deuteranopia.
Linea 111: Colore tile 9 deuteranopia.
Linea 112: Colore tile 10 deuteranopia.
Linea 113: Colore tile 11 deuteranopia.
Linea 114: Colore tile 12 deuteranopia.
Linea 115: Colore tile 13 deuteranopia.
Linea 116: Colore tile 14 deuteranopia.
Linea 117: Colore tile 15 deuteranopia.
Linea 118: Chiude TILE_COLORS_DEUTERANOPIA.
Linea 119: Riga vuota per separare.

### Costante: TILE_COLORS_TRITANOPIA

#### Codice
```typescript
export const TILE_COLORS_TRITANOPIA: Record<number, string> = {
  1: 'from-red-500 to-red-600',
  2: 'from-orange-500 to-orange-600',
  3: 'from-yellow-500 to-yellow-600',
  4: 'from-rose-500 to-rose-600',
  5: 'from-lime-500 to-lime-600',
  6: 'from-emerald-500 to-emerald-600',
  7: 'from-green-500 to-green-600',
  8: 'from-zinc-500 to-zinc-600',
  9: 'from-stone-500 to-stone-600',
  10: 'from-amber-500 to-amber-600',
  11: 'from-fuchsia-500 to-fuchsia-600',
  12: 'from-pink-500 to-pink-600',
  13: 'from-indigo-500 to-indigo-600',
  14: 'from-slate-500 to-slate-600',
  15: 'from-violet-500 to-violet-600',
};

```

#### Spiegazione
Linea 120: Inizia la palette tritanopia.
Linea 121: Colore tile 1 tritanopia.
Linea 122: Colore tile 2 tritanopia.
Linea 123: Colore tile 3 tritanopia.
Linea 124: Colore tile 4 tritanopia.
Linea 125: Colore tile 5 tritanopia.
Linea 126: Colore tile 6 tritanopia.
Linea 127: Colore tile 7 tritanopia.
Linea 128: Colore tile 8 tritanopia.
Linea 129: Colore tile 9 tritanopia.
Linea 130: Colore tile 10 tritanopia.
Linea 131: Colore tile 11 tritanopia.
Linea 132: Colore tile 12 tritanopia.
Linea 133: Colore tile 13 tritanopia.
Linea 134: Colore tile 14 tritanopia.
Linea 135: Colore tile 15 tritanopia.
Linea 136: Chiude TILE_COLORS_TRITANOPIA.
Linea 137: Riga vuota per separare.

### Costante: TILE_COLORS_ACHROMATOPSIA

#### Codice
```typescript
export const TILE_COLORS_ACHROMATOPSIA: Record<number, string> = {
  1: 'from-slate-500 to-slate-600',
  2: 'from-slate-600 to-slate-700',
  3: 'from-zinc-400 to-zinc-500',
  4: 'from-zinc-500 to-zinc-600',
  5: 'from-gray-400 to-gray-500',
  6: 'from-gray-500 to-gray-600',
  7: 'from-neutral-500 to-neutral-600',
  8: 'from-neutral-600 to-neutral-700',
  9: 'from-stone-400 to-stone-500',
  10: 'from-stone-500 to-stone-600',
  11: 'from-zinc-300 to-zinc-400',
  12: 'from-zinc-700 to-zinc-800',
  13: 'from-gray-300 to-gray-400',
  14: 'from-gray-600 to-gray-700',
  15: 'from-stone-600 to-stone-700',
};

```

#### Spiegazione
Linea 138: Inizia la palette achromatopsia.
Linea 139: Colore tile 1 achromatopsia.
Linea 140: Colore tile 2 achromatopsia.
Linea 141: Colore tile 3 achromatopsia.
Linea 142: Colore tile 4 achromatopsia.
Linea 143: Colore tile 5 achromatopsia.
Linea 144: Colore tile 6 achromatopsia.
Linea 145: Colore tile 7 achromatopsia.
Linea 146: Colore tile 8 achromatopsia.
Linea 147: Colore tile 9 achromatopsia.
Linea 148: Colore tile 10 achromatopsia.
Linea 149: Colore tile 11 achromatopsia.
Linea 150: Colore tile 12 achromatopsia.
Linea 151: Colore tile 13 achromatopsia.
Linea 152: Colore tile 14 achromatopsia.
Linea 153: Colore tile 15 achromatopsia.
Linea 154: Chiude TILE_COLORS_ACHROMATOPSIA.
Linea 155: Riga vuota per separare.

### Costante: TILE_COLORS_BY_MODE

#### Codice
```typescript
export const TILE_COLORS_BY_MODE: Record<ColorPaletteMode, Record<number, string>> = {
  default: TILE_COLORS,
  protanopia: TILE_COLORS_PROTANOPIA,
  deuteranopia: TILE_COLORS_DEUTERANOPIA,
  tritanopia: TILE_COLORS_TRITANOPIA,
  achromatopsia: TILE_COLORS_ACHROMATOPSIA,
};

```

#### Spiegazione
Linea 156: Mappa palette per modalita'.
Linea 157: Associa default alla palette standard.
Linea 158: Associa protanopia.
Linea 159: Associa deuteranopia.
Linea 160: Associa tritanopia.
Linea 161: Associa achromatopsia.
Linea 162: Chiude TILE_COLORS_BY_MODE.
Linea 163: Riga vuota per separare.

### Costante: COLOR_PALETTE_LABELS

#### Codice
```typescript
export const COLOR_PALETTE_LABELS: Record<ColorPaletteMode, string> = {
  default: 'Standard',
  protanopia: 'Protanopia',
  deuteranopia: 'Deuteranopia',
  tritanopia: 'Tritanopia',
  achromatopsia: 'Achromatopsia',
};

```

#### Spiegazione
Linea 164: Etichette leggibili per le palette.
Linea 165: Label per default.
Linea 166: Label per protanopia.
Linea 167: Label per deuteranopia.
Linea 168: Label per tritanopia.
Linea 169: Label per achromatopsia.
Linea 170: Chiude COLOR_PALETTE_LABELS.
Linea 171: Riga vuota per separare.

### Costante: DEFAULT_SPEED

#### Codice
```typescript
export const DEFAULT_SPEED = 400;
```

#### Spiegazione
Linea 172: Imposta la velocita' default del replay.

### Costante: MIN_SPEED

#### Codice
```typescript
export const MIN_SPEED = 100;
```

#### Spiegazione
Linea 173: Imposta la velocita' minima.

### Costante: MAX_SPEED

#### Codice
```typescript
export const MAX_SPEED = 1500;
```

#### Spiegazione
Linea 174: Imposta la velocita' massima.

### Costante: SPEED_STEP

#### Codice
```typescript
export const SPEED_STEP = 50;
```

#### Spiegazione
Linea 175: Imposta lo step per lo slider.

<a id="src-features-puzzle-utils-puzzle-ts"></a>
## src/features/puzzle/utils/puzzle.ts

### Import

#### Codice
```typescript
import type { Direction, Grid } from '@/features/puzzle/types/puzzle';
import { DIRECTION_DELTAS } from '@/features/puzzle/constants/puzzle';

/**
 * Valida se una griglia ha dimensioni corrette e contiene numeri validi.
 */
```

#### Spiegazione
Linea 1: Importa i tipi Direction e Grid.
Linea 2: Importa i delta delle direzioni.
Linea 3: Riga vuota per separare.
Linea 4: Inizio commento JSDoc della funzione validateGrid.
Linea 5: Descrive la funzione validateGrid.
Linea 6: Chiude il commento JSDoc.

### Funzione: validateGrid

#### Codice
```typescript
function validateGrid(grid: Grid, expectedSize?: number): boolean {
  if (!Array.isArray(grid) || grid.length === 0) return false;
```

#### Spiegazione
Linea 7: Definisce validateGrid con griglia e size opzionale.
Linea 8: Verifica array non vuoto.

### Costante: size

#### Codice
```typescript
  const size = expectedSize ?? grid.length;
  if (grid.length !== size) return false;

  for (const row of grid) {
    if (!Array.isArray(row) || row.length !== size) return false;
    for (const cell of row) {
      if (typeof cell !== 'number' || cell < 0 || cell >= size * size) return false;
    }
  }
  return true;
}

/**
 * Trova la posizione dello zero (casella vuota) nella griglia.
 * Lancia un errore se la griglia Ã¨ invalida o zero non viene trovato.
 */
```

#### Spiegazione
Linea 9: Determina size attesa (parametro o lunghezza griglia).
Linea 10: Verifica che il numero di righe sia corretto.
Linea 11: Riga vuota per separare.
Linea 12: Ciclo sulle righe.
Linea 13: Verifica riga valida e lunghezza corretta.
Linea 14: Ciclo sulle celle.
Linea 15: Verifica che la cella sia numero nel range.
Linea 16: Chiude il ciclo celle.
Linea 17: Chiude il ciclo righe.
Linea 18: Ritorna true se valida.
Linea 19: Chiude validateGrid.
Linea 20: Riga vuota per separare.
Linea 21: Inizio commento JSDoc di findZero.
Linea 22: Descrive la ricerca dello zero.
Linea 23: Nota su errore se griglia invalida.
Linea 24: Chiude il commento JSDoc.

### Funzione: findZero

#### Codice
```typescript
export function findZero(grid: Grid): [number, number] {
  if (!validateGrid(grid)) {
    console.error('Invalid grid:', grid);
    throw new Error('Grid must be square and contain valid tile values');
  }

```

#### Spiegazione
Linea 25: Definisce findZero.
Linea 26: Valida la griglia, altrimenti logga errore.
Linea 27: Logga la griglia invalida.
Linea 28: Lancia un errore se griglia invalida.
Linea 29: Chiude if della validazione.
Linea 30: Riga vuota per separare.

### Costante: size

#### Codice
```typescript
  const size = grid.length;

  for (let r = 0; r < size; r++) {
    for (let c = 0; c < size; c++) {
      if (grid[r][c] === 0) return [r, c];
    }
  }

  console.error('Zero not found in grid:', grid);
  throw new Error('No zero (empty cell) found in grid');
}

/**
 * Applica una mossa alla griglia, restituendo una nuova griglia.
 */
```

#### Spiegazione
Linea 31: Salva la dimensione della griglia.
Linea 32: Riga vuota per separare.
Linea 33: Ciclo sulle righe.
Linea 34: Ciclo sulle colonne.
Linea 35: Se trova lo zero, ritorna la posizione.
Linea 36: Chiude ciclo colonne.
Linea 37: Chiude ciclo righe.
Linea 38: Riga vuota per separare.
Linea 39: Logga errore se zero non trovato.
Linea 40: Lancia un errore se zero non trovato.
Linea 41: Chiude findZero.
Linea 42: Riga vuota per separare.
Linea 43: Inizio commento JSDoc di applyMove.
Linea 44: Descrive l'applicazione di una mossa.
Linea 45: Chiude il commento JSDoc.

### Funzione: applyMove

#### Codice
```typescript
export function applyMove(grid: Grid, dir: Direction): Grid {
```

#### Spiegazione
Linea 46: Definisce applyMove.

### Costante: newGrid

#### Codice
```typescript
  const newGrid = grid.map(row => [...row]);
```

#### Spiegazione
Linea 47: Clona la griglia per non mutare l'originale.

### Sezione

#### Codice
```typescript
  const [r, c] = findZero(newGrid);
```

#### Spiegazione
Linea 48: Trova la posizione dello zero.

### Costante: size

#### Codice
```typescript
  const size = newGrid.length;
```

#### Spiegazione
Linea 49: Salva la dimensione.

### Sezione

#### Codice
```typescript
  const [dr, dc] = DIRECTION_DELTAS[dir];
```

#### Spiegazione
Linea 50: Legge il delta per la direzione.

### Costante: nr

#### Codice
```typescript
  const nr = r + dr;
```

#### Spiegazione
Linea 51: Calcola nuova riga dello zero.

### Costante: nc

#### Codice
```typescript
  const nc = c + dc;
  if (nr >= 0 && nr < size && nc >= 0 && nc < size) {
    [newGrid[r][c], newGrid[nr][nc]] = [newGrid[nr][nc], newGrid[r][c]];
  }
  return newGrid;
}

/**
 * Calcola tutti gli stati intermedi dalla griglia iniziale applicando tutte le mosse.
 * Valida la griglia iniziale e registra errori se si verificano.
 */
```

#### Spiegazione
Linea 52: Calcola nuova colonna dello zero.
Linea 53: Verifica che la nuova posizione sia valida.
Linea 54: Scambia zero con la tessera adiacente.
Linea 55: Chiude if di validazione.
Linea 56: Ritorna la nuova griglia.
Linea 57: Chiude applyMove.
Linea 58: Riga vuota per separare.
Linea 59: Inizio commento JSDoc di computeAllStates.
Linea 60: Descrive il calcolo degli stati intermedi.
Linea 61: Nota sulla validazione iniziale.
Linea 62: Chiude il commento JSDoc.

### Funzione: computeAllStates

#### Codice
```typescript
export function computeAllStates(initialGrid: Grid, movesSequence: Direction[]): {
  grids: Grid[];
  moves: (Direction | null)[];
} {
  if (!validateGrid(initialGrid)) {
    throw new Error('Initial grid must be square and valid');
  }

```

#### Spiegazione
Linea 63: Definisce computeAllStates e il tipo di ritorno.
Linea 64: Tipo di ritorno: array di griglie.
Linea 65: Tipo di ritorno: array di mosse.
Linea 66: Chiude la firma di ritorno.
Linea 67: Valida la griglia iniziale.
Linea 68: Lancia errore se non valida.
Linea 69: Chiude if.
Linea 70: Riga vuota per separare.

### Costante: grids

#### Codice
```typescript
  const grids: Grid[] = [initialGrid.map(row => [...row])];
```

#### Spiegazione
Linea 71: Inizializza grids con una copia dello stato iniziale.

### Costante: moves

#### Codice
```typescript
  const moves: (Direction | null)[] = [null];
  let current = initialGrid.map(row => [...row]);

  try {
    for (const move of movesSequence) {
      current = applyMove(current, move);
      grids.push(current);
      moves.push(move);
    }
  } catch (error) {
    console.error('Error computing puzzle states:', error);
    throw error;
  }

  return { grids, moves };
}

/**
 * Determina quale tessera Ã¨ stata appena spostata confrontando due stati.
 */
```

#### Spiegazione
Linea 72: Inizializza moves con null per lo step 0.
Linea 73: Copia iniziale in current.
Linea 74: Riga vuota per separare.
Linea 75: Inizia try per calcolare le mosse.
Linea 76: Ciclo su ogni mossa.
Linea 77: Applica la mossa e aggiorna current.
Linea 78: Aggiunge la griglia a grids.
Linea 79: Aggiunge la mossa a moves.
Linea 80: Chiude il ciclo.
Linea 81: Passa al catch.
Linea 82: Logga errore.
Linea 83: Rilancia l'errore.
Linea 84: Chiude try/catch.
Linea 85: Riga vuota per separare.
Linea 86: Ritorna grids e moves.
Linea 87: Chiude computeAllStates.
Linea 88: Riga vuota per separare.
Linea 89: Inizio commento JSDoc di getMovedTile.
Linea 90: Descrive il calcolo della tessera mossa.
Linea 91: Chiude il commento JSDoc.

### Funzione: getMovedTile

#### Codice
```typescript
export function getMovedTile(currentGrid: Grid, prevGrid: Grid | null): number {
  if (!prevGrid) return -1;
  try {
```

#### Spiegazione
Linea 92: Definisce getMovedTile.
Linea 93: Se non c'e' prevGrid, ritorna -1.
Linea 94: Inizia try.

### Sezione

#### Codice
```typescript
    const [r0, c0] = findZero(prevGrid);
    return currentGrid[r0][c0];
  } catch (error) {
    console.error('Error getting moved tile:', error);
    return -1;
  }
}

/**
 * Serializza una griglia in stringa leggibile.
 */
```

#### Spiegazione
Linea 95: Trova la posizione dello zero nel prevGrid.
Linea 96: Ritorna il valore in currentGrid in quella posizione.
Linea 97: Passa al catch.
Linea 98: Logga errore.
Linea 99: Ritorna -1 in caso di errore.
Linea 100: Chiude try/catch.
Linea 101: Chiude getMovedTile.
Linea 102: Riga vuota per separare.
Linea 103: Inizio commento JSDoc di gridToString.
Linea 104: Descrive la serializzazione della griglia.
Linea 105: Chiude il commento JSDoc.

### Funzione: gridToString

#### Codice
```typescript
export function gridToString(grid: Grid): string {
  if (!validateGrid(grid)) {
    return '[invalid grid]';
  }
  return grid.map(r => `[${r.join(',')}]`).join(',');
}

/**
 * Verifica se la griglia e nello stato finale: numeri in ordine e zero in basso a destra.
 */
```

#### Spiegazione
Linea 106: Definisce gridToString.
Linea 107: Se griglia non valida, ritorna stringa placeholder.
Linea 108: Ritorna placeholder per griglia invalida.
Linea 109: Chiude if.
Linea 110: Serializza la griglia in stringa leggibile.
Linea 111: Chiude gridToString.
Linea 112: Riga vuota per separare.
Linea 113: Inizio commento JSDoc di isGridSolved.
Linea 114: Descrive la verifica di stato finale.
Linea 115: Chiude il commento JSDoc.

### Funzione: isGridSolved

#### Codice
```typescript
export function isGridSolved(grid: Grid): boolean {
  if (!validateGrid(grid)) return false;

```

#### Spiegazione
Linea 116: Definisce isGridSolved.
Linea 117: Se griglia invalida, ritorna false.
Linea 118: Riga vuota per separare.

### Costante: size

#### Codice
```typescript
  const size = grid.length;
  let expected = 1;

  for (let r = 0; r < size; r++) {
    for (let c = 0; c < size; c++) {
```

#### Spiegazione
Linea 119: Salva la dimensione.
Linea 120: Inizializza il valore atteso.
Linea 121: Riga vuota per separare.
Linea 122: Ciclo sulle righe.
Linea 123: Ciclo sulle colonne.

### Costante: isLastCell

#### Codice
```typescript
      const isLastCell = r === size - 1 && c === size - 1;

      if (isLastCell) {
        return grid[r][c] === 0;
      }

      if (grid[r][c] !== expected) {
        return false;
      }

      expected += 1;
    }
  }

  return false;
}
```

#### Spiegazione
Linea 124: Determina se la cella e' l'ultima.
Linea 125: Riga vuota per separare.
Linea 126: Se e' ultima cella, verifica che sia 0.
Linea 127: Ritorna true/false per ultima cella.
Linea 128: Chiude if ultima cella.
Linea 129: Riga vuota per separare.
Linea 130: Se il valore non coincide con expected, ritorna false.
Linea 131: Ritorna false se ordine errato.
Linea 132: Chiude if ordine errato.
Linea 133: Riga vuota per separare.
Linea 134: Incrementa expected.
Linea 135: Chiude il ciclo colonne.
Linea 136: Chiude il ciclo righe.
Linea 137: Riga vuota per separare.
Linea 138: Ritorna false se non risolto (safety).
Linea 139: Chiude isGridSolved.

<a id="src-features-puzzle-hooks-useplayback-ts"></a>
## src/features/puzzle/hooks/usePlayback.ts

### Direttive

#### Codice
```typescript
"use client";

```

#### Spiegazione
Linea 1: Direttiva client per hook React.
Linea 2: Riga vuota per separare import.

### Import

#### Codice
```typescript
import { useEffect, useRef } from 'react';
import { useIsPlaying, useSpeed, useTick, usePause } from '@/features/puzzle/store/puzzleSelectors';

/**
 * Hook che gestisce l'autoplay del puzzle.
 * Crea un interval che avanza di un passo alla velocitÃ  impostata.
 * Fixed: Ottimizzato per evitare memory leaks con dipendenze corrette.
 */
```

#### Spiegazione
Linea 3: Importa useEffect e useRef.
Linea 4: Importa selector e azioni dal store.
Linea 5: Riga vuota per separare.
Linea 6: Inizio commento JSDoc del hook.
Linea 7: Descrive la funzione del hook.
Linea 8: Spiega l'uso dell'interval con velocita' impostata.
Linea 9: Nota sulla prevenzione dei memory leak.
Linea 10: Chiude il commento.

### Funzione: usePlayback

#### Codice
```typescript
export function usePlayback() {
```

#### Spiegazione
Linea 11: Definisce il hook usePlayback.

### Costante: intervalRef

#### Codice
```typescript
  const intervalRef = useRef<ReturnType<typeof setInterval> | null>(null);
```

#### Spiegazione
Linea 12: Crea un ref per l'interval.

### Costante: isPlaying

#### Codice
```typescript
  const isPlaying = useIsPlaying();
```

#### Spiegazione
Linea 13: Legge isPlaying dallo store.

### Costante: speed

#### Codice
```typescript
  const speed = useSpeed();
```

#### Spiegazione
Linea 14: Legge speed dallo store.

### Costante: tick

#### Codice
```typescript
  const tick = useTick();
```

#### Spiegazione
Linea 15: Legge tick dallo store.

### Costante: pause

#### Codice
```typescript
  const pause = usePause();

  useEffect(() => {
    if (!isPlaying) {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
        intervalRef.current = null;
      }
      return;
    }

    intervalRef.current = setInterval(() => {
```

#### Spiegazione
Linea 16: Legge pause dallo store.
Linea 17: Riga vuota per separare.
Linea 18: useEffect per gestire l'interval.
Linea 19: Se non in play, entra nel cleanup.
Linea 20: Se esiste interval, lo cancella.
Linea 21: clearInterval dell'interval.
Linea 22: Reset del ref a null.
Linea 23: Chiude if interval.
Linea 24: Ritorna per evitare setup.
Linea 25: Chiude if !isPlaying.
Linea 26: Riga vuota per separare.
Linea 27: Crea interval che chiama tick.

### Costante: hasMore

#### Codice
```typescript
      const hasMore = tick();
      if (!hasMore) {
        pause();
      }
    }, speed);

    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
        intervalRef.current = null;
      }
    };
  }, [isPlaying, speed, tick, pause]);
}
```

#### Spiegazione
Linea 28: Chiama tick e salva se ci sono altri step.
Linea 29: Se finito, chiama pause.
Linea 30: Chiude if hasMore.
Linea 31: Chiude setInterval.
Linea 32: Passa speed come delay.
Linea 33: Riga vuota per separare.
Linea 34: Cleanup dell'effetto.
Linea 35: Se interval attivo, lo cancella.
Linea 36: clearInterval.
Linea 37: Reset del ref.
Linea 38: Chiude if.
Linea 39: Chiude cleanup.
Linea 40: Chiude useEffect con dipendenze.
Linea 41: Chiude il hook usePlayback.

<a id="src-features-puzzle-store-usepuzzlestore-ts"></a>
## src/features/puzzle/store/usePuzzleStore.ts

### Import

#### Codice
```typescript
import { create } from 'zustand';
import type { ColorPaletteMode, Direction, Grid, GridSize, ThemeMode } from '@/features/puzzle/types/puzzle';
import { DEFAULT_GRID_SIZE, DEFAULT_SPEED, PUZZLE_CONFIGS } from '@/features/puzzle/constants/puzzle';
import { computeAllStates, applyMove, isGridSolved } from '@/features/puzzle/utils/puzzle';
import { postSolvePuzzle } from '@/features/puzzle/api/solverApi';

```

#### Spiegazione
Linea 1: Importa create da zustand per creare lo store.
Linea 2: Importa i tipi usati nello stato.
Linea 3: Importa costanti di configurazione.
Linea 4: Importa utilita' per stati e mosse.
Linea 5: Importa la funzione API per solve.
Linea 6: Riga vuota per separare.

### Costante: defaultConfig

#### Codice
```typescript
const defaultConfig = PUZZLE_CONFIGS[DEFAULT_GRID_SIZE];
```

#### Spiegazione
Linea 7: Seleziona la config di default dal catalogo.

### Sezione

#### Codice
```typescript
const { grids: defaultGrids, moves: defaultMoves } = computeAllStates(
  defaultConfig.initialGrid,
  defaultConfig.moves,
);

```

#### Spiegazione
Linea 8: Precalcola griglie e mosse della config di default.
Linea 9: Passa la griglia iniziale alla funzione.
Linea 10: Passa la sequenza di mosse alla funzione.
Linea 11: Chiude la chiamata computeAllStates.
Linea 12: Riga vuota per separare.

### Funzione: cloneGrid

#### Codice
```typescript
function cloneGrid(grid: Grid): Grid {
  return grid.map(row => [...row]);
}

```

#### Spiegazione
Linea 13: Helper per clonare una griglia.
Linea 14: Clona ogni riga con spread.
Linea 15: Chiude cloneGrid.
Linea 16: Riga vuota per separare.

### Funzione: generateGoalGrid

#### Codice
```typescript
function generateGoalGrid(size: GridSize): Grid {
```

#### Spiegazione
Linea 17: Helper per generare lo stato goal.

### Costante: grid

#### Codice
```typescript
  const grid: Grid = [];
  let value = 1;
  for (let i = 0; i < size; i++) {
```

#### Spiegazione
Linea 18: Inizializza la griglia vuota.
Linea 19: Inizializza il contatore delle tessere.
Linea 20: Ciclo sulle righe.

### Costante: row

#### Codice
```typescript
    const row: number[] = [];
    for (let j = 0; j < size; j++) {
      if (i === size - 1 && j === size - 1) {
        row.push(0);
      } else {
        row.push(value++);
      }
    }
    grid.push(row);
  }
  return grid;
}

```

#### Spiegazione
Linea 21: Inizializza una riga.
Linea 22: Ciclo sulle colonne.
Linea 23: Se e' l'ultima cella, inserisce 0.
Linea 24: Inserisce 0 nella cella finale.
Linea 25: Altrimenti, entra nel ramo else.
Linea 26: Inserisce il valore e incrementa.
Linea 27: Chiude l'else.
Linea 28: Chiude il ciclo colonne.
Linea 29: Aggiunge la riga alla griglia.
Linea 30: Chiude il ciclo righe.
Linea 31: Ritorna la griglia goal.
Linea 32: Chiude generateGoalGrid.
Linea 33: Riga vuota per separare.

### Interfaccia: PuzzleState

#### Codice
```typescript
interface PuzzleState {
  // Config corrente
  gridSize: GridSize;
  initialGrid: Grid;
  solutionMoves: Direction[];

  // Dati pre-calcolati
  allStates: Grid[];
  allMoves: (Direction | null)[];
  totalSteps: number;

  // ModalitÃ  gioco
  gameMode: 'play' | 'replay';
  manualGrid: Grid;

  // Settings globali UI
  themeMode: ThemeMode;
  colorPaletteMode: ColorPaletteMode;
  musicEnabled: boolean;

  // Stato replay/animazione
  step: number;
  isPlaying: boolean;
  speed: number;
  elapsedSeconds: number;
  timerEnabled: boolean;

  // Derivati
  currentGrid: Grid;
  prevGrid: Grid | null;
  currentMove: Direction | null;
  isSolved: boolean;
  error: string | null;

  // Azioni - Replay
  setStep: (step: number) => void;
  goNext: () => void;
  goPrev: () => void;
  play: () => void;
  pause: () => void;
  togglePlay: () => void;
  setSpeed: (speed: number) => void;
  jumpToStep: (step: number) => void;
  tick: () => boolean;

  // Azioni - Game Mode
  playMove: (direction: Direction) => void;
  giveUp: () => Promise<void>;
  restartGame: () => void;
  setGridSize: (size: GridSize) => void;
  tickElapsed: () => void;
  resetElapsed: () => void;
  setTimerEnabled: (enabled: boolean) => void;
  toggleTimerEnabled: () => void;
  setThemeMode: (mode: ThemeMode) => void;
  toggleThemeMode: () => void;
  setColorPaletteMode: (mode: ColorPaletteMode) => void;
  toggleMusicEnabled: () => void;
  clearError: () => void;

  // Custom board flow
  setCustomBoard: (size: GridSize, grid: Grid) => void;
  setGeneratedPuzzle: (size: GridSize, initialGrid: Grid, moves: Direction[]) => Promise<void>;
  setSolutionMovesFromApi: (moves: Direction[]) => void;
}

```

#### Spiegazione
Linea 34: Inizia la definizione dell'interfaccia PuzzleState.
Linea 35: Commento: sezione config corrente.
Linea 36: Stato: gridSize.
Linea 37: Stato: initialGrid.
Linea 38: Stato: solutionMoves.
Linea 39: Riga vuota per separare.
Linea 40: Commento: dati pre-calcolati.
Linea 41: Stato: allStates.
Linea 42: Stato: allMoves.
Linea 43: Stato: totalSteps.
Linea 44: Riga vuota per separare.
Linea 45: Commento: modalita' di gioco.
Linea 46: Stato: gameMode.
Linea 47: Stato: manualGrid.
Linea 48: Riga vuota per separare.
Linea 49: Commento: settings UI.
Linea 50: Stato: themeMode.
Linea 51: Stato: colorPaletteMode.
Linea 52: Stato: musicEnabled.
Linea 53: Riga vuota per separare.
Linea 54: Commento: stato replay/animazione.
Linea 55: Stato: step.
Linea 56: Stato: isPlaying.
Linea 57: Stato: speed.
Linea 58: Stato: elapsedSeconds.
Linea 59: Stato: timerEnabled.
Linea 60: Riga vuota per separare.
Linea 61: Commento: campi derivati.
Linea 62: Stato: currentGrid.
Linea 63: Stato: prevGrid.
Linea 64: Stato: currentMove.
Linea 65: Stato: isSolved.
Linea 66: Stato: error.
Linea 67: Riga vuota per separare.
Linea 68: Commento: azioni replay.
Linea 69: Azione setStep.
Linea 70: Azione goNext.
Linea 71: Azione goPrev.
Linea 72: Azione play.
Linea 73: Azione pause.
Linea 74: Azione togglePlay.
Linea 75: Azione setSpeed.
Linea 76: Azione jumpToStep.
Linea 77: Azione tick.
Linea 78: Riga vuota per separare.
Linea 79: Commento: azioni game mode.
Linea 80: Azione playMove.
Linea 81: Azione giveUp.
Linea 82: Azione restartGame.
Linea 83: Azione setGridSize.
Linea 84: Azione tickElapsed.
Linea 85: Azione resetElapsed.
Linea 86: Azione setTimerEnabled.
Linea 87: Azione toggleTimerEnabled.
Linea 88: Azione setThemeMode.
Linea 89: Azione toggleThemeMode.
Linea 90: Azione setColorPaletteMode.
Linea 91: Azione toggleMusicEnabled.
Linea 92: Azione clearError.
Linea 93: Riga vuota per separare.
Linea 94: Commento: custom board flow.
Linea 95: Azione setCustomBoard.
Linea 96: Azione setGeneratedPuzzle.
Linea 97: Azione setSolutionMovesFromApi.
Linea 98: Chiude l'interfaccia PuzzleState.
Linea 99: Riga vuota per separare.

### Costante: usePuzzleStore

#### Codice
```typescript
export const usePuzzleStore = create<PuzzleState>((set, get) => ({
  // Config corrente
  gridSize: defaultConfig.gridSize,
  initialGrid: cloneGrid(defaultConfig.initialGrid),
  solutionMoves: [...defaultConfig.moves],

  // Dati pre-calcolati
  allStates: defaultGrids,
  allMoves: defaultMoves,
  totalSteps: defaultGrids.length - 1,

  // ModalitÃ  gioco - Inizia in 'play'
  gameMode: 'play',
  manualGrid: cloneGrid(defaultGrids[0]),

  // Settings globali
  themeMode: 'dark',
  colorPaletteMode: 'default',
  musicEnabled: true,

  // Stato replay/animazione iniziale
  step: 0,
  isPlaying: false,
  speed: DEFAULT_SPEED,
  elapsedSeconds: 0,
  timerEnabled: true,

  // Derivati
  currentGrid: cloneGrid(defaultGrids[0]),
  prevGrid: null,
  currentMove: null,
  isSolved: false,
  error: null,

  // Azioni - Replay
  setStep: (step) => {
```

#### Spiegazione
Linea 100: Crea lo store Zustand con stato e azioni.
Linea 101: Commento: config corrente.
Linea 102: Stato iniziale gridSize.
Linea 103: Stato iniziale initialGrid (clonato).
Linea 104: Stato iniziale solutionMoves.
Linea 105: Riga vuota per separare.
Linea 106: Commento: dati pre-calcolati.
Linea 107: Stato iniziale allStates.
Linea 108: Stato iniziale allMoves.
Linea 109: Stato iniziale totalSteps.
Linea 110: Riga vuota per separare.
Linea 111: Commento: modalita' di gioco.
Linea 112: Imposta gameMode su play.
Linea 113: Imposta manualGrid con lo stato iniziale.
Linea 114: Riga vuota per separare.
Linea 115: Commento: settings globali.
Linea 116: Imposta themeMode su dark.
Linea 117: Imposta colorPaletteMode su default.
Linea 118: Imposta musicEnabled su true.
Linea 119: Riga vuota per separare.
Linea 120: Commento: replay iniziale.
Linea 121: Imposta step iniziale.
Linea 122: Imposta isPlaying a false.
Linea 123: Imposta speed con DEFAULT_SPEED.
Linea 124: Imposta elapsedSeconds a 0.
Linea 125: Imposta timerEnabled a true.
Linea 126: Riga vuota per separare.
Linea 127: Commento: campi derivati.
Linea 128: Imposta currentGrid iniziale.
Linea 129: Imposta prevGrid a null.
Linea 130: Imposta currentMove a null.
Linea 131: Imposta isSolved a false.
Linea 132: Imposta error a null.
Linea 133: Riga vuota per separare.
Linea 134: Commento: azioni replay.
Linea 135: Definisce setStep.

### Sezione

#### Codice
```typescript
    const { allStates, allMoves, totalSteps, gameMode } = get();
    if (gameMode !== 'replay') return;

```

#### Spiegazione
Linea 136: Legge stato necessario con get().
Linea 137: Esce se non in replay.
Linea 138: Riga vuota per separare.

### Costante: clamped

#### Codice
```typescript
    const clamped = Math.max(0, Math.min(totalSteps, step));
    set({
      step: clamped,
      currentGrid: allStates[clamped],
      prevGrid: clamped > 0 ? allStates[clamped - 1] : null,
      currentMove: allMoves[clamped],
      isSolved: clamped === totalSteps,
    });
  },

  goNext: () => {
```

#### Spiegazione
Linea 139: Clampa lo step tra 0 e totalSteps.
Linea 140: Aggiorna lo stato con set().
Linea 141: Aggiorna step.
Linea 142: Aggiorna currentGrid.
Linea 143: Aggiorna prevGrid se step > 0.
Linea 144: Aggiorna currentMove.
Linea 145: Aggiorna isSolved.
Linea 146: Chiude set().
Linea 147: Chiude setStep.
Linea 148: Riga vuota per separare.
Linea 149: Definisce goNext.

### Sezione

#### Codice
```typescript
    const { step, totalSteps, gameMode } = get();
    if (gameMode !== 'replay' || step >= totalSteps) return;
    get().setStep(step + 1);
  },

  goPrev: () => {
```

#### Spiegazione
Linea 150: Legge step, totalSteps e gameMode.
Linea 151: Se non in replay o a fine, esce.
Linea 152: Avanza di uno step.
Linea 153: Chiude goNext.
Linea 154: Riga vuota per separare.
Linea 155: Definisce goPrev.

### Sezione

#### Codice
```typescript
    const { step, gameMode } = get();
    if (gameMode !== 'replay' || step <= 0) return;
    get().setStep(step - 1);
  },

  play: () => {
```

#### Spiegazione
Linea 156: Legge step e gameMode.
Linea 157: Esce se non replay o gia' a 0.
Linea 158: Torna indietro di uno step.
Linea 159: Chiude goPrev.
Linea 160: Riga vuota per separare.
Linea 161: Definisce play.

### Sezione

#### Codice
```typescript
    const { step, totalSteps, gameMode } = get();
    if (gameMode !== 'replay') return;
    if (step >= totalSteps) get().setStep(0);
    set({ isPlaying: true });
  },

  pause: () => {
    set({ isPlaying: false });
  },

  togglePlay: () => {
```

#### Spiegazione
Linea 162: Legge step, totalSteps e gameMode.
Linea 163: Esce se non replay.
Linea 164: Se a fine, resetta step a 0.
Linea 165: Imposta isPlaying a true.
Linea 166: Chiude play.
Linea 167: Riga vuota per separare.
Linea 168: Definisce pause.
Linea 169: Imposta isPlaying a false.
Linea 170: Chiude pause.
Linea 171: Riga vuota per separare.
Linea 172: Definisce togglePlay.

### Sezione

#### Codice
```typescript
    const { isPlaying, gameMode } = get();
    if (gameMode !== 'replay') return;
    if (isPlaying) get().pause();
    else get().play();
  },

  setSpeed: (speed) => set({ speed }),

  jumpToStep: (step) => {
```

#### Spiegazione
Linea 173: Legge isPlaying e gameMode.
Linea 174: Esce se non replay.
Linea 175: Se in play, chiama pause.
Linea 176: Altrimenti, chiama play.
Linea 177: Chiude togglePlay.
Linea 178: Riga vuota per separare.
Linea 179: Definisce setSpeed con setter diretto.
Linea 180: Riga vuota per separare.
Linea 181: Definisce jumpToStep.

### Sezione

#### Codice
```typescript
    const { gameMode } = get();
    if (gameMode !== 'replay') return;
    get().pause();
    get().setStep(step);
  },

  tick: () => {
```

#### Spiegazione
Linea 182: Legge gameMode.
Linea 183: Esce se non replay.
Linea 184: Mette in pausa.
Linea 185: Imposta lo step richiesto.
Linea 186: Chiude jumpToStep.
Linea 187: Riga vuota per separare.
Linea 188: Definisce tick per autoplay.

### Sezione

#### Codice
```typescript
    const { step, totalSteps, gameMode } = get();
    if (gameMode !== 'replay' || step >= totalSteps) {
      get().pause();
      return false;
    }
    get().setStep(step + 1);
    return true;
  },

  // Azioni - Game Mode
  playMove: (direction: Direction) => {
```

#### Spiegazione
Linea 189: Legge step, totalSteps e gameMode.
Linea 190: Se non replay o finito, entra nel blocco.
Linea 191: Mette in pausa.
Linea 192: Ritorna false (nessun passo).
Linea 193: Chiude if.
Linea 194: Avanza di uno step.
Linea 195: Ritorna true (ha avanzato).
Linea 196: Chiude tick.
Linea 197: Riga vuota per separare.
Linea 198: Commento: azioni game mode.
Linea 199: Definisce playMove.

### Sezione

#### Codice
```typescript
    const { gameMode, manualGrid, isSolved } = get();
    if (gameMode !== 'play' || isSolved) return;

    try {
```

#### Spiegazione
Linea 200: Legge gameMode, manualGrid e isSolved.
Linea 201: Esce se non play o gia' risolto.
Linea 202: Riga vuota per separare.
Linea 203: Inizia try per applicare la mossa.

### Costante: newGrid

#### Codice
```typescript
      const newGrid = applyMove(manualGrid, direction);
      set({
        manualGrid: newGrid,
        isSolved: isGridSolved(newGrid),
      });
    } catch (error) {
      console.warn('Invalid move:', error);
    }
  },

  giveUp: async () => {
```

#### Spiegazione
Linea 204: Calcola newGrid applicando la mossa.
Linea 205: Aggiorna stato con set().
Linea 206: Imposta manualGrid.
Linea 207: Aggiorna isSolved con check.
Linea 208: Chiude set().
Linea 209: Passa al catch.
Linea 210: Logga mossa non valida.
Linea 211: Chiude try/catch.
Linea 212: Chiude playMove.
Linea 213: Riga vuota per separare.
Linea 214: Definisce giveUp async.

### Sezione

#### Codice
```typescript
    const { manualGrid } = get();
    
    try {
      // Chiama l'API per ottenere la soluzione del puzzle corrente
```

#### Spiegazione
Linea 215: Legge manualGrid.
Linea 216: Riga vuota per separare.
Linea 217: Inizia try.
Linea 218: Commento: richiesta solve al backend.

### Costante: solutionMoves

#### Codice
```typescript
      const solutionMoves = await postSolvePuzzle(manualGrid);
      
      // Genera gli stati dal puzzle mischiato (inizio) al goal (fine)
```

#### Spiegazione
Linea 219: Chiama postSolvePuzzle.
Linea 220: Riga vuota per separare.
Linea 221: Commento: genera gli stati del replay.

### Sezione

#### Codice
```typescript
      const { grids, moves: allMoves } = computeAllStates(manualGrid, solutionMoves);
      
      set({
        gameMode: 'replay',
        initialGrid: cloneGrid(manualGrid),
        step: 0,
        currentGrid: grids[0],
        prevGrid: null,
        currentMove: allMoves[0],
        isPlaying: true,
        allStates: grids,
        allMoves,
        totalSteps: grids.length - 1,
        error: null,
      });
    } catch (error) {
      console.error('Errore nel risolvere il puzzle:', error);
      
      // Estrai il messaggio di errore
      let errorMessage = 'Errore nella risoluzione del puzzle. Riprova!';
      if (error instanceof Error) {
        if (error.message.includes('500') || error.message.includes('not solvable')) {
          errorMessage = 'âŒ Puzzle non risolvibile\n\nIl puzzle attuale non puÃ² essere risolto. Prova a fare altre mosse o ricarica il gioco!';
        }
      }

      set({
        error: errorMessage,
      });
    }
  },

  restartGame: () => {
```

#### Spiegazione
Linea 222: Calcola allStates e allMoves.
Linea 223: Riga vuota per separare.
Linea 224: Aggiorna lo store con i dati di replay.
Linea 225: Imposta gameMode su replay.
Linea 226: Imposta initialGrid.
Linea 227: Reset step.
Linea 228: Imposta currentGrid.
Linea 229: Imposta prevGrid a null.
Linea 230: Imposta currentMove iniziale.
Linea 231: Avvia isPlaying.
Linea 232: Salva allStates.
Linea 233: Salva allMoves.
Linea 234: Imposta totalSteps.
Linea 235: Resetta error.
Linea 236: Chiude set().
Linea 237: Passa al catch.
Linea 238: Logga errore di solve.
Linea 239: Riga vuota per separare.
Linea 240: Commento: costruzione messaggio errore.
Linea 241: Imposta messaggio di default.
Linea 242: Se error e' Error, entra.
Linea 243: Se messaggio contiene 500 o not solvable, personalizza.
Linea 244: Messaggio per puzzle non risolvibile.
Linea 245: Chiude if interno.
Linea 246: Chiude if esterno.
Linea 247: Riga vuota per separare.
Linea 248: Aggiorna lo stato error.
Linea 249: Imposta error nel set.
Linea 250: Chiude set().
Linea 251: Chiude catch.
Linea 252: Chiude giveUp.
Linea 253: Riga vuota per separare.
Linea 254: Definisce restartGame.

### Sezione

#### Codice
```typescript
    const { allStates } = get();
    // allStates[0] = goal, allStates[last] = puzzle mischiato
```

#### Spiegazione
Linea 255: Legge allStates.
Linea 256: Commento: note sui contenuti di allStates.

### Costante: puzzleStart

#### Codice
```typescript
    const puzzleStart = allStates[allStates.length - 1] || allStates[0];
    set({
      gameMode: 'play',
      manualGrid: cloneGrid(puzzleStart),
      step: 0,
      isPlaying: false,
      currentGrid: cloneGrid(puzzleStart),
      prevGrid: null,
      currentMove: null,
      isSolved: false,
      elapsedSeconds: 0,
    });
  },

  setGridSize: (size: GridSize) => {
```

#### Spiegazione
Linea 257: Calcola puzzleStart dall'ultimo stato.
Linea 258: Aggiorna lo stato per tornare a play.
Linea 259: Imposta gameMode play.
Linea 260: Imposta manualGrid al puzzleStart.
Linea 261: Reset step.
Linea 262: Ferma isPlaying.
Linea 263: Imposta currentGrid.
Linea 264: Imposta prevGrid a null.
Linea 265: Imposta currentMove a null.
Linea 266: Imposta isSolved a false.
Linea 267: Reset elapsedSeconds.
Linea 268: Chiude set().
Linea 269: Chiude restartGame.
Linea 270: Riga vuota per separare.
Linea 271: Definisce setGridSize.

### Costante: config

#### Codice
```typescript
    const config = PUZZLE_CONFIGS[size];
```

#### Spiegazione
Linea 272: Seleziona la config per size.

### Sezione

#### Codice
```typescript
    const { grids, moves } = computeAllStates(config.initialGrid, config.moves);

    set({
      gridSize: size,
      initialGrid: cloneGrid(config.initialGrid),
      solutionMoves: [...config.moves],
      allStates: grids,
      allMoves: moves,
      totalSteps: grids.length - 1,
      gameMode: 'play',
      manualGrid: cloneGrid(grids[0]),
      step: 0,
      isPlaying: false,
      currentGrid: cloneGrid(grids[0]),
      prevGrid: null,
      currentMove: null,
      isSolved: false,
      elapsedSeconds: 0,
    });
  },

  tickElapsed: () => {
```

#### Spiegazione
Linea 273: Calcola griglie e mosse per la config.
Linea 274: Riga vuota per separare.
Linea 275: Aggiorna lo stato con la nuova config.
Linea 276: Imposta gridSize.
Linea 277: Imposta initialGrid.
Linea 278: Imposta solutionMoves.
Linea 279: Imposta allStates.
Linea 280: Imposta allMoves.
Linea 281: Imposta totalSteps.
Linea 282: Imposta gameMode play.
Linea 283: Imposta manualGrid.
Linea 284: Reset step.
Linea 285: Ferma isPlaying.
Linea 286: Imposta currentGrid.
Linea 287: Imposta prevGrid a null.
Linea 288: Imposta currentMove a null.
Linea 289: Imposta isSolved a false.
Linea 290: Reset elapsedSeconds.
Linea 291: Chiude set().
Linea 292: Chiude setGridSize.
Linea 293: Riga vuota per separare.
Linea 294: Definisce tickElapsed.

### Sezione

#### Codice
```typescript
    const { gameMode, isSolved } = get();
    if (gameMode !== 'play' || isSolved) return;
    set(state => ({ elapsedSeconds: state.elapsedSeconds + 1 }));
  },

  resetElapsed: () => {
    set({ elapsedSeconds: 0 });
  },

  setTimerEnabled: (enabled: boolean) => {
    set({ timerEnabled: enabled });
  },

  toggleTimerEnabled: () => {
    set(state => ({ timerEnabled: !state.timerEnabled }));
  },

  setThemeMode: (mode: ThemeMode) => {
    set({ themeMode: mode });
  },

  toggleThemeMode: () => {
    set(state => ({ themeMode: state.themeMode === 'dark' ? 'light' : 'dark' }));
  },

  setColorPaletteMode: (mode: ColorPaletteMode) => {
    set({ colorPaletteMode: mode });
  },

  toggleMusicEnabled: () => {
    set(state => ({ musicEnabled: !state.musicEnabled }));
  },

  clearError: () => {
    set({ error: null });
  },

  setCustomBoard: (size: GridSize, grid: Grid) => {
```

#### Spiegazione
Linea 295: Legge gameMode e isSolved.
Linea 296: Esce se non play o risolto.
Linea 297: Incrementa elapsedSeconds.
Linea 298: Chiude tickElapsed.
Linea 299: Riga vuota per separare.
Linea 300: Definisce resetElapsed.
Linea 301: Resetta elapsedSeconds a 0.
Linea 302: Chiude resetElapsed.
Linea 303: Riga vuota per separare.
Linea 304: Definisce setTimerEnabled.
Linea 305: Aggiorna timerEnabled.
Linea 306: Chiude setTimerEnabled.
Linea 307: Riga vuota per separare.
Linea 308: Definisce toggleTimerEnabled.
Linea 309: Inverte timerEnabled.
Linea 310: Chiude toggleTimerEnabled.
Linea 311: Riga vuota per separare.
Linea 312: Definisce setThemeMode.
Linea 313: Aggiorna themeMode.
Linea 314: Chiude setThemeMode.
Linea 315: Riga vuota per separare.
Linea 316: Definisce toggleThemeMode.
Linea 317: Inverte themeMode tra dark e light.
Linea 318: Chiude toggleThemeMode.
Linea 319: Riga vuota per separare.
Linea 320: Definisce setColorPaletteMode.
Linea 321: Aggiorna colorPaletteMode.
Linea 322: Chiude setColorPaletteMode.
Linea 323: Riga vuota per separare.
Linea 324: Definisce toggleMusicEnabled.
Linea 325: Inverte musicEnabled.
Linea 326: Chiude toggleMusicEnabled.
Linea 327: Riga vuota per separare.
Linea 328: Definisce clearError.
Linea 329: Resetta error a null.
Linea 330: Chiude clearError.
Linea 331: Riga vuota per separare.
Linea 332: Definisce setCustomBoard.

### Costante: customGrid

#### Codice
```typescript
    const customGrid = cloneGrid(grid);
    set({
      gridSize: size,
      initialGrid: customGrid,
      solutionMoves: [],
      allStates: [customGrid],
      allMoves: [null],
      totalSteps: 0,
      gameMode: 'play',
      manualGrid: cloneGrid(customGrid),
      step: 0,
      isPlaying: false,
      currentGrid: cloneGrid(customGrid),
      prevGrid: null,
      currentMove: null,
      isSolved: isGridSolved(customGrid),
      elapsedSeconds: 0,
    });
  },

  setGeneratedPuzzle: async (size: GridSize, initialGrid: Grid, moves: Direction[]) => {
    // initialGrid dal backend Ã¨ il puzzle mischiato
```

#### Spiegazione
Linea 333: Clona la griglia custom.
Linea 334: Aggiorna lo stato con la griglia custom.
Linea 335: Imposta gridSize.
Linea 336: Imposta initialGrid.
Linea 337: Resetta solutionMoves.
Linea 338: Imposta allStates con sola griglia.
Linea 339: Imposta allMoves con null.
Linea 340: Imposta totalSteps a 0.
Linea 341: Imposta gameMode play.
Linea 342: Imposta manualGrid.
Linea 343: Reset step.
Linea 344: Ferma isPlaying.
Linea 345: Imposta currentGrid.
Linea 346: Imposta prevGrid a null.
Linea 347: Imposta currentMove a null.
Linea 348: Imposta isSolved in base alla griglia.
Linea 349: Reset elapsedSeconds.
Linea 350: Chiude set().
Linea 351: Chiude setCustomBoard.
Linea 352: Riga vuota per separare.
Linea 353: Definisce setGeneratedPuzzle async.
Linea 354: Commento: initialGrid e' il puzzle mischiato.

### Costante: scrambledGrid

#### Codice
```typescript
    const scrambledGrid = cloneGrid(initialGrid);
    
    // Genera lo stato goal per il playback
```

#### Spiegazione
Linea 355: Clona la griglia mischiata.
Linea 356: Riga vuota per separare.
Linea 357: Commento: genera stato goal per fallback.

### Costante: goalGrid

#### Codice
```typescript
    const goalGrid = generateGoalGrid(size);

    try {
      // Chiama l'API per ottenere le mosse di soluzione
```

#### Spiegazione
Linea 358: Crea la griglia goal.
Linea 359: Riga vuota per separare.
Linea 360: Inizia try.
Linea 361: Commento: chiama API solve.

### Costante: solutionMoves

#### Codice
```typescript
      const solutionMoves = await postSolvePuzzle(scrambledGrid);
      
      // Genera gli stati dal puzzle mischiato (inizio) al goal (fine)
```

#### Spiegazione
Linea 362: Richiede le mosse soluzione.
Linea 363: Riga vuota per separare.
Linea 364: Commento: genera stati dal puzzle mischiato.

### Sezione

#### Codice
```typescript
      const { grids, moves: allMoves } = computeAllStates(scrambledGrid, solutionMoves);

      set({
        gridSize: size,
        initialGrid: cloneGrid(scrambledGrid),
        solutionMoves: solutionMoves,
        allStates: grids,
        allMoves,
        totalSteps: grids.length - 1,
        gameMode: 'play',
        manualGrid: cloneGrid(scrambledGrid),
        step: 0,
        isPlaying: false,
        currentGrid: cloneGrid(scrambledGrid),
        prevGrid: null,
        currentMove: null,
        isSolved: false,
        elapsedSeconds: 0,
        error: null,
      });
    } catch (error) {
      console.warn('Puzzle irrisolvibile o errore API. Gioco senza playback animato.', error);
      
      // Estrai il messaggio di errore
      let errorMessage = 'Il puzzle generato non Ã¨ risolvibile. Riprova!';
      if (error instanceof Error) {
        if (error.message.includes('500') || error.message.includes('not solvable')) {
          errorMessage = 'âŒ Puzzle non risolvibile\n\nIl backend ha generato un puzzle che non puÃ² essere risolto. Per favore riprova!';
        }
      }

      set({
        gridSize: size,
        initialGrid: cloneGrid(scrambledGrid),
        solutionMoves: [],
        allStates: [scrambledGrid, goalGrid],
        allMoves: [null, null],
        totalSteps: 1,
        gameMode: 'play',
        manualGrid: cloneGrid(scrambledGrid),
        step: 0,
        isPlaying: false,
        currentGrid: cloneGrid(scrambledGrid),
        prevGrid: null,
        currentMove: null,
        isSolved: false,
        elapsedSeconds: 0,
        error: errorMessage,
      });
    }
  },

  setSolutionMovesFromApi: (moves: Direction[]) => {
```

#### Spiegazione
Linea 365: Calcola griglie e mosse del replay.
Linea 366: Riga vuota per separare.
Linea 367: Aggiorna lo stato con i dati generati.
Linea 368: Imposta gridSize.
Linea 369: Imposta initialGrid.
Linea 370: Imposta solutionMoves.
Linea 371: Imposta allStates.
Linea 372: Imposta allMoves.
Linea 373: Imposta totalSteps.
Linea 374: Imposta gameMode play.
Linea 375: Imposta manualGrid.
Linea 376: Reset step.
Linea 377: Ferma isPlaying.
Linea 378: Imposta currentGrid.
Linea 379: Imposta prevGrid a null.
Linea 380: Imposta currentMove a null.
Linea 381: Imposta isSolved a false.
Linea 382: Reset elapsedSeconds.
Linea 383: Resetta error.
Linea 384: Chiude set().
Linea 385: Passa al catch.
Linea 386: Logga warning su puzzle non risolvibile.
Linea 387: Riga vuota per separare.
Linea 388: Commento: costruzione messaggio errore.
Linea 389: Messaggio di default per puzzle non risolvibile.
Linea 390: Se error e' Error, entra.
Linea 391: Se messaggio contiene 500 o not solvable, personalizza.
Linea 392: Messaggio specifico per errore backend.
Linea 393: Chiude if interno.
Linea 394: Chiude if esterno.
Linea 395: Riga vuota per separare.
Linea 396: Aggiorna lo stato con fallback locale.
Linea 397: Imposta gridSize.
Linea 398: Imposta initialGrid.
Linea 399: Resetta solutionMoves.
Linea 400: Imposta allStates con scrambled e goal.
Linea 401: Imposta allMoves con null.
Linea 402: Imposta totalSteps a 1.
Linea 403: Imposta gameMode play.
Linea 404: Imposta manualGrid.
Linea 405: Reset step.
Linea 406: Ferma isPlaying.
Linea 407: Imposta currentGrid.
Linea 408: Imposta prevGrid a null.
Linea 409: Imposta currentMove a null.
Linea 410: Imposta isSolved a false.
Linea 411: Reset elapsedSeconds.
Linea 412: Imposta error con errorMessage.
Linea 413: Chiude set().
Linea 414: Chiude catch.
Linea 415: Chiude setGeneratedPuzzle.
Linea 416: Riga vuota per separare.
Linea 417: Definisce setSolutionMovesFromApi.

### Sezione

#### Codice
```typescript
    const { initialGrid } = get();
```

#### Spiegazione
Linea 418: Legge initialGrid dallo store.

### Sezione

#### Codice
```typescript
    const { grids, moves: allMoves } = computeAllStates(initialGrid, moves);

    set({
      solutionMoves: [...moves],
      allStates: grids,
      allMoves,
      totalSteps: grids.length - 1,
      gameMode: 'replay',
      step: 0,
      isPlaying: false,
      currentGrid: grids[0],
      prevGrid: null,
      currentMove: allMoves[0],
      isSolved: false,
    });
  },
}));

// Usabile anche fuori da React, ad esempio nelle API request builders.
```

#### Spiegazione
Linea 419: Calcola griglie e mosse dalla soluzione.
Linea 420: Riga vuota per separare.
Linea 421: Aggiorna lo stato per la modalita' replay.
Linea 422: Imposta solutionMoves.
Linea 423: Imposta allStates.
Linea 424: Imposta allMoves.
Linea 425: Imposta totalSteps.
Linea 426: Imposta gameMode replay.
Linea 427: Reset step.
Linea 428: Ferma isPlaying.
Linea 429: Imposta currentGrid.
Linea 430: Imposta prevGrid a null.
Linea 431: Imposta currentMove.
Linea 432: Imposta isSolved a false.
Linea 433: Chiude set().
Linea 434: Chiude setSolutionMovesFromApi.
Linea 435: Chiude create(...) e lo store.
Linea 436: Riga vuota per separare.
Linea 437: Commento: uso fuori da React.

### Costante: getGridSizeForApi

#### Codice
```typescript
export const getGridSizeForApi = (): GridSize => usePuzzleStore.getState().gridSize;
```

#### Spiegazione
Linea 438: Esporta helper per leggere gridSize dallo store.

<a id="src-features-puzzle-store-puzzleselectors-ts"></a>
## src/features/puzzle/store/puzzleSelectors.ts

### Import

#### Codice
```typescript
import { usePuzzleStore } from './usePuzzleStore';

// State selectors
```

#### Spiegazione
Linea 5: Importa usePuzzleStore.
Linea 6: Riga vuota per separare.
Linea 7: Commento: selector di stato.

### Costante: useGridSize

#### Codice
```typescript
export const useGridSize = () => usePuzzleStore(s => s.gridSize);
```

#### Spiegazione
Linea 8: Selector per gridSize.

### Costante: useInitialGrid

#### Codice
```typescript
export const useInitialGrid = () => usePuzzleStore(s => s.initialGrid);
```

#### Spiegazione
Linea 9: Selector per initialGrid.

### Costante: useSolutionMoves

#### Codice
```typescript
export const useSolutionMoves = () => usePuzzleStore(s => s.solutionMoves);
```

#### Spiegazione
Linea 10: Selector per solutionMoves.

### Costante: useAllStates

#### Codice
```typescript
export const useAllStates = () => usePuzzleStore(s => s.allStates);
```

#### Spiegazione
Linea 11: Selector per allStates.

### Costante: useAllMoves

#### Codice
```typescript
export const useAllMoves = () => usePuzzleStore(s => s.allMoves);
```

#### Spiegazione
Linea 12: Selector per allMoves.

### Costante: useTotalSteps

#### Codice
```typescript
export const useTotalSteps = () => usePuzzleStore(s => s.totalSteps);
```

#### Spiegazione
Linea 13: Selector per totalSteps.

### Costante: useStep

#### Codice
```typescript
export const useStep = () => usePuzzleStore(s => s.step);
```

#### Spiegazione
Linea 14: Selector per step.

### Costante: useIsPlaying

#### Codice
```typescript
export const useIsPlaying = () => usePuzzleStore(s => s.isPlaying);
```

#### Spiegazione
Linea 15: Selector per isPlaying.

### Costante: useSpeed

#### Codice
```typescript
export const useSpeed = () => usePuzzleStore(s => s.speed);
```

#### Spiegazione
Linea 16: Selector per speed.

### Costante: useElapsedSeconds

#### Codice
```typescript
export const useElapsedSeconds = () => usePuzzleStore(s => s.elapsedSeconds);
```

#### Spiegazione
Linea 17: Selector per elapsedSeconds.

### Costante: useTimerEnabled

#### Codice
```typescript
export const useTimerEnabled = () => usePuzzleStore(s => s.timerEnabled);
```

#### Spiegazione
Linea 18: Selector per timerEnabled.

### Costante: useThemeMode

#### Codice
```typescript
export const useThemeMode = () => usePuzzleStore(s => s.themeMode);
```

#### Spiegazione
Linea 19: Selector per themeMode.

### Costante: useColorPaletteMode

#### Codice
```typescript
export const useColorPaletteMode = () => usePuzzleStore(s => s.colorPaletteMode);
```

#### Spiegazione
Linea 20: Selector per colorPaletteMode.

### Costante: useMusicEnabled

#### Codice
```typescript
export const useMusicEnabled = () => usePuzzleStore(s => s.musicEnabled);
```

#### Spiegazione
Linea 21: Selector per musicEnabled.

### Costante: useCurrentGrid

#### Codice
```typescript
export const useCurrentGrid = () => usePuzzleStore(s => s.currentGrid);
```

#### Spiegazione
Linea 22: Selector per currentGrid.

### Costante: usePrevGrid

#### Codice
```typescript
export const usePrevGrid = () => usePuzzleStore(s => s.prevGrid);
```

#### Spiegazione
Linea 23: Selector per prevGrid.

### Costante: useCurrentMove

#### Codice
```typescript
export const useCurrentMove = () => usePuzzleStore(s => s.currentMove);
```

#### Spiegazione
Linea 24: Selector per currentMove.

### Costante: useIsSolved

#### Codice
```typescript
export const useIsSolved = () => usePuzzleStore(s => s.isSolved);
```

#### Spiegazione
Linea 25: Selector per isSolved.

### Costante: useError

#### Codice
```typescript
export const useError = () => usePuzzleStore(s => s.error);

// Game mode selectors
```

#### Spiegazione
Linea 26: Selector per error.
Linea 27: Riga vuota per separare.
Linea 28: Commento: selector per game mode.

### Costante: useGameMode

#### Codice
```typescript
export const useGameMode = () => usePuzzleStore(s => s.gameMode);
```

#### Spiegazione
Linea 29: Selector per gameMode.

### Costante: useManualGrid

#### Codice
```typescript
export const useManualGrid = () => usePuzzleStore(s => s.manualGrid);

// Replay action selectors
```

#### Spiegazione
Linea 30: Selector per manualGrid.
Linea 31: Riga vuota per separare.
Linea 32: Commento: selector per azioni replay.

### Costante: useSetStep

#### Codice
```typescript
export const useSetStep = () => usePuzzleStore(s => s.setStep);
```

#### Spiegazione
Linea 33: Selector setStep.

### Costante: useGoNext

#### Codice
```typescript
export const useGoNext = () => usePuzzleStore(s => s.goNext);
```

#### Spiegazione
Linea 34: Selector goNext.

### Costante: useGoPrev

#### Codice
```typescript
export const useGoPrev = () => usePuzzleStore(s => s.goPrev);
```

#### Spiegazione
Linea 35: Selector goPrev.

### Costante: usePlay

#### Codice
```typescript
export const usePlay = () => usePuzzleStore(s => s.play);
```

#### Spiegazione
Linea 36: Selector play.

### Costante: usePause

#### Codice
```typescript
export const usePause = () => usePuzzleStore(s => s.pause);
```

#### Spiegazione
Linea 37: Selector pause.

### Costante: useTogglePlay

#### Codice
```typescript
export const useTogglePlay = () => usePuzzleStore(s => s.togglePlay);
```

#### Spiegazione
Linea 38: Selector togglePlay.

### Costante: useSetSpeed

#### Codice
```typescript
export const useSetSpeed = () => usePuzzleStore(s => s.setSpeed);
```

#### Spiegazione
Linea 39: Selector setSpeed.

### Costante: useJumpToStep

#### Codice
```typescript
export const useJumpToStep = () => usePuzzleStore(s => s.jumpToStep);
```

#### Spiegazione
Linea 40: Selector jumpToStep.

### Costante: useTick

#### Codice
```typescript
export const useTick = () => usePuzzleStore(s => s.tick);

// Game mode action selectors
```

#### Spiegazione
Linea 41: Selector tick.
Linea 42: Riga vuota per separare.
Linea 43: Commento: selector per azioni game mode.

### Costante: usePlayMove

#### Codice
```typescript
export const usePlayMove = () => usePuzzleStore(s => s.playMove);
```

#### Spiegazione
Linea 44: Selector playMove.

### Costante: useGiveUp

#### Codice
```typescript
export const useGiveUp = () => usePuzzleStore(s => s.giveUp);
```

#### Spiegazione
Linea 45: Selector giveUp.

### Costante: useRestartGame

#### Codice
```typescript
export const useRestartGame = () => usePuzzleStore(s => s.restartGame);
```

#### Spiegazione
Linea 46: Selector restartGame.

### Costante: useSetGridSize

#### Codice
```typescript
export const useSetGridSize = () => usePuzzleStore(s => s.setGridSize);
```

#### Spiegazione
Linea 47: Selector setGridSize.

### Costante: useTickElapsed

#### Codice
```typescript
export const useTickElapsed = () => usePuzzleStore(s => s.tickElapsed);
```

#### Spiegazione
Linea 48: Selector tickElapsed.

### Costante: useResetElapsed

#### Codice
```typescript
export const useResetElapsed = () => usePuzzleStore(s => s.resetElapsed);
```

#### Spiegazione
Linea 49: Selector resetElapsed.

### Costante: useSetTimerEnabled

#### Codice
```typescript
export const useSetTimerEnabled = () => usePuzzleStore(s => s.setTimerEnabled);
```

#### Spiegazione
Linea 50: Selector setTimerEnabled.

### Costante: useToggleTimerEnabled

#### Codice
```typescript
export const useToggleTimerEnabled = () => usePuzzleStore(s => s.toggleTimerEnabled);
```

#### Spiegazione
Linea 51: Selector toggleTimerEnabled.

### Costante: useSetThemeMode

#### Codice
```typescript
export const useSetThemeMode = () => usePuzzleStore(s => s.setThemeMode);
```

#### Spiegazione
Linea 52: Selector setThemeMode.

### Costante: useToggleThemeMode

#### Codice
```typescript
export const useToggleThemeMode = () => usePuzzleStore(s => s.toggleThemeMode);
```

#### Spiegazione
Linea 53: Selector toggleThemeMode.

### Costante: useSetColorPaletteMode

#### Codice
```typescript
export const useSetColorPaletteMode = () => usePuzzleStore(s => s.setColorPaletteMode);
```

#### Spiegazione
Linea 54: Selector setColorPaletteMode.

### Costante: useToggleMusicEnabled

#### Codice
```typescript
export const useToggleMusicEnabled = () => usePuzzleStore(s => s.toggleMusicEnabled);
```

#### Spiegazione
Linea 55: Selector toggleMusicEnabled.

### Costante: useClearError

#### Codice
```typescript
export const useClearError = () => usePuzzleStore(s => s.clearError);
```

#### Spiegazione
Linea 56: Selector clearError.

### Costante: useSetCustomBoard

#### Codice
```typescript
export const useSetCustomBoard = () => usePuzzleStore(s => s.setCustomBoard);
```

#### Spiegazione
Linea 57: Selector setCustomBoard.

### Costante: useSetGeneratedPuzzle

#### Codice
```typescript
export const useSetGeneratedPuzzle = () => usePuzzleStore(s => s.setGeneratedPuzzle);
```

#### Spiegazione
Linea 58: Selector setGeneratedPuzzle.

### Costante: useSetSolutionMovesFromApi

#### Codice
```typescript
export const useSetSolutionMovesFromApi = () => usePuzzleStore(s => s.setSolutionMovesFromApi);
```

#### Spiegazione
Linea 59: Selector setSolutionMovesFromApi.

<a id="src-features-puzzle-api-solverapi-ts"></a>
## src/features/puzzle/api/solverApi.ts

### Import

#### Codice
```typescript
import type { Direction, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';
```

#### Spiegazione
Linea 1: Importa tipi per le API.

### Costante: BACKEND_API_URL

#### Codice
```typescript
const BACKEND_API_URL: string = process.env.BACKEND_API_URL || 'http://localhost:8080/puzzle';

```

#### Spiegazione
Linea 2: Definisce BACKEND_API_URL da env o default localhost.
Linea 3: Riga vuota per separare.

### Classe: ApiConnectionError

#### Codice
```typescript
export class ApiConnectionError extends Error {
  constructor(url: string, cause?: unknown) {
    super(`Cannot reach the server at "${url}". Check that the backend is running and the address is correct.`);
    this.name = 'ApiConnectionError';
    this.cause = cause;
  }
}

```

#### Spiegazione
Linea 4: Definisce l'errore ApiConnectionError.
Linea 5: Costruttore con url e causa opzionale.
Linea 6: Messaggio di errore per mancata connessione.
Linea 7: Imposta il nome della classe errore.
Linea 8: Salva la causa.
Linea 9: Chiude il costruttore.
Linea 10: Chiude la classe ApiConnectionError.
Linea 11: Riga vuota per separare.

### Classe: ApiTimeoutError

#### Codice
```typescript
export class ApiTimeoutError extends Error {
  constructor(url: string) {
    super(`Request to "${url}" timed out. The server may be overloaded or unreachable.`);
    this.name = 'ApiTimeoutError';
  }
}

```

#### Spiegazione
Linea 12: Definisce l'errore ApiTimeoutError.
Linea 13: Costruttore con url.
Linea 14: Messaggio di errore per timeout.
Linea 15: Imposta il nome della classe errore.
Linea 16: Chiude il costruttore.
Linea 17: Chiude ApiTimeoutError.
Linea 18: Riga vuota per separare.

### Classe: ApiHttpError

#### Codice
```typescript
export class ApiHttpError extends Error {
  constructor(public readonly status: number, endpoint: string) {
    super(`Server returned HTTP ${status} for "${endpoint}".`);
    this.name = 'ApiHttpError';
  }
}

```

#### Spiegazione
Linea 19: Definisce l'errore ApiHttpError.
Linea 20: Costruttore con status ed endpoint.
Linea 21: Messaggio di errore con status.
Linea 22: Imposta il nome della classe errore.
Linea 23: Chiude il costruttore.
Linea 24: Chiude ApiHttpError.
Linea 25: Riga vuota per separare.

### Classe: ApiPayloadError

#### Codice
```typescript
export class ApiPayloadError extends Error {
  constructor(endpoint: string) {
    super(`Unexpected response payload from "${endpoint}". The API contract may have changed.`);
    this.name = 'ApiPayloadError';
  }
}

```

#### Spiegazione
Linea 26: Definisce l'errore ApiPayloadError.
Linea 27: Costruttore con endpoint.
Linea 28: Messaggio per payload inatteso.
Linea 29: Imposta il nome della classe errore.
Linea 30: Chiude il costruttore.
Linea 31: Chiude ApiPayloadError.
Linea 32: Riga vuota per separare.

### Funzione: handleFetchError

#### Codice
```typescript
function handleFetchError(error: unknown, url: string): never {
  if (error instanceof ApiConnectionError || error instanceof ApiTimeoutError || error instanceof ApiHttpError || error instanceof ApiPayloadError) {
    throw error;
  }
  if (error instanceof DOMException && error.name === 'AbortError') {
    throw new ApiTimeoutError(url);
  }
  // TypeError from fetch means a network/address-level failure (DNS, ECONNREFUSED, etc.)
  if (error instanceof TypeError) {
    throw new ApiConnectionError(url, error);
  }
  throw new Error(`Unexpected error: ${String(error)}`);
}

```

#### Spiegazione
Linea 33: Funzione handleFetchError normalizza gli errori.
Linea 34: Rilancia errori gia' tipizzati.
Linea 35: Rilancia l'errore tipizzato.
Linea 36: Chiude l'if dei tipizzati.
Linea 37: Gestisce AbortError come timeout.
Linea 38: Lancia ApiTimeoutError.
Linea 39: Chiude if AbortError.
Linea 40: Commento: TypeError indica errore di rete.
Linea 41: Gestisce TypeError come connessione fallita.
Linea 42: Lancia ApiConnectionError.
Linea 43: Chiude if TypeError.
Linea 44: Lancia errore generico se non riconosciuto.
Linea 45: Chiude handleFetchError.
Linea 46: Riga vuota per separare.

### Funzione: isGrid

#### Codice
```typescript
function isGrid(value: unknown): value is Grid {
  return Array.isArray(value) && value.every((row) => Array.isArray(row) && row.every((cell) => typeof cell === 'number'));
}

```

#### Spiegazione
Linea 47: Type guard per verificare una griglia.
Linea 48: Controlla array di array di numeri.
Linea 49: Chiude isGrid.
Linea 50: Riga vuota per separare.

### Funzione: isMoves

#### Codice
```typescript
function isMoves(value: unknown): value is Direction[] {
  return Array.isArray(value) && value.every((move) => typeof move === 'string');
}


export async function postSolvePuzzle(currentGrid: Grid): Promise<Direction[]> {
```

#### Spiegazione
Linea 51: Type guard per verificare la lista mosse.
Linea 52: Controlla array di stringhe.
Linea 53: Chiude isMoves (non usata nel file).
Linea 54: Riga vuota per separare.
Linea 55: Riga vuota (spaziatura).
Linea 56: Definisce postSolvePuzzle.

### Costante: endpoint

#### Codice
```typescript
  const endpoint = BACKEND_API_URL + '/solve';
```

#### Spiegazione
Linea 57: Costruisce endpoint /solve.

### Costante: controller

#### Codice
```typescript
  const controller = new AbortController();
```

#### Spiegazione
Linea 58: Crea AbortController.

### Costante: timeoutId

#### Codice
```typescript
  const timeoutId = setTimeout(() => controller.abort(), 20000);

  try {
```

#### Spiegazione
Linea 59: Imposta timeout a 20s.
Linea 60: Riga vuota per separare.
Linea 61: Inizia try di fetch.

### Costante: response

#### Codice
```typescript
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ "grid": currentGrid }),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new ApiHttpError(response.status, endpoint);
    }

```

#### Spiegazione
Linea 62: Esegue fetch POST verso /solve.
Linea 63: Metodo POST.
Linea 64: Header JSON.
Linea 65: Body con griglia corrente.
Linea 66: Passa il signal per abort.
Linea 67: Chiude fetch.
Linea 68: Riga vuota per separare.
Linea 69: Se response non ok, lancia ApiHttpError.
Linea 70: Lancia l'errore con status.
Linea 71: Chiude if.
Linea 72: Riga vuota per separare.

### Costante: payload

#### Codice
```typescript
    const payload = await response.json();
    return payload?.moves || [];
  } catch (error) {
    handleFetchError(error, endpoint);
  } finally {
    clearTimeout(timeoutId);
  }
}

export async function postGeneratePuzzle(gridSize: GridSize): Promise<PuzzleConfig> {
```

#### Spiegazione
Linea 73: Parse JSON della risposta.
Linea 74: Ritorna moves o array vuoto.
Linea 75: Catch degli errori.
Linea 76: Normalizza e rilancia l'errore.
Linea 77: Finally: cleanup.
Linea 78: Cancella il timeout.
Linea 79: Chiude finally.
Linea 80: Chiude postSolvePuzzle.
Linea 81: Riga vuota per separare.
Linea 82: Definisce postGeneratePuzzle.

### Costante: endpoint

#### Codice
```typescript
  const endpoint = BACKEND_API_URL + '/generate';
```

#### Spiegazione
Linea 83: Costruisce endpoint /generate.

### Costante: controller

#### Codice
```typescript
  const controller = new AbortController();
```

#### Spiegazione
Linea 84: Crea AbortController.

### Costante: timeoutId

#### Codice
```typescript
  const timeoutId = setTimeout(() => controller.abort(), 20000);

  try {
```

#### Spiegazione
Linea 85: Imposta timeout a 20s.
Linea 86: Riga vuota per separare.
Linea 87: Inizia try di fetch.

### Costante: response

#### Codice
```typescript
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({"size": gridSize }),
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new ApiHttpError(response.status, endpoint);
    }

```

#### Spiegazione
Linea 88: Esegue fetch POST verso /generate.
Linea 89: Metodo POST.
Linea 90: Header JSON.
Linea 91: Body con size.
Linea 92: Passa signal per abort.
Linea 93: Chiude fetch.
Linea 94: Riga vuota per separare.
Linea 95: Se response non ok, lancia ApiHttpError.
Linea 96: Lancia errore con status.
Linea 97: Chiude if.
Linea 98: Riga vuota per separare.

### Costante: payload

#### Codice
```typescript
    const payload = await response.json();
```

#### Spiegazione
Linea 99: Parse JSON della risposta.

### Costante: grid

#### Codice
```typescript
    const grid = payload?.grid;

    if (!isGrid(grid)) {
      throw new ApiPayloadError(endpoint);
    }

    return {
      gridSize,
      initialGrid: grid,
      moves: [],
    };
  } catch (error) {
    handleFetchError(error, endpoint);
  } finally {
    clearTimeout(timeoutId);
  }
}
```

#### Spiegazione
Linea 100: Estrae la griglia dal payload.
Linea 101: Riga vuota per separare.
Linea 102: Se griglia non valida, lancia ApiPayloadError.
Linea 103: Lancia ApiPayloadError.
Linea 104: Chiude if.
Linea 105: Riga vuota per separare.
Linea 106: Ritorna il PuzzleConfig con griglia e mosse vuote.
Linea 107: Inserisce gridSize.
Linea 108: Inserisce initialGrid.
Linea 109: Inserisce moves vuote.
Linea 110: Chiude return.
Linea 111: Catch degli errori.
Linea 112: Normalizza e rilancia.
Linea 113: Finally: cleanup.
Linea 114: Cancella il timeout.
Linea 115: Chiude finally.
Linea 116: Chiude postGeneratePuzzle.

<a id="src-features-puzzle-components-gameclient-tsx"></a>
## src/features/puzzle/components/GameClient.tsx

### Direttive

#### Codice
```typescript
"use client";

```

#### Spiegazione
Linea 1: Direttiva client per componente React.
Linea 2: Riga vuota per separare.

### Import

#### Codice
```typescript
import { usePlayback } from '@/features/puzzle/hooks/usePlayback';
import Header from '@/features/puzzle/components/Header';
import MoveInfo from '@/features/puzzle/components/MoveInfo';
import PuzzleGrid from '@/features/puzzle/components/PuzzleGrid';
import Controls from '@/features/puzzle/components/Controls';
import SpeedControl from '@/features/puzzle/components/SpeedControl';
import ProgressBar from '@/features/puzzle/components/ProgressBar';
import MoveList from '@/features/puzzle/components/MoveList';
import StatesPreview from '@/features/puzzle/components/StatesPreview';
import Footer from '@/features/puzzle/components/Footer';
import GamePlayNavbar from '@/features/puzzle/components/GamePlayNavbar';
import { useGameMode, useThemeMode, useError, useClearError } from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 3: Importa il hook usePlayback.
Linea 4: Importa Header.
Linea 5: Importa MoveInfo.
Linea 6: Importa PuzzleGrid.
Linea 7: Importa Controls.
Linea 8: Importa SpeedControl.
Linea 9: Importa ProgressBar.
Linea 10: Importa MoveList.
Linea 11: Importa StatesPreview.
Linea 12: Importa Footer.
Linea 13: Importa GamePlayNavbar.
Linea 14: Importa selector per gameMode, tema ed errori.
Linea 15: Riga vuota per separare.

### Componente: App

#### Codice
```typescript
export default function App() {
  // Attiva il loop di autoplay
  usePlayback();
```

#### Spiegazione
Linea 16: Definisce il componente App (GameClient).
Linea 17: Commento: attiva autoplay.
Linea 18: Chiama usePlayback.

### Costante: gameMode

#### Codice
```typescript
  const gameMode = useGameMode();
```

#### Spiegazione
Linea 19: Legge gameMode dallo store.

### Costante: themeMode

#### Codice
```typescript
  const themeMode = useThemeMode();
```

#### Spiegazione
Linea 20: Legge themeMode dallo store.

### Costante: error

#### Codice
```typescript
  const error = useError();
```

#### Spiegazione
Linea 21: Legge error dallo store.

### Costante: clearError

#### Codice
```typescript
  const clearError = useClearError();

```

#### Spiegazione
Linea 22: Legge clearError dallo store.
Linea 23: Riga vuota per separare.

### Costante: rootThemeClass

#### Codice
```typescript
  const rootThemeClass =
    themeMode === 'dark'
      ? 'bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 text-white'
      : 'bg-gradient-to-br from-slate-100 via-sky-100 to-blue-50 text-slate-900';

```

#### Spiegazione
Linea 24: Definisce classi tema per root.
Linea 25: Condizione per tema dark.
Linea 26: Classi per tema scuro.
Linea 27: Classi per tema chiaro.
Linea 28: Riga vuota per separare.

### Costante: panelThemeClass

#### Codice
```typescript
  const panelThemeClass =
    themeMode === 'dark'
      ? 'border-gray-700/60 bg-slate-900/80 shadow-xl shadow-black/30'
      : 'border-slate-300/80 bg-white/85 shadow-xl shadow-sky-200/40';

```

#### Spiegazione
Linea 29: Definisce classi tema per pannelli.
Linea 30: Condizione per tema dark.
Linea 31: Classi pannello scuro.
Linea 32: Classi pannello chiaro.
Linea 33: Riga vuota per separare.

### Costante: hintThemeClass

#### Codice
```typescript
  const hintThemeClass =
    themeMode === 'dark'
      ? 'border-gray-700/60 bg-gray-900/60 text-gray-300'
      : 'border-slate-300/80 bg-slate-50/90 text-slate-700';

```

#### Spiegazione
Linea 34: Definisce classi per hint a sinistra.
Linea 35: Condizione per tema dark.
Linea 36: Classi hint scuro.
Linea 37: Classi hint chiaro.
Linea 38: Riga vuota per separare.

### Costante: replayHintThemeClass

#### Codice
```typescript
  const replayHintThemeClass = themeMode === 'dark' ? 'text-gray-400' : 'text-slate-500';

  return (
    <div className={`w-full h-full flex flex-col overflow-hidden ${rootThemeClass}`}>
      <div className="shrink-0 px-3 py-2 sm:px-4 sm:py-3">
        <Header />
      </div>

      <GamePlayNavbar />

      <div className="flex-1 min-h-0 px-3 pb-2 sm:px-4 sm:pb-4 lg:px-6">
        <div className="h-full min-h-0 grid grid-cols-1 xl:grid-cols-[280px_minmax(0,1fr)_340px] gap-4">
          <aside className={`order-2 xl:order-1 min-h-0 rounded-xl border p-3 sm:p-4 overflow-y-auto ${panelThemeClass}`}>
            {gameMode === 'replay' ? (
              <div className="flex flex-col gap-3">
                <Controls />
                <MoveInfo />
                <SpeedControl />
                <ProgressBar />
              </div>
            ) : (
              <div className={`rounded-lg border p-3 text-sm leading-relaxed ${hintThemeClass}`}>
                Clicca le tessere adiacenti allo spazio vuoto per muovere il puzzle.
              </div>
            )}
          </aside>

          <main className="order-1 xl:order-2 min-h-0 flex items-center justify-center overflow-hidden">
            <PuzzleGrid />
          </main>

          <aside className={`order-3 min-h-0 rounded-xl border p-3 sm:p-4 overflow-y-auto ${panelThemeClass}`}>
            {gameMode === 'replay' ? (
              <div className="flex flex-col gap-3">
                <MoveList />
                <StatesPreview />
                <Footer />
              </div>
            ) : (
              <div className={`h-full flex items-center justify-center text-center text-xs sm:text-sm leading-relaxed ${replayHintThemeClass}`}>
                Se non riesci, premi Arrendi nella barra sopra per avviare la soluzione animata.
              </div>
            )}
          </aside>
        </div>
      </div>

      {error && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">
            <div className="text-4xl sm:text-5xl mb-2">âŒ</div>
            <div className="text-lg sm:text-xl font-bold text-red-400 whitespace-pre-wrap mb-4">{error}</div>
            <button
              onClick={clearError}
              className="mt-4 px-6 py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg transition-colors"
            >
              OK
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
```

#### Spiegazione
Linea 39: Classe per hint replay.
Linea 40: Sceglie colore in base al tema.
Linea 41: Riga vuota per separare.
Linea 42: Inizia il return del JSX.
Linea 43: Apre il wrapper principale con tema.
Linea 44: Apre header con padding.
Linea 45: Renderizza Header.
Linea 46: Chiude header.
Linea 47: Riga vuota per separare.
Linea 48: Renderizza GamePlayNavbar.
Linea 49: Riga vuota per separare.
Linea 50: Apre la griglia principale della pagina.
Linea 51: Apre il layout a 3 colonne responsive.
Linea 52: Apre aside sinistro con pannello.
Linea 53: Render condizionale per replay.
Linea 54: Apre container dei controlli replay.
Linea 55: Renderizza Controls.
Linea 56: Renderizza MoveInfo.
Linea 57: Renderizza SpeedControl.
Linea 58: Renderizza ProgressBar.
Linea 59: Chiude container.
Linea 60: Altrimenti mostra hint.
Linea 61: Testo di hint per la modalita' play.
Linea 62: Chiude il blocco hint.
Linea 63: Chiude aside sinistro.
Linea 64: Riga vuota per separare.
Linea 65: Apre il main centrale.
Linea 66: Renderizza PuzzleGrid.
Linea 67: Chiude main centrale.
Linea 68: Riga vuota per separare.
Linea 69: Apre aside destro con pannello.
Linea 70: Render condizionale per replay.
Linea 71: Apre container replay destro.
Linea 72: Renderizza MoveList.
Linea 73: Renderizza StatesPreview.
Linea 74: Renderizza Footer.
Linea 75: Chiude container.
Linea 76: Altrimenti mostra hint replay.
Linea 77: Testo del hint replay.
Linea 78: Chiude blocco hint replay.
Linea 79: Chiude aside destro.
Linea 80: Chiude layout a colonne.
Linea 81: Chiude il contenitore principale.
Linea 82: Riga vuota per separare.
Linea 83: Render condizionale overlay errore.
Linea 84: Apre overlay full screen.
Linea 85: Apre card errore.
Linea 86: Icona errore (simbolo).
Linea 87: Mostra il testo errore.
Linea 88: Apre bottone OK.
Linea 89: onClick chiama clearError.
Linea 90: Classi bottone.
Linea 91: Chiude tag apertura bottone.
Linea 92: Testo OK.
Linea 93: Chiude bottone.
Linea 94: Chiude card errore.
Linea 95: Chiude overlay.
Linea 96: Chiude condizionale error.
Linea 97: Chiude il contenitore interno dell'overlay.
Linea 98: Chiude l'overlay.
Linea 99: Chiude il render condizionale dell'errore.
Linea 100: Chiude wrapper principale.
Linea 101: Chiude return.
Linea 102: Chiude il componente App.

<a id="src-features-puzzle-components-gameplaynavbar-tsx"></a>
## src/features/puzzle/components/GamePlayNavbar.tsx

### Direttive

#### Codice
```typescript
"use client";

```

#### Spiegazione
Linea 1: Direttiva client per usare hook React.
Linea 2: Riga vuota per separare import.

### Import

#### Codice
```typescript
import Link from 'next/link';
import { useEffect, useMemo, useState } from 'react';
import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';
import { postGeneratePuzzle, postSolvePuzzle, ApiConnectionError, ApiTimeoutError, ApiHttpError, ApiPayloadError } from '@/features/puzzle/api/solverApi';
import {
  useColorPaletteMode,
  useCurrentGrid,
  useElapsedSeconds,
  useGameMode,
  useGiveUp,
  useGridSize,
  useMusicEnabled,
  useRestartGame,
  useSetGeneratedPuzzle,
  useSetGridSize,
  useSetSolutionMovesFromApi,
  useSolutionMoves,
  useTimerEnabled,
  useThemeMode,
  useTickElapsed,
  useToggleTimerEnabled,
  useToggleThemeMode,
} from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 3: Importa Link per navigazione.
Linea 4: Importa useEffect, useMemo e useState.
Linea 5: Importa le label delle palette colori.
Linea 6: Importa funzioni API e classi errore (postSolvePuzzle non usata qui).
Linea 7: Inizia import dei selector.
Linea 8: Importa useColorPaletteMode.
Linea 9: Importa useCurrentGrid (non usato nel file).
Linea 10: Importa useElapsedSeconds.
Linea 11: Importa useGameMode.
Linea 12: Importa useGiveUp.
Linea 13: Importa useGridSize.
Linea 14: Importa useMusicEnabled.
Linea 15: Importa useRestartGame.
Linea 16: Importa useSetGeneratedPuzzle.
Linea 17: Importa useSetGridSize.
Linea 18: Importa useSetSolutionMovesFromApi (non usato nel file).
Linea 19: Importa useSolutionMoves (non usato nel file).
Linea 20: Importa useTimerEnabled.
Linea 21: Importa useThemeMode.
Linea 22: Importa useTickElapsed.
Linea 23: Importa useToggleTimerEnabled.
Linea 24: Importa useToggleThemeMode.
Linea 25: Chiude import selector.
Linea 26: Riga vuota per separare.

### Funzione: formatTime

#### Codice
```typescript
function formatTime(totalSeconds: number): string {
```

#### Spiegazione
Linea 27: Funzione helper per formattare il tempo in mm:ss.

### Costante: mins

#### Codice
```typescript
  const mins = Math.floor(totalSeconds / 60);
```

#### Spiegazione
Linea 28: Calcola i minuti.

### Costante: secs

#### Codice
```typescript
  const secs = totalSeconds % 60;
  return `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
}

```

#### Spiegazione
Linea 29: Calcola i secondi.
Linea 30: Ritorna la stringa formattata con padding.
Linea 31: Chiude formatTime.
Linea 32: Riga vuota per separare.

### Componente: GamePlayNavbar

#### Codice
```typescript
export default function GamePlayNavbar() {
```

#### Spiegazione
Linea 33: Definisce il componente GamePlayNavbar.

### Costante: gameMode

#### Codice
```typescript
  const gameMode = useGameMode();
```

#### Spiegazione
Linea 34: Legge gameMode dallo store.

### Costante: elapsedSeconds

#### Codice
```typescript
  const elapsedSeconds = useElapsedSeconds();
```

#### Spiegazione
Linea 35: Legge elapsedSeconds.

### Costante: gridSize

#### Codice
```typescript
  const gridSize = useGridSize();
```

#### Spiegazione
Linea 36: Legge gridSize.

### Costante: currentGrid

#### Codice
```typescript
  const currentGrid = useCurrentGrid();
```

#### Spiegazione
Linea 37: Legge currentGrid (valore non usato).

### Costante: solutionMoves

#### Codice
```typescript
  const solutionMoves = useSolutionMoves();
```

#### Spiegazione
Linea 38: Legge solutionMoves (valore non usato).

### Costante: setGridSize

#### Codice
```typescript
  const setGridSize = useSetGridSize();
```

#### Spiegazione
Linea 39: Legge setGridSize.

### Costante: setGeneratedPuzzle

#### Codice
```typescript
  const setGeneratedPuzzle = useSetGeneratedPuzzle();
```

#### Spiegazione
Linea 40: Legge setGeneratedPuzzle.

### Costante: setSolutionMovesFromApi

#### Codice
```typescript
  const setSolutionMovesFromApi = useSetSolutionMovesFromApi();
```

#### Spiegazione
Linea 41: Legge setSolutionMovesFromApi (non usato).

### Costante: tickElapsed

#### Codice
```typescript
  const tickElapsed = useTickElapsed();
```

#### Spiegazione
Linea 42: Legge tickElapsed.

### Costante: giveUp

#### Codice
```typescript
  const giveUp = useGiveUp();
```

#### Spiegazione
Linea 43: Legge giveUp.

### Costante: restartGame

#### Codice
```typescript
  const restartGame = useRestartGame();
```

#### Spiegazione
Linea 44: Legge restartGame.

### Costante: timerEnabled

#### Codice
```typescript
  const timerEnabled = useTimerEnabled();
```

#### Spiegazione
Linea 45: Legge timerEnabled.

### Costante: toggleTimerEnabled

#### Codice
```typescript
  const toggleTimerEnabled = useToggleTimerEnabled();
```

#### Spiegazione
Linea 46: Legge toggleTimerEnabled.

### Costante: themeMode

#### Codice
```typescript
  const themeMode = useThemeMode();
```

#### Spiegazione
Linea 47: Legge themeMode.

### Costante: toggleThemeMode

#### Codice
```typescript
  const toggleThemeMode = useToggleThemeMode();
```

#### Spiegazione
Linea 48: Legge toggleThemeMode.

### Costante: colorPaletteMode

#### Codice
```typescript
  const colorPaletteMode = useColorPaletteMode();
```

#### Spiegazione
Linea 49: Legge colorPaletteMode.

### Costante: musicEnabled

#### Codice
```typescript
  const musicEnabled = useMusicEnabled();
```

#### Spiegazione
Linea 50: Legge musicEnabled.

### Sezione

#### Codice
```typescript
  const [selectedGridSize, setSelectedGridSize] = useState<3 | 4>(gridSize);
```

#### Spiegazione
Linea 51: Stato locale per la size selezionata.

### Sezione

#### Codice
```typescript
  const [gridLoading, setGridLoading] = useState(false);
```

#### Spiegazione
Linea 52: Stato locale per il loading del cambio tabella.

### Sezione

#### Codice
```typescript
  const [surrenderLoading, setSurrenderLoading] = useState(false);
```

#### Spiegazione
Linea 53: Stato locale per il loading dell'arrendersi.

### Sezione

#### Codice
```typescript
  const [gridFeedback, setGridFeedback] = useState<string | null>(null);

  useEffect(() => {
    if (gameMode !== 'play' || !timerEnabled) return;

```

#### Spiegazione
Linea 54: Stato locale per messaggi di feedback.
Linea 55: Riga vuota per separare.
Linea 56: useEffect per avviare il timer.
Linea 57: Se non in play o timer disattivo, esce.
Linea 58: Riga vuota per separare.

### Costante: id

#### Codice
```typescript
    const id = setInterval(() => {
      tickElapsed();
    }, 1000);

    return () => clearInterval(id);
  }, [gameMode, timerEnabled, tickElapsed]);

  useEffect(() => {
    setSelectedGridSize(gridSize);
  }, [gridSize]);

```

#### Spiegazione
Linea 59: Crea interval che incrementa il timer.
Linea 60: Chiama tickElapsed ogni secondo.
Linea 61: Chiude setInterval.
Linea 62: Riga vuota per separare.
Linea 63: Cleanup che cancella l'interval.
Linea 64: Chiude useEffect e dipendenze.
Linea 65: Riga vuota per separare.
Linea 66: useEffect per sincronizzare gridSize nello state locale.
Linea 67: Aggiorna selectedGridSize quando cambia gridSize.
Linea 68: Chiude useEffect.
Linea 69: Riga vuota per separare.

### Costante: formattedTime

#### Codice
```typescript
  const formattedTime = useMemo(() => formatTime(elapsedSeconds), [elapsedSeconds]);

```

#### Spiegazione
Linea 70: Memoizza il tempo formattato.
Linea 71: Riga vuota per separare.

### Costante: handleGridSizeChange

#### Codice
```typescript
  const handleGridSizeChange = async () => {
    setGridLoading(true);
    setGridFeedback('Caricamento puzzle da API...');

    try {
```

#### Spiegazione
Linea 72: Handler async per cambiare la dimensione.
Linea 73: Imposta loading su true.
Linea 74: Imposta feedback di caricamento.
Linea 75: Riga vuota per separare.
Linea 76: Inizia try.

### Costante: generatedPuzzle

#### Codice
```typescript
      const generatedPuzzle = await postGeneratePuzzle(selectedGridSize);
      await setGeneratedPuzzle(selectedGridSize, generatedPuzzle.initialGrid, generatedPuzzle.moves);
      setGridFeedback(null);
    } catch (err) {
      setGridSize(selectedGridSize);
      if (err instanceof ApiConnectionError) {
        setGridFeedback('Impossibile raggiungere il server. Controlla che il backend sia avviato e che l\'indirizzo sia corretto. Caricata configurazione locale.');
      } else if (err instanceof ApiTimeoutError) {
        setGridFeedback('Il server non ha risposto in tempo. Caricata configurazione locale.');
      } else if (err instanceof ApiHttpError) {
        setGridFeedback(`Errore dal server (HTTP ${err.status}). Caricata configurazione locale.`);
      } else if (err instanceof ApiPayloadError) {
        setGridFeedback('Risposta del server non valida. Caricata configurazione locale.');
      } else {
        setGridFeedback('API non disponibile: caricata configurazione locale.');
      }
    } finally {
      setGridLoading(false);
    }
  };

```

#### Spiegazione
Linea 77: Richiede puzzle generato al backend.
Linea 78: Aggiorna lo store con il puzzle generato.
Linea 79: Resetta il feedback.
Linea 80: Passa al catch.
Linea 81: Fallback locale: imposta config locale.
Linea 82: Se errore di connessione, messaggio specifico.
Linea 83: Messaggio per errore di connessione.
Linea 84: Se timeout, messaggio specifico.
Linea 85: Messaggio per timeout.
Linea 86: Se HTTP error, messaggio specifico.
Linea 87: Messaggio con status HTTP.
Linea 88: Se payload error, messaggio specifico.
Linea 89: Messaggio per payload invalido.
Linea 90: Altrimenti, messaggio generico API.
Linea 91: Messaggio generico.
Linea 92: Chiude i blocchi if/else.
Linea 93: Chiude catch.
Linea 94: Finally: reset loading.
Linea 95: Imposta gridLoading a false.
Linea 96: Chiude finally.
Linea 97: Chiude handleGridSizeChange.

### Costante: handleGiveUp

#### Codice
```typescript
  const handleGiveUp = async () => {
    setSurrenderLoading(true);
    setGridFeedback(null);

    try {
      await giveUp();
      setGridFeedback(null);
    } catch (err) {
      if (err instanceof ApiConnectionError) {
        setGridFeedback('Impossibile raggiungere il server. Controlla che il backend sia avviato e che l\'indirizzo sia corretto.');
      } else if (err instanceof ApiTimeoutError) {
        setGridFeedback('Il server non ha risposto in tempo. Riprova piÃ¹ tardi.');
      } else if (err instanceof ApiHttpError) {
        setGridFeedback(`Errore dal server (HTTP ${err.status}). Nessuna soluzione ricevuta.`);
      } else if (err instanceof ApiPayloadError) {
        setGridFeedback('Risposta del server non valida. Nessuna soluzione ricevuta.');
      } else {
        setGridFeedback('API solve non disponibile: nessuna soluzione ricevuta.');
      }
    } finally {
      setSurrenderLoading(false);
    }
  };

  return (
    <nav className="mx-3 mb-2 sm:mx-4 sm:mb-3 rounded-xl border border-cyan-500/30 bg-slate-900/80 px-3 py-2 sm:px-4 sm:py-3 shadow-lg shadow-cyan-900/40">
      <div className="flex flex-wrap items-center justify-between gap-2 sm:gap-3">
        <div className="flex items-center gap-2">
          <Link
            href="/"
            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"
          >
            Home
          </Link>
          <Link
            href="/settings"
            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"
          >
            Impostazioni
          </Link>
          <Link
            href="/custom"
            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"
          >
            Tabella Manuale
          </Link>
        </div>

        <div className="flex items-center gap-2 rounded-lg border border-gray-700/60 bg-gray-900/70 px-3 py-1.5">
          <span className="text-xs uppercase tracking-wide text-gray-400">Tempo</span>
          <span className="font-mono text-sm sm:text-base text-cyan-300">{formattedTime}</span>
        </div>

        <div className="flex items-center gap-2">
          <label htmlFor="grid-size" className="text-xs uppercase tracking-wide text-gray-400">
            Tabella
          </label>
          <div className="relative">
            <select
              id="grid-size"
              value={selectedGridSize}
              onChange={(e) => setSelectedGridSize(Number(e.target.value) as 3 | 4)}
              disabled={gridLoading}
              className="no-native-arrow rounded-lg border border-gray-600 bg-gray-800 pl-2.5 pr-8 py-1.5 text-sm text-gray-100 outline-none focus:border-cyan-400"
            >
              <option value={3}>3 x 3</option>
              <option value={4}>4 x 4</option>
            </select>
            <span className="pointer-events-none absolute inset-y-0 right-2 flex items-center text-cyan-300" aria-hidden="true">
              <svg viewBox="0 0 20 20" fill="currentColor" className="h-4 w-4">
                <path
                  fillRule="evenodd"
                  d="M5.23 7.21a.75.75 0 0 1 1.06.02L10 11.12l3.71-3.89a.75.75 0 1 1 1.08 1.04l-4.25 4.45a.75.75 0 0 1-1.08 0L5.21 8.27a.75.75 0 0 1 .02-1.06Z"
                  clipRule="evenodd"
                />
              </svg>
            </span>
          </div>
          <button
            onClick={() => {
              void handleGridSizeChange();
            }}
            disabled={gridLoading}
            className="rounded-lg border border-cyan-400/60 bg-cyan-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-cyan-600 disabled:opacity-60"
          >
            {gridLoading ? 'Cambio...' : 'Cambia Tabella'}
          </button>
        </div>

        <button
          onClick={toggleTimerEnabled}
          className="rounded-lg border border-amber-400/60 bg-amber-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-amber-600"
        >
          {timerEnabled ? 'Stop Timer' : 'Avvia Timer'}
        </button>

        <button
          onClick={toggleThemeMode}
          className="rounded-lg border border-sky-400/60 bg-sky-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-sky-600"
        >
          {themeMode === 'dark' ? 'Modalita Chiara' : 'Modalita Scura'}
        </button>

        {gameMode === 'play' ? (
          <button
            onClick={() => {
              void handleGiveUp();
            }}
            disabled={surrenderLoading}
            className="rounded-lg border border-orange-400/60 bg-orange-600 px-4 py-1.5 text-sm font-semibold text-white transition-colors hover:bg-orange-500"
          >
            {surrenderLoading ? 'Calcolo...' : 'Arrenditi'}
          </button>
        ) : (
          <button
            onClick={restartGame}
            className="rounded-lg border border-emerald-400/60 bg-emerald-600 px-4 py-1.5 text-sm font-semibold text-white transition-colors hover:bg-emerald-500"
          >
            Riprova
          </button>
        )}
      </div>

      <div className="mt-2 text-[11px] sm:text-xs text-gray-400">
        Tema: <span className="text-gray-200">{themeMode === 'dark' ? 'Scuro' : 'Chiaro'}</span> Â·
        Palette: <span className="text-gray-200">{COLOR_PALETTE_LABELS[colorPaletteMode]}</span> Â·
        Musica: <span className="text-gray-200">{musicEnabled ? 'Attiva' : 'Disattivata'}</span>
      </div>

      {gridFeedback && (
        <div className="mt-1 text-[11px] sm:text-xs text-amber-300">{gridFeedback}</div>
      )}
    </nav>
  );
}
```

#### Spiegazione
Linea 98: Riga vuota per separare.
Linea 99: Handler async per arrendersi.
Linea 100: Imposta loading su true.
Linea 101: Resetta feedback.
Linea 102: Riga vuota per separare.
Linea 103: Inizia try.
Linea 104: Chiama giveUp (API solve via store).
Linea 105: Resetta feedback.
Linea 106: Passa al catch.
Linea 107: Se errore di connessione, messaggio specifico.
Linea 108: Messaggio connessione.
Linea 109: Se timeout, messaggio specifico.
Linea 110: Messaggio timeout.
Linea 111: Se HTTP error, messaggio specifico.
Linea 112: Messaggio HTTP con status.
Linea 113: Se payload error, messaggio specifico.
Linea 114: Messaggio payload.
Linea 115: Altrimenti, messaggio generico.
Linea 116: Messaggio API non disponibile.
Linea 117: Chiude i blocchi if/else.
Linea 118: Finally: reset loading.
Linea 119: Imposta surrenderLoading a false.
Linea 120: Chiude finally.
Linea 121: Chiude handleGiveUp.
Linea 122: Riga vuota per separare.
Linea 123: Inizia il return del JSX.
Linea 124: Apre nav con stile.
Linea 125: Apre il contenitore principale dei controlli.
Linea 126: Apre gruppo link navigazione.
Linea 127: Apre Link verso home.
Linea 128: Imposta href home.
Linea 129: Classi del link.
Linea 130: Chiude tag di apertura Link.
Linea 131: Testo link Home.
Linea 132: Chiude Link.
Linea 133: Apre Link verso settings.
Linea 134: Imposta href settings.
Linea 135: Classi del link.
Linea 136: Chiude tag di apertura Link.
Linea 137: Testo link Impostazioni.
Linea 138: Chiude Link.
Linea 139: Apre Link verso custom.
Linea 140: Imposta href custom.
Linea 141: Classi del link.
Linea 142: Chiude tag di apertura Link.
Linea 143: Testo link Tabella Manuale.
Linea 144: Chiude Link.
Linea 145: Chiude gruppo link.
Linea 146: Riga vuota per separare.
Linea 147: Apre blocco tempo.
Linea 148: Label "Tempo".
Linea 149: Mostra il tempo formattato.
Linea 150: Chiude blocco tempo.
Linea 151: Riga vuota per separare.
Linea 152: Apre blocco selezione tabella.
Linea 153: Label per select.
Linea 154: Apre wrapper relativo.
Linea 155: Apre select.
Linea 156: Imposta id.
Linea 157: Imposta value.
Linea 158: onChange aggiorna selectedGridSize.
Linea 159: Disabilita durante loading.
Linea 160: Classi select.
Linea 161: Opzione 3x3.
Linea 162: Opzione 4x4.
Linea 163: Chiude select.
Linea 164: Apre icona freccia custom.
Linea 165: Apre svg.
Linea 166: Apre path.
Linea 167: Attributo fillRule.
Linea 168: Definisce il path.
Linea 169: Attributo clipRule.
Linea 170: Chiude path.
Linea 171: Chiude svg.
Linea 172: Chiude icona freccia.
Linea 173: Chiude wrapper relativo.
Linea 174: Apre bottone cambio tabella.
Linea 175: onClick richiama handleGridSizeChange.
Linea 176: Disabilita durante loading.
Linea 177: Classi bottone.
Linea 178: Chiude tag di apertura bottone.
Linea 179: Testo condizionale del bottone.
Linea 180: Chiude bottone.
Linea 181: Chiude blocco selezione tabella.
Linea 182: Riga vuota per separare.
Linea 183: Apre bottone toggle timer.
Linea 184: onClick toggleTimerEnabled.
Linea 185: Classi bottone.
Linea 186: Chiude tag di apertura bottone.
Linea 187: Testo condizionale timer.
Linea 188: Chiude bottone.
Linea 189: Riga vuota per separare.
Linea 190: Apre bottone toggle tema.
Linea 191: onClick toggleThemeMode.
Linea 192: Classi bottone.
Linea 193: Chiude tag di apertura bottone.
Linea 194: Testo condizionale tema.
Linea 195: Chiude bottone.
Linea 196: Riga vuota per separare.
Linea 197: Render condizionale per play/replay.
Linea 198: Apre bottone Arrenditi.
Linea 199: onClick richiama handleGiveUp.
Linea 200: Disabilita durante loading.
Linea 201: Classi bottone Arrenditi.
Linea 202: Chiude tag di apertura bottone.
Linea 203: Testo condizionale Arrenditi.
Linea 204: Chiude bottone.
Linea 205: Altrimenti, entra nel ramo replay.
Linea 206: Apre bottone Riprova.
Linea 207: onClick restartGame.
Linea 208: Classi bottone Riprova.
Linea 209: Chiude tag di apertura bottone.
Linea 210: Testo Riprova.
Linea 211: Chiude bottone.
Linea 212: Chiude ternario play/replay.
Linea 213: Chiude contenitore principale.
Linea 214: Riga vuota per separare.
Linea 215: Apre riga di stato (tema/palette/musica).
Linea 216: Mostra tema corrente.
Linea 217: Mostra palette corrente.
Linea 218: Mostra stato musica.
Linea 219: Chiude riga di stato.
Linea 220: Riga vuota per separare.
Linea 221: Render condizionale feedback.
Linea 222: Mostra feedback se presente.
Linea 223: Chiude il render condizionale.
Linea 224: Chiude nav.
Linea 225: Chiude return.
Linea 226: Chiude il componente GamePlayNavbar.
Linea 227: Spiegazione mancante per questa riga.
Linea 228: Spiegazione mancante per questa riga.
Linea 229: Spiegazione mancante per questa riga.
Linea 230: Spiegazione mancante per questa riga.
Linea 231: Spiegazione mancante per questa riga.
Linea 232: Spiegazione mancante per questa riga.

<a id="src-features-puzzle-components-puzzlegrid-tsx"></a>
## src/features/puzzle/components/PuzzleGrid.tsx

### Import

#### Codice
```typescript
import { memo } from 'react';
import { useCurrentGrid, usePrevGrid, useIsSolved, useTotalSteps, useGameMode, useManualGrid, usePlayMove, useGridSize } from '@/features/puzzle/store/puzzleSelectors';
import { DIRECTION_DELTAS } from '@/features/puzzle/constants/puzzle';
import { getMovedTile } from '@/features/puzzle/utils/puzzle';
import Tile from './Tile';

```

#### Spiegazione
Linea 1: Importa memo da React.
Linea 2: Importa selector dallo store.
Linea 3: Importa i delta direzioni.
Linea 4: Importa getMovedTile.
Linea 5: Importa il componente Tile.
Linea 6: Riga vuota per separare.

### Costante: PuzzleGrid

#### Codice
```typescript
const PuzzleGrid = memo(function PuzzleGrid() {
```

#### Spiegazione
Linea 7: Definisce PuzzleGrid con memo.

### Costante: gameMode

#### Codice
```typescript
  const gameMode = useGameMode();
```

#### Spiegazione
Linea 8: Legge gameMode.

### Costante: currentGrid

#### Codice
```typescript
  const currentGrid = useCurrentGrid();
```

#### Spiegazione
Linea 9: Legge currentGrid.

### Costante: manualGrid

#### Codice
```typescript
  const manualGrid = useManualGrid();
```

#### Spiegazione
Linea 10: Legge manualGrid.

### Costante: gridSize

#### Codice
```typescript
  const gridSize = useGridSize();
```

#### Spiegazione
Linea 11: Legge gridSize.

### Costante: prevGrid

#### Codice
```typescript
  const prevGrid = usePrevGrid();
```

#### Spiegazione
Linea 12: Legge prevGrid.

### Costante: isSolved

#### Codice
```typescript
  const isSolved = useIsSolved();
```

#### Spiegazione
Linea 13: Legge isSolved.

### Costante: totalSteps

#### Codice
```typescript
  const totalSteps = useTotalSteps();
```

#### Spiegazione
Linea 14: Legge totalSteps.

### Costante: playMove

#### Codice
```typescript
  const playMove = usePlayMove();

  // Scegli quale grid mostrare
```

#### Spiegazione
Linea 15: Legge playMove.
Linea 16: Riga vuota per separare.
Linea 17: Commento: quale grid mostrare.

### Costante: displayGrid

#### Codice
```typescript
  const displayGrid = gameMode === 'play' ? manualGrid : currentGrid;
```

#### Spiegazione
Linea 18: Sceglie displayGrid in base al gameMode.

### Costante: movedTile

#### Codice
```typescript
  const movedTile = gameMode === 'replay' ? getMovedTile(currentGrid, prevGrid) : -1;
```

#### Spiegazione
Linea 19: Calcola movedTile in replay.

### Costante: solvedTitle

#### Codice
```typescript
  const solvedTitle = gameMode === 'play' ? 'Vittoria!' : 'Risolto!';

```

#### Spiegazione
Linea 20: Sceglie il titolo per stato risolto.
Linea 21: Riga vuota per separare.

### Costante: handleTileClick

#### Codice
```typescript
  const handleTileClick = (index: number) => {
    if (gameMode !== 'play') return;

```

#### Spiegazione
Linea 22: Handler click su tessera.
Linea 23: Se non play, esce.
Linea 24: Riga vuota per separare.

### Costante: row

#### Codice
```typescript
    const row = Math.floor(index / gridSize);
```

#### Spiegazione
Linea 25: Calcola riga dall'indice.

### Costante: col

#### Codice
```typescript
    const col = index % gridSize;

    // Trova lo zero (casella vuota)
    let zeroRow = 0, zeroCol = 0;
    for (let r = 0; r < gridSize; r++) {
      for (let c = 0; c < gridSize; c++) {
        if (manualGrid[r][c] === 0) {
          zeroRow = r;
          zeroCol = c;
        }
      }
    }

    // Verifica se il tile Ã¨ adiacente allo zero
```

#### Spiegazione
Linea 26: Calcola colonna dall'indice.
Linea 27: Riga vuota per separare.
Linea 28: Commento: trova lo zero.
Linea 29: Inizializza zeroRow e zeroCol.
Linea 30: Ciclo sulle righe.
Linea 31: Ciclo sulle colonne.
Linea 32: Se trova zero, aggiorna coordinate.
Linea 33: Aggiorna zeroRow.
Linea 34: Aggiorna zeroCol.
Linea 35: Chiude if.
Linea 36: Chiude ciclo colonne.
Linea 37: Chiude ciclo righe.
Linea 38: Riga vuota per separare.
Linea 39: Commento: verifica adiacenza.

### Costante: directions

#### Codice
```typescript
    const directions = ['SINISTRA', 'DESTRA', 'SOPRA', 'SOTTO'] as const;
    for (const dir of directions) {
```

#### Spiegazione
Linea 40: Elenco direzioni possibili.
Linea 41: Ciclo sulle direzioni.

### Sezione

#### Codice
```typescript
      const [dr, dc] = DIRECTION_DELTAS[dir];
      if (zeroRow + dr === row && zeroCol + dc === col) {
        playMove(dir);
        return;
      }
    }
  };

```

#### Spiegazione
Linea 42: Recupera delta della direzione.
Linea 43: Se adiacente, esegue la mossa.
Linea 44: Chiama playMove.
Linea 45: Ritorna per uscire.
Linea 46: Chiude if.
Linea 47: Chiude ciclo direzioni.
Linea 48: Chiude handleTileClick.
Linea 49: Riga vuota per separare.

### Costante: gridMaxClass

#### Codice
```typescript
  const gridMaxClass =
    gridSize === 3
      ? 'max-w-[460px] sm:max-w-[580px] lg:max-w-[700px]'
      : 'max-w-[380px] sm:max-w-[500px] lg:max-w-[620px]';

  return (
    <div className="relative flex justify-center w-full">
      <div
        className={`grid gap-1.5 sm:gap-2 p-3 sm:p-4 bg-gray-800/60 rounded-xl sm:rounded-2xl backdrop-blur-sm border border-gray-700/50 shadow-2xl w-full ${gridMaxClass}`}
        style={{ gridTemplateColumns: `repeat(${gridSize}, 1fr)` }}
      >
        {displayGrid.flat().map((val, i) => (
          <div
            key={i}
            onClick={() => handleTileClick(i)}
            className={gameMode === 'play' ? 'cursor-pointer' : ''}
          >
            <Tile value={val} isMoving={val === movedTile} />
          </div>
        ))}
      </div>

      {isSolved && (
        <div className="absolute inset-0 flex items-center justify-center">
          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">
            <div className="text-4xl sm:text-5xl mb-2 animate-bounce">ðŸŽ‰</div>
            <div className="text-lg sm:text-xl font-bold text-green-400">{solvedTitle}</div>
            {gameMode === 'replay' ? (
              <div className="text-xs sm:text-sm text-gray-400 mt-1">in {totalSteps} mosse</div>
            ) : (
              <div className="text-xs sm:text-sm text-gray-400 mt-1">Ordine corretto con spazio in basso a destra</div>
            )}
          </div>
        </div>
      )}
    </div>
  );
});

export default PuzzleGrid;
```

#### Spiegazione
Linea 50: Definisce classi max width in base alla size.
Linea 51: Se 3x3 usa un max piu' largo.
Linea 52: Classi per 3x3.
Linea 53: Classi per 4x4.
Linea 54: Riga vuota per separare.
Linea 55: Inizia il return del JSX.
Linea 56: Apre wrapper relativo.
Linea 57: Apre il contenitore grid.
Linea 58: Classi per layout grid e stile.
Linea 59: Imposta le colonne in base a gridSize.
Linea 60: Chiude tag di apertura div.
Linea 61: Mappa le celle per renderizzare Tile.
Linea 62: Apre wrapper della cella.
Linea 63: Imposta key.
Linea 64: onClick chiama handleTileClick.
Linea 65: Abilita cursor-pointer solo in play.
Linea 66: Chiude tag di apertura wrapper.
Linea 67: Renderizza Tile con animazione se moving.
Linea 68: Chiude wrapper cella.
Linea 69: Chiude map.
Linea 70: Chiude il contenitore grid.
Linea 71: Riga vuota per separare.
Linea 72: Render condizionale overlay solved.
Linea 73: Apre overlay full screen.
Linea 74: Apre card solved.
Linea 75: Icona di vittoria (simbolo).
Linea 76: Titolo risolto.
Linea 77: Render condizionale per replay.
Linea 78: Mostra numero mosse in replay.
Linea 79: Altrimenti, testo descrittivo.
Linea 80: Testo per modalita' play.
Linea 81: Chiude ternario.
Linea 82: Chiude card.
Linea 83: Chiude overlay.
Linea 84: Chiude condizionale solved.
Linea 85: Chiude wrapper principale.
Linea 86: Chiude return.
Linea 87: Chiude memo.
Linea 88: Riga vuota per separare.
Linea 89: Esporta PuzzleGrid di default.

<a id="src-features-puzzle-components-controls-tsx"></a>
## src/features/puzzle/components/Controls.tsx

### Import

#### Codice
```typescript
import { useStep, useTotalSteps, useIsPlaying, useGoPrev, useGoNext, useTogglePlay, usePause, useGameMode, useRestartGame } from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 1: Importa selector e azioni dallo store.
Linea 2: Riga vuota per separare.

### Componente: Controls

#### Codice
```typescript
export default function Controls() {
```

#### Spiegazione
Linea 3: Definisce il componente Controls.

### Costante: gameMode

#### Codice
```typescript
  const gameMode = useGameMode();
```

#### Spiegazione
Linea 4: Legge gameMode.

### Costante: step

#### Codice
```typescript
  const step = useStep();
```

#### Spiegazione
Linea 5: Legge step.

### Costante: totalSteps

#### Codice
```typescript
  const totalSteps = useTotalSteps();
```

#### Spiegazione
Linea 6: Legge totalSteps.

### Costante: isPlaying

#### Codice
```typescript
  const isPlaying = useIsPlaying();
```

#### Spiegazione
Linea 7: Legge isPlaying.

### Costante: goPrev

#### Codice
```typescript
  const goPrev = useGoPrev();
```

#### Spiegazione
Linea 8: Legge goPrev.

### Costante: goNext

#### Codice
```typescript
  const goNext = useGoNext();
```

#### Spiegazione
Linea 9: Legge goNext.

### Costante: togglePlay

#### Codice
```typescript
  const togglePlay = useTogglePlay();
```

#### Spiegazione
Linea 10: Legge togglePlay.

### Costante: pause

#### Codice
```typescript
  const pause = usePause();
```

#### Spiegazione
Linea 11: Legge pause.

### Costante: restartGame

#### Codice
```typescript
  const restartGame = useRestartGame();

```

#### Spiegazione
Linea 12: Legge restartGame.
Linea 13: Riga vuota per separare.

### Costante: handlePrev

#### Codice
```typescript
  const handlePrev = () => { pause(); goPrev(); };
```

#### Spiegazione
Linea 14: Handler per andare indietro con pausa.

### Costante: handleNext

#### Codice
```typescript
  const handleNext = () => { pause(); goNext(); };

  if (gameMode === 'play') {
    return null;
  }

  // gameMode === 'replay'
  return (
    <div className="flex flex-wrap items-center justify-center gap-2 sm:gap-3">
      <button
        onClick={restartGame}
        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"
      >
        âŸ² Riprova
      </button>
      <button
        onClick={handlePrev}
        disabled={step === 0}
        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 disabled:opacity-40 disabled:cursor-not-allowed rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"
      >
        â—€ Indietro
      </button>
      <button
        onClick={togglePlay}
        className={`px-4 sm:px-6 py-2 rounded-lg text-xs sm:text-sm font-bold transition-colors border active:scale-95 ${
          isPlaying
            ? 'bg-red-600 hover:bg-red-500 border-red-500'
            : 'bg-green-600 hover:bg-green-500 border-green-500'
        }`}
      >
        {isPlaying ? 'â¸ Pausa' : 'â–¶ Avvia'}
      </button>
      <button
        onClick={handleNext}
        disabled={step === totalSteps}
        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 disabled:opacity-40 disabled:cursor-not-allowed rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"
      >
        Avanti â–¶
      </button>
    </div>
  );
}
```

#### Spiegazione
Linea 15: Handler per andare avanti con pausa.
Linea 16: Riga vuota per separare.
Linea 17: Se in play, non renderizza nulla.
Linea 18: Ritorna null.
Linea 19: Chiude if.
Linea 20: Riga vuota per separare.
Linea 21: Commento: modalita' replay.
Linea 22: Inizia il return del JSX.
Linea 23: Apre il container dei bottoni.
Linea 24: Apre bottone Riprova.
Linea 25: onClick restartGame.
Linea 26: Classi bottone.
Linea 27: Chiude tag di apertura bottone.
Linea 28: Testo bottone Riprova.
Linea 29: Chiude bottone.
Linea 30: Apre bottone Indietro.
Linea 31: onClick handlePrev.
Linea 32: Disabilita se step=0.
Linea 33: Classi bottone.
Linea 34: Chiude tag di apertura bottone.
Linea 35: Testo bottone Indietro.
Linea 36: Chiude bottone.
Linea 37: Apre bottone Play/Pausa.
Linea 38: onClick togglePlay.
Linea 39: Inizia className con ternario.
Linea 40: Se isPlaying, usa stile pausa.
Linea 41: Classi per pausa.
Linea 42: Classi per play.
Linea 43: Chiude template literal.
Linea 44: Chiude tag di apertura bottone.
Linea 45: Testo condizionale play/pausa.
Linea 46: Chiude bottone.
Linea 47: Apre bottone Avanti.
Linea 48: onClick handleNext.
Linea 49: Disabilita se step=totalSteps.
Linea 50: Classi bottone.
Linea 51: Chiude tag di apertura bottone.
Linea 52: Testo bottone Avanti.
Linea 53: Chiude bottone.
Linea 54: Chiude container.
Linea 55: Chiude return.
Linea 56: Chiude il componente Controls.

<a id="src-features-puzzle-components-header-tsx"></a>
## src/features/puzzle/components/Header.tsx

### Import

#### Codice
```typescript
import { memo } from 'react';
import { useGridSize, useSolutionMoves } from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 1: Importa memo da React.
Linea 2: Importa selector per gridSize e moves.
Linea 3: Riga vuota per separare.

### Costante: Header

#### Codice
```typescript
const Header = memo(function Header() {
```

#### Spiegazione
Linea 4: Definisce Header con memo.

### Costante: gridSize

#### Codice
```typescript
  const gridSize = useGridSize();
```

#### Spiegazione
Linea 5: Legge gridSize.

### Costante: moves

#### Codice
```typescript
  const moves = useSolutionMoves();
```

#### Spiegazione
Linea 6: Legge moves.

### Costante: puzzleName

#### Codice
```typescript
  const puzzleName = gridSize === 4 ? '15' : '8';

  return (
    <div className="text-center">
      <h1 className="text-3xl sm:text-4xl md:text-5xl font-extrabold bg-gradient-to-r from-blue-400 via-purple-400 to-pink-400 bg-clip-text text-transparent">
        {puzzleName}-Puzzle Solver
      </h1>
      <p className="text-gray-400 mt-1 sm:mt-2 text-xs sm:text-sm md:text-base">
        {gridSize}Ã—{gridSize} puzzle Â· {moves.length} mosse per risolvere
      </p>
    </div>
  );
});

export default Header;
```

#### Spiegazione
Linea 7: Determina il nome puzzle (15 o 8).
Linea 8: Riga vuota per separare.
Linea 9: Inizia il return del JSX.
Linea 10: Apre container centrato.
Linea 11: Apre h1 con stile gradient.
Linea 12: Mostra il titolo con numero puzzle.
Linea 13: Chiude h1.
Linea 14: Apre paragrafo descrittivo.
Linea 15: Mostra dimensione e numero mosse.
Linea 16: Chiude paragrafo.
Linea 17: Chiude container.
Linea 18: Chiude return.
Linea 19: Chiude memo.
Linea 20: Riga vuota per separare.
Linea 21: Esporta Header di default.

<a id="src-features-puzzle-components-moveinfo-tsx"></a>
## src/features/puzzle/components/MoveInfo.tsx

### Import

#### Codice
```typescript
import { memo } from 'react';
import { useStep, useTotalSteps, useCurrentMove } from '@/features/puzzle/store/puzzleSelectors';
import { DIRECTION_LABELS } from '@/features/puzzle/constants/puzzle';

```

#### Spiegazione
Linea 1: Importa memo da React.
Linea 2: Importa selector per step, totalSteps e currentMove.
Linea 3: Importa DIRECTION_LABELS.
Linea 4: Riga vuota per separare.

### Costante: MoveInfo

#### Codice
```typescript
const MoveInfo = memo(function MoveInfo() {
```

#### Spiegazione
Linea 5: Definisce MoveInfo con memo.

### Costante: step

#### Codice
```typescript
  const step = useStep();
```

#### Spiegazione
Linea 6: Legge step.

### Costante: totalSteps

#### Codice
```typescript
  const totalSteps = useTotalSteps();
```

#### Spiegazione
Linea 7: Legge totalSteps.

### Costante: currentMove

#### Codice
```typescript
  const currentMove = useCurrentMove();

  return (
    <div className="flex flex-wrap items-center justify-center gap-2 sm:gap-3 bg-gray-800/80 px-3 sm:px-5 py-2 sm:py-3 rounded-xl border border-gray-700/60">
      <span className="text-gray-400 text-xs sm:text-sm font-mono">
        Passo {step}/{totalSteps}
      </span>
      {currentMove && (
        <>
          <span className="text-gray-600 hidden sm:inline">|</span>
          <span className="text-xl sm:text-2xl">{DIRECTION_LABELS[currentMove].icon}</span>
          <span className="font-semibold text-yellow-300 text-sm sm:text-base">{currentMove}</span>
          <span className="text-gray-500 text-xs sm:text-sm">({DIRECTION_LABELS[currentMove].en})</span>
        </>
      )}
      {step === 0 && (
        <>
          <span className="text-gray-600 hidden sm:inline">|</span>
          <span className="text-gray-300 text-xs sm:text-sm">Stato Iniziale</span>
        </>
      )}
    </div>
  );
});

export default MoveInfo;
```

#### Spiegazione
Linea 8: Legge currentMove.
Linea 9: Riga vuota per separare.
Linea 10: Inizia il return del JSX.
Linea 11: Apre container con stile.
Linea 12: Mostra "Passo step/totalSteps".
Linea 13: Contenuto dinamico del passo.
Linea 14: Chiude span.
Linea 15: Render condizionale se currentMove esiste.
Linea 16: Apre fragment.
Linea 17: Separatore verticale (solo desktop).
Linea 18: Icona direzione.
Linea 19: Nome direzione.
Linea 20: Nome inglese direzione.
Linea 21: Chiude fragment.
Linea 22: Chiude condizionale currentMove.
Linea 23: Render condizionale se step == 0.
Linea 24: Apre fragment.
Linea 25: Separatore verticale.
Linea 26: Testo "Stato Iniziale".
Linea 27: Chiude fragment.
Linea 28: Chiude condizionale step==0.
Linea 29: Chiude container.
Linea 30: Chiude return.
Linea 31: Chiude memo.
Linea 32: Riga vuota per separare.
Linea 33: Esporta MoveInfo di default.

<a id="src-features-puzzle-components-movelist-tsx"></a>
## src/features/puzzle/components/MoveList.tsx

### Direttive

#### Codice
```typescript
"use client";

```

#### Spiegazione
Linea 1: Direttiva client per usare hook.
Linea 2: Riga vuota per separare import.

### Import

#### Codice
```typescript
import { useEffect, useRef } from 'react';
import { useStep, useJumpToStep, useSolutionMoves } from '@/features/puzzle/store/puzzleSelectors';
import { DIRECTION_LABELS } from '@/features/puzzle/constants/puzzle';

```

#### Spiegazione
Linea 3: Importa useEffect e useRef.
Linea 4: Importa selector per step, jumpToStep e moves.
Linea 5: Importa DIRECTION_LABELS.
Linea 6: Riga vuota per separare.

### Componente: MoveList

#### Codice
```typescript
export default function MoveList() {
```

#### Spiegazione
Linea 7: Definisce il componente MoveList.

### Costante: step

#### Codice
```typescript
  const step = useStep();
```

#### Spiegazione
Linea 8: Legge step.

### Costante: jumpToStep

#### Codice
```typescript
  const jumpToStep = useJumpToStep();
```

#### Spiegazione
Linea 9: Legge jumpToStep.

### Costante: moves

#### Codice
```typescript
  const moves = useSolutionMoves();
```

#### Spiegazione
Linea 10: Legge moves.

### Costante: listRef

#### Codice
```typescript
  const listRef = useRef<HTMLDivElement>(null);

  // Auto-scroll alla mossa attiva
  useEffect(() => {
    if (listRef.current && step > 0) {
```

#### Spiegazione
Linea 11: Crea ref per la lista.
Linea 12: Riga vuota per separare.
Linea 13: Commento: auto-scroll.
Linea 14: useEffect per scrollare alla mossa attiva.
Linea 15: Se listRef esiste e step > 0.

### Costante: activeBtn

#### Codice
```typescript
      const activeBtn = listRef.current.querySelector(`[data-step="${step}"]`);
      if (activeBtn) {
        activeBtn.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' });
      }
    }
  }, [step]);

  return (
    <div className="w-full bg-gray-800/50 rounded-xl border border-gray-700/50 p-3 sm:p-4">
      <h2 className="text-xs sm:text-sm font-semibold text-gray-400 mb-2 sm:mb-3 uppercase tracking-wider">
        Sequenza Mosse ({moves.length})
      </h2>
      <div ref={listRef} className="max-h-44 overflow-y-auto pr-1 flex flex-wrap gap-1 sm:gap-1.5">
        {moves.map((move, i) => {
```

#### Spiegazione
Linea 16: Trova il bottone attivo via data-step.
Linea 17: Se trovato, scrollIntoView.
Linea 18: Scroll smooth con allineamento.
Linea 19: Chiude if.
Linea 20: Chiude useEffect.
Linea 21: Riga vuota per separare.
Linea 22: Inizia il return del JSX.
Linea 23: Apre container lista.
Linea 24: Titolo lista con conteggio.
Linea 25: Testo del titolo.
Linea 26: Chiude h2.
Linea 27: Apre container scrollabile e flex.
Linea 28: Mappa le mosse.
Linea 29: Calcola indice mossa (1-based).

### Costante: moveIndex

#### Codice
```typescript
          const moveIndex = i + 1;
```

#### Spiegazione
Linea 30: Determina se e' attiva.

### Costante: isActive

#### Codice
```typescript
          const isActive = moveIndex === step;
```

#### Spiegazione
Linea 31: Determina se e' passata.

### Costante: isPast

#### Codice
```typescript
          const isPast = moveIndex < step;

          return (
            <button
              key={i}
              data-step={moveIndex}
              onClick={() => jumpToStep(moveIndex)}
              title={`#${moveIndex}: ${move} (${DIRECTION_LABELS[move].en})`}
              className={`
                px-1.5 sm:px-2 py-0.5 sm:py-1 rounded text-[10px] sm:text-xs font-mono
                transition-all border cursor-pointer
                ${isActive
                  ? 'bg-yellow-500/30 border-yellow-500 text-yellow-300 scale-110 shadow-lg shadow-yellow-500/20 z-10'
                  : isPast
                  ? 'bg-green-900/30 border-green-700/50 text-green-400/80'
                  : 'bg-gray-700/40 border-gray-600/40 text-gray-400 hover:bg-gray-600/50'
                }
              `}
            >
              {DIRECTION_LABELS[move].icon}
            </button>
          );
        })}
      </div>
    </div>
  );
}
```

#### Spiegazione
Linea 32: Riga vuota per separare.
Linea 33: Ritorna il bottone per la mossa.
Linea 34: Apre bottone.
Linea 35: key React.
Linea 36: data-step per auto-scroll.
Linea 37: onClick jumpToStep.
Linea 38: title con descrizione mossa.
Linea 39: Inizia className multiline.
Linea 40: Padding e font.
Linea 41: Stili base.
Linea 42: Inizia ternario stili.
Linea 43: Stile per mossa attiva.
Linea 44: Stile per mossa passata.
Linea 45: Stile per mossa futura.
Linea 46: Chiude ternario.
Linea 47: Chiude template literal.
Linea 48: Chiude tag di apertura bottone.
Linea 49: Renderizza icona direzione.
Linea 50: Chiude bottone.
Linea 51: Chiude return della map.
Linea 52: Chiude map.
Linea 53: Chiude container lista.
Linea 54: Chiude container esterno.
Linea 55: Chiude return.
Linea 56: Chiude il componente MoveList.
Linea 57: Spiegazione mancante per questa riga.
Linea 58: Spiegazione mancante per questa riga.

<a id="src-features-puzzle-components-progressbar-tsx"></a>
## src/features/puzzle/components/ProgressBar.tsx

### Import

#### Codice
```typescript
import { useStep, useTotalSteps } from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 1: Importa selector per step e totalSteps.
Linea 2: Riga vuota per separare.

### Componente: ProgressBar

#### Codice
```typescript
export default function ProgressBar() {
```

#### Spiegazione
Linea 3: Definisce il componente ProgressBar.

### Costante: step

#### Codice
```typescript
  const step = useStep();
```

#### Spiegazione
Linea 4: Legge step.

### Costante: totalSteps

#### Codice
```typescript
  const totalSteps = useTotalSteps();
```

#### Spiegazione
Linea 5: Legge totalSteps.

### Costante: percentage

#### Codice
```typescript
  const percentage = totalSteps > 0 ? (step / totalSteps) * 100 : 0;

  return (
    <div className="w-full max-w-md">
      <div className="h-2 bg-gray-700 rounded-full overflow-hidden">
        <div
          className="h-full bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 transition-all duration-300 rounded-full"
          style={{ width: `${percentage}%` }}
        />
      </div>
    </div>
  );
}
```

#### Spiegazione
Linea 6: Calcola la percentuale di avanzamento.
Linea 7: Riga vuota per separare.
Linea 8: Inizia il return del JSX.
Linea 9: Apre container con max width.
Linea 10: Apre barra di sfondo.
Linea 11: Apre barra di progresso.
Linea 12: Classi per gradiente e animazione.
Linea 13: Imposta width in base alla percentuale.
Linea 14: Chiude barra di progresso.
Linea 15: Chiude barra di sfondo.
Linea 16: Chiude container.
Linea 17: Chiude return.
Linea 18: Chiude il componente ProgressBar.

<a id="src-features-puzzle-components-speedcontrol-tsx"></a>
## src/features/puzzle/components/SpeedControl.tsx

### Import

#### Codice
```typescript
import { useSpeed, useSetSpeed } from '@/features/puzzle/store/puzzleSelectors';
import { MIN_SPEED, MAX_SPEED, SPEED_STEP } from '@/features/puzzle/constants/puzzle';

```

#### Spiegazione
Linea 1: Importa selector per speed e setter.
Linea 2: Importa costanti di velocita'.
Linea 3: Riga vuota per separare.

### Componente: SpeedControl

#### Codice
```typescript
export default function SpeedControl() {
```

#### Spiegazione
Linea 4: Definisce il componente SpeedControl.

### Costante: speed

#### Codice
```typescript
  const speed = useSpeed();
```

#### Spiegazione
Linea 5: Legge speed.

### Costante: setSpeed

#### Codice
```typescript
  const setSpeed = useSetSpeed();

```

#### Spiegazione
Linea 6: Legge setSpeed.
Linea 7: Riga vuota per separare.

### Costante: invertedMax

#### Codice
```typescript
  const invertedMax = MIN_SPEED + MAX_SPEED;

  return (
    <div className="flex items-center gap-2 sm:gap-3 bg-gray-800/60 rounded-lg px-3 sm:px-4 py-2 border border-gray-700/40">
      <span className="text-gray-400 text-xs sm:text-sm">VelocitÃ :</span>
      <input
```

#### Spiegazione
Linea 8: Calcola il massimo invertito per slider.
Linea 9: Riga vuota per separare.
Linea 10: Inizia il return del JSX.
Linea 11: Apre container slider.
Linea 12: Label "Velocita".
Linea 13: Apre input range.

### Sezione

#### Codice
```typescript
        type="range"
        min={MIN_SPEED}
        max={MAX_SPEED}
        step={SPEED_STEP}
        value={invertedMax - speed}
        onChange={e => setSpeed(invertedMax - Number(e.target.value))}
        className="w-24 sm:w-32 accent-purple-500"
      />
    </div>
  );
}
```

#### Spiegazione
Linea 14: Tipo input range.
Linea 15: Imposta min.
Linea 16: Imposta max.
Linea 17: Imposta step.
Linea 18: Inverte la value per slider.
Linea 19: onChange aggiorna speed invertendo.
Linea 20: Classi input.
Linea 21: Chiude input.
Linea 22: Chiude container.
Linea 23: Chiude return.
Linea 24: Chiude il componente SpeedControl.

<a id="src-features-puzzle-components-statespreview-tsx"></a>
## src/features/puzzle/components/StatesPreview.tsx

### Import

#### Codice
```typescript
import { memo } from 'react';
import { useAllStates, useTotalSteps, useGridSize, useInitialGrid } from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 1: Importa memo da React.
Linea 2: Importa selector per stati e dimensione.
Linea 3: Riga vuota per separare.

### Costante: MiniGrid

#### Codice
```typescript
const MiniGrid = memo(function MiniGrid({
  grid,
  gridSize,
  label,
  labelColor,
  borderColor,
}: {
  grid: number[][];
  gridSize: number;
  label: string;
  labelColor: string;
  borderColor: string;
}) {
  return (
    <div className={`bg-gray-800/40 rounded-xl border ${borderColor} p-3 sm:p-4`}>
      <h3 className={`text-xs font-semibold ${labelColor} uppercase tracking-wider mb-2`}>
        {label}
      </h3>
      <div className="grid gap-1" style={{ gridTemplateColumns: `repeat(${gridSize}, 1fr)` }}>
        {grid.flat().map((val, i) => (
          <div
            key={i}
            className={`
              aspect-square rounded flex items-center justify-center text-xs sm:text-sm font-bold
              ${val === 0
                ? 'bg-gray-700/30 text-gray-600'
                : label.includes('âœ“')
                ? 'bg-green-900/40 text-green-400'
                : 'bg-gray-700/60 text-gray-300'
              }
            `}
          >
            {val === 0 ? 'Â·' : val}
          </div>
        ))}
      </div>
    </div>
  );
});

```

#### Spiegazione
Linea 4: Definisce il componente MiniGrid con memo.
Linea 5: Destruttura le props.
Linea 6: Prop gridSize.
Linea 7: Prop label.
Linea 8: Prop labelColor.
Linea 9: Prop borderColor.
Linea 10: Apre la definizione del tipo props.
Linea 11: Tipo grid.
Linea 12: Tipo gridSize.
Linea 13: Tipo label.
Linea 14: Tipo labelColor.
Linea 15: Tipo borderColor.
Linea 16: Chiude tipo props.
Linea 17: Inizia il return del JSX.
Linea 18: Apre card MiniGrid con bordi.
Linea 19: Apre h3 con label.
Linea 20: Mostra label.
Linea 21: Chiude h3.
Linea 22: Apre griglia con colonne dinamiche.
Linea 23: Mappa le celle della griglia.
Linea 24: Apre cella mini.
Linea 25: Imposta key.
Linea 26: Inizia className multiline.
Linea 27: Classi base della cella.
Linea 28: Se valore e' 0, usa stile vuoto.
Linea 29: Stile vuoto.
Linea 30: Se label include check, usa stile finale.
Linea 31: Stile finale.
Linea 32: Stile default.
Linea 33: Chiude ternario.
Linea 34: Chiude template literal.
Linea 35: Chiude tag di apertura div.
Linea 36: Mostra punto o valore.
Linea 37: Chiude cella.
Linea 38: Chiude map.
Linea 39: Chiude griglia.
Linea 40: Chiude card MiniGrid.
Linea 41: Chiude return.
Linea 42: Chiude memo MiniGrid.
Linea 43: Riga vuota per separare.

### Costante: StatesPreview

#### Codice
```typescript
const StatesPreview = memo(function StatesPreview() {
```

#### Spiegazione
Linea 44: Definisce StatesPreview con memo.

### Costante: allStates

#### Codice
```typescript
  const allStates = useAllStates();
```

#### Spiegazione
Linea 45: Legge allStates.

### Costante: totalSteps

#### Codice
```typescript
  const totalSteps = useTotalSteps();
```

#### Spiegazione
Linea 46: Legge totalSteps.

### Costante: gridSize

#### Codice
```typescript
  const gridSize = useGridSize();
```

#### Spiegazione
Linea 47: Legge gridSize.

### Costante: initialGrid

#### Codice
```typescript
  const initialGrid = useInitialGrid();
```

#### Spiegazione
Linea 48: Legge initialGrid.

### Costante: finalGrid

#### Codice
```typescript
  const finalGrid = allStates[totalSteps];

  return (
    <div className="w-full grid grid-cols-1 sm:grid-cols-2 gap-3 sm:gap-4">
      <MiniGrid
        grid={initialGrid}
        gridSize={gridSize}
        label="Stato Iniziale"
        labelColor="text-gray-500"
        borderColor="border-gray-700/40"
      />
      <MiniGrid
        grid={finalGrid}
        gridSize={gridSize}
        label="Stato Finale âœ“"
        labelColor="text-green-500"
        borderColor="border-green-700/30"
      />
    </div>
  );
});

export default StatesPreview;
```

#### Spiegazione
Linea 49: Calcola finalGrid.
Linea 50: Riga vuota per separare.
Linea 51: Inizia il return del JSX.
Linea 52: Apre container a due colonne.
Linea 53: Renderizza MiniGrid stato iniziale.
Linea 54: Passa grid iniziale.
Linea 55: Passa gridSize.
Linea 56: Passa label "Stato Iniziale".
Linea 57: Passa colore label.
Linea 58: Passa colore bordo.
Linea 59: Chiude MiniGrid iniziale.
Linea 60: Renderizza MiniGrid stato finale.
Linea 61: Passa grid finale.
Linea 62: Passa gridSize.
Linea 63: Passa label "Stato Finale".
Linea 64: Passa colore label.
Linea 65: Passa colore bordo.
Linea 66: Chiude MiniGrid finale.
Linea 67: Chiude container.
Linea 68: Chiude return.
Linea 69: Chiude memo StatesPreview.
Linea 70: Riga vuota per separare.
Linea 71: Esporta StatesPreview di default.

<a id="src-features-puzzle-components-tile-tsx"></a>
## src/features/puzzle/components/Tile.tsx

### Import

#### Codice
```typescript
import { memo } from 'react';
import { TILE_COLORS_BY_MODE } from '@/features/puzzle/constants/puzzle';
import { useColorPaletteMode } from '@/features/puzzle/store/puzzleSelectors';

```

#### Spiegazione
Linea 1: Importa memo da React.
Linea 2: Importa le palette colori per le tessere.
Linea 3: Importa selector per colorPaletteMode.
Linea 4: Riga vuota per separare.

### Interfaccia: TileProps

#### Codice
```typescript
interface TileProps {
  value: number;
  isMoving: boolean;
}

```

#### Spiegazione
Linea 5: Definisce l'interfaccia TileProps.
Linea 6: Prop value.
Linea 7: Prop isMoving.
Linea 8: Chiude l'interfaccia.
Linea 9: Riga vuota per separare.

### Costante: Tile

#### Codice
```typescript
const Tile = memo(function Tile({ value, isMoving }: TileProps) {
```

#### Spiegazione
Linea 10: Definisce Tile con memo.

### Costante: colorPaletteMode

#### Codice
```typescript
  const colorPaletteMode = useColorPaletteMode();

  if (value === 0) {
    return (
      <div className="aspect-square rounded-lg sm:rounded-xl bg-gray-800/40 border-2 border-dashed border-gray-600/50" />
    );
  }

```

#### Spiegazione
Linea 11: Legge colorPaletteMode.
Linea 12: Riga vuota per separare.
Linea 13: Se value e' 0, ritorna la cella vuota.
Linea 14: Apre il return della cella vuota.
Linea 15: Div con stile vuoto e bordo tratteggiato.
Linea 16: Chiude return.
Linea 17: Chiude if.
Linea 18: Riga vuota per separare.

### Costante: palette

#### Codice
```typescript
  const palette = TILE_COLORS_BY_MODE[colorPaletteMode];

  return (
    <div
      className={`
        aspect-square rounded-lg sm:rounded-xl
        bg-gradient-to-br ${palette[value] || 'from-gray-500 to-gray-600'}
        shadow-lg flex items-center justify-center text-white font-bold
        text-lg sm:text-2xl md:text-3xl
        border border-white/20 transition-all duration-300
        ${isMoving ? 'scale-105 sm:scale-110 shadow-2xl ring-2 ring-white/50' : 'scale-100'}
      `}
    >
      {value}
    </div>
  );
});

export default Tile;
```

#### Spiegazione
Linea 19: Seleziona la palette in base alla modalita'.
Linea 20: Riga vuota per separare.
Linea 21: Inizia il return del JSX per la tessera.
Linea 22: Apre il div della tessera.
Linea 23: Inizia className multiline.
Linea 24: Classi base per dimensione.
Linea 25: Colore di sfondo in base alla palette.
Linea 26: Classi di layout e testo.
Linea 27: Dimensioni del font.
Linea 28: Bordo e transizione.
Linea 29: Stile condizionale se isMoving.
Linea 30: Chiude template literal.
Linea 31: Chiude tag di apertura div.
Linea 32: Mostra il valore numerico.
Linea 33: Chiude div.
Linea 34: Chiude return.
Linea 35: Chiude memo.
Linea 36: Riga vuota per separare.
Linea 37: Esporta Tile di default.

<a id="src-features-puzzle-components-footer-tsx"></a>
## src/features/puzzle/components/Footer.tsx

### Import

#### Codice
```typescript
import { memo } from 'react';
import { useAllStates, useTotalSteps, useInitialGrid } from '@/features/puzzle/store/puzzleSelectors';
import { gridToString } from '@/features/puzzle/utils/puzzle';

```

#### Spiegazione
Linea 1: Importa memo da React.
Linea 2: Importa selector per stati.
Linea 3: Importa gridToString.
Linea 4: Riga vuota per separare.

### Costante: Footer

#### Codice
```typescript
const Footer = memo(function Footer() {
```

#### Spiegazione
Linea 5: Definisce Footer con memo.

### Costante: allStates

#### Codice
```typescript
  const allStates = useAllStates();
```

#### Spiegazione
Linea 6: Legge allStates.

### Costante: totalSteps

#### Codice
```typescript
  const totalSteps = useTotalSteps();
```

#### Spiegazione
Linea 7: Legge totalSteps.

### Costante: initialGrid

#### Codice
```typescript
  const initialGrid = useInitialGrid();

```

#### Spiegazione
Linea 8: Legge initialGrid.
Linea 9: Riga vuota per separare.

### Costante: initialStr

#### Codice
```typescript
  const initialStr = gridToString(initialGrid);
```

#### Spiegazione
Linea 10: Serializza la griglia iniziale.

### Costante: finalStr

#### Codice
```typescript
  const finalStr = gridToString(allStates[totalSteps]);

  return (
    <div className="text-center text-gray-500 text-[10px] sm:text-xs pb-4 font-mono">
      <span className="text-gray-600">[{initialStr}]</span>
      <span className="mx-2">â†’</span>
      <span className="text-green-600">[{finalStr}]</span>
    </div>
  );
});

export default Footer;
```

#### Spiegazione
Linea 11: Serializza la griglia finale.
Linea 12: Riga vuota per separare.
Linea 13: Inizia il return del JSX.
Linea 14: Apre il container footer.
Linea 15: Mostra la griglia iniziale.
Linea 16: Mostra una freccia tra le griglie.
Linea 17: Mostra la griglia finale.
Linea 18: Chiude container.
Linea 19: Chiude return.
Linea 20: Chiude memo.
Linea 21: Riga vuota per separare.
Linea 22: Esporta Footer di default.
