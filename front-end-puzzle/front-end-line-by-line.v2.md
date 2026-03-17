# Frontend Line-by-Line (src/)

Questo file analizza il frontend riga per riga. Ogni sezione e' raggiungibile tramite ancora.
Nota: le righe vuote sono indicate come `(linea vuota)` per renderle visibili.

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
`(linea vuota)` — Riga vuota per leggibilita'.
`import "./globals.css";` — Importa i CSS globali dell'app.
`import Provider from "@/components/Provider";` — Importa il componente Provider (wrapper per i children).
`(linea vuota)` — Riga vuota per separare import e funzione.
`export default function RootLayout({` — Definisce il componente RootLayout esportato di default.
`  children,` — Estrae la prop children dalla firma della funzione.
`}: {` — Apre il tipo della prop children.
`  children: React.ReactNode;` — Tipizza children come React.ReactNode.
`}) {` — Chiude la definizione del tipo props.
`  return (` — Inizia il return del JSX.
`    <html lang="it">` — Apre il tag html e imposta la lingua su italiano.
`      <Provider>` — Avvolge il body con Provider.
`        <body className="h-screen w-screen overflow-hidden bg-gradient-to-br from-blue-900 via-indigo-900 to-black">` — Apre il body con classi Tailwind per full screen e gradiente.
`          <div className="h-full w-full flex items-center justify-center p-2 sm:p-4">` — Apre un contenitore centrato con padding responsive.
`            <div className="w-full h-full max-w-[1700px] bg-slate-900/80 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 flex flex-col overflow-hidden">` — Apre il pannello principale con max width, blur e bordi.
`              {children}` — Renderizza i children dentro il pannello.
`            </div>` — Chiude il pannello principale.
`          </div>` — Chiude il contenitore esterno.
`        </body>` — Chiude il body.
`      </Provider>` — Chiude Provider.
`    </html>` — Chiude il tag html.
`  );` — Chiude il return.
`}` — Chiude la funzione RootLayout.

<a id="src-app-page-tsx"></a>
## src/app/page.tsx
`import Link from "next/link";` — Importa il componente Link di Next.js per la navigazione client-side.
`(linea vuota)` — Riga vuota per separare import e funzione.
`export default function Home() {` — Definisce il componente Home esportato di default.
`  const pages = [` — Crea un array di pagine con route e label.
`    { href: "/game", label: "Gioca" },` — Definisce il link alla pagina di gioco.
`    { href: "/custom", label: "Tabella Manuale" },` — Definisce il link alla tabella manuale.
`    { href: "/settings", label: "Impostazioni" },` — Definisce il link alla pagina impostazioni.
`  ];` — Chiude l'array pages.
`(linea vuota)` — Riga vuota per separare dati e JSX.
`  return (` — Inizia il return del JSX.
`    <main className="flex flex-col items-center justify-center text-white space-y-12">` — Apre il main con layout centrato e testo bianco.
`      ` — Riga vuota per separare header e lista.
`      <h1 className="text-6xl font-extrabold tracking-wide bg-gradient-to-r from-cyan-400 to-blue-500 bg-clip-text text-transparent">` — Apre il titolo principale con stile gradient.
`        GAME HUB` — Inserisce il testo "GAME HUB".
`      </h1>` — Chiude il tag h1.
`(linea vuota)` — Riga vuota per separare titolo e pulsanti.
`      <div className="flex flex-col space-y-6 w-64">` — Apre il contenitore dei link con spaziatura verticale.
`        {pages.map((page) => (` — Mappa l'array pages in una lista di Link.
`          <Link` — Apre il tag Link.
`            key={page.href}` — Usa l'href come key per React.
`            href={page.href}` — Imposta l'href del link.
`            className="text-center py-4 rounded-xl bg-blue-600 hover:bg-blue-500 transition-all duration-300 text-xl font-semibold shadow-lg hover:scale-105 active:scale-95"` — Imposta classi Tailwind per stile bottone.
`          >` — Chiude il tag di apertura del Link.
`            {page.label}` — Renderizza la label del link.
`          </Link>` — Chiude il Link.
`        ))}` — Chiude la map.
`      </div>` — Chiude il contenitore dei link.
`    </main>` — Chiude il main.
`  );` — Chiude il return.
`}` — Chiude la funzione Home.

<a id="src-app-game-layout-tsx"></a>
## src/app/game/layout.tsx
`'use client';` — Direttiva client per usare hook e state nel layout.
`(linea vuota)` — Riga vuota per leggibilita'.
`export default function GameLayout({ children }: { children: React.ReactNode }) {` — Definisce il layout della route /game.
`  return (` — Inizia il return del JSX.
`    <div className="w-full h-full">` — Apre un wrapper full size.
`      <main className="h-full w-full">{children}</main>` — Inserisce il main e renderizza i children.
`    </div>` — Chiude il wrapper.
`  );` — Chiude il return.
`}` — Chiude la funzione GameLayout.

<a id="src-app-game-page-tsx"></a>
## src/app/game/page.tsx
`import GameClient from "@/features/puzzle/components/GameClient";` — Importa GameClient che contiene la UI del gioco.
`(linea vuota)` — Riga vuota per separare import e funzione.
`export default function Page() {` — Definisce il componente Page.
`  return <GameClient />;` — Renderizza GameClient.
`}` — Chiude la funzione Page.

