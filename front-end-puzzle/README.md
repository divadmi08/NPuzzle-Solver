# Front-end NPuzzle Solver

Frontend web del progetto NPuzzle (3x3 e 4x4), realizzato con Next.js + React + TypeScript.

## Cosa fa questa app

- Mostra una Home da cui accedere a Gioco, Tabella Manuale e Impostazioni.
- Permette gioco manuale con contatore mosse e timer.
- Mostra il confronto tra mosse utente e minimo ottimale calcolato dal backend.
- Supporta replay automatico della soluzione.
- Supporta inserimento di board personalizzata con validazione input.

## Prerequisiti

- Node.js 20+
- pnpm
- Backend avviato su `http://localhost:8080` (default)

## Installazione e avvio

```bash
pnpm i
pnpm dev
```

Frontend disponibile su `http://localhost:3000`.

## Come giocare dal frontend

1. Vai su Home.
2. Apri Gioco.
3. Seleziona 3x3 o 4x4.
4. Muovi le tessere adiacenti allo spazio vuoto.
5. Controlla tempo e numero mosse in alto.
6. Se vuoi la soluzione automatica, premi Arrenditi.

## Configurazione backend URL

L'API usa per default:

- `http://localhost:8080/puzzle`

Puoi cambiare con variabile ambiente:

- `BACKEND_API_URL`

## Build produzione

```bash
pnpm build
pnpm start
```

## Documentazione tecnica

- Guida frontend: `front-end-documentation.md`
- Guida API/store dettagliata: `front-end-api-store-line-by-line.md`
