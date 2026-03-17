# NPuzzle Solver

Progetto full-stack per giocare e risolvere il classico puzzle scorrevole (8-puzzle e 15-puzzle).

Il repository contiene:
- frontend web (Next.js + React + TypeScript)
- backend API (Spring Boot, solver con A* e IDA*)

## Cosa fa il gioco

- Genera puzzle 3x3 o 4x4 risolvibili.
- Ti permette di giocare manualmente spostando le tessere.
- Mostra contatore mosse e tempo.
- Confronta le tue mosse con il minimo calcolato dal backend.
- Se ti arrendi, calcola e mostra il replay della soluzione.
- Permette anche inserimento di una tabella personalizzata.

## Prerequisiti

- Git
- Node.js 20+
- pnpm
- Java 25 (coerente con `puzzle-solver/pom.xml`)

## Come scaricare il progetto

```bash
git clone <URL_DEL_TUO_REPOSITORY>
cd NPuzzle-Solver
```

## Avvio rapido (frontend + backend)

Apri due terminali.

Terminale 1 (backend):

```bash
cd puzzle-solver
./mvnw spring-boot:run
```

Su Windows PowerShell:

```powershell
cd puzzle-solver
.\mvnw.cmd spring-boot:run
```

Terminale 2 (frontend):

```bash
cd front-end-puzzle
pnpm i
pnpm dev
```

Poi apri:
- frontend: `http://localhost:3000`
- backend API: `http://localhost:8080`

## Come giocare

1. Apri la Home del frontend.
2. Entra in Gioco.
3. Scegli 3x3 o 4x4.
4. Muovi le tessere adiacenti allo spazio vuoto.
5. Se vuoi, usa Arrenditi per vedere la soluzione automatica.
6. In Tabella Manuale puoi inserire uno stato personalizzato e risolverlo.

## Documentazione interna

- Frontend tecnico: `front-end-puzzle/front-end-documentation.md`
- Frontend API/store dettagliato: `front-end-puzzle/front-end-api-store-line-by-line.md`
- Backend tecnico: `backend-documentation.md`