<a id="src-app-custom-page-tsx"></a>
## src/app/custom/page.tsx
`"use client";` — Direttiva client per usare hook e stato nel componente.
`(linea vuota)` — Riga vuota per leggibilita'.
`import Link from 'next/link';` — Importa Link per navigazione client-side.
`import { useMemo, useState } from 'react';` — Importa useMemo e useState da React.
`import type { GridSize } from '@/features/puzzle/types/puzzle';` — Importa il tipo GridSize.
`import { useSetCustomBoard, useSetSolutionMovesFromApi } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per impostare tabella custom e mosse soluzione.
`import { useRouter } from 'next/navigation';` — Importa useRouter per navigazione programmata.
`import { postSolvePuzzle } from '@/features/puzzle/api/solverApi';` — Importa la funzione API per risolvere il puzzle.
`(linea vuota)` — Riga vuota per separare import e funzioni helper.
`function createEmptyGrid(size: GridSize): number[][] {` — Dichiarazione helper createEmptyGrid (non usata nel file).
`  return Array.from({ length: size }, () => Array.from({ length: size }, () => 0));` — Ritorna una matrice size x size inizializzata a 0.
`}` — Chiude createEmptyGrid.
`(linea vuota)` — Riga vuota per separare helper.
`function parseCell(value: string): number {` — Dichiarazione helper parseCell per convertire stringa in numero.
`  const num = Number(value.trim());` — Converte il valore in numero dopo trim.
`  return Number.isNaN(num) ? -1 : num;` — Ritorna -1 se NaN, altrimenti il numero.
`}` — Chiude parseCell.
`(linea vuota)` — Riga vuota per separare helper.
`function validateGridValues(values: number[], size: GridSize): string | null {` — Dichiarazione helper validateGridValues.
`  const maxValue = size * size - 1;` — Calcola il valore massimo consentito (size^2 - 1).
`(linea vuota)` — Riga vuota per separare blocco.
`  if (values.some((v) => v < 0 || v > maxValue)) {` — Verifica che tutti i valori siano nel range 0..maxValue.
`    return ``I valori devono essere compresi tra 0 e ${maxValue}.``;` — Ritorna messaggio di errore sul range.
`  }` — Chiude il blocco if del range.
`(linea vuota)` — Riga vuota per separare blocco.
`  const uniq = new Set(values);` — Crea un Set per controllare duplicati.
`  if (uniq.size !== values.length) {` — Confronta dimensione Set con array.
`    return 'Ogni numero deve comparire una sola volta.';` — Ritorna errore se ci sono duplicati.
`  }` — Chiude il blocco if duplicati.
`(linea vuota)` — Riga vuota per separare blocco.
`  for (let n = 0; n <= maxValue; n++) {` — Ciclo su tutti i numeri richiesti.
`    if (!uniq.has(n)) {` — Se un numero manca, ritorna errore.
`      return ``Manca il numero ${n}.``;` — Messaggio di errore per numero mancante.
`    }` — Chiude l'if di numero mancante.
`  }` — Chiude il ciclo for.
`(linea vuota)` — Riga vuota per separare blocco.
`  return null;` — Ritorna null se non ci sono errori.
`}` — Chiude validateGridValues.
`(linea vuota)` — Riga vuota per separare helper.
`function toMatrix(values: number[], size: GridSize): number[][] {` — Dichiarazione helper toMatrix.
`  const matrix: number[][] = [];` — Inizializza la matrice di output.
`  for (let r = 0; r < size; r++) {` — Ciclo sulle righe.
`    matrix.push(values.slice(r * size, (r + 1) * size));` — Aggiunge una slice di size elementi per ogni riga.
`  }` — Chiude il ciclo for.
`  return matrix;` — Ritorna la matrice.
`}` — Chiude toMatrix.
`(linea vuota)` — Riga vuota per separare dalla componente principale.
`export default function CustomBoardPage() {` — Definisce il componente CustomBoardPage.
`  const router = useRouter();` — Inizializza il router per la navigazione.
`  const setCustomBoard = useSetCustomBoard();` — Recupera l'azione per impostare tabella custom.
`  const setSolutionMovesFromApi = useSetSolutionMovesFromApi();` — Recupera l'azione per impostare mosse da API.
`(linea vuota)` — Riga vuota per separare stato e azioni.
`  const [gridSize, setGridSize] = useState<GridSize>(4);` — Stato della dimensione griglia (default 4).
`  const [cellValues, setCellValues] = useState<string[]>(Array.from({ length: 16 }, (_, i) => String(i)));` — Stato dei valori celle (default 0..15).
`  const [isSubmitting, setIsSubmitting] = useState(false);` — Stato di invio in corso.
`  const [feedback, setFeedback] = useState<string | null>(null);` — Stato messaggi di feedback.
`  const [error, setError] = useState<string | null>(null);` — Stato per errore bloccante (overlay).
`(linea vuota)` — Riga vuota per separare calcoli derivati.
`  const maxValue = useMemo(() => gridSize * gridSize - 1, [gridSize]);` — Calcola maxValue con useMemo in base alla dimensione.
`(linea vuota)` — Riga vuota per separare handler.
`  const handleSizeChange = (size: GridSize) => {` — Handler per cambio dimensione griglia.
`    setGridSize(size);` — Aggiorna la dimensione.
`    setCellValues(Array.from({ length: size * size }, (_, i) => String(i)));` — Rigenera l'array dei valori celle.
`    setFeedback(null);` — Reset del feedback.
`  };` — Chiude handleSizeChange.
`(linea vuota)` — Riga vuota per separare handler.
`  const updateCell = (index: number, value: string) => {` — Handler per aggiornare una singola cella.
`    setCellValues((prev) => {` — Aggiorna lo stato cellValues con funzione.
`      const next = [...prev];` — Clona l'array precedente.
`      next[index] = value;` — Aggiorna l'indice richiesto.
`      return next;` — Ritorna il nuovo array.
`    });` — Chiude setCellValues.
`  };` — Chiude updateCell.
`(linea vuota)` — Riga vuota per separare handler.
`  const buildGrid = (): number[][] | null => {` — Handler per costruire la griglia validata.
`    const numbers = cellValues.map(parseCell);` — Converte i valori stringa in numeri.
`    const error = validateGridValues(numbers, gridSize);` — Valida la griglia.
`    if (error) {` — Se c'e' un errore, lo mostra.
`      setFeedback(error);` — Imposta il feedback con l'errore.
`      return null;` — Ritorna null per bloccare il flusso.
`    }` — Chiude l'if di errore.
`    return toMatrix(numbers, gridSize);` — Ritorna la matrice validata.
`  };` — Chiude buildGrid.
`(linea vuota)` — Riga vuota per separare handler.
`  const handleUseBoard = () => {` — Handler per usare la tabella senza API.
`    const grid = buildGrid();` — Costruisce la griglia.
`    if (!grid) return;` — Esce se la griglia non e' valida.
`(linea vuota)` — Riga vuota per separare side effect.
`    setCustomBoard(gridSize, grid);` — Salva la griglia nello store.
`    setFeedback('Tabella salvata. Ora puoi giocare o avviare la soluzione.');` — Imposta un messaggio di feedback.
`    router.push('/game');` — Naviga alla pagina /game.
`  };` — Chiude handleUseBoard.
`(linea vuota)` — Riga vuota per separare handler.
`  const handleResolveWithApi = async () => {` — Handler async per risolvere via API.
`    const grid = buildGrid();` — Costruisce la griglia.
`    if (!grid) return;` — Esce se non valida.
`(linea vuota)` — Riga vuota per separare stato.
`    setIsSubmitting(true);` — Imposta isSubmitting a true.
`    setFeedback('Invio richiesta API in corso...');` — Mostra feedback di invio API.
`(linea vuota)` — Riga vuota per separare try/catch.
`    try {` — Inizia il blocco try.
`      setCustomBoard(gridSize, grid);` — Salva la griglia nello store.
`      const moves = await postSolvePuzzle(grid);` — Chiama l'API di solve e attende le mosse.
`(linea vuota)` — Riga vuota per separare side effect.
`      setSolutionMovesFromApi(moves);` — Salva le mosse nello store e passa in replay.
`      setFeedback(``Soluzione caricata: ${moves.length} mosse.``);` — Mostra feedback con numero di mosse.
`      router.push('/game');` — Naviga alla pagina /game.
`    } catch (err) {` — Passa al blocco catch.
`      // Verifica se Ã¨ un errore di puzzle non risolvibile` — Commento: controllo errore puzzle non risolvibile.
`      if (err instanceof Error && (err.message.includes('500') || err.message.includes('not solvable'))) {` — Se errore contiene 500 o not solvable, usa errore bloccante.
`        setError('âŒ Tabella non risolvibile\n\nLa tabella inserita non puÃ² essere risolta. Verifica i numeri e riprova!');` — Imposta il testo di errore per puzzle non risolvibile.
`      } else {` — Altrimenti, entra nel ramo else.
`        setFeedback('API non disponibile o risposta non valida. La tabella Ã¨ stata salvata comunque.');` — Feedback soft se API non disponibile.
`      }` — Chiude il blocco else.
`    } finally {` — Entra nel blocco finally.
`      setIsSubmitting(false);` — Resetta isSubmitting.
`    }` — Chiude finally.
`  };` — Chiude handleResolveWithApi.
`(linea vuota)` — Riga vuota per separare calcoli UI.
`  const cellsCount = gridSize * gridSize;` — Calcola il numero di celle.
`  const boardMaxWidth = gridSize === 3 ? 360 : 460;` — Imposta larghezza massima board in base alla dimensione.
`(linea vuota)` — Riga vuota per separare JSX.
`  return (` — Inizia il return del JSX.
`    <main className="h-full w-full overflow-y-auto px-3 py-3 sm:px-5 sm:py-5 text-white">` — Apre il main con padding e scroll.
`      <div className="h-full w-full rounded-2xl border border-slate-700 bg-slate-900/80 p-4 sm:p-6 shadow-2xl flex flex-col">` — Apre il contenitore principale con stile card.
`        <div className="flex flex-col items-center justify-center gap-3 text-center">` — Apre il blocco header pagina.
`          <h1 className="text-2xl sm:text-3xl font-bold">Tabella Manuale</h1>` — Titolo della pagina.
`          <div className="flex flex-wrap items-center justify-center gap-2">` — Apre il contenitore dei link.
`            <Link` — Apre il Link verso home.
`              href="/"` — Imposta href per home.
`              className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-sm font-semibold hover:bg-gray-700"` — Imposta classi Tailwind.
`            >` — Chiude il tag Link.
`              Home` — Testo del link Home.
`            </Link>` — Chiude il Link.
`            <Link` — Apre il Link verso /game.
`              href="/game"` — Imposta href verso gioco.
`              className="rounded-lg border border-cyan-500/60 bg-cyan-700 px-3 py-1.5 text-sm font-semibold hover:bg-cyan-600"` — Imposta classi Tailwind.
`            >` — Chiude il tag Link.
`              Vai al Gioco` — Testo del link Vai al Gioco.
`            </Link>` — Chiude il Link.
`          </div>` — Chiude il contenitore link.
`        </div>` — Chiude il blocco header.
`(linea vuota)` — Riga vuota per separare layout.
`        <div className="mt-5 min-h-0 flex-1 grid grid-cols-1 xl:grid-cols-3 gap-4">` — Apre la griglia principale della pagina.
`          <section className="w-full rounded-xl border border-slate-700 bg-slate-800/70 p-4 xl:col-span-1 xl:overflow-y-auto">` — Apre sezione Configurazione.
`            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Configurazione</h2>` — Titolo della sezione.
`            <div className="mt-3 flex gap-2">` — Apre il contenitore dei bottoni size.
`              <button` — Apre il bottone per 3x3.
`                onClick={() => handleSizeChange(3)}` — onClick imposta size 3.
`                className={``rounded-lg border px-3 py-2 text-sm font-semibold ${` — Inizia classe con ternario per stato attivo.
`                  gridSize === 3 ? 'border-emerald-400/70 bg-emerald-700' : 'border-slate-600 bg-slate-700'` — Se size e' 3 usa stile attivo, altrimenti inattivo.
`                }``}` — Chiude il template literal della classe.
`              >` — Chiude il tag di apertura button.
`                3 x 3` — Testo "3 x 3".
`              </button>` — Chiude il bottone 3x3.
`              <button` — Apre il bottone per 4x4.
`                onClick={() => handleSizeChange(4)}` — onClick imposta size 4.
`                className={``rounded-lg border px-3 py-2 text-sm font-semibold ${` — Inizia classe con ternario per stato attivo.
`                  gridSize === 4 ? 'border-emerald-400/70 bg-emerald-700' : 'border-slate-600 bg-slate-700'` — Se size e' 4 usa stile attivo, altrimenti inattivo.
`                }``}` — Chiude il template literal della classe.
`              >` — Chiude il tag di apertura button.
`                4 x 4` — Testo "4 x 4".
`              </button>` — Chiude il bottone 4x4.
`            </div>` — Chiude il contenitore bottoni size.
`(linea vuota)` — Riga vuota per separare testo.
`            <p className="mt-4 text-xs text-slate-400">` — Apre paragrafo istruzioni.
`              Inserisci tutti i numeri da 0 a {maxValue} una sola volta. Lo 0 e la casella vuota.` — Testo con range valido e significato dello zero.
`            </p>` — Chiude il paragrafo.
`(linea vuota)` — Riga vuota per separare bottoni azione.
`            <div className="mt-4 flex flex-col gap-2">` — Apre contenitore bottoni azione.
`              <button` — Apre bottone "Usa Questa Tabella".
`                onClick={handleUseBoard}` — onClick usa la tabella locale.
`                disabled={isSubmitting}` — Disabilita se in submitting.
`                className="rounded-lg border border-sky-400/60 bg-sky-700 px-4 py-2 text-sm font-semibold hover:bg-sky-600 disabled:opacity-60"` — Classi per bottone.
`              >` — Chiude il tag di apertura button.
`                Usa Questa Tabella` — Testo del bottone.
`              </button>` — Chiude bottone.
`              <button` — Apre bottone "Risolvi Con API".
`                onClick={handleResolveWithApi}` — onClick chiama la risoluzione via API.
`                disabled={isSubmitting}` — Disabilita se in submitting.
`                className="rounded-lg border border-orange-400/60 bg-orange-600 px-4 py-2 text-sm font-semibold hover:bg-orange-500 disabled:opacity-60"` — Classi per bottone.
`              >` — Chiude il tag di apertura button.
`                {isSubmitting ? 'Richiesta API...' : 'Risolvi Con API'}` — Testo condizionale in base a isSubmitting.
`              </button>` — Chiude bottone.
`            </div>` — Chiude contenitore bottoni.
`          </section>` — Chiude sezione Configurazione.
`(linea vuota)` — Riga vuota per separare sezione grid.
`          <section className="w-full rounded-xl border border-slate-700 bg-slate-800/70 p-4 flex min-h-0 flex-col xl:col-span-2">` — Apre sezione Posizione Numeri.
`            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Posizione Numeri</h2>` — Titolo della sezione.
`            <div className="mt-4 min-h-0 flex-1 flex items-center justify-center">` — Apre contenitore per la griglia input.
`              <div` — Apre il wrapper della griglia.
`                className="mx-auto grid w-full gap-2"` — Classi per layout grid.
`                style={{` — Apre l'oggetto style.
`                  gridTemplateColumns: ``repeat(${gridSize}, minmax(0, 1fr))``,` — Imposta le colonne in base a gridSize.
`                  maxWidth: ``min(${boardMaxWidth}px, calc(100vh - 290px))``,` — Imposta maxWidth in base all'altezza viewport.
`                }}` — Chiude l'oggetto style.
`              >` — Chiude il tag div.
`                {Array.from({ length: cellsCount }).map((_, index) => (` — Mappa le celle per creare input numerici.
`                  <input` — Apre input per una cella.
`                    key={index}` — Imposta key su index.
`                    type="number"` — Tipo input number.
`                    min={0}` — Minimo 0.
`                    max={maxValue}` — Massimo maxValue.
`                    value={cellValues[index] ?? ''}` — Value collegato a cellValues.
`                    onChange={(e) => updateCell(index, e.target.value)}` — onChange aggiorna il valore cella.
`                    className="no-spinner aspect-square w-full rounded-lg border border-slate-600 bg-slate-900 px-2 text-center text-lg sm:text-xl font-semibold text-white outline-none focus:border-cyan-400"` — Classi per stile input.
`                  />` — Chiude input.
`                ))}` — Chiude la map delle celle.
`              </div>` — Chiude il wrapper della griglia.
`            </div>` — Chiude contenitore della griglia.
`(linea vuota)` — Riga vuota per separare feedback.
`            {feedback && (` — Render condizionale del feedback.
`              <p className="mt-4 rounded-lg border border-slate-700 bg-slate-900/70 px-3 py-2 text-sm text-slate-200">` — Apre paragrafo feedback.
`                {feedback}` — Inserisce il testo feedback.
`              </p>` — Chiude paragrafo.
`            )}` — Chiude condizionale feedback.
`          </section>` — Chiude sezione Posizione Numeri.
`        </div>` — Chiude la griglia principale.
`      </div>` — Chiude il contenitore principale.
`(linea vuota)` — Riga vuota per separare overlay errore.
`      {error && (` — Render condizionale overlay errore.
`        <div className="fixed inset-0 flex items-center justify-center z-50">` — Apre overlay full screen.
`          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">` — Apre card dell'errore.
`            <div className="text-4xl sm:text-5xl mb-2">âŒ</div>` — Icona errore (simbolo) in grande.
`            <div className="text-lg sm:text-xl font-bold text-red-400 whitespace-pre-wrap mb-4">{error}</div>` — Testo errore formattato.
`            <button` — Apre bottone di chiusura.
`              onClick={() => setError(null)}` — onClick resetta l'errore.
`              className="mt-4 px-6 py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg transition-colors"` — Classi per bottone.
`            >` — Chiude il tag di apertura button.
`              OK` — Testo del bottone OK.
`            </button>` — Chiude bottone.
`          </div>` — Chiude card errore.
`        </div>` — Chiude overlay.
`      )}` — Chiude condizionale errore.
`    </main>` — Chiude il main.
`  );` — Chiude il return.
`}` — Chiude la funzione CustomBoardPage.

<a id="src-app-settings-page-tsx"></a>
## src/app/settings/page.tsx
`"use client";` — Direttiva client per usare hook e stato.
`(linea vuota)` — Riga vuota per separare import e codice.
`import Link from 'next/link';` — Importa Link di Next.js.
`import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';` — Importa le label delle palette colori.
`import type { ColorPaletteMode } from '@/features/puzzle/types/puzzle';` — Importa il tipo ColorPaletteMode.
`import {` — Importa selector e azioni dallo store.
`  useColorPaletteMode,` — Seleziona lo stato colorPaletteMode.
`  useMusicEnabled,` — Seleziona lo stato musicEnabled.
`  useSetColorPaletteMode,` — Importa azione per impostare palette.
`  useThemeMode,` — Seleziona lo stato themeMode.
`  useToggleMusicEnabled,` — Importa toggle musica.
`  useToggleThemeMode,` — Importa toggle tema.
`} from '@/features/puzzle/store/puzzleSelectors';` — Chiude l'import multiplo.
`(linea vuota)` — Riga vuota per separare costanti.
`const COLOR_MODES: ColorPaletteMode[] = [` — Definisce l'elenco delle palette disponibili.
`  'default',` — Aggiunge modalita' standard.
`  'protanopia',` — Aggiunge protanopia.
`  'deuteranopia',` — Aggiunge deuteranopia.
`  'tritanopia',` — Aggiunge tritanopia.
`  'achromatopsia',` — Aggiunge achromatopsia.
`];` — Chiude array COLOR_MODES.
`(linea vuota)` — Riga vuota per separare componente.
`export default function SettingsPage() {` — Definisce il componente SettingsPage.
`  const themeMode = useThemeMode();` — Legge il tema corrente dallo store.
`  const toggleThemeMode = useToggleThemeMode();` — Prende l'azione per toggle tema.
`  const colorPaletteMode = useColorPaletteMode();` — Legge la palette corrente.
`  const setColorPaletteMode = useSetColorPaletteMode();` — Prende l'azione per impostare palette.
`  const musicEnabled = useMusicEnabled();` — Legge lo stato musica.
`  const toggleMusicEnabled = useToggleMusicEnabled();` — Prende l'azione per toggle musica.
`(linea vuota)` — Riga vuota per separare JSX.
`  return (` — Inizia il return del JSX.
`    <main className="w-full h-full overflow-y-auto px-4 py-5 sm:px-6 sm:py-8 text-white">` — Apre main con scroll e padding.
`      <div className="mx-auto w-full max-w-3xl rounded-2xl border border-slate-700 bg-slate-900/80 p-5 sm:p-7 shadow-2xl">` — Apre card centrale con stile.
`        <div className="flex flex-wrap items-center justify-between gap-3">` — Apre header con titolo e link.
`          <h1 className="text-2xl sm:text-3xl font-bold">Impostazioni</h1>` — Titolo "Impostazioni".
`          <Link` — Apre Link verso /game.
`            href="/game"` — Imposta href.
`            className="rounded-lg border border-cyan-500/60 bg-cyan-700 px-3 py-1.5 text-sm font-semibold hover:bg-cyan-600"` — Classi del bottone.
`          >` — Chiude il tag di apertura Link.
`            Torna al Gioco` — Testo del link.
`          </Link>` — Chiude Link.
`        </div>` — Chiude header.
`(linea vuota)` — Riga vuota per separare sezioni.
`        <div className="mt-6 space-y-5">` — Apre contenitore sezioni con spacing.
`          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">` — Apre sezione tema.
`            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Tema</h2>` — Titolo sezione tema.
`            <p className="mt-1 text-xs text-slate-400">Mantieni il tema scuro o attiva quello chiaro.</p>` — Descrizione breve del tema.
`            <button` — Apre bottone toggle tema.
`              onClick={toggleThemeMode}` — onClick richiama toggleThemeMode.
`              className="mt-3 rounded-lg border border-sky-400/60 bg-sky-700 px-4 py-2 text-sm font-semibold hover:bg-sky-600"` — Classi bottone.
`            >` — Chiude tag apertura bottone.
`              {themeMode === 'dark' ? 'Passa a Modalita Chiara' : 'Passa a Modalita Scura'}` — Testo bottone in base al tema.
`            </button>` — Chiude bottone.
`          </section>` — Chiude sezione tema.
`(linea vuota)` — Riga vuota per separare sezioni.
`          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">` — Apre sezione colori tabella.
`            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Colori Tabella</h2>` — Titolo sezione palette.
`            <p className="mt-1 text-xs text-slate-400">Scegli il profilo: standard o uno dei principali tipi di daltonismo.</p>` — Descrizione palette.
`            <div className="mt-3 flex flex-wrap gap-2">` — Apre contenitore dei bottoni palette.
`              {COLOR_MODES.map((mode) => (` — Mappa le modalita' colore.
`                <button` — Apre bottone per una modalita'.
`                  key={mode}` — key per React.
`                  onClick={() => setColorPaletteMode(mode)}` — onClick imposta la modalita'.
`                  className={``rounded-lg border px-3 py-2 text-sm font-semibold ${` — Inizia className con ternario.
`                    colorPaletteMode === mode` — Se attiva usa stile evidenziato.
`                      ? 'border-emerald-400/70 bg-emerald-700'` — Stringa stile attivo.
`                      : 'border-slate-600 bg-slate-700'` — Stringa stile inattivo.
`                  }``}` — Chiude il template literal.
`                >` — Chiude il tag di apertura button.
`                  {COLOR_PALETTE_LABELS[mode]}` — Renderizza la label della modalita'.
`                </button>` — Chiude bottone.
`              ))}` — Chiude map.
`            </div>` — Chiude contenitore bottoni.
`          </section>` — Chiude sezione colori.
`(linea vuota)` — Riga vuota per separare sezioni.
`          <section className="rounded-xl border border-slate-700 bg-slate-800/70 p-4">` — Apre sezione musica.
`            <h2 className="text-sm font-semibold uppercase tracking-wide text-slate-300">Musica</h2>` — Titolo sezione musica.
`            <p className="mt-1 text-xs text-slate-400">Attiva o disattiva la musica del gioco.</p>` — Descrizione musica.
`            <button` — Apre bottone toggle musica.
`              onClick={toggleMusicEnabled}` — onClick richiama toggleMusicEnabled.
`              className="mt-3 rounded-lg border border-violet-400/60 bg-violet-700 px-4 py-2 text-sm font-semibold hover:bg-violet-600"` — Classi bottone.
`            >` — Chiude tag apertura bottone.
`              {musicEnabled ? 'Disattiva Musica' : 'Attiva Musica'}` — Testo bottone in base allo stato musica.
`            </button>` — Chiude bottone.
`          </section>` — Chiude sezione musica.
`        </div>` — Chiude contenitore sezioni.
`      </div>` — Chiude card.
`    </main>` — Chiude main.
`  );` — Chiude return.
`}` — Chiude la funzione SettingsPage.

<a id="src-app-globals-css"></a>
## src/app/globals.css
`@import "tailwindcss";` — Importa le base utilities di Tailwind CSS.
`(linea vuota)` — Riga vuota per separare blocchi.
`:root {` — Apre il selettore :root.
`  --background: #ffffff;` — Definisce la variabile --background chiara.
`  --foreground: #171717;` — Definisce la variabile --foreground scura.
`}` — Chiude :root.
`(linea vuota)` — Riga vuota per separare blocchi.
`@theme inline {` — Apre il blocco @theme inline per Tailwind.
`  --color-background: var(--background);` — Mappa --color-background su --background.
`  --color-foreground: var(--foreground);` — Mappa --color-foreground su --foreground.
`  --font-sans: var(--font-geist-sans);` — Mappa font sans su Geist.
`  --font-mono: var(--font-geist-mono);` — Mappa font mono su Geist mono.
`}` — Chiude @theme inline.
`(linea vuota)` — Riga vuota per separare blocchi.
`@media (prefers-color-scheme: dark) {` — Apre media query prefers-color-scheme: dark.
`  :root {` — Apre :root in modalita' scura.
`    --background: #0a0a0a;` — Imposta background scuro.
`    --foreground: #ededed;` — Imposta foreground chiaro.
`  }` — Chiude :root.
`}` — Chiude media query.
`(linea vuota)` — Riga vuota per separare blocchi.
`body {` — Apre stile body.
`  /* background: var(--background);` — Commento: proprietà background e color disabilitate.
`  color: var(--foreground); */` — Commento: chiusura del blocco commentato.
`  font-family: Arial, Helvetica, sans-serif;` — Imposta font-family di default.
`}` — Chiude stile body.
`(linea vuota)` — Riga vuota per separare blocchi.
`input[type='number']::-webkit-outer-spin-button,` — Selettore per rimuovere spinner numerici (WebKit).
`input[type='number']::-webkit-inner-spin-button {` — Selettore per spinner interno WebKit.
`  -webkit-appearance: none;` — Disattiva l'aspetto nativo.
`  margin: 0;` — Rimuove margine predefinito.
`}` — Chiude il blocco spinner WebKit.
`(linea vuota)` — Riga vuota per separare blocchi.
`input[type='number'] {` — Selettore per input number.
`  appearance: textfield;` — Imposta appearance a textfield.
`  -moz-appearance: textfield;` — Imposta -moz-appearance a textfield.
`}` — Chiude il blocco input number.
`(linea vuota)` — Riga vuota per separare blocchi.
`select.no-native-arrow {` — Selettore per select con classe no-native-arrow.
`  appearance: none;` — Disattiva appearance standard.
`  -webkit-appearance: none;` — Disattiva appearance WebKit.
`  -moz-appearance: none;` — Disattiva appearance Firefox.
`  background-image: none;` — Rimuove immagine di background della freccia.
`}` — Chiude il blocco select.
`(linea vuota)` — Riga vuota per separare blocchi.
`select.no-native-arrow::-ms-expand {` — Selettore per la freccia in IE/Edge legacy.
`  display: none;` — Nasconde la freccia nativa.
`}` — Chiude il blocco select legacy.

<a id="src-app-favicon-ico"></a>
## src/app/favicon.ico
File binario (icona). Non e' un file di testo, quindi non e' possibile una spiegazione riga per riga.

<a id="src-components-provider-tsx"></a>
## src/components/Provider.tsx
`'use client';` — Direttiva client per abilitare componenti client.
`(linea vuota)` — Riga vuota per separare.
`export default function Providers({` — Definisce il componente Providers.
`  children,` — Estrae children dalle props.
`}: {` — Apre il tipo delle props.
`  children: React.ReactNode;` — Tipizza children come React.ReactNode.
`}) {` — Chiude la definizione delle props.
`  return <>{children}</>;` — Ritorna i children senza wrapper aggiuntivi.
`}` — Chiude la funzione Providers.

<a id="src-utils-cn-ts"></a>
## src/utils/cn.ts
`import { clsx, type ClassValue } from "clsx";` — Importa clsx e il tipo ClassValue.
`import { twMerge } from "tailwind-merge";` — Importa twMerge per unire classi Tailwind.
`(linea vuota)` — Riga vuota per separare import e funzione.
`export function cn(...inputs: ClassValue[]) {` — Definisce helper cn per unire classi.
`  return twMerge(clsx(inputs));` — Ritorna classi combinate con clsx e twMerge.
`}` — Chiude la funzione cn.

<a id="src-features-puzzle-types-puzzle-ts"></a>
## src/features/puzzle/types/puzzle.ts
`export type Grid = number[][];` — Definisce il tipo Grid come matrice di numeri.
`export type GridSize = 3 | 4;` — Definisce GridSize come 3 o 4.
`export type ThemeMode = 'dark' | 'light';` — Definisce ThemeMode come dark o light.
`export type ColorPaletteMode =` — Inizia la definizione di ColorPaletteMode.
`  | 'default'` — Aggiunge modalita' default.
`  | 'protanopia'` — Aggiunge protanopia.
`  | 'deuteranopia'` — Aggiunge deuteranopia.
`  | 'tritanopia'` — Aggiunge tritanopia.
`  | 'achromatopsia';` — Aggiunge achromatopsia.
`(linea vuota)` — Riga vuota per separare tipi.
`export type Direction = 'SINISTRA' | 'SOTTO' | 'SOPRA' | 'DESTRA';` — Definisce Direction per le quattro direzioni.
`(linea vuota)` — Riga vuota per separare interfacce.
`export interface DirectionInfo {` — Inizia l'interfaccia DirectionInfo.
`  icon: string;` — Campo icon (stringa).
`  en: string;` — Campo en (etichetta inglese).
`}` — Chiude l'interfaccia DirectionInfo.
`(linea vuota)` — Riga vuota per separare interfacce.
`export interface PuzzleConfig {` — Inizia l'interfaccia PuzzleConfig.
`  gridSize: GridSize;` — Campo gridSize.
`  initialGrid: Grid;` — Campo initialGrid.
`  moves: Direction[];` — Campo moves.
`}` — Chiude l'interfaccia PuzzleConfig.

<a id="src-features-puzzle-constants-puzzle-ts"></a>
## src/features/puzzle/constants/puzzle.ts
`import type { ColorPaletteMode, Direction, DirectionInfo, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';` — Importa tipi usati nelle costanti.
`(linea vuota)` — Riga vuota per separare.
`const INITIAL_GRID_4X4: Grid = [` — Inizia la griglia iniziale 4x4.
`  [7, 2, 0, 8],` — Riga 0 della griglia 4x4.
`  [3, 11, 14, 5],` — Riga 1 della griglia 4x4.
`  [6, 15, 10, 1],` — Riga 2 della griglia 4x4.
`  [9, 12, 13, 4],` — Riga 3 della griglia 4x4.
`];` — Chiude la griglia iniziale 4x4.
`(linea vuota)` — Riga vuota per separare.
`const MOVES_4X4: Direction[] = [` — Inizia la sequenza di mosse per 4x4.
`  'DESTRA', 'SOTTO', 'SOTTO', 'SINISTRA', 'SINISTRA', 'SOTTO',` — Prima parte delle mosse 4x4.
`  'DESTRA', 'SOPRA', 'SOPRA', 'DESTRA', 'SOTTO', 'SOTTO',` — Seconda parte delle mosse 4x4.
`  'SINISTRA', 'SOPRA', 'SINISTRA', 'SOPRA', 'DESTRA', 'DESTRA',` — Terza parte delle mosse 4x4.
`  'SOTTO', 'SINISTRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',` — Quarta parte delle mosse 4x4.
`  'DESTRA', 'SOPRA', 'SOPRA', 'SOPRA', 'DESTRA', 'SOTTO',` — Quinta parte delle mosse 4x4.
`  'SINISTRA', 'SOPRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',` — Sesta parte delle mosse 4x4.
`  'DESTRA', 'SOPRA', 'SINISTRA', 'SINISTRA', 'SOTTO', 'DESTRA',` — Settima parte delle mosse 4x4.
`  'DESTRA', 'SOPRA', 'SINISTRA', 'SOTTO', 'SOTTO', 'DESTRA',` — Ottava parte delle mosse 4x4.
`  'SOTTO', 'DESTRA',` — Ultime mosse 4x4.
`];` — Chiude l'array MOVES_4X4.
`(linea vuota)` — Riga vuota per separare.
`const INITIAL_GRID_3X3: Grid = [` — Inizia la griglia iniziale 3x3.
`  [1, 2, 3],` — Riga 0 della griglia 3x3.
`  [7, 0, 6],` — Riga 1 della griglia 3x3.
`  [5, 4, 8],` — Riga 2 della griglia 3x3.
`];` — Chiude la griglia 3x3.
`(linea vuota)` — Riga vuota per separare.
`const MOVES_3X3: Direction[] = [` — Inizia la sequenza di mosse 3x3.
`  'SOTTO',` — Mossa 1.
`  'SINISTRA',` — Mossa 2.
`  'SOPRA',` — Mossa 3.
`  'DESTRA',` — Mossa 4.
`  'SOTTO',` — Mossa 5.
`  'DESTRA',` — Mossa 6.
`];` — Chiude l'array MOVES_3X3.
`(linea vuota)` — Riga vuota per separare.
`export const PUZZLE_CONFIGS: Record<GridSize, PuzzleConfig> = {` — Espone le configurazioni per 3x3 e 4x4.
`  3: {` — Inizia config per 3x3.
`    gridSize: 3,` — Imposta gridSize=3.
`    initialGrid: INITIAL_GRID_3X3,` — Imposta initialGrid 3x3.
`    moves: MOVES_3X3,` — Imposta moves 3x3.
`  },` — Chiude config 3x3.
`  4: {` — Inizia config per 4x4.
`    gridSize: 4,` — Imposta gridSize=4.
`    initialGrid: INITIAL_GRID_4X4,` — Imposta initialGrid 4x4.
`    moves: MOVES_4X4,` — Imposta moves 4x4.
`  },` — Chiude config 4x4.
`};` — Chiude PUZZLE_CONFIGS.
`(linea vuota)` — Riga vuota per separare.
`export const DEFAULT_GRID_SIZE: GridSize = 4;` — Definisce la dimensione di default (4).
`(linea vuota)` — Riga vuota per separare.
`export const DIRECTION_LABELS: Record<Direction, DirectionInfo> = {` — Inizia la mappa etichette direzioni.
`  SINISTRA: { icon: 'â†', en: 'LEFT' },` — Etichetta per SINISTRA con icona e inglese.
`  DESTRA:   { icon: 'â†’', en: 'RIGHT' },` — Etichetta per DESTRA con icona e inglese.
`  SOPRA:    { icon: 'â†‘', en: 'UP' },` — Etichetta per SOPRA con icona e inglese.
`  SOTTO:    { icon: 'â†“', en: 'DOWN' },` — Etichetta per SOTTO con icona e inglese.
`};` — Chiude DIRECTION_LABELS.
`(linea vuota)` — Riga vuota per separare.
`export const DIRECTION_DELTAS: Record<Direction, [number, number]> = {` — Inizia la mappa dei delta per direzioni.
`  SINISTRA: [0, -1],` — Delta per SINISTRA.
`  DESTRA:   [0,  1],` — Delta per DESTRA.
`  SOPRA:    [-1, 0],` — Delta per SOPRA.
`  SOTTO:    [ 1, 0],` — Delta per SOTTO.
`};` — Chiude DIRECTION_DELTAS.
`(linea vuota)` — Riga vuota per separare.
`export const TILE_COLORS: Record<number, string> = {` — Inizia la palette colori standard.
`  1:  'from-blue-500 to-blue-600',` — Colore tile 1.
`  2:  'from-emerald-500 to-emerald-600',` — Colore tile 2.
`  3:  'from-amber-500 to-amber-600',` — Colore tile 3.
`  4:  'from-purple-500 to-purple-600',` — Colore tile 4.
`  5:  'from-rose-500 to-rose-600',` — Colore tile 5.
`  6:  'from-cyan-500 to-cyan-600',` — Colore tile 6.
`  7:  'from-orange-500 to-orange-600',` — Colore tile 7.
`  8:  'from-indigo-500 to-indigo-600',` — Colore tile 8.
`  9:  'from-teal-500 to-teal-600',` — Colore tile 9.
`  10: 'from-pink-500 to-pink-600',` — Colore tile 10.
`  11: 'from-lime-500 to-lime-600',` — Colore tile 11.
`  12: 'from-fuchsia-500 to-fuchsia-600',` — Colore tile 12.
`  13: 'from-sky-500 to-sky-600',` — Colore tile 13.
`  14: 'from-red-500 to-red-600',` — Colore tile 14.
`  15: 'from-violet-500 to-violet-600',` — Colore tile 15.
`};` — Chiude TILE_COLORS.
`(linea vuota)` — Riga vuota per separare.
`export const TILE_COLORS_PROTANOPIA: Record<number, string> = {` — Inizia la palette protanopia.
`  1: 'from-sky-500 to-sky-600',` — Colore tile 1 protanopia.
`  2: 'from-cyan-500 to-cyan-600',` — Colore tile 2 protanopia.
`  3: 'from-yellow-500 to-yellow-600',` — Colore tile 3 protanopia.
`  4: 'from-violet-500 to-violet-600',` — Colore tile 4 protanopia.
`  5: 'from-slate-500 to-slate-600',` — Colore tile 5 protanopia.
`  6: 'from-indigo-500 to-indigo-600',` — Colore tile 6 protanopia.
`  7: 'from-amber-500 to-amber-600',` — Colore tile 7 protanopia.
`  8: 'from-blue-500 to-blue-600',` — Colore tile 8 protanopia.
`  9: 'from-emerald-500 to-emerald-600',` — Colore tile 9 protanopia.
`  10: 'from-zinc-500 to-zinc-600',` — Colore tile 10 protanopia.
`  11: 'from-lime-500 to-lime-600',` — Colore tile 11 protanopia.
`  12: 'from-orange-500 to-orange-600',` — Colore tile 12 protanopia.
`  13: 'from-fuchsia-500 to-fuchsia-600',` — Colore tile 13 protanopia.
`  14: 'from-stone-500 to-stone-600',` — Colore tile 14 protanopia.
`  15: 'from-teal-500 to-teal-600',` — Colore tile 15 protanopia.
`};` — Chiude TILE_COLORS_PROTANOPIA.
`(linea vuota)` — Riga vuota per separare.
`export const TILE_COLORS_DEUTERANOPIA: Record<number, string> = {` — Inizia la palette deuteranopia.
`  1: 'from-blue-500 to-blue-600',` — Colore tile 1 deuteranopia.
`  2: 'from-cyan-500 to-cyan-600',` — Colore tile 2 deuteranopia.
`  3: 'from-amber-500 to-amber-600',` — Colore tile 3 deuteranopia.
`  4: 'from-purple-500 to-purple-600',` — Colore tile 4 deuteranopia.
`  5: 'from-zinc-500 to-zinc-600',` — Colore tile 5 deuteranopia.
`  6: 'from-sky-500 to-sky-600',` — Colore tile 6 deuteranopia.
`  7: 'from-yellow-500 to-yellow-600',` — Colore tile 7 deuteranopia.
`  8: 'from-indigo-500 to-indigo-600',` — Colore tile 8 deuteranopia.
`  9: 'from-teal-500 to-teal-600',` — Colore tile 9 deuteranopia.
`  10: 'from-rose-500 to-rose-600',` — Colore tile 10 deuteranopia.
`  11: 'from-lime-500 to-lime-600',` — Colore tile 11 deuteranopia.
`  12: 'from-fuchsia-500 to-fuchsia-600',` — Colore tile 12 deuteranopia.
`  13: 'from-orange-500 to-orange-600',` — Colore tile 13 deuteranopia.
`  14: 'from-stone-500 to-stone-600',` — Colore tile 14 deuteranopia.
`  15: 'from-emerald-500 to-emerald-600',` — Colore tile 15 deuteranopia.
`};` — Chiude TILE_COLORS_DEUTERANOPIA.
`(linea vuota)` — Riga vuota per separare.
`export const TILE_COLORS_TRITANOPIA: Record<number, string> = {` — Inizia la palette tritanopia.
`  1: 'from-red-500 to-red-600',` — Colore tile 1 tritanopia.
`  2: 'from-orange-500 to-orange-600',` — Colore tile 2 tritanopia.
`  3: 'from-yellow-500 to-yellow-600',` — Colore tile 3 tritanopia.
`  4: 'from-rose-500 to-rose-600',` — Colore tile 4 tritanopia.
`  5: 'from-lime-500 to-lime-600',` — Colore tile 5 tritanopia.
`  6: 'from-emerald-500 to-emerald-600',` — Colore tile 6 tritanopia.
`  7: 'from-green-500 to-green-600',` — Colore tile 7 tritanopia.
`  8: 'from-zinc-500 to-zinc-600',` — Colore tile 8 tritanopia.
`  9: 'from-stone-500 to-stone-600',` — Colore tile 9 tritanopia.
`  10: 'from-amber-500 to-amber-600',` — Colore tile 10 tritanopia.
`  11: 'from-fuchsia-500 to-fuchsia-600',` — Colore tile 11 tritanopia.
`  12: 'from-pink-500 to-pink-600',` — Colore tile 12 tritanopia.
`  13: 'from-indigo-500 to-indigo-600',` — Colore tile 13 tritanopia.
`  14: 'from-slate-500 to-slate-600',` — Colore tile 14 tritanopia.
`  15: 'from-violet-500 to-violet-600',` — Colore tile 15 tritanopia.
`};` — Chiude TILE_COLORS_TRITANOPIA.
`(linea vuota)` — Riga vuota per separare.
`export const TILE_COLORS_ACHROMATOPSIA: Record<number, string> = {` — Inizia la palette achromatopsia.
`  1: 'from-slate-500 to-slate-600',` — Colore tile 1 achromatopsia.
`  2: 'from-slate-600 to-slate-700',` — Colore tile 2 achromatopsia.
`  3: 'from-zinc-400 to-zinc-500',` — Colore tile 3 achromatopsia.
`  4: 'from-zinc-500 to-zinc-600',` — Colore tile 4 achromatopsia.
`  5: 'from-gray-400 to-gray-500',` — Colore tile 5 achromatopsia.
`  6: 'from-gray-500 to-gray-600',` — Colore tile 6 achromatopsia.
`  7: 'from-neutral-500 to-neutral-600',` — Colore tile 7 achromatopsia.
`  8: 'from-neutral-600 to-neutral-700',` — Colore tile 8 achromatopsia.
`  9: 'from-stone-400 to-stone-500',` — Colore tile 9 achromatopsia.
`  10: 'from-stone-500 to-stone-600',` — Colore tile 10 achromatopsia.
`  11: 'from-zinc-300 to-zinc-400',` — Colore tile 11 achromatopsia.
`  12: 'from-zinc-700 to-zinc-800',` — Colore tile 12 achromatopsia.
`  13: 'from-gray-300 to-gray-400',` — Colore tile 13 achromatopsia.
`  14: 'from-gray-600 to-gray-700',` — Colore tile 14 achromatopsia.
`  15: 'from-stone-600 to-stone-700',` — Colore tile 15 achromatopsia.
`};` — Chiude TILE_COLORS_ACHROMATOPSIA.
`(linea vuota)` — Riga vuota per separare.
`export const TILE_COLORS_BY_MODE: Record<ColorPaletteMode, Record<number, string>> = {` — Mappa palette per modalita'.
`  default: TILE_COLORS,` — Associa default alla palette standard.
`  protanopia: TILE_COLORS_PROTANOPIA,` — Associa protanopia.
`  deuteranopia: TILE_COLORS_DEUTERANOPIA,` — Associa deuteranopia.
`  tritanopia: TILE_COLORS_TRITANOPIA,` — Associa tritanopia.
`  achromatopsia: TILE_COLORS_ACHROMATOPSIA,` — Associa achromatopsia.
`};` — Chiude TILE_COLORS_BY_MODE.
`(linea vuota)` — Riga vuota per separare.
`export const COLOR_PALETTE_LABELS: Record<ColorPaletteMode, string> = {` — Etichette leggibili per le palette.
`  default: 'Standard',` — Label per default.
`  protanopia: 'Protanopia',` — Label per protanopia.
`  deuteranopia: 'Deuteranopia',` — Label per deuteranopia.
`  tritanopia: 'Tritanopia',` — Label per tritanopia.
`  achromatopsia: 'Achromatopsia',` — Label per achromatopsia.
`};` — Chiude COLOR_PALETTE_LABELS.
`(linea vuota)` — Riga vuota per separare.
`export const DEFAULT_SPEED = 400;` — Imposta la velocita' default del replay.
`export const MIN_SPEED = 100;` — Imposta la velocita' minima.
`export const MAX_SPEED = 1500;` — Imposta la velocita' massima.
`export const SPEED_STEP = 50;` — Imposta lo step per lo slider.

<a id="src-features-puzzle-utils-puzzle-ts"></a>
## src/features/puzzle/utils/puzzle.ts
`import type { Direction, Grid } from '@/features/puzzle/types/puzzle';` — Importa i tipi Direction e Grid.
`import { DIRECTION_DELTAS } from '@/features/puzzle/constants/puzzle';` — Importa i delta delle direzioni.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc della funzione validateGrid.
` * Valida se una griglia ha dimensioni corrette e contiene numeri validi.` — Descrive la funzione validateGrid.
` */` — Chiude il commento JSDoc.
`function validateGrid(grid: Grid, expectedSize?: number): boolean {` — Definisce validateGrid con griglia e size opzionale.
`  if (!Array.isArray(grid) || grid.length === 0) return false;` — Verifica array non vuoto.
`  const size = expectedSize ?? grid.length;` — Determina size attesa (parametro o lunghezza griglia).
`  if (grid.length !== size) return false;` — Verifica che il numero di righe sia corretto.
`(linea vuota)` — Riga vuota per separare.
`  for (const row of grid) {` — Ciclo sulle righe.
`    if (!Array.isArray(row) || row.length !== size) return false;` — Verifica riga valida e lunghezza corretta.
`    for (const cell of row) {` — Ciclo sulle celle.
`      if (typeof cell !== 'number' || cell < 0 || cell >= size * size) return false;` — Verifica che la cella sia numero nel range.
`    }` — Chiude il ciclo celle.
`  }` — Chiude il ciclo righe.
`  return true;` — Ritorna true se valida.
`}` — Chiude validateGrid.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc di findZero.
` * Trova la posizione dello zero (casella vuota) nella griglia.` — Descrive la ricerca dello zero.
` * Lancia un errore se la griglia Ã¨ invalida o zero non viene trovato.` — Nota su errore se griglia invalida.
` */` — Chiude il commento JSDoc.
`export function findZero(grid: Grid): [number, number] {` — Definisce findZero.
`  if (!validateGrid(grid)) {` — Valida la griglia, altrimenti logga errore.
`    console.error('Invalid grid:', grid);` — Logga la griglia invalida.
`    throw new Error('Grid must be square and contain valid tile values');` — Lancia un errore se griglia invalida.
`  }` — Chiude if della validazione.
`(linea vuota)` — Riga vuota per separare.
`  const size = grid.length;` — Salva la dimensione della griglia.
`(linea vuota)` — Riga vuota per separare.
`  for (let r = 0; r < size; r++) {` — Ciclo sulle righe.
`    for (let c = 0; c < size; c++) {` — Ciclo sulle colonne.
`      if (grid[r][c] === 0) return [r, c];` — Se trova lo zero, ritorna la posizione.
`    }` — Chiude ciclo colonne.
`  }` — Chiude ciclo righe.
`(linea vuota)` — Riga vuota per separare.
`  console.error('Zero not found in grid:', grid);` — Logga errore se zero non trovato.
`  throw new Error('No zero (empty cell) found in grid');` — Lancia un errore se zero non trovato.
`}` — Chiude findZero.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc di applyMove.
` * Applica una mossa alla griglia, restituendo una nuova griglia.` — Descrive l'applicazione di una mossa.
` */` — Chiude il commento JSDoc.
`export function applyMove(grid: Grid, dir: Direction): Grid {` — Definisce applyMove.
`  const newGrid = grid.map(row => [...row]);` — Clona la griglia per non mutare l'originale.
`  const [r, c] = findZero(newGrid);` — Trova la posizione dello zero.
`  const size = newGrid.length;` — Salva la dimensione.
`  const [dr, dc] = DIRECTION_DELTAS[dir];` — Legge il delta per la direzione.
`  const nr = r + dr;` — Calcola nuova riga dello zero.
`  const nc = c + dc;` — Calcola nuova colonna dello zero.
`  if (nr >= 0 && nr < size && nc >= 0 && nc < size) {` — Verifica che la nuova posizione sia valida.
`    [newGrid[r][c], newGrid[nr][nc]] = [newGrid[nr][nc], newGrid[r][c]];` — Scambia zero con la tessera adiacente.
`  }` — Chiude if di validazione.
`  return newGrid;` — Ritorna la nuova griglia.
`}` — Chiude applyMove.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc di computeAllStates.
` * Calcola tutti gli stati intermedi dalla griglia iniziale applicando tutte le mosse.` — Descrive il calcolo degli stati intermedi.
` * Valida la griglia iniziale e registra errori se si verificano.` — Nota sulla validazione iniziale.
` */` — Chiude il commento JSDoc.
`export function computeAllStates(initialGrid: Grid, movesSequence: Direction[]): {` — Definisce computeAllStates e il tipo di ritorno.
`  grids: Grid[];` — Tipo di ritorno: array di griglie.
`  moves: (Direction | null)[];` — Tipo di ritorno: array di mosse.
`} {` — Chiude la firma di ritorno.
`  if (!validateGrid(initialGrid)) {` — Valida la griglia iniziale.
`    throw new Error('Initial grid must be square and valid');` — Lancia errore se non valida.
`  }` — Chiude if.
`(linea vuota)` — Riga vuota per separare.
`  const grids: Grid[] = [initialGrid.map(row => [...row])];` — Inizializza grids con una copia dello stato iniziale.
`  const moves: (Direction | null)[] = [null];` — Inizializza moves con null per lo step 0.
`  let current = initialGrid.map(row => [...row]);` — Copia iniziale in current.
`(linea vuota)` — Riga vuota per separare.
`  try {` — Inizia try per calcolare le mosse.
`    for (const move of movesSequence) {` — Ciclo su ogni mossa.
`      current = applyMove(current, move);` — Applica la mossa e aggiorna current.
`      grids.push(current);` — Aggiunge la griglia a grids.
`      moves.push(move);` — Aggiunge la mossa a moves.
`    }` — Chiude il ciclo.
`  } catch (error) {` — Passa al catch.
`    console.error('Error computing puzzle states:', error);` — Logga errore.
`    throw error;` — Rilancia l'errore.
`  }` — Chiude try/catch.
`(linea vuota)` — Riga vuota per separare.
`  return { grids, moves };` — Ritorna grids e moves.
`}` — Chiude computeAllStates.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc di getMovedTile.
` * Determina quale tessera Ã¨ stata appena spostata confrontando due stati.` — Descrive il calcolo della tessera mossa.
` */` — Chiude il commento JSDoc.
`export function getMovedTile(currentGrid: Grid, prevGrid: Grid | null): number {` — Definisce getMovedTile.
`  if (!prevGrid) return -1;` — Se non c'e' prevGrid, ritorna -1.
`  try {` — Inizia try.
`    const [r0, c0] = findZero(prevGrid);` — Trova la posizione dello zero nel prevGrid.
`    return currentGrid[r0][c0];` — Ritorna il valore in currentGrid in quella posizione.
`  } catch (error) {` — Passa al catch.
`    console.error('Error getting moved tile:', error);` — Logga errore.
`    return -1;` — Ritorna -1 in caso di errore.
`  }` — Chiude try/catch.
`}` — Chiude getMovedTile.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc di gridToString.
` * Serializza una griglia in stringa leggibile.` — Descrive la serializzazione della griglia.
` */` — Chiude il commento JSDoc.
`export function gridToString(grid: Grid): string {` — Definisce gridToString.
`  if (!validateGrid(grid)) {` — Se griglia non valida, ritorna stringa placeholder.
`    return '[invalid grid]';` — Ritorna placeholder per griglia invalida.
`  }` — Chiude if.
`  return grid.map(r => ``[${r.join(',')}]``).join(',');` — Serializza la griglia in stringa leggibile.
`}` — Chiude gridToString.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc di isGridSolved.
` * Verifica se la griglia e nello stato finale: numeri in ordine e zero in basso a destra.` — Descrive la verifica di stato finale.
` */` — Chiude il commento JSDoc.
`export function isGridSolved(grid: Grid): boolean {` — Definisce isGridSolved.
`  if (!validateGrid(grid)) return false;` — Se griglia invalida, ritorna false.
`(linea vuota)` — Riga vuota per separare.
`  const size = grid.length;` — Salva la dimensione.
`  let expected = 1;` — Inizializza il valore atteso.
`(linea vuota)` — Riga vuota per separare.
`  for (let r = 0; r < size; r++) {` — Ciclo sulle righe.
`    for (let c = 0; c < size; c++) {` — Ciclo sulle colonne.
`      const isLastCell = r === size - 1 && c === size - 1;` — Determina se la cella e' l'ultima.
`(linea vuota)` — Riga vuota per separare.
`      if (isLastCell) {` — Se e' ultima cella, verifica che sia 0.
`        return grid[r][c] === 0;` — Ritorna true/false per ultima cella.
`      }` — Chiude if ultima cella.
`(linea vuota)` — Riga vuota per separare.
`      if (grid[r][c] !== expected) {` — Se il valore non coincide con expected, ritorna false.
`        return false;` — Ritorna false se ordine errato.
`      }` — Chiude if ordine errato.
`(linea vuota)` — Riga vuota per separare.
`      expected += 1;` — Incrementa expected.
`    }` — Chiude il ciclo colonne.
`  }` — Chiude il ciclo righe.
`(linea vuota)` — Riga vuota per separare.
`  return false;` — Ritorna false se non risolto (safety).
`}` — Chiude isGridSolved.

<a id="src-features-puzzle-hooks-useplayback-ts"></a>
## src/features/puzzle/hooks/usePlayback.ts
`"use client";` — Direttiva client per hook React.
`(linea vuota)` — Riga vuota per separare import.
`import { useEffect, useRef } from 'react';` — Importa useEffect e useRef.
`import { useIsPlaying, useSpeed, useTick, usePause } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector e azioni dal store.
`(linea vuota)` — Riga vuota per separare.
`/**` — Inizio commento JSDoc del hook.
` * Hook che gestisce l'autoplay del puzzle.` — Descrive la funzione del hook.
` * Crea un interval che avanza di un passo alla velocitÃ  impostata.` — Spiega l'uso dell'interval con velocita' impostata.
` * Fixed: Ottimizzato per evitare memory leaks con dipendenze corrette.` — Nota sulla prevenzione dei memory leak.
` */` — Chiude il commento.
`export function usePlayback() {` — Definisce il hook usePlayback.
`  const intervalRef = useRef<ReturnType<typeof setInterval> | null>(null);` — Crea un ref per l'interval.
`  const isPlaying = useIsPlaying();` — Legge isPlaying dallo store.
`  const speed = useSpeed();` — Legge speed dallo store.
`  const tick = useTick();` — Legge tick dallo store.
`  const pause = usePause();` — Legge pause dallo store.
`(linea vuota)` — Riga vuota per separare.
`  useEffect(() => {` — useEffect per gestire l'interval.
`    if (!isPlaying) {` — Se non in play, entra nel cleanup.
`      if (intervalRef.current) {` — Se esiste interval, lo cancella.
`        clearInterval(intervalRef.current);` — clearInterval dell'interval.
`        intervalRef.current = null;` — Reset del ref a null.
`      }` — Chiude if interval.
`      return;` — Ritorna per evitare setup.
`    }` — Chiude if !isPlaying.
`(linea vuota)` — Riga vuota per separare.
`    intervalRef.current = setInterval(() => {` — Crea interval che chiama tick.
`      const hasMore = tick();` — Chiama tick e salva se ci sono altri step.
`      if (!hasMore) {` — Se finito, chiama pause.
`        pause();` — Chiude if hasMore.
`      }` — Chiude setInterval.
`    }, speed);` — Passa speed come delay.
`(linea vuota)` — Riga vuota per separare.
`    return () => {` — Cleanup dell'effetto.
`      if (intervalRef.current) {` — Se interval attivo, lo cancella.
`        clearInterval(intervalRef.current);` — clearInterval.
`        intervalRef.current = null;` — Reset del ref.
`      }` — Chiude if.
`    };` — Chiude cleanup.
`  }, [isPlaying, speed, tick, pause]);` — Chiude useEffect con dipendenze.
`}` — Chiude il hook usePlayback.

<a id="src-features-puzzle-store-usepuzzlestore-ts"></a>
## src/features/puzzle/store/usePuzzleStore.ts
`import { create } from 'zustand';` — Importa create da zustand per creare lo store.
`import type { ColorPaletteMode, Direction, Grid, GridSize, ThemeMode } from '@/features/puzzle/types/puzzle';` — Importa i tipi usati nello stato.
`import { DEFAULT_GRID_SIZE, DEFAULT_SPEED, PUZZLE_CONFIGS } from '@/features/puzzle/constants/puzzle';` — Importa costanti di configurazione.
`import { computeAllStates, applyMove, isGridSolved } from '@/features/puzzle/utils/puzzle';` — Importa utilita' per stati e mosse.
`import { postSolvePuzzle } from '@/features/puzzle/api/solverApi';` — Importa la funzione API per solve.
`(linea vuota)` — Riga vuota per separare.
`const defaultConfig = PUZZLE_CONFIGS[DEFAULT_GRID_SIZE];` — Seleziona la config di default dal catalogo.
`const { grids: defaultGrids, moves: defaultMoves } = computeAllStates(` — Precalcola griglie e mosse della config di default.
`  defaultConfig.initialGrid,` — Passa la griglia iniziale alla funzione.
`  defaultConfig.moves,` — Passa la sequenza di mosse alla funzione.
`);` — Chiude la chiamata computeAllStates.
`(linea vuota)` — Riga vuota per separare.
`function cloneGrid(grid: Grid): Grid {` — Helper per clonare una griglia.
`  return grid.map(row => [...row]);` — Clona ogni riga con spread.
`}` — Chiude cloneGrid.
`(linea vuota)` — Riga vuota per separare.
`function generateGoalGrid(size: GridSize): Grid {` — Helper per generare lo stato goal.
`  const grid: Grid = [];` — Inizializza la griglia vuota.
`  let value = 1;` — Inizializza il contatore delle tessere.
`  for (let i = 0; i < size; i++) {` — Ciclo sulle righe.
`    const row: number[] = [];` — Inizializza una riga.
`    for (let j = 0; j < size; j++) {` — Ciclo sulle colonne.
`      if (i === size - 1 && j === size - 1) {` — Se e' l'ultima cella, inserisce 0.
`        row.push(0);` — Inserisce 0 nella cella finale.
`      } else {` — Altrimenti, entra nel ramo else.
`        row.push(value++);` — Inserisce il valore e incrementa.
`      }` — Chiude l'else.
`    }` — Chiude il ciclo colonne.
`    grid.push(row);` — Aggiunge la riga alla griglia.
`  }` — Chiude il ciclo righe.
`  return grid;` — Ritorna la griglia goal.
`}` — Chiude generateGoalGrid.
`(linea vuota)` — Riga vuota per separare.
`interface PuzzleState {` — Inizia la definizione dell'interfaccia PuzzleState.
`  // Config corrente` — Commento: sezione config corrente.
`  gridSize: GridSize;` — Stato: gridSize.
`  initialGrid: Grid;` — Stato: initialGrid.
`  solutionMoves: Direction[];` — Stato: solutionMoves.
`(linea vuota)` — Riga vuota per separare.
`  // Dati pre-calcolati` — Commento: dati pre-calcolati.
`  allStates: Grid[];` — Stato: allStates.
`  allMoves: (Direction | null)[];` — Stato: allMoves.
`  totalSteps: number;` — Stato: totalSteps.
`(linea vuota)` — Riga vuota per separare.
`  // ModalitÃ  gioco` — Commento: modalita' di gioco.
`  gameMode: 'play' | 'replay';` — Stato: gameMode.
`  manualGrid: Grid;` — Stato: manualGrid.
`(linea vuota)` — Riga vuota per separare.
`  // Settings globali UI` — Commento: settings UI.
`  themeMode: ThemeMode;` — Stato: themeMode.
`  colorPaletteMode: ColorPaletteMode;` — Stato: colorPaletteMode.
`  musicEnabled: boolean;` — Stato: musicEnabled.
`(linea vuota)` — Riga vuota per separare.
`  // Stato replay/animazione` — Commento: stato replay/animazione.
`  step: number;` — Stato: step.
`  isPlaying: boolean;` — Stato: isPlaying.
`  speed: number;` — Stato: speed.
`  elapsedSeconds: number;` — Stato: elapsedSeconds.
`  timerEnabled: boolean;` — Stato: timerEnabled.
`(linea vuota)` — Riga vuota per separare.
`  // Derivati` — Commento: campi derivati.
`  currentGrid: Grid;` — Stato: currentGrid.
`  prevGrid: Grid | null;` — Stato: prevGrid.
`  currentMove: Direction | null;` — Stato: currentMove.
`  isSolved: boolean;` — Stato: isSolved.
`  error: string | null;` — Stato: error.
`(linea vuota)` — Riga vuota per separare.
`  // Azioni - Replay` — Commento: azioni replay.
`  setStep: (step: number) => void;` — Azione setStep.
`  goNext: () => void;` — Azione goNext.
`  goPrev: () => void;` — Azione goPrev.
`  play: () => void;` — Azione play.
`  pause: () => void;` — Azione pause.
`  togglePlay: () => void;` — Azione togglePlay.
`  setSpeed: (speed: number) => void;` — Azione setSpeed.
`  jumpToStep: (step: number) => void;` — Azione jumpToStep.
`  tick: () => boolean;` — Azione tick.
`(linea vuota)` — Riga vuota per separare.
`  // Azioni - Game Mode` — Commento: azioni game mode.
`  playMove: (direction: Direction) => void;` — Azione playMove.
`  giveUp: () => Promise<void>;` — Azione giveUp.
`  restartGame: () => void;` — Azione restartGame.
`  setGridSize: (size: GridSize) => void;` — Azione setGridSize.
`  tickElapsed: () => void;` — Azione tickElapsed.
`  resetElapsed: () => void;` — Azione resetElapsed.
`  setTimerEnabled: (enabled: boolean) => void;` — Azione setTimerEnabled.
`  toggleTimerEnabled: () => void;` — Azione toggleTimerEnabled.
`  setThemeMode: (mode: ThemeMode) => void;` — Azione setThemeMode.
`  toggleThemeMode: () => void;` — Azione toggleThemeMode.
`  setColorPaletteMode: (mode: ColorPaletteMode) => void;` — Azione setColorPaletteMode.
`  toggleMusicEnabled: () => void;` — Azione toggleMusicEnabled.
`  clearError: () => void;` — Azione clearError.
`(linea vuota)` — Riga vuota per separare.
`  // Custom board flow` — Commento: custom board flow.
`  setCustomBoard: (size: GridSize, grid: Grid) => void;` — Azione setCustomBoard.
`  setGeneratedPuzzle: (size: GridSize, initialGrid: Grid, moves: Direction[]) => Promise<void>;` — Azione setGeneratedPuzzle.
`  setSolutionMovesFromApi: (moves: Direction[]) => void;` — Azione setSolutionMovesFromApi.
`}` — Chiude l'interfaccia PuzzleState.
`(linea vuota)` — Riga vuota per separare.
`export const usePuzzleStore = create<PuzzleState>((set, get) => ({` — Crea lo store Zustand con stato e azioni.
`  // Config corrente` — Commento: config corrente.
`  gridSize: defaultConfig.gridSize,` — Stato iniziale gridSize.
`  initialGrid: cloneGrid(defaultConfig.initialGrid),` — Stato iniziale initialGrid (clonato).
`  solutionMoves: [...defaultConfig.moves],` — Stato iniziale solutionMoves.
`(linea vuota)` — Riga vuota per separare.
`  // Dati pre-calcolati` — Commento: dati pre-calcolati.
`  allStates: defaultGrids,` — Stato iniziale allStates.
`  allMoves: defaultMoves,` — Stato iniziale allMoves.
`  totalSteps: defaultGrids.length - 1,` — Stato iniziale totalSteps.
`(linea vuota)` — Riga vuota per separare.
`  // ModalitÃ  gioco - Inizia in 'play'` — Commento: modalita' di gioco.
`  gameMode: 'play',` — Imposta gameMode su play.
`  manualGrid: cloneGrid(defaultGrids[0]),` — Imposta manualGrid con lo stato iniziale.
`(linea vuota)` — Riga vuota per separare.
`  // Settings globali` — Commento: settings globali.
`  themeMode: 'dark',` — Imposta themeMode su dark.
`  colorPaletteMode: 'default',` — Imposta colorPaletteMode su default.
`  musicEnabled: true,` — Imposta musicEnabled su true.
`(linea vuota)` — Riga vuota per separare.
`  // Stato replay/animazione iniziale` — Commento: replay iniziale.
`  step: 0,` — Imposta step iniziale.
`  isPlaying: false,` — Imposta isPlaying a false.
`  speed: DEFAULT_SPEED,` — Imposta speed con DEFAULT_SPEED.
`  elapsedSeconds: 0,` — Imposta elapsedSeconds a 0.
`  timerEnabled: true,` — Imposta timerEnabled a true.
`(linea vuota)` — Riga vuota per separare.
`  // Derivati` — Commento: campi derivati.
`  currentGrid: cloneGrid(defaultGrids[0]),` — Imposta currentGrid iniziale.
`  prevGrid: null,` — Imposta prevGrid a null.
`  currentMove: null,` — Imposta currentMove a null.
`  isSolved: false,` — Imposta isSolved a false.
`  error: null,` — Imposta error a null.
`(linea vuota)` — Riga vuota per separare.
`  // Azioni - Replay` — Commento: azioni replay.
`  setStep: (step) => {` — Definisce setStep.
`    const { allStates, allMoves, totalSteps, gameMode } = get();` — Legge stato necessario con get().
`    if (gameMode !== 'replay') return;` — Esce se non in replay.
`(linea vuota)` — Riga vuota per separare.
`    const clamped = Math.max(0, Math.min(totalSteps, step));` — Clampa lo step tra 0 e totalSteps.
`    set({` — Aggiorna lo stato con set().
`      step: clamped,` — Aggiorna step.
`      currentGrid: allStates[clamped],` — Aggiorna currentGrid.
`      prevGrid: clamped > 0 ? allStates[clamped - 1] : null,` — Aggiorna prevGrid se step > 0.
`      currentMove: allMoves[clamped],` — Aggiorna currentMove.
`      isSolved: clamped === totalSteps,` — Aggiorna isSolved.
`    });` — Chiude set().
`  },` — Chiude setStep.
`(linea vuota)` — Riga vuota per separare.
`  goNext: () => {` — Definisce goNext.
`    const { step, totalSteps, gameMode } = get();` — Legge step, totalSteps e gameMode.
`    if (gameMode !== 'replay' || step >= totalSteps) return;` — Se non in replay o a fine, esce.
`    get().setStep(step + 1);` — Avanza di uno step.
`  },` — Chiude goNext.
`(linea vuota)` — Riga vuota per separare.
`  goPrev: () => {` — Definisce goPrev.
`    const { step, gameMode } = get();` — Legge step e gameMode.
`    if (gameMode !== 'replay' || step <= 0) return;` — Esce se non replay o gia' a 0.
`    get().setStep(step - 1);` — Torna indietro di uno step.
`  },` — Chiude goPrev.
`(linea vuota)` — Riga vuota per separare.
`  play: () => {` — Definisce play.
`    const { step, totalSteps, gameMode } = get();` — Legge step, totalSteps e gameMode.
`    if (gameMode !== 'replay') return;` — Esce se non replay.
`    if (step >= totalSteps) get().setStep(0);` — Se a fine, resetta step a 0.
`    set({ isPlaying: true });` — Imposta isPlaying a true.
`  },` — Chiude play.
`(linea vuota)` — Riga vuota per separare.
`  pause: () => {` — Definisce pause.
`    set({ isPlaying: false });` — Imposta isPlaying a false.
`  },` — Chiude pause.
`(linea vuota)` — Riga vuota per separare.
`  togglePlay: () => {` — Definisce togglePlay.
`    const { isPlaying, gameMode } = get();` — Legge isPlaying e gameMode.
`    if (gameMode !== 'replay') return;` — Esce se non replay.
`    if (isPlaying) get().pause();` — Se in play, chiama pause.
`    else get().play();` — Altrimenti, chiama play.
`  },` — Chiude togglePlay.
`(linea vuota)` — Riga vuota per separare.
`  setSpeed: (speed) => set({ speed }),` — Definisce setSpeed con setter diretto.
`(linea vuota)` — Riga vuota per separare.
`  jumpToStep: (step) => {` — Definisce jumpToStep.
`    const { gameMode } = get();` — Legge gameMode.
`    if (gameMode !== 'replay') return;` — Esce se non replay.
`    get().pause();` — Mette in pausa.
`    get().setStep(step);` — Imposta lo step richiesto.
`  },` — Chiude jumpToStep.
`(linea vuota)` — Riga vuota per separare.
`  tick: () => {` — Definisce tick per autoplay.
`    const { step, totalSteps, gameMode } = get();` — Legge step, totalSteps e gameMode.
`    if (gameMode !== 'replay' || step >= totalSteps) {` — Se non replay o finito, entra nel blocco.
`      get().pause();` — Mette in pausa.
`      return false;` — Ritorna false (nessun passo).
`    }` — Chiude if.
`    get().setStep(step + 1);` — Avanza di uno step.
`    return true;` — Ritorna true (ha avanzato).
`  },` — Chiude tick.
`(linea vuota)` — Riga vuota per separare.
`  // Azioni - Game Mode` — Commento: azioni game mode.
`  playMove: (direction: Direction) => {` — Definisce playMove.
`    const { gameMode, manualGrid, isSolved } = get();` — Legge gameMode, manualGrid e isSolved.
`    if (gameMode !== 'play' || isSolved) return;` — Esce se non play o gia' risolto.
`(linea vuota)` — Riga vuota per separare.
`    try {` — Inizia try per applicare la mossa.
`      const newGrid = applyMove(manualGrid, direction);` — Calcola newGrid applicando la mossa.
`      set({` — Aggiorna stato con set().
`        manualGrid: newGrid,` — Imposta manualGrid.
`        isSolved: isGridSolved(newGrid),` — Aggiorna isSolved con check.
`      });` — Chiude set().
`    } catch (error) {` — Passa al catch.
`      console.warn('Invalid move:', error);` — Logga mossa non valida.
`    }` — Chiude try/catch.
`  },` — Chiude playMove.
`(linea vuota)` — Riga vuota per separare.
`  giveUp: async () => {` — Definisce giveUp async.
`    const { manualGrid } = get();` — Legge manualGrid.
`    ` — Riga vuota per separare.
`    try {` — Inizia try.
`      // Chiama l'API per ottenere la soluzione del puzzle corrente` — Commento: richiesta solve al backend.
`      const solutionMoves = await postSolvePuzzle(manualGrid);` — Chiama postSolvePuzzle.
`      ` — Riga vuota per separare.
`      // Genera gli stati dal puzzle mischiato (inizio) al goal (fine)` — Commento: genera gli stati del replay.
`      const { grids, moves: allMoves } = computeAllStates(manualGrid, solutionMoves);` — Calcola allStates e allMoves.
`      ` — Riga vuota per separare.
`      set({` — Aggiorna lo store con i dati di replay.
`        gameMode: 'replay',` — Imposta gameMode su replay.
`        initialGrid: cloneGrid(manualGrid),` — Imposta initialGrid.
`        step: 0,` — Reset step.
`        currentGrid: grids[0],` — Imposta currentGrid.
`        prevGrid: null,` — Imposta prevGrid a null.
`        currentMove: allMoves[0],` — Imposta currentMove iniziale.
`        isPlaying: true,` — Avvia isPlaying.
`        allStates: grids,` — Salva allStates.
`        allMoves,` — Salva allMoves.
`        totalSteps: grids.length - 1,` — Imposta totalSteps.
`        error: null,` — Resetta error.
`      });` — Chiude set().
`    } catch (error) {` — Passa al catch.
`      console.error('Errore nel risolvere il puzzle:', error);` — Logga errore di solve.
`      ` — Riga vuota per separare.
`      // Estrai il messaggio di errore` — Commento: costruzione messaggio errore.
`      let errorMessage = 'Errore nella risoluzione del puzzle. Riprova!';` — Imposta messaggio di default.
`      if (error instanceof Error) {` — Se error e' Error, entra.
`        if (error.message.includes('500') || error.message.includes('not solvable')) {` — Se messaggio contiene 500 o not solvable, personalizza.
`          errorMessage = 'âŒ Puzzle non risolvibile\n\nIl puzzle attuale non puÃ² essere risolto. Prova a fare altre mosse o ricarica il gioco!';` — Messaggio per puzzle non risolvibile.
`        }` — Chiude if interno.
`      }` — Chiude if esterno.
`(linea vuota)` — Riga vuota per separare.
`      set({` — Aggiorna lo stato error.
`        error: errorMessage,` — Imposta error nel set.
`      });` — Chiude set().
`    }` — Chiude catch.
`  },` — Chiude giveUp.
`(linea vuota)` — Riga vuota per separare.
`  restartGame: () => {` — Definisce restartGame.
`    const { allStates } = get();` — Legge allStates.
`    // allStates[0] = goal, allStates[last] = puzzle mischiato` — Commento: note sui contenuti di allStates.
`    const puzzleStart = allStates[allStates.length - 1] || allStates[0];` — Calcola puzzleStart dall'ultimo stato.
`    set({` — Aggiorna lo stato per tornare a play.
`      gameMode: 'play',` — Imposta gameMode play.
`      manualGrid: cloneGrid(puzzleStart),` — Imposta manualGrid al puzzleStart.
`      step: 0,` — Reset step.
`      isPlaying: false,` — Ferma isPlaying.
`      currentGrid: cloneGrid(puzzleStart),` — Imposta currentGrid.
`      prevGrid: null,` — Imposta prevGrid a null.
`      currentMove: null,` — Imposta currentMove a null.
`      isSolved: false,` — Imposta isSolved a false.
`      elapsedSeconds: 0,` — Reset elapsedSeconds.
`    });` — Chiude set().
`  },` — Chiude restartGame.
`(linea vuota)` — Riga vuota per separare.
`  setGridSize: (size: GridSize) => {` — Definisce setGridSize.
`    const config = PUZZLE_CONFIGS[size];` — Seleziona la config per size.
`    const { grids, moves } = computeAllStates(config.initialGrid, config.moves);` — Calcola griglie e mosse per la config.
`(linea vuota)` — Riga vuota per separare.
`    set({` — Aggiorna lo stato con la nuova config.
`      gridSize: size,` — Imposta gridSize.
`      initialGrid: cloneGrid(config.initialGrid),` — Imposta initialGrid.
`      solutionMoves: [...config.moves],` — Imposta solutionMoves.
`      allStates: grids,` — Imposta allStates.
`      allMoves: moves,` — Imposta allMoves.
`      totalSteps: grids.length - 1,` — Imposta totalSteps.
`      gameMode: 'play',` — Imposta gameMode play.
`      manualGrid: cloneGrid(grids[0]),` — Imposta manualGrid.
`      step: 0,` — Reset step.
`      isPlaying: false,` — Ferma isPlaying.
`      currentGrid: cloneGrid(grids[0]),` — Imposta currentGrid.
`      prevGrid: null,` — Imposta prevGrid a null.
`      currentMove: null,` — Imposta currentMove a null.
`      isSolved: false,` — Imposta isSolved a false.
`      elapsedSeconds: 0,` — Reset elapsedSeconds.
`    });` — Chiude set().
`  },` — Chiude setGridSize.
`(linea vuota)` — Riga vuota per separare.
`  tickElapsed: () => {` — Definisce tickElapsed.
`    const { gameMode, isSolved } = get();` — Legge gameMode e isSolved.
`    if (gameMode !== 'play' || isSolved) return;` — Esce se non play o risolto.
`    set(state => ({ elapsedSeconds: state.elapsedSeconds + 1 }));` — Incrementa elapsedSeconds.
`  },` — Chiude tickElapsed.
`(linea vuota)` — Riga vuota per separare.
`  resetElapsed: () => {` — Definisce resetElapsed.
`    set({ elapsedSeconds: 0 });` — Resetta elapsedSeconds a 0.
`  },` — Chiude resetElapsed.
`(linea vuota)` — Riga vuota per separare.
`  setTimerEnabled: (enabled: boolean) => {` — Definisce setTimerEnabled.
`    set({ timerEnabled: enabled });` — Aggiorna timerEnabled.
`  },` — Chiude setTimerEnabled.
`(linea vuota)` — Riga vuota per separare.
`  toggleTimerEnabled: () => {` — Definisce toggleTimerEnabled.
`    set(state => ({ timerEnabled: !state.timerEnabled }));` — Inverte timerEnabled.
`  },` — Chiude toggleTimerEnabled.
`(linea vuota)` — Riga vuota per separare.
`  setThemeMode: (mode: ThemeMode) => {` — Definisce setThemeMode.
`    set({ themeMode: mode });` — Aggiorna themeMode.
`  },` — Chiude setThemeMode.
`(linea vuota)` — Riga vuota per separare.
`  toggleThemeMode: () => {` — Definisce toggleThemeMode.
`    set(state => ({ themeMode: state.themeMode === 'dark' ? 'light' : 'dark' }));` — Inverte themeMode tra dark e light.
`  },` — Chiude toggleThemeMode.
`(linea vuota)` — Riga vuota per separare.
`  setColorPaletteMode: (mode: ColorPaletteMode) => {` — Definisce setColorPaletteMode.
`    set({ colorPaletteMode: mode });` — Aggiorna colorPaletteMode.
`  },` — Chiude setColorPaletteMode.
`(linea vuota)` — Riga vuota per separare.
`  toggleMusicEnabled: () => {` — Definisce toggleMusicEnabled.
`    set(state => ({ musicEnabled: !state.musicEnabled }));` — Inverte musicEnabled.
`  },` — Chiude toggleMusicEnabled.
`(linea vuota)` — Riga vuota per separare.
`  clearError: () => {` — Definisce clearError.
`    set({ error: null });` — Resetta error a null.
`  },` — Chiude clearError.
`(linea vuota)` — Riga vuota per separare.
`  setCustomBoard: (size: GridSize, grid: Grid) => {` — Definisce setCustomBoard.
`    const customGrid = cloneGrid(grid);` — Clona la griglia custom.
`    set({` — Aggiorna lo stato con la griglia custom.
`      gridSize: size,` — Imposta gridSize.
`      initialGrid: customGrid,` — Imposta initialGrid.
`      solutionMoves: [],` — Resetta solutionMoves.
`      allStates: [customGrid],` — Imposta allStates con sola griglia.
`      allMoves: [null],` — Imposta allMoves con null.
`      totalSteps: 0,` — Imposta totalSteps a 0.
`      gameMode: 'play',` — Imposta gameMode play.
`      manualGrid: cloneGrid(customGrid),` — Imposta manualGrid.
`      step: 0,` — Reset step.
`      isPlaying: false,` — Ferma isPlaying.
`      currentGrid: cloneGrid(customGrid),` — Imposta currentGrid.
`      prevGrid: null,` — Imposta prevGrid a null.
`      currentMove: null,` — Imposta currentMove a null.
`      isSolved: isGridSolved(customGrid),` — Imposta isSolved in base alla griglia.
`      elapsedSeconds: 0,` — Reset elapsedSeconds.
`    });` — Chiude set().
`  },` — Chiude setCustomBoard.
`(linea vuota)` — Riga vuota per separare.
`  setGeneratedPuzzle: async (size: GridSize, initialGrid: Grid, moves: Direction[]) => {` — Definisce setGeneratedPuzzle async.
`    // initialGrid dal backend Ã¨ il puzzle mischiato` — Commento: initialGrid e' il puzzle mischiato.
`    const scrambledGrid = cloneGrid(initialGrid);` — Clona la griglia mischiata.
`    ` — Riga vuota per separare.
`    // Genera lo stato goal per il playback` — Commento: genera stato goal per fallback.
`    const goalGrid = generateGoalGrid(size);` — Crea la griglia goal.
`(linea vuota)` — Riga vuota per separare.
`    try {` — Inizia try.
`      // Chiama l'API per ottenere le mosse di soluzione` — Commento: chiama API solve.
`      const solutionMoves = await postSolvePuzzle(scrambledGrid);` — Richiede le mosse soluzione.
`      ` — Riga vuota per separare.
`      // Genera gli stati dal puzzle mischiato (inizio) al goal (fine)` — Commento: genera stati dal puzzle mischiato.
`      const { grids, moves: allMoves } = computeAllStates(scrambledGrid, solutionMoves);` — Calcola griglie e mosse del replay.
`(linea vuota)` — Riga vuota per separare.
`      set({` — Aggiorna lo stato con i dati generati.
`        gridSize: size,` — Imposta gridSize.
`        initialGrid: cloneGrid(scrambledGrid),` — Imposta initialGrid.
`        solutionMoves: solutionMoves,` — Imposta solutionMoves.
`        allStates: grids,` — Imposta allStates.
`        allMoves,` — Imposta allMoves.
`        totalSteps: grids.length - 1,` — Imposta totalSteps.
`        gameMode: 'play',` — Imposta gameMode play.
`        manualGrid: cloneGrid(scrambledGrid),` — Imposta manualGrid.
`        step: 0,` — Reset step.
`        isPlaying: false,` — Ferma isPlaying.
`        currentGrid: cloneGrid(scrambledGrid),` — Imposta currentGrid.
`        prevGrid: null,` — Imposta prevGrid a null.
`        currentMove: null,` — Imposta currentMove a null.
`        isSolved: false,` — Imposta isSolved a false.
`        elapsedSeconds: 0,` — Reset elapsedSeconds.
`        error: null,` — Resetta error.
`      });` — Chiude set().
`    } catch (error) {` — Passa al catch.
`      console.warn('Puzzle irrisolvibile o errore API. Gioco senza playback animato.', error);` — Logga warning su puzzle non risolvibile.
`      ` — Riga vuota per separare.
`      // Estrai il messaggio di errore` — Commento: costruzione messaggio errore.
`      let errorMessage = 'Il puzzle generato non Ã¨ risolvibile. Riprova!';` — Messaggio di default per puzzle non risolvibile.
`      if (error instanceof Error) {` — Se error e' Error, entra.
`        if (error.message.includes('500') || error.message.includes('not solvable')) {` — Se messaggio contiene 500 o not solvable, personalizza.
`          errorMessage = 'âŒ Puzzle non risolvibile\n\nIl backend ha generato un puzzle che non puÃ² essere risolto. Per favore riprova!';` — Messaggio specifico per errore backend.
`        }` — Chiude if interno.
`      }` — Chiude if esterno.
`(linea vuota)` — Riga vuota per separare.
`      set({` — Aggiorna lo stato con fallback locale.
`        gridSize: size,` — Imposta gridSize.
`        initialGrid: cloneGrid(scrambledGrid),` — Imposta initialGrid.
`        solutionMoves: [],` — Resetta solutionMoves.
`        allStates: [scrambledGrid, goalGrid],` — Imposta allStates con scrambled e goal.
`        allMoves: [null, null],` — Imposta allMoves con null.
`        totalSteps: 1,` — Imposta totalSteps a 1.
`        gameMode: 'play',` — Imposta gameMode play.
`        manualGrid: cloneGrid(scrambledGrid),` — Imposta manualGrid.
`        step: 0,` — Reset step.
`        isPlaying: false,` — Ferma isPlaying.
`        currentGrid: cloneGrid(scrambledGrid),` — Imposta currentGrid.
`        prevGrid: null,` — Imposta prevGrid a null.
`        currentMove: null,` — Imposta currentMove a null.
`        isSolved: false,` — Imposta isSolved a false.
`        elapsedSeconds: 0,` — Reset elapsedSeconds.
`        error: errorMessage,` — Imposta error con errorMessage.
`      });` — Chiude set().
`    }` — Chiude catch.
`  },` — Chiude setGeneratedPuzzle.
`(linea vuota)` — Riga vuota per separare.
`  setSolutionMovesFromApi: (moves: Direction[]) => {` — Definisce setSolutionMovesFromApi.
`    const { initialGrid } = get();` — Legge initialGrid dallo store.
`    const { grids, moves: allMoves } = computeAllStates(initialGrid, moves);` — Calcola griglie e mosse dalla soluzione.
`(linea vuota)` — Riga vuota per separare.
`    set({` — Aggiorna lo stato per la modalita' replay.
`      solutionMoves: [...moves],` — Imposta solutionMoves.
`      allStates: grids,` — Imposta allStates.
`      allMoves,` — Imposta allMoves.
`      totalSteps: grids.length - 1,` — Imposta totalSteps.
`      gameMode: 'replay',` — Imposta gameMode replay.
`      step: 0,` — Reset step.
`      isPlaying: false,` — Ferma isPlaying.
`      currentGrid: grids[0],` — Imposta currentGrid.
`      prevGrid: null,` — Imposta prevGrid a null.
`      currentMove: allMoves[0],` — Imposta currentMove.
`      isSolved: false,` — Imposta isSolved a false.
`    });` — Chiude set().
`  },` — Chiude setSolutionMovesFromApi.
`}));` — Chiude create(...) e lo store.
`(linea vuota)` — Riga vuota per separare.
`// Usabile anche fuori da React, ad esempio nelle API request builders.` — Commento: uso fuori da React.
`export const getGridSizeForApi = (): GridSize => usePuzzleStore.getState().gridSize;` — Esporta helper per leggere gridSize dallo store.

<a id="src-features-puzzle-store-puzzleselectors-ts"></a>
## src/features/puzzle/store/puzzleSelectors.ts
`/**` — Inizio commento JSDoc sui selector tipizzati.
` * Type-safe selectors for usePuzzleStore` — Spiega l'obiettivo dei selector.
` * Centralizes all Zustand selectors to avoid inline type casting` — Nota sulla centralizzazione dei selector.
` */` — Chiude commento.
`import { usePuzzleStore } from './usePuzzleStore';` — Importa usePuzzleStore.
`(linea vuota)` — Riga vuota per separare.
`// State selectors` — Commento: selector di stato.
`export const useGridSize = () => usePuzzleStore(s => s.gridSize);` — Selector per gridSize.
`export const useInitialGrid = () => usePuzzleStore(s => s.initialGrid);` — Selector per initialGrid.
`export const useSolutionMoves = () => usePuzzleStore(s => s.solutionMoves);` — Selector per solutionMoves.
`export const useAllStates = () => usePuzzleStore(s => s.allStates);` — Selector per allStates.
`export const useAllMoves = () => usePuzzleStore(s => s.allMoves);` — Selector per allMoves.
`export const useTotalSteps = () => usePuzzleStore(s => s.totalSteps);` — Selector per totalSteps.
`export const useStep = () => usePuzzleStore(s => s.step);` — Selector per step.
`export const useIsPlaying = () => usePuzzleStore(s => s.isPlaying);` — Selector per isPlaying.
`export const useSpeed = () => usePuzzleStore(s => s.speed);` — Selector per speed.
`export const useElapsedSeconds = () => usePuzzleStore(s => s.elapsedSeconds);` — Selector per elapsedSeconds.
`export const useTimerEnabled = () => usePuzzleStore(s => s.timerEnabled);` — Selector per timerEnabled.
`export const useThemeMode = () => usePuzzleStore(s => s.themeMode);` — Selector per themeMode.
`export const useColorPaletteMode = () => usePuzzleStore(s => s.colorPaletteMode);` — Selector per colorPaletteMode.
`export const useMusicEnabled = () => usePuzzleStore(s => s.musicEnabled);` — Selector per musicEnabled.
`export const useCurrentGrid = () => usePuzzleStore(s => s.currentGrid);` — Selector per currentGrid.
`export const usePrevGrid = () => usePuzzleStore(s => s.prevGrid);` — Selector per prevGrid.
`export const useCurrentMove = () => usePuzzleStore(s => s.currentMove);` — Selector per currentMove.
`export const useIsSolved = () => usePuzzleStore(s => s.isSolved);` — Selector per isSolved.
`export const useError = () => usePuzzleStore(s => s.error);` — Selector per error.
`(linea vuota)` — Riga vuota per separare.
`// Game mode selectors` — Commento: selector per game mode.
`export const useGameMode = () => usePuzzleStore(s => s.gameMode);` — Selector per gameMode.
`export const useManualGrid = () => usePuzzleStore(s => s.manualGrid);` — Selector per manualGrid.
`(linea vuota)` — Riga vuota per separare.
`// Replay action selectors` — Commento: selector per azioni replay.
`export const useSetStep = () => usePuzzleStore(s => s.setStep);` — Selector setStep.
`export const useGoNext = () => usePuzzleStore(s => s.goNext);` — Selector goNext.
`export const useGoPrev = () => usePuzzleStore(s => s.goPrev);` — Selector goPrev.
`export const usePlay = () => usePuzzleStore(s => s.play);` — Selector play.
`export const usePause = () => usePuzzleStore(s => s.pause);` — Selector pause.
`export const useTogglePlay = () => usePuzzleStore(s => s.togglePlay);` — Selector togglePlay.
`export const useSetSpeed = () => usePuzzleStore(s => s.setSpeed);` — Selector setSpeed.
`export const useJumpToStep = () => usePuzzleStore(s => s.jumpToStep);` — Selector jumpToStep.
`export const useTick = () => usePuzzleStore(s => s.tick);` — Selector tick.
`(linea vuota)` — Riga vuota per separare.
`// Game mode action selectors` — Commento: selector per azioni game mode.
`export const usePlayMove = () => usePuzzleStore(s => s.playMove);` — Selector playMove.
`export const useGiveUp = () => usePuzzleStore(s => s.giveUp);` — Selector giveUp.
`export const useRestartGame = () => usePuzzleStore(s => s.restartGame);` — Selector restartGame.
`export const useSetGridSize = () => usePuzzleStore(s => s.setGridSize);` — Selector setGridSize.
`export const useTickElapsed = () => usePuzzleStore(s => s.tickElapsed);` — Selector tickElapsed.
`export const useResetElapsed = () => usePuzzleStore(s => s.resetElapsed);` — Selector resetElapsed.
`export const useSetTimerEnabled = () => usePuzzleStore(s => s.setTimerEnabled);` — Selector setTimerEnabled.
`export const useToggleTimerEnabled = () => usePuzzleStore(s => s.toggleTimerEnabled);` — Selector toggleTimerEnabled.
`export const useSetThemeMode = () => usePuzzleStore(s => s.setThemeMode);` — Selector setThemeMode.
`export const useToggleThemeMode = () => usePuzzleStore(s => s.toggleThemeMode);` — Selector toggleThemeMode.
`export const useSetColorPaletteMode = () => usePuzzleStore(s => s.setColorPaletteMode);` — Selector setColorPaletteMode.
`export const useToggleMusicEnabled = () => usePuzzleStore(s => s.toggleMusicEnabled);` — Selector toggleMusicEnabled.
`export const useClearError = () => usePuzzleStore(s => s.clearError);` — Selector clearError.
`export const useSetCustomBoard = () => usePuzzleStore(s => s.setCustomBoard);` — Selector setCustomBoard.
`export const useSetGeneratedPuzzle = () => usePuzzleStore(s => s.setGeneratedPuzzle);` — Selector setGeneratedPuzzle.
`export const useSetSolutionMovesFromApi = () => usePuzzleStore(s => s.setSolutionMovesFromApi);` — Selector setSolutionMovesFromApi.

<a id="src-features-puzzle-api-solverapi-ts"></a>
## src/features/puzzle/api/solverApi.ts
`import type { Direction, Grid, GridSize, PuzzleConfig } from '@/features/puzzle/types/puzzle';` — Importa tipi per le API.
`const BACKEND_API_URL: string = process.env.BACKEND_API_URL || 'http://localhost:8080/puzzle';` — Definisce BACKEND_API_URL da env o default localhost.
`(linea vuota)` — Riga vuota per separare.
`export class ApiConnectionError extends Error {` — Definisce l'errore ApiConnectionError.
`  constructor(url: string, cause?: unknown) {` — Costruttore con url e causa opzionale.
`    super(``Cannot reach the server at "${url}". Check that the backend is running and the address is correct.``);` — Messaggio di errore per mancata connessione.
`    this.name = 'ApiConnectionError';` — Imposta il nome della classe errore.
`    this.cause = cause;` — Salva la causa.
`  }` — Chiude il costruttore.
`}` — Chiude la classe ApiConnectionError.
`(linea vuota)` — Riga vuota per separare.
`export class ApiTimeoutError extends Error {` — Definisce l'errore ApiTimeoutError.
`  constructor(url: string) {` — Costruttore con url.
`    super(``Request to "${url}" timed out. The server may be overloaded or unreachable.``);` — Messaggio di errore per timeout.
`    this.name = 'ApiTimeoutError';` — Imposta il nome della classe errore.
`  }` — Chiude il costruttore.
`}` — Chiude ApiTimeoutError.
`(linea vuota)` — Riga vuota per separare.
`export class ApiHttpError extends Error {` — Definisce l'errore ApiHttpError.
`  constructor(public readonly status: number, endpoint: string) {` — Costruttore con status ed endpoint.
`    super(``Server returned HTTP ${status} for "${endpoint}".``);` — Messaggio di errore con status.
`    this.name = 'ApiHttpError';` — Imposta il nome della classe errore.
`  }` — Chiude il costruttore.
`}` — Chiude ApiHttpError.
`(linea vuota)` — Riga vuota per separare.
`export class ApiPayloadError extends Error {` — Definisce l'errore ApiPayloadError.
`  constructor(endpoint: string) {` — Costruttore con endpoint.
`    super(``Unexpected response payload from "${endpoint}". The API contract may have changed.``);` — Messaggio per payload inatteso.
`    this.name = 'ApiPayloadError';` — Imposta il nome della classe errore.
`  }` — Chiude il costruttore.
`}` — Chiude ApiPayloadError.
`(linea vuota)` — Riga vuota per separare.
`function handleFetchError(error: unknown, url: string): never {` — Funzione handleFetchError normalizza gli errori.
`  if (error instanceof ApiConnectionError || error instanceof ApiTimeoutError || error instanceof ApiHttpError || error instanceof ApiPayloadError) {` — Rilancia errori gia' tipizzati.
`    throw error;` — Rilancia l'errore tipizzato.
`  }` — Chiude l'if dei tipizzati.
`  if (error instanceof DOMException && error.name === 'AbortError') {` — Gestisce AbortError come timeout.
`    throw new ApiTimeoutError(url);` — Lancia ApiTimeoutError.
`  }` — Chiude if AbortError.
`  // TypeError from fetch means a network/address-level failure (DNS, ECONNREFUSED, etc.)` — Commento: TypeError indica errore di rete.
`  if (error instanceof TypeError) {` — Gestisce TypeError come connessione fallita.
`    throw new ApiConnectionError(url, error);` — Lancia ApiConnectionError.
`  }` — Chiude if TypeError.
`  throw new Error(``Unexpected error: ${String(error)}``);` — Lancia errore generico se non riconosciuto.
`}` — Chiude handleFetchError.
`(linea vuota)` — Riga vuota per separare.
`function isGrid(value: unknown): value is Grid {` — Type guard per verificare una griglia.
`  return Array.isArray(value) && value.every((row) => Array.isArray(row) && row.every((cell) => typeof cell === 'number'));` — Controlla array di array di numeri.
`}` — Chiude isGrid.
`(linea vuota)` — Riga vuota per separare.
`function isMoves(value: unknown): value is Direction[] {` — Type guard per verificare la lista mosse.
`  return Array.isArray(value) && value.every((move) => typeof move === 'string');` — Controlla array di stringhe.
`}` — Chiude isMoves (non usata nel file).
`(linea vuota)` — Riga vuota per separare.
`(linea vuota)` — Riga vuota (spaziatura).
`export async function postSolvePuzzle(currentGrid: Grid): Promise<Direction[]> {` — Definisce postSolvePuzzle.
`  const endpoint = BACKEND_API_URL + '/solve';` — Costruisce endpoint /solve.
`  const controller = new AbortController();` — Crea AbortController.
`  const timeoutId = setTimeout(() => controller.abort(), 20000);` — Imposta timeout a 20s.
`(linea vuota)` — Riga vuota per separare.
`  try {` — Inizia try di fetch.
`    const response = await fetch(endpoint, {` — Esegue fetch POST verso /solve.
`      method: 'POST',` — Metodo POST.
`      headers: { 'Content-Type': 'application/json' },` — Header JSON.
`      body: JSON.stringify({ "grid": currentGrid }),` — Body con griglia corrente.
`      signal: controller.signal,` — Passa il signal per abort.
`    });` — Chiude fetch.
`(linea vuota)` — Riga vuota per separare.
`    if (!response.ok) {` — Se response non ok, lancia ApiHttpError.
`      throw new ApiHttpError(response.status, endpoint);` — Lancia l'errore con status.
`    }` — Chiude if.
`(linea vuota)` — Riga vuota per separare.
`    const payload = await response.json();` — Parse JSON della risposta.
`    return payload?.moves || [];` — Ritorna moves o array vuoto.
`  } catch (error) {` — Catch degli errori.
`    handleFetchError(error, endpoint);` — Normalizza e rilancia l'errore.
`  } finally {` — Finally: cleanup.
`    clearTimeout(timeoutId);` — Cancella il timeout.
`  }` — Chiude finally.
`}` — Chiude postSolvePuzzle.
`(linea vuota)` — Riga vuota per separare.
`export async function postGeneratePuzzle(gridSize: GridSize): Promise<PuzzleConfig> {` — Definisce postGeneratePuzzle.
`  const endpoint = BACKEND_API_URL + '/generate';` — Costruisce endpoint /generate.
`  const controller = new AbortController();` — Crea AbortController.
`  const timeoutId = setTimeout(() => controller.abort(), 20000);` — Imposta timeout a 20s.
`(linea vuota)` — Riga vuota per separare.
`  try {` — Inizia try di fetch.
`    const response = await fetch(endpoint, {` — Esegue fetch POST verso /generate.
`      method: 'POST',` — Metodo POST.
`      headers: { 'Content-Type': 'application/json' },` — Header JSON.
`      body: JSON.stringify({"size": gridSize }),` — Body con size.
`      signal: controller.signal,` — Passa signal per abort.
`    });` — Chiude fetch.
`(linea vuota)` — Riga vuota per separare.
`    if (!response.ok) {` — Se response non ok, lancia ApiHttpError.
`      throw new ApiHttpError(response.status, endpoint);` — Lancia errore con status.
`    }` — Chiude if.
`(linea vuota)` — Riga vuota per separare.
`    const payload = await response.json();` — Parse JSON della risposta.
`    const grid = payload?.grid;` — Estrae la griglia dal payload.
`(linea vuota)` — Riga vuota per separare.
`    if (!isGrid(grid)) {` — Se griglia non valida, lancia ApiPayloadError.
`      throw new ApiPayloadError(endpoint);` — Lancia ApiPayloadError.
`    }` — Chiude if.
`(linea vuota)` — Riga vuota per separare.
`    return {` — Ritorna il PuzzleConfig con griglia e mosse vuote.
`      gridSize,` — Inserisce gridSize.
`      initialGrid: grid,` — Inserisce initialGrid.
`      moves: [],` — Inserisce moves vuote.
`    };` — Chiude return.
`  } catch (error) {` — Catch degli errori.
`    handleFetchError(error, endpoint);` — Normalizza e rilancia.
`  } finally {` — Finally: cleanup.
`    clearTimeout(timeoutId);` — Cancella il timeout.
`  }` — Chiude finally.
`}` — Chiude postGeneratePuzzle.

<a id="src-features-puzzle-components-gameclient-tsx"></a>
## src/features/puzzle/components/GameClient.tsx
`"use client";` — Direttiva client per componente React.
`(linea vuota)` — Riga vuota per separare.
`import { usePlayback } from '@/features/puzzle/hooks/usePlayback';` — Importa il hook usePlayback.
`import Header from '@/features/puzzle/components/Header';` — Importa Header.
`import MoveInfo from '@/features/puzzle/components/MoveInfo';` — Importa MoveInfo.
`import PuzzleGrid from '@/features/puzzle/components/PuzzleGrid';` — Importa PuzzleGrid.
`import Controls from '@/features/puzzle/components/Controls';` — Importa Controls.
`import SpeedControl from '@/features/puzzle/components/SpeedControl';` — Importa SpeedControl.
`import ProgressBar from '@/features/puzzle/components/ProgressBar';` — Importa ProgressBar.
`import MoveList from '@/features/puzzle/components/MoveList';` — Importa MoveList.
`import StatesPreview from '@/features/puzzle/components/StatesPreview';` — Importa StatesPreview.
`import Footer from '@/features/puzzle/components/Footer';` — Importa Footer.
`import GamePlayNavbar from '@/features/puzzle/components/GamePlayNavbar';` — Importa GamePlayNavbar.
`import { useGameMode, useThemeMode, useError, useClearError } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per gameMode, tema ed errori.
`(linea vuota)` — Riga vuota per separare.
`export default function App() {` — Definisce il componente App (GameClient).
`  // Attiva il loop di autoplay` — Commento: attiva autoplay.
`  usePlayback();` — Chiama usePlayback.
`  const gameMode = useGameMode();` — Legge gameMode dallo store.
`  const themeMode = useThemeMode();` — Legge themeMode dallo store.
`  const error = useError();` — Legge error dallo store.
`  const clearError = useClearError();` — Legge clearError dallo store.
`(linea vuota)` — Riga vuota per separare.
`  const rootThemeClass =` — Definisce classi tema per root.
`    themeMode === 'dark'` — Condizione per tema dark.
`      ? 'bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 text-white'` — Classi per tema scuro.
`      : 'bg-gradient-to-br from-slate-100 via-sky-100 to-blue-50 text-slate-900';` — Classi per tema chiaro.
`(linea vuota)` — Riga vuota per separare.
`  const panelThemeClass =` — Definisce classi tema per pannelli.
`    themeMode === 'dark'` — Condizione per tema dark.
`      ? 'border-gray-700/60 bg-slate-900/80 shadow-xl shadow-black/30'` — Classi pannello scuro.
`      : 'border-slate-300/80 bg-white/85 shadow-xl shadow-sky-200/40';` — Classi pannello chiaro.
`(linea vuota)` — Riga vuota per separare.
`  const hintThemeClass =` — Definisce classi per hint a sinistra.
`    themeMode === 'dark'` — Condizione per tema dark.
`      ? 'border-gray-700/60 bg-gray-900/60 text-gray-300'` — Classi hint scuro.
`      : 'border-slate-300/80 bg-slate-50/90 text-slate-700';` — Classi hint chiaro.
`(linea vuota)` — Riga vuota per separare.
`  const replayHintThemeClass = themeMode === 'dark' ? 'text-gray-400' : 'text-slate-500';` — Classe per hint replay.
`(linea vuota)` — Sceglie colore in base al tema.
`  return (` — Riga vuota per separare.
`    <div className={``w-full h-full flex flex-col overflow-hidden ${rootThemeClass}``}>` — Inizia il return del JSX.
`      <div className="shrink-0 px-3 py-2 sm:px-4 sm:py-3">` — Apre il wrapper principale con tema.
`        <Header />` — Apre header con padding.
`      </div>` — Renderizza Header.
`(linea vuota)` — Chiude header.
`      <GamePlayNavbar />` — Riga vuota per separare.
`(linea vuota)` — Renderizza GamePlayNavbar.
`      <div className="flex-1 min-h-0 px-3 pb-2 sm:px-4 sm:pb-4 lg:px-6">` — Riga vuota per separare.
`        <div className="h-full min-h-0 grid grid-cols-1 xl:grid-cols-[280px_minmax(0,1fr)_340px] gap-4">` — Apre la griglia principale della pagina.
`          <aside className={``order-2 xl:order-1 min-h-0 rounded-xl border p-3 sm:p-4 overflow-y-auto ${panelThemeClass}``}>` — Apre il layout a 3 colonne responsive.
`            {gameMode === 'replay' ? (` — Apre aside sinistro con pannello.
`              <div className="flex flex-col gap-3">` — Render condizionale per replay.
`                <Controls />` — Apre container dei controlli replay.
`                <MoveInfo />` — Renderizza Controls.
`                <SpeedControl />` — Renderizza MoveInfo.
`                <ProgressBar />` — Renderizza SpeedControl.
`              </div>` — Renderizza ProgressBar.
`            ) : (` — Chiude container.
`              <div className={``rounded-lg border p-3 text-sm leading-relaxed ${hintThemeClass}``}>` — Altrimenti mostra hint.
`                Clicca le tessere adiacenti allo spazio vuoto per muovere il puzzle.` — Testo di hint per la modalita' play.
`              </div>` — Chiude il blocco hint.
`            )}` — Chiude aside sinistro.
`          </aside>` — Riga vuota per separare.
`(linea vuota)` — Apre il main centrale.
`          <main className="order-1 xl:order-2 min-h-0 flex items-center justify-center overflow-hidden">` — Renderizza PuzzleGrid.
`            <PuzzleGrid />` — Chiude main centrale.
`          </main>` — Riga vuota per separare.
`(linea vuota)` — Apre aside destro con pannello.
`          <aside className={``order-3 min-h-0 rounded-xl border p-3 sm:p-4 overflow-y-auto ${panelThemeClass}``}>` — Render condizionale per replay.
`            {gameMode === 'replay' ? (` — Apre container replay destro.
`              <div className="flex flex-col gap-3">` — Renderizza MoveList.
`                <MoveList />` — Renderizza StatesPreview.
`                <StatesPreview />` — Renderizza Footer.
`                <Footer />` — Chiude container.
`              </div>` — Altrimenti mostra hint replay.
`            ) : (` — Testo del hint replay.
`              <div className={``h-full flex items-center justify-center text-center text-xs sm:text-sm leading-relaxed ${replayHintThemeClass}``}>` — Chiude blocco hint replay.
`                Se non riesci, premi Arrendi nella barra sopra per avviare la soluzione animata.` — Chiude aside destro.
`              </div>` — Chiude layout a colonne.
`            )}` — Chiude il contenitore principale.
`          </aside>` — Riga vuota per separare.
`        </div>` — Render condizionale overlay errore.
`      </div>` — Apre overlay full screen.
`(linea vuota)` — Apre card errore.
`      {error && (` — Icona errore (simbolo).
`        <div className="fixed inset-0 flex items-center justify-center z-50">` — Mostra il testo errore.
`          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">` — Apre bottone OK.
`            <div className="text-4xl sm:text-5xl mb-2">âŒ</div>` — onClick chiama clearError.
`            <div className="text-lg sm:text-xl font-bold text-red-400 whitespace-pre-wrap mb-4">{error}</div>` — Classi bottone.
`            <button` — Chiude tag apertura bottone.
`              onClick={clearError}` — Testo OK.
`              className="mt-4 px-6 py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg transition-colors"` — Chiude bottone.
`            >` — Chiude card errore.
`              OK` — Chiude overlay.
`            </button>` — Chiude condizionale error.
`          </div>` — Chiude il contenitore interno dell'overlay.
`        </div>` — Chiude l'overlay.
`      )}` — Chiude il render condizionale dell'errore.
`    </div>` — Chiude wrapper principale.
`  );` — Chiude return.
`}` — Chiude il componente App.

<a id="src-features-puzzle-components-gameplaynavbar-tsx"></a>
## src/features/puzzle/components/GamePlayNavbar.tsx
`"use client";` — Direttiva client per usare hook React.
`(linea vuota)` — Riga vuota per separare import.
`import Link from 'next/link';` — Importa Link per navigazione.
`import { useEffect, useMemo, useState } from 'react';` — Importa useEffect, useMemo e useState.
`import { COLOR_PALETTE_LABELS } from '@/features/puzzle/constants/puzzle';` — Importa le label delle palette colori.
`import { postGeneratePuzzle, postSolvePuzzle, ApiConnectionError, ApiTimeoutError, ApiHttpError, ApiPayloadError } from '@/features/puzzle/api/solverApi';` — Importa funzioni API e classi errore (postSolvePuzzle non usata qui).
`import {` — Inizia import dei selector.
`  useColorPaletteMode,` — Importa useColorPaletteMode.
`  useCurrentGrid,` — Importa useCurrentGrid (non usato nel file).
`  useElapsedSeconds,` — Importa useElapsedSeconds.
`  useGameMode,` — Importa useGameMode.
`  useGiveUp,` — Importa useGiveUp.
`  useGridSize,` — Importa useGridSize.
`  useMusicEnabled,` — Importa useMusicEnabled.
`  useRestartGame,` — Importa useRestartGame.
`  useSetGeneratedPuzzle,` — Importa useSetGeneratedPuzzle.
`  useSetGridSize,` — Importa useSetGridSize.
`  useSetSolutionMovesFromApi,` — Importa useSetSolutionMovesFromApi (non usato nel file).
`  useSolutionMoves,` — Importa useSolutionMoves (non usato nel file).
`  useTimerEnabled,` — Importa useTimerEnabled.
`  useThemeMode,` — Importa useThemeMode.
`  useTickElapsed,` — Importa useTickElapsed.
`  useToggleTimerEnabled,` — Importa useToggleTimerEnabled.
`  useToggleThemeMode,` — Importa useToggleThemeMode.
`} from '@/features/puzzle/store/puzzleSelectors';` — Chiude import selector.
`(linea vuota)` — Riga vuota per separare.
`function formatTime(totalSeconds: number): string {` — Funzione helper per formattare il tempo in mm:ss.
`  const mins = Math.floor(totalSeconds / 60);` — Calcola i minuti.
`  const secs = totalSeconds % 60;` — Calcola i secondi.
`  return ``${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}``;` — Ritorna la stringa formattata con padding.
`}` — Chiude formatTime.
`(linea vuota)` — Riga vuota per separare.
`export default function GamePlayNavbar() {` — Definisce il componente GamePlayNavbar.
`  const gameMode = useGameMode();` — Legge gameMode dallo store.
`  const elapsedSeconds = useElapsedSeconds();` — Legge elapsedSeconds.
`  const gridSize = useGridSize();` — Legge gridSize.
`  const currentGrid = useCurrentGrid();` — Legge currentGrid (valore non usato).
`  const solutionMoves = useSolutionMoves();` — Legge solutionMoves (valore non usato).
`  const setGridSize = useSetGridSize();` — Legge setGridSize.
`  const setGeneratedPuzzle = useSetGeneratedPuzzle();` — Legge setGeneratedPuzzle.
`  const setSolutionMovesFromApi = useSetSolutionMovesFromApi();` — Legge setSolutionMovesFromApi (non usato).
`  const tickElapsed = useTickElapsed();` — Legge tickElapsed.
`  const giveUp = useGiveUp();` — Legge giveUp.
`  const restartGame = useRestartGame();` — Legge restartGame.
`  const timerEnabled = useTimerEnabled();` — Legge timerEnabled.
`  const toggleTimerEnabled = useToggleTimerEnabled();` — Legge toggleTimerEnabled.
`  const themeMode = useThemeMode();` — Legge themeMode.
`  const toggleThemeMode = useToggleThemeMode();` — Legge toggleThemeMode.
`  const colorPaletteMode = useColorPaletteMode();` — Legge colorPaletteMode.
`  const musicEnabled = useMusicEnabled();` — Legge musicEnabled.
`  const [selectedGridSize, setSelectedGridSize] = useState<3 | 4>(gridSize);` — Stato locale per la size selezionata.
`  const [gridLoading, setGridLoading] = useState(false);` — Stato locale per il loading del cambio tabella.
`  const [surrenderLoading, setSurrenderLoading] = useState(false);` — Stato locale per il loading dell'arrendersi.
`  const [gridFeedback, setGridFeedback] = useState<string | null>(null);` — Stato locale per messaggi di feedback.
`(linea vuota)` — Riga vuota per separare.
`  useEffect(() => {` — useEffect per avviare il timer.
`    if (gameMode !== 'play' || !timerEnabled) return;` — Se non in play o timer disattivo, esce.
`(linea vuota)` — Riga vuota per separare.
`    const id = setInterval(() => {` — Crea interval che incrementa il timer.
`      tickElapsed();` — Chiama tickElapsed ogni secondo.
`    }, 1000);` — Chiude setInterval.
`(linea vuota)` — Riga vuota per separare.
`    return () => clearInterval(id);` — Cleanup che cancella l'interval.
`  }, [gameMode, timerEnabled, tickElapsed]);` — Chiude useEffect e dipendenze.
`(linea vuota)` — Riga vuota per separare.
`  useEffect(() => {` — useEffect per sincronizzare gridSize nello state locale.
`    setSelectedGridSize(gridSize);` — Aggiorna selectedGridSize quando cambia gridSize.
`  }, [gridSize]);` — Chiude useEffect.
`(linea vuota)` — Riga vuota per separare.
`  const formattedTime = useMemo(() => formatTime(elapsedSeconds), [elapsedSeconds]);` — Memoizza il tempo formattato.
`(linea vuota)` — Riga vuota per separare.
`  const handleGridSizeChange = async () => {` — Handler async per cambiare la dimensione.
`    setGridLoading(true);` — Imposta loading su true.
`    setGridFeedback('Caricamento puzzle da API...');` — Imposta feedback di caricamento.
`(linea vuota)` — Riga vuota per separare.
`    try {` — Inizia try.
`      const generatedPuzzle = await postGeneratePuzzle(selectedGridSize);` — Richiede puzzle generato al backend.
`      await setGeneratedPuzzle(selectedGridSize, generatedPuzzle.initialGrid, generatedPuzzle.moves);` — Aggiorna lo store con il puzzle generato.
`      setGridFeedback(null);` — Resetta il feedback.
`    } catch (err) {` — Passa al catch.
`      setGridSize(selectedGridSize);` — Fallback locale: imposta config locale.
`      if (err instanceof ApiConnectionError) {` — Se errore di connessione, messaggio specifico.
`        setGridFeedback('Impossibile raggiungere il server. Controlla che il backend sia avviato e che l\'indirizzo sia corretto. Caricata configurazione locale.');` — Messaggio per errore di connessione.
`      } else if (err instanceof ApiTimeoutError) {` — Se timeout, messaggio specifico.
`        setGridFeedback('Il server non ha risposto in tempo. Caricata configurazione locale.');` — Messaggio per timeout.
`      } else if (err instanceof ApiHttpError) {` — Se HTTP error, messaggio specifico.
`        setGridFeedback(``Errore dal server (HTTP ${err.status}). Caricata configurazione locale.``);` — Messaggio con status HTTP.
`      } else if (err instanceof ApiPayloadError) {` — Se payload error, messaggio specifico.
`        setGridFeedback('Risposta del server non valida. Caricata configurazione locale.');` — Messaggio per payload invalido.
`      } else {` — Altrimenti, messaggio generico API.
`        setGridFeedback('API non disponibile: caricata configurazione locale.');` — Messaggio generico.
`      }` — Chiude i blocchi if/else.
`    } finally {` — Chiude catch.
`      setGridLoading(false);` — Finally: reset loading.
`    }` — Imposta gridLoading a false.
`  };` — Chiude finally.
`(linea vuota)` — Chiude handleGridSizeChange.
`  const handleGiveUp = async () => {` — Riga vuota per separare.
`    setSurrenderLoading(true);` — Handler async per arrendersi.
`    setGridFeedback(null);` — Imposta loading su true.
`(linea vuota)` — Resetta feedback.
`    try {` — Riga vuota per separare.
`      await giveUp();` — Inizia try.
`      setGridFeedback(null);` — Chiama giveUp (API solve via store).
`    } catch (err) {` — Resetta feedback.
`      if (err instanceof ApiConnectionError) {` — Passa al catch.
`        setGridFeedback('Impossibile raggiungere il server. Controlla che il backend sia avviato e che l\'indirizzo sia corretto.');` — Se errore di connessione, messaggio specifico.
`      } else if (err instanceof ApiTimeoutError) {` — Messaggio connessione.
`        setGridFeedback('Il server non ha risposto in tempo. Riprova piÃ¹ tardi.');` — Se timeout, messaggio specifico.
`      } else if (err instanceof ApiHttpError) {` — Messaggio timeout.
`        setGridFeedback(``Errore dal server (HTTP ${err.status}). Nessuna soluzione ricevuta.``);` — Se HTTP error, messaggio specifico.
`      } else if (err instanceof ApiPayloadError) {` — Messaggio HTTP con status.
`        setGridFeedback('Risposta del server non valida. Nessuna soluzione ricevuta.');` — Se payload error, messaggio specifico.
`      } else {` — Messaggio payload.
`        setGridFeedback('API solve non disponibile: nessuna soluzione ricevuta.');` — Altrimenti, messaggio generico.
`      }` — Messaggio API non disponibile.
`    } finally {` — Chiude i blocchi if/else.
`      setSurrenderLoading(false);` — Finally: reset loading.
`    }` — Imposta surrenderLoading a false.
`  };` — Chiude finally.
`(linea vuota)` — Chiude handleGiveUp.
`  return (` — Riga vuota per separare.
`    <nav className="mx-3 mb-2 sm:mx-4 sm:mb-3 rounded-xl border border-cyan-500/30 bg-slate-900/80 px-3 py-2 sm:px-4 sm:py-3 shadow-lg shadow-cyan-900/40">` — Inizia il return del JSX.
`      <div className="flex flex-wrap items-center justify-between gap-2 sm:gap-3">` — Apre nav con stile.
`        <div className="flex items-center gap-2">` — Apre il contenitore principale dei controlli.
`          <Link` — Apre gruppo link navigazione.
`            href="/"` — Apre Link verso home.
`            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"` — Imposta href home.
`          >` — Classi del link.
`            Home` — Chiude tag di apertura Link.
`          </Link>` — Testo link Home.
`          <Link` — Chiude Link.
`            href="/settings"` — Apre Link verso settings.
`            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"` — Imposta href settings.
`          >` — Classi del link.
`            Impostazioni` — Chiude tag di apertura Link.
`          </Link>` — Testo link Impostazioni.
`          <Link` — Chiude Link.
`            href="/custom"` — Apre Link verso custom.
`            className="rounded-lg border border-gray-600 bg-gray-800 px-3 py-1.5 text-xs sm:text-sm text-gray-100 hover:bg-gray-700"` — Imposta href custom.
`          >` — Classi del link.
`            Tabella Manuale` — Chiude tag di apertura Link.
`          </Link>` — Testo link Tabella Manuale.
`        </div>` — Chiude Link.
`(linea vuota)` — Chiude gruppo link.
`        <div className="flex items-center gap-2 rounded-lg border border-gray-700/60 bg-gray-900/70 px-3 py-1.5">` — Riga vuota per separare.
`          <span className="text-xs uppercase tracking-wide text-gray-400">Tempo</span>` — Apre blocco tempo.
`          <span className="font-mono text-sm sm:text-base text-cyan-300">{formattedTime}</span>` — Label "Tempo".
`        </div>` — Mostra il tempo formattato.
`(linea vuota)` — Chiude blocco tempo.
`        <div className="flex items-center gap-2">` — Riga vuota per separare.
`          <label htmlFor="grid-size" className="text-xs uppercase tracking-wide text-gray-400">` — Apre blocco selezione tabella.
`            Tabella` — Label per select.
`          </label>` — Apre wrapper relativo.
`          <div className="relative">` — Apre select.
`            <select` — Imposta id.
`              id="grid-size"` — Imposta value.
`              value={selectedGridSize}` — onChange aggiorna selectedGridSize.
`              onChange={(e) => setSelectedGridSize(Number(e.target.value) as 3 | 4)}` — Disabilita durante loading.
`              disabled={gridLoading}` — Classi select.
`              className="no-native-arrow rounded-lg border border-gray-600 bg-gray-800 pl-2.5 pr-8 py-1.5 text-sm text-gray-100 outline-none focus:border-cyan-400"` — Opzione 3x3.
`            >` — Opzione 4x4.
`              <option value={3}>3 x 3</option>` — Chiude select.
`              <option value={4}>4 x 4</option>` — Apre icona freccia custom.
`            </select>` — Apre svg.
`            <span className="pointer-events-none absolute inset-y-0 right-2 flex items-center text-cyan-300" aria-hidden="true">` — Apre path.
`              <svg viewBox="0 0 20 20" fill="currentColor" className="h-4 w-4">` — Attributo fillRule.
`                <path` — Definisce il path.
`                  fillRule="evenodd"` — Attributo clipRule.
`                  d="M5.23 7.21a.75.75 0 0 1 1.06.02L10 11.12l3.71-3.89a.75.75 0 1 1 1.08 1.04l-4.25 4.45a.75.75 0 0 1-1.08 0L5.21 8.27a.75.75 0 0 1 .02-1.06Z"` — Chiude path.
`                  clipRule="evenodd"` — Chiude svg.
`                />` — Chiude icona freccia.
`              </svg>` — Chiude wrapper relativo.
`            </span>` — Apre bottone cambio tabella.
`          </div>` — onClick richiama handleGridSizeChange.
`          <button` — Disabilita durante loading.
`            onClick={() => {` — Classi bottone.
`              void handleGridSizeChange();` — Chiude tag di apertura bottone.
`            }}` — Testo condizionale del bottone.
`            disabled={gridLoading}` — Chiude bottone.
`            className="rounded-lg border border-cyan-400/60 bg-cyan-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-cyan-600 disabled:opacity-60"` — Chiude blocco selezione tabella.
`          >` — Riga vuota per separare.
`            {gridLoading ? 'Cambio...' : 'Cambia Tabella'}` — Apre bottone toggle timer.
`          </button>` — onClick toggleTimerEnabled.
`        </div>` — Classi bottone.
`(linea vuota)` — Chiude tag di apertura bottone.
`        <button` — Testo condizionale timer.
`          onClick={toggleTimerEnabled}` — Chiude bottone.
`          className="rounded-lg border border-amber-400/60 bg-amber-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-amber-600"` — Riga vuota per separare.
`        >` — Apre bottone toggle tema.
`          {timerEnabled ? 'Stop Timer' : 'Avvia Timer'}` — onClick toggleThemeMode.
`        </button>` — Classi bottone.
`(linea vuota)` — Chiude tag di apertura bottone.
`        <button` — Testo condizionale tema.
`          onClick={toggleThemeMode}` — Chiude bottone.
`          className="rounded-lg border border-sky-400/60 bg-sky-700 px-3 py-1.5 text-xs sm:text-sm font-semibold text-white transition-colors hover:bg-sky-600"` — Riga vuota per separare.
`        >` — Render condizionale per play/replay.
`          {themeMode === 'dark' ? 'Modalita Chiara' : 'Modalita Scura'}` — Apre bottone Arrenditi.
`        </button>` — onClick richiama handleGiveUp.
`(linea vuota)` — Disabilita durante loading.
`        {gameMode === 'play' ? (` — Classi bottone Arrenditi.
`          <button` — Chiude tag di apertura bottone.
`            onClick={() => {` — Testo condizionale Arrenditi.
`              void handleGiveUp();` — Chiude bottone.
`            }}` — Altrimenti, entra nel ramo replay.
`            disabled={surrenderLoading}` — Apre bottone Riprova.
`            className="rounded-lg border border-orange-400/60 bg-orange-600 px-4 py-1.5 text-sm font-semibold text-white transition-colors hover:bg-orange-500"` — onClick restartGame.
`          >` — Classi bottone Riprova.
`            {surrenderLoading ? 'Calcolo...' : 'Arrenditi'}` — Chiude tag di apertura bottone.
`          </button>` — Testo Riprova.
`        ) : (` — Chiude bottone.
`          <button` — Chiude ternario play/replay.
`            onClick={restartGame}` — Chiude contenitore principale.
`            className="rounded-lg border border-emerald-400/60 bg-emerald-600 px-4 py-1.5 text-sm font-semibold text-white transition-colors hover:bg-emerald-500"` — Riga vuota per separare.
`          >` — Apre riga di stato (tema/palette/musica).
`            Riprova` — Mostra tema corrente.
`          </button>` — Mostra palette corrente.
`        )}` — Mostra stato musica.
`      </div>` — Chiude riga di stato.
`(linea vuota)` — Riga vuota per separare.
`      <div className="mt-2 text-[11px] sm:text-xs text-gray-400">` — Render condizionale feedback.
`        Tema: <span className="text-gray-200">{themeMode === 'dark' ? 'Scuro' : 'Chiaro'}</span> Â·` — Mostra feedback se presente.
`        Palette: <span className="text-gray-200">{COLOR_PALETTE_LABELS[colorPaletteMode]}</span> Â·` — Chiude il render condizionale.
`        Musica: <span className="text-gray-200">{musicEnabled ? 'Attiva' : 'Disattivata'}</span>` — Chiude nav.
`      </div>` — Chiude return.
`(linea vuota)` — Chiude il componente GamePlayNavbar.
`      {gridFeedback && (` — Spiegazione mancante per questa riga.
`        <div className="mt-1 text-[11px] sm:text-xs text-amber-300">{gridFeedback}</div>` — Spiegazione mancante per questa riga.
`      )}` — Spiegazione mancante per questa riga.
`    </nav>` — Spiegazione mancante per questa riga.
`  );` — Spiegazione mancante per questa riga.
`}` — Spiegazione mancante per questa riga.

<a id="src-features-puzzle-components-puzzlegrid-tsx"></a>
## src/features/puzzle/components/PuzzleGrid.tsx
`import { memo } from 'react';` — Importa memo da React.
`import { useCurrentGrid, usePrevGrid, useIsSolved, useTotalSteps, useGameMode, useManualGrid, usePlayMove, useGridSize } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector dallo store.
`import { DIRECTION_DELTAS } from '@/features/puzzle/constants/puzzle';` — Importa i delta direzioni.
`import { getMovedTile } from '@/features/puzzle/utils/puzzle';` — Importa getMovedTile.
`import Tile from './Tile';` — Importa il componente Tile.
`(linea vuota)` — Riga vuota per separare.
`const PuzzleGrid = memo(function PuzzleGrid() {` — Definisce PuzzleGrid con memo.
`  const gameMode = useGameMode();` — Legge gameMode.
`  const currentGrid = useCurrentGrid();` — Legge currentGrid.
`  const manualGrid = useManualGrid();` — Legge manualGrid.
`  const gridSize = useGridSize();` — Legge gridSize.
`  const prevGrid = usePrevGrid();` — Legge prevGrid.
`  const isSolved = useIsSolved();` — Legge isSolved.
`  const totalSteps = useTotalSteps();` — Legge totalSteps.
`  const playMove = usePlayMove();` — Legge playMove.
`(linea vuota)` — Riga vuota per separare.
`  // Scegli quale grid mostrare` — Commento: quale grid mostrare.
`  const displayGrid = gameMode === 'play' ? manualGrid : currentGrid;` — Sceglie displayGrid in base al gameMode.
`  const movedTile = gameMode === 'replay' ? getMovedTile(currentGrid, prevGrid) : -1;` — Calcola movedTile in replay.
`  const solvedTitle = gameMode === 'play' ? 'Vittoria!' : 'Risolto!';` — Sceglie il titolo per stato risolto.
`(linea vuota)` — Riga vuota per separare.
`  const handleTileClick = (index: number) => {` — Handler click su tessera.
`    if (gameMode !== 'play') return;` — Se non play, esce.
`(linea vuota)` — Riga vuota per separare.
`    const row = Math.floor(index / gridSize);` — Calcola riga dall'indice.
`    const col = index % gridSize;` — Calcola colonna dall'indice.
`(linea vuota)` — Riga vuota per separare.
`    // Trova lo zero (casella vuota)` — Commento: trova lo zero.
`    let zeroRow = 0, zeroCol = 0;` — Inizializza zeroRow e zeroCol.
`    for (let r = 0; r < gridSize; r++) {` — Ciclo sulle righe.
`      for (let c = 0; c < gridSize; c++) {` — Ciclo sulle colonne.
`        if (manualGrid[r][c] === 0) {` — Se trova zero, aggiorna coordinate.
`          zeroRow = r;` — Aggiorna zeroRow.
`          zeroCol = c;` — Aggiorna zeroCol.
`        }` — Chiude if.
`      }` — Chiude ciclo colonne.
`    }` — Chiude ciclo righe.
`(linea vuota)` — Riga vuota per separare.
`    // Verifica se il tile Ã¨ adiacente allo zero` — Commento: verifica adiacenza.
`    const directions = ['SINISTRA', 'DESTRA', 'SOPRA', 'SOTTO'] as const;` — Elenco direzioni possibili.
`    for (const dir of directions) {` — Ciclo sulle direzioni.
`      const [dr, dc] = DIRECTION_DELTAS[dir];` — Recupera delta della direzione.
`      if (zeroRow + dr === row && zeroCol + dc === col) {` — Se adiacente, esegue la mossa.
`        playMove(dir);` — Chiama playMove.
`        return;` — Ritorna per uscire.
`      }` — Chiude if.
`    }` — Chiude ciclo direzioni.
`  };` — Chiude handleTileClick.
`(linea vuota)` — Riga vuota per separare.
`  const gridMaxClass =` — Definisce classi max width in base alla size.
`    gridSize === 3` — Se 3x3 usa un max piu' largo.
`      ? 'max-w-[460px] sm:max-w-[580px] lg:max-w-[700px]'` — Classi per 3x3.
`      : 'max-w-[380px] sm:max-w-[500px] lg:max-w-[620px]';` — Classi per 4x4.
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX.
`    <div className="relative flex justify-center w-full">` — Apre wrapper relativo.
`      <div` — Apre il contenitore grid.
`        className={``grid gap-1.5 sm:gap-2 p-3 sm:p-4 bg-gray-800/60 rounded-xl sm:rounded-2xl backdrop-blur-sm border border-gray-700/50 shadow-2xl w-full ${gridMaxClass}``}` — Classi per layout grid e stile.
`        style={{ gridTemplateColumns: ``repeat(${gridSize}, 1fr)`` }}` — Imposta le colonne in base a gridSize.
`      >` — Chiude tag di apertura div.
`        {displayGrid.flat().map((val, i) => (` — Mappa le celle per renderizzare Tile.
`          <div` — Apre wrapper della cella.
`            key={i}` — Imposta key.
`            onClick={() => handleTileClick(i)}` — onClick chiama handleTileClick.
`            className={gameMode === 'play' ? 'cursor-pointer' : ''}` — Abilita cursor-pointer solo in play.
`          >` — Chiude tag di apertura wrapper.
`            <Tile value={val} isMoving={val === movedTile} />` — Renderizza Tile con animazione se moving.
`          </div>` — Chiude wrapper cella.
`        ))}` — Chiude map.
`      </div>` — Chiude il contenitore grid.
`(linea vuota)` — Riga vuota per separare.
`      {isSolved && (` — Render condizionale overlay solved.
`        <div className="absolute inset-0 flex items-center justify-center">` — Apre overlay full screen.
`          <div className="bg-black/50 rounded-xl sm:rounded-2xl backdrop-blur-[3px] p-6 sm:p-8 text-center">` — Apre card solved.
`            <div className="text-4xl sm:text-5xl mb-2 animate-bounce">ðŸŽ‰</div>` — Icona di vittoria (simbolo).
`            <div className="text-lg sm:text-xl font-bold text-green-400">{solvedTitle}</div>` — Titolo risolto.
`            {gameMode === 'replay' ? (` — Render condizionale per replay.
`              <div className="text-xs sm:text-sm text-gray-400 mt-1">in {totalSteps} mosse</div>` — Mostra numero mosse in replay.
`            ) : (` — Altrimenti, testo descrittivo.
`              <div className="text-xs sm:text-sm text-gray-400 mt-1">Ordine corretto con spazio in basso a destra</div>` — Testo per modalita' play.
`            )}` — Chiude ternario.
`          </div>` — Chiude card.
`        </div>` — Chiude overlay.
`      )}` — Chiude condizionale solved.
`    </div>` — Chiude wrapper principale.
`  );` — Chiude return.
`});` — Chiude memo.
`(linea vuota)` — Riga vuota per separare.
`export default PuzzleGrid;` — Esporta PuzzleGrid di default.

<a id="src-features-puzzle-components-controls-tsx"></a>
## src/features/puzzle/components/Controls.tsx
`import { useStep, useTotalSteps, useIsPlaying, useGoPrev, useGoNext, useTogglePlay, usePause, useGameMode, useRestartGame } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector e azioni dallo store.
`(linea vuota)` — Riga vuota per separare.
`export default function Controls() {` — Definisce il componente Controls.
`  const gameMode = useGameMode();` — Legge gameMode.
`  const step = useStep();` — Legge step.
`  const totalSteps = useTotalSteps();` — Legge totalSteps.
`  const isPlaying = useIsPlaying();` — Legge isPlaying.
`  const goPrev = useGoPrev();` — Legge goPrev.
`  const goNext = useGoNext();` — Legge goNext.
`  const togglePlay = useTogglePlay();` — Legge togglePlay.
`  const pause = usePause();` — Legge pause.
`  const restartGame = useRestartGame();` — Legge restartGame.
`(linea vuota)` — Riga vuota per separare.
`  const handlePrev = () => { pause(); goPrev(); };` — Handler per andare indietro con pausa.
`  const handleNext = () => { pause(); goNext(); };` — Handler per andare avanti con pausa.
`(linea vuota)` — Riga vuota per separare.
`  if (gameMode === 'play') {` — Se in play, non renderizza nulla.
`    return null;` — Ritorna null.
`  }` — Chiude if.
`(linea vuota)` — Riga vuota per separare.
`  // gameMode === 'replay'` — Commento: modalita' replay.
`  return (` — Inizia il return del JSX.
`    <div className="flex flex-wrap items-center justify-center gap-2 sm:gap-3">` — Apre il container dei bottoni.
`      <button` — Apre bottone Riprova.
`        onClick={restartGame}` — onClick restartGame.
`        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"` — Classi bottone.
`      >` — Chiude tag di apertura bottone.
`        âŸ² Riprova` — Testo bottone Riprova.
`      </button>` — Chiude bottone.
`      <button` — Apre bottone Indietro.
`        onClick={handlePrev}` — onClick handlePrev.
`        disabled={step === 0}` — Disabilita se step=0.
`        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 disabled:opacity-40 disabled:cursor-not-allowed rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"` — Classi bottone.
`      >` — Chiude tag di apertura bottone.
`        â—€ Indietro` — Testo bottone Indietro.
`      </button>` — Chiude bottone.
`      <button` — Apre bottone Play/Pausa.
`        onClick={togglePlay}` — onClick togglePlay.
`        className={``px-4 sm:px-6 py-2 rounded-lg text-xs sm:text-sm font-bold transition-colors border active:scale-95 ${` — Inizia className con ternario.
`          isPlaying` — Se isPlaying, usa stile pausa.
`            ? 'bg-red-600 hover:bg-red-500 border-red-500'` — Classi per pausa.
`            : 'bg-green-600 hover:bg-green-500 border-green-500'` — Classi per play.
`        }``}` — Chiude template literal.
`      >` — Chiude tag di apertura bottone.
`        {isPlaying ? 'â¸ Pausa' : 'â–¶ Avvia'}` — Testo condizionale play/pausa.
`      </button>` — Chiude bottone.
`      <button` — Apre bottone Avanti.
`        onClick={handleNext}` — onClick handleNext.
`        disabled={step === totalSteps}` — Disabilita se step=totalSteps.
`        className="px-3 sm:px-4 py-2 bg-gray-700 hover:bg-gray-600 disabled:opacity-40 disabled:cursor-not-allowed rounded-lg text-xs sm:text-sm font-medium transition-colors border border-gray-600 active:scale-95"` — Classi bottone.
`      >` — Chiude tag di apertura bottone.
`        Avanti â–¶` — Testo bottone Avanti.
`      </button>` — Chiude bottone.
`    </div>` — Chiude container.
`  );` — Chiude return.
`}` — Chiude il componente Controls.

<a id="src-features-puzzle-components-header-tsx"></a>
## src/features/puzzle/components/Header.tsx
`import { memo } from 'react';` — Importa memo da React.
`import { useGridSize, useSolutionMoves } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per gridSize e moves.
`(linea vuota)` — Riga vuota per separare.
`const Header = memo(function Header() {` — Definisce Header con memo.
`  const gridSize = useGridSize();` — Legge gridSize.
`  const moves = useSolutionMoves();` — Legge moves.
`  const puzzleName = gridSize === 4 ? '15' : '8';` — Determina il nome puzzle (15 o 8).
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX.
`    <div className="text-center">` — Apre container centrato.
`      <h1 className="text-3xl sm:text-4xl md:text-5xl font-extrabold bg-gradient-to-r from-blue-400 via-purple-400 to-pink-400 bg-clip-text text-transparent">` — Apre h1 con stile gradient.
`        {puzzleName}-Puzzle Solver` — Mostra il titolo con numero puzzle.
`      </h1>` — Chiude h1.
`      <p className="text-gray-400 mt-1 sm:mt-2 text-xs sm:text-sm md:text-base">` — Apre paragrafo descrittivo.
`        {gridSize}Ã—{gridSize} puzzle Â· {moves.length} mosse per risolvere` — Mostra dimensione e numero mosse.
`      </p>` — Chiude paragrafo.
`    </div>` — Chiude container.
`  );` — Chiude return.
`});` — Chiude memo.
`(linea vuota)` — Riga vuota per separare.
`export default Header;` — Esporta Header di default.

<a id="src-features-puzzle-components-moveinfo-tsx"></a>
## src/features/puzzle/components/MoveInfo.tsx
`import { memo } from 'react';` — Importa memo da React.
`import { useStep, useTotalSteps, useCurrentMove } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per step, totalSteps e currentMove.
`import { DIRECTION_LABELS } from '@/features/puzzle/constants/puzzle';` — Importa DIRECTION_LABELS.
`(linea vuota)` — Riga vuota per separare.
`const MoveInfo = memo(function MoveInfo() {` — Definisce MoveInfo con memo.
`  const step = useStep();` — Legge step.
`  const totalSteps = useTotalSteps();` — Legge totalSteps.
`  const currentMove = useCurrentMove();` — Legge currentMove.
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX.
`    <div className="flex flex-wrap items-center justify-center gap-2 sm:gap-3 bg-gray-800/80 px-3 sm:px-5 py-2 sm:py-3 rounded-xl border border-gray-700/60">` — Apre container con stile.
`      <span className="text-gray-400 text-xs sm:text-sm font-mono">` — Mostra "Passo step/totalSteps".
`        Passo {step}/{totalSteps}` — Contenuto dinamico del passo.
`      </span>` — Chiude span.
`      {currentMove && (` — Render condizionale se currentMove esiste.
`        <>` — Apre fragment.
`          <span className="text-gray-600 hidden sm:inline">|</span>` — Separatore verticale (solo desktop).
`          <span className="text-xl sm:text-2xl">{DIRECTION_LABELS[currentMove].icon}</span>` — Icona direzione.
`          <span className="font-semibold text-yellow-300 text-sm sm:text-base">{currentMove}</span>` — Nome direzione.
`          <span className="text-gray-500 text-xs sm:text-sm">({DIRECTION_LABELS[currentMove].en})</span>` — Nome inglese direzione.
`        </>` — Chiude fragment.
`      )}` — Chiude condizionale currentMove.
`      {step === 0 && (` — Render condizionale se step == 0.
`        <>` — Apre fragment.
`          <span className="text-gray-600 hidden sm:inline">|</span>` — Separatore verticale.
`          <span className="text-gray-300 text-xs sm:text-sm">Stato Iniziale</span>` — Testo "Stato Iniziale".
`        </>` — Chiude fragment.
`      )}` — Chiude condizionale step==0.
`    </div>` — Chiude container.
`  );` — Chiude return.
`});` — Chiude memo.
`(linea vuota)` — Riga vuota per separare.
`export default MoveInfo;` — Esporta MoveInfo di default.

<a id="src-features-puzzle-components-movelist-tsx"></a>
## src/features/puzzle/components/MoveList.tsx
`"use client";` — Direttiva client per usare hook.
`(linea vuota)` — Riga vuota per separare import.
`import { useEffect, useRef } from 'react';` — Importa useEffect e useRef.
`import { useStep, useJumpToStep, useSolutionMoves } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per step, jumpToStep e moves.
`import { DIRECTION_LABELS } from '@/features/puzzle/constants/puzzle';` — Importa DIRECTION_LABELS.
`(linea vuota)` — Riga vuota per separare.
`export default function MoveList() {` — Definisce il componente MoveList.
`  const step = useStep();` — Legge step.
`  const jumpToStep = useJumpToStep();` — Legge jumpToStep.
`  const moves = useSolutionMoves();` — Legge moves.
`  const listRef = useRef<HTMLDivElement>(null);` — Crea ref per la lista.
`(linea vuota)` — Riga vuota per separare.
`  // Auto-scroll alla mossa attiva` — Commento: auto-scroll.
`  useEffect(() => {` — useEffect per scrollare alla mossa attiva.
`    if (listRef.current && step > 0) {` — Se listRef esiste e step > 0.
`      const activeBtn = listRef.current.querySelector(``[data-step="${step}"]``);` — Trova il bottone attivo via data-step.
`      if (activeBtn) {` — Se trovato, scrollIntoView.
`        activeBtn.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' });` — Scroll smooth con allineamento.
`      }` — Chiude if.
`    }` — Chiude useEffect.
`  }, [step]);` — Riga vuota per separare.
`(linea vuota)` — Inizia il return del JSX.
`  return (` — Apre container lista.
`    <div className="w-full bg-gray-800/50 rounded-xl border border-gray-700/50 p-3 sm:p-4">` — Titolo lista con conteggio.
`      <h2 className="text-xs sm:text-sm font-semibold text-gray-400 mb-2 sm:mb-3 uppercase tracking-wider">` — Testo del titolo.
`        Sequenza Mosse ({moves.length})` — Chiude h2.
`      </h2>` — Apre container scrollabile e flex.
`      <div ref={listRef} className="max-h-44 overflow-y-auto pr-1 flex flex-wrap gap-1 sm:gap-1.5">` — Mappa le mosse.
`        {moves.map((move, i) => {` — Calcola indice mossa (1-based).
`          const moveIndex = i + 1;` — Determina se e' attiva.
`          const isActive = moveIndex === step;` — Determina se e' passata.
`          const isPast = moveIndex < step;` — Riga vuota per separare.
`(linea vuota)` — Ritorna il bottone per la mossa.
`          return (` — Apre bottone.
`            <button` — key React.
`              key={i}` — data-step per auto-scroll.
`              data-step={moveIndex}` — onClick jumpToStep.
`              onClick={() => jumpToStep(moveIndex)}` — title con descrizione mossa.
`              title={``#${moveIndex}: ${move} (${DIRECTION_LABELS[move].en})``}` — Inizia className multiline.
`              className={``` — Padding e font.
`                px-1.5 sm:px-2 py-0.5 sm:py-1 rounded text-[10px] sm:text-xs font-mono` — Stili base.
`                transition-all border cursor-pointer` — Inizia ternario stili.
`                ${isActive` — Stile per mossa attiva.
`                  ? 'bg-yellow-500/30 border-yellow-500 text-yellow-300 scale-110 shadow-lg shadow-yellow-500/20 z-10'` — Stile per mossa passata.
`                  : isPast` — Stile per mossa futura.
`                  ? 'bg-green-900/30 border-green-700/50 text-green-400/80'` — Chiude ternario.
`                  : 'bg-gray-700/40 border-gray-600/40 text-gray-400 hover:bg-gray-600/50'` — Chiude template literal.
`                }` — Chiude tag di apertura bottone.
`              ``}` — Renderizza icona direzione.
`            >` — Chiude bottone.
`              {DIRECTION_LABELS[move].icon}` — Chiude return della map.
`            </button>` — Chiude map.
`          );` — Chiude container lista.
`        })}` — Chiude container esterno.
`      </div>` — Chiude return.
`    </div>` — Chiude il componente MoveList.
`  );` — Spiegazione mancante per questa riga.
`}` — Spiegazione mancante per questa riga.

<a id="src-features-puzzle-components-progressbar-tsx"></a>
## src/features/puzzle/components/ProgressBar.tsx
`import { useStep, useTotalSteps } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per step e totalSteps.
`(linea vuota)` — Riga vuota per separare.
`export default function ProgressBar() {` — Definisce il componente ProgressBar.
`  const step = useStep();` — Legge step.
`  const totalSteps = useTotalSteps();` — Legge totalSteps.
`  const percentage = totalSteps > 0 ? (step / totalSteps) * 100 : 0;` — Calcola la percentuale di avanzamento.
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX.
`    <div className="w-full max-w-md">` — Apre container con max width.
`      <div className="h-2 bg-gray-700 rounded-full overflow-hidden">` — Apre barra di sfondo.
`        <div` — Apre barra di progresso.
`          className="h-full bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 transition-all duration-300 rounded-full"` — Classi per gradiente e animazione.
`          style={{ width: ``${percentage}%`` }}` — Imposta width in base alla percentuale.
`        />` — Chiude barra di progresso.
`      </div>` — Chiude barra di sfondo.
`    </div>` — Chiude container.
`  );` — Chiude return.
`}` — Chiude il componente ProgressBar.

<a id="src-features-puzzle-components-speedcontrol-tsx"></a>
## src/features/puzzle/components/SpeedControl.tsx
`import { useSpeed, useSetSpeed } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per speed e setter.
`import { MIN_SPEED, MAX_SPEED, SPEED_STEP } from '@/features/puzzle/constants/puzzle';` — Importa costanti di velocita'.
`(linea vuota)` — Riga vuota per separare.
`export default function SpeedControl() {` — Definisce il componente SpeedControl.
`  const speed = useSpeed();` — Legge speed.
`  const setSpeed = useSetSpeed();` — Legge setSpeed.
`(linea vuota)` — Riga vuota per separare.
`  const invertedMax = MIN_SPEED + MAX_SPEED;` — Calcola il massimo invertito per slider.
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX.
`    <div className="flex items-center gap-2 sm:gap-3 bg-gray-800/60 rounded-lg px-3 sm:px-4 py-2 border border-gray-700/40">` — Apre container slider.
`      <span className="text-gray-400 text-xs sm:text-sm">VelocitÃ :</span>` — Label "Velocita".
`      <input` — Apre input range.
`        type="range"` — Tipo input range.
`        min={MIN_SPEED}` — Imposta min.
`        max={MAX_SPEED}` — Imposta max.
`        step={SPEED_STEP}` — Imposta step.
`        value={invertedMax - speed}` — Inverte la value per slider.
`        onChange={e => setSpeed(invertedMax - Number(e.target.value))}` — onChange aggiorna speed invertendo.
`        className="w-24 sm:w-32 accent-purple-500"` — Classi input.
`      />` — Chiude input.
`    </div>` — Chiude container.
`  );` — Chiude return.
`}` — Chiude il componente SpeedControl.

<a id="src-features-puzzle-components-statespreview-tsx"></a>
## src/features/puzzle/components/StatesPreview.tsx
`import { memo } from 'react';` — Importa memo da React.
`import { useAllStates, useTotalSteps, useGridSize, useInitialGrid } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per stati e dimensione.
`(linea vuota)` — Riga vuota per separare.
`const MiniGrid = memo(function MiniGrid({` — Definisce il componente MiniGrid con memo.
`  grid,` — Destruttura le props.
`  gridSize,` — Prop gridSize.
`  label,` — Prop label.
`  labelColor,` — Prop labelColor.
`  borderColor,` — Prop borderColor.
`}: {` — Apre la definizione del tipo props.
`  grid: number[][];` — Tipo grid.
`  gridSize: number;` — Tipo gridSize.
`  label: string;` — Tipo label.
`  labelColor: string;` — Tipo labelColor.
`  borderColor: string;` — Tipo borderColor.
`}) {` — Chiude tipo props.
`  return (` — Inizia il return del JSX.
`    <div className={``bg-gray-800/40 rounded-xl border ${borderColor} p-3 sm:p-4``}>` — Apre card MiniGrid con bordi.
`      <h3 className={``text-xs font-semibold ${labelColor} uppercase tracking-wider mb-2``}>` — Apre h3 con label.
`        {label}` — Mostra label.
`      </h3>` — Chiude h3.
`      <div className="grid gap-1" style={{ gridTemplateColumns: ``repeat(${gridSize}, 1fr)`` }}>` — Apre griglia con colonne dinamiche.
`        {grid.flat().map((val, i) => (` — Mappa le celle della griglia.
`          <div` — Apre cella mini.
`            key={i}` — Imposta key.
`            className={``` — Inizia className multiline.
`              aspect-square rounded flex items-center justify-center text-xs sm:text-sm font-bold` — Classi base della cella.
`              ${val === 0` — Se valore e' 0, usa stile vuoto.
`                ? 'bg-gray-700/30 text-gray-600'` — Stile vuoto.
`                : label.includes('âœ“')` — Se label include check, usa stile finale.
`                ? 'bg-green-900/40 text-green-400'` — Stile finale.
`                : 'bg-gray-700/60 text-gray-300'` — Stile default.
`              }` — Chiude ternario.
`            ``}` — Chiude template literal.
`          >` — Chiude tag di apertura div.
`            {val === 0 ? 'Â·' : val}` — Mostra punto o valore.
`          </div>` — Chiude cella.
`        ))}` — Chiude map.
`      </div>` — Chiude griglia.
`    </div>` — Chiude card MiniGrid.
`  );` — Chiude return.
`});` — Chiude memo MiniGrid.
`(linea vuota)` — Riga vuota per separare.
`const StatesPreview = memo(function StatesPreview() {` — Definisce StatesPreview con memo.
`  const allStates = useAllStates();` — Legge allStates.
`  const totalSteps = useTotalSteps();` — Legge totalSteps.
`  const gridSize = useGridSize();` — Legge gridSize.
`  const initialGrid = useInitialGrid();` — Legge initialGrid.
`  const finalGrid = allStates[totalSteps];` — Calcola finalGrid.
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX.
`    <div className="w-full grid grid-cols-1 sm:grid-cols-2 gap-3 sm:gap-4">` — Apre container a due colonne.
`      <MiniGrid` — Renderizza MiniGrid stato iniziale.
`        grid={initialGrid}` — Passa grid iniziale.
`        gridSize={gridSize}` — Passa gridSize.
`        label="Stato Iniziale"` — Passa label "Stato Iniziale".
`        labelColor="text-gray-500"` — Passa colore label.
`        borderColor="border-gray-700/40"` — Passa colore bordo.
`      />` — Chiude MiniGrid iniziale.
`      <MiniGrid` — Renderizza MiniGrid stato finale.
`        grid={finalGrid}` — Passa grid finale.
`        gridSize={gridSize}` — Passa gridSize.
`        label="Stato Finale âœ“"` — Passa label "Stato Finale".
`        labelColor="text-green-500"` — Passa colore label.
`        borderColor="border-green-700/30"` — Passa colore bordo.
`      />` — Chiude MiniGrid finale.
`    </div>` — Chiude container.
`  );` — Chiude return.
`});` — Chiude memo StatesPreview.
`(linea vuota)` — Riga vuota per separare.
`export default StatesPreview;` — Esporta StatesPreview di default.

<a id="src-features-puzzle-components-tile-tsx"></a>
## src/features/puzzle/components/Tile.tsx
`import { memo } from 'react';` — Importa memo da React.
`import { TILE_COLORS_BY_MODE } from '@/features/puzzle/constants/puzzle';` — Importa le palette colori per le tessere.
`import { useColorPaletteMode } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per colorPaletteMode.
`(linea vuota)` — Riga vuota per separare.
`interface TileProps {` — Definisce l'interfaccia TileProps.
`  value: number;` — Prop value.
`  isMoving: boolean;` — Prop isMoving.
`}` — Chiude l'interfaccia.
`(linea vuota)` — Riga vuota per separare.
`const Tile = memo(function Tile({ value, isMoving }: TileProps) {` — Definisce Tile con memo.
`  const colorPaletteMode = useColorPaletteMode();` — Legge colorPaletteMode.
`(linea vuota)` — Riga vuota per separare.
`  if (value === 0) {` — Se value e' 0, ritorna la cella vuota.
`    return (` — Apre il return della cella vuota.
`      <div className="aspect-square rounded-lg sm:rounded-xl bg-gray-800/40 border-2 border-dashed border-gray-600/50" />` — Div con stile vuoto e bordo tratteggiato.
`    );` — Chiude return.
`  }` — Chiude if.
`(linea vuota)` — Riga vuota per separare.
`  const palette = TILE_COLORS_BY_MODE[colorPaletteMode];` — Seleziona la palette in base alla modalita'.
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX per la tessera.
`    <div` — Apre il div della tessera.
`      className={``` — Inizia className multiline.
`        aspect-square rounded-lg sm:rounded-xl` — Classi base per dimensione.
`        bg-gradient-to-br ${palette[value] || 'from-gray-500 to-gray-600'}` — Colore di sfondo in base alla palette.
`        shadow-lg flex items-center justify-center text-white font-bold` — Classi di layout e testo.
`        text-lg sm:text-2xl md:text-3xl` — Dimensioni del font.
`        border border-white/20 transition-all duration-300` — Bordo e transizione.
`        ${isMoving ? 'scale-105 sm:scale-110 shadow-2xl ring-2 ring-white/50' : 'scale-100'}` — Stile condizionale se isMoving.
`      ``}` — Chiude template literal.
`    >` — Chiude tag di apertura div.
`      {value}` — Mostra il valore numerico.
`    </div>` — Chiude div.
`  );` — Chiude return.
`});` — Chiude memo.
`(linea vuota)` — Riga vuota per separare.
`export default Tile;` — Esporta Tile di default.

<a id="src-features-puzzle-components-footer-tsx"></a>
## src/features/puzzle/components/Footer.tsx
`import { memo } from 'react';` — Importa memo da React.
`import { useAllStates, useTotalSteps, useInitialGrid } from '@/features/puzzle/store/puzzleSelectors';` — Importa selector per stati.
`import { gridToString } from '@/features/puzzle/utils/puzzle';` — Importa gridToString.
`(linea vuota)` — Riga vuota per separare.
`const Footer = memo(function Footer() {` — Definisce Footer con memo.
`  const allStates = useAllStates();` — Legge allStates.
`  const totalSteps = useTotalSteps();` — Legge totalSteps.
`  const initialGrid = useInitialGrid();` — Legge initialGrid.
`(linea vuota)` — Riga vuota per separare.
`  const initialStr = gridToString(initialGrid);` — Serializza la griglia iniziale.
`  const finalStr = gridToString(allStates[totalSteps]);` — Serializza la griglia finale.
`(linea vuota)` — Riga vuota per separare.
`  return (` — Inizia il return del JSX.
`    <div className="text-center text-gray-500 text-[10px] sm:text-xs pb-4 font-mono">` — Apre il container footer.
`      <span className="text-gray-600">[{initialStr}]</span>` — Mostra la griglia iniziale.
`      <span className="mx-2">â†’</span>` — Mostra una freccia tra le griglie.
`      <span className="text-green-600">[{finalStr}]</span>` — Mostra la griglia finale.
`    </div>` — Chiude container.
`  );` — Chiude return.
`});` — Chiude memo.
`(linea vuota)` — Riga vuota per separare.
`export default Footer;` — Esporta Footer di default.
