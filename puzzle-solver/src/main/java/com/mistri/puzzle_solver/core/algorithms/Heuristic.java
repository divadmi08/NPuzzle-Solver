package com.mistri.puzzle_solver.core.algorithms;

import com.mistri.puzzle_solver.core.model.PuzzleState;
import org.springframework.stereotype.Component;
import com.mistri.puzzle_solver.PDB.runtime.PDBLoader;

@Component
public class Heuristic {

    private final PDBLoader caricatorePdb;

    public Heuristic(PDBLoader caricatorePdb) {
        this.caricatorePdb = caricatorePdb;
    }

    public int stima(PuzzleState stato) {
        return stima(stato.getTessereSenzaCopia(), stato.getDimensione());
    }

    public int stima(int[] tessere, int dimensione) {
        int distanzaManhattan = distanzaManhattan(tessere, dimensione);
        int conflittoLineare = conflittoLineare(tessere, dimensione);
        int valorePdb = stimaPdb(tessere, dimensione);
        return Math.max(distanzaManhattan + conflittoLineare, valorePdb);
    }

    private int distanzaManhattan(int[] tessere, int dimensione) {
        int distanza = 0;
        for (int indice = 0; indice < dimensione * dimensione; indice++) {
            int valore = tessere[indice];
            if (valore == 0) {
                continue;
            }
            int riga = indice / dimensione;
            int colonna = indice % dimensione;
            int rigaObiettivo = (valore - 1) / dimensione;
            int colonnaObiettivo = (valore - 1) % dimensione;
            distanza += Math.abs(riga - rigaObiettivo) + Math.abs(colonna - colonnaObiettivo);
        }
        return distanza;
    }

    private int conflittoLineare(int[] tessere, int dimensione) {
        int conflitti = 0;

        for (int riga = 0; riga < dimensione; riga++) {
            for (int colonnaA = 0; colonnaA < dimensione; colonnaA++) {
                int tesseraA = tessere[riga * dimensione + colonnaA];
                if (tesseraA == 0 || rigaObiettivo(tesseraA, dimensione) != riga) {
                    continue;
                }
                int colonnaObiettivoA = colonnaObiettivo(tesseraA, dimensione);
                for (int colonnaB = colonnaA + 1; colonnaB < dimensione; colonnaB++) {
                    int tesseraB = tessere[riga * dimensione + colonnaB];
                    if (tesseraB == 0 || rigaObiettivo(tesseraB, dimensione) != riga) {
                        continue;
                    }
                    if (colonnaObiettivoA > colonnaObiettivo(tesseraB, dimensione)) {
                        conflitti++;
                    }
                }
            }
        }

        for (int colonna = 0; colonna < dimensione; colonna++) {
            for (int rigaA = 0; rigaA < dimensione; rigaA++) {
                int tesseraA = tessere[rigaA * dimensione + colonna];
                if (tesseraA == 0 || colonnaObiettivo(tesseraA, dimensione) != colonna) {
                    continue;
                }
                int rigaObiettivoA = rigaObiettivo(tesseraA, dimensione);
                for (int rigaB = rigaA + 1; rigaB < dimensione; rigaB++) {
                    int tesseraB = tessere[rigaB * dimensione + colonna];
                    if (tesseraB == 0 || colonnaObiettivo(tesseraB, dimensione) != colonna) {
                        continue;
                    }
                    if (rigaObiettivoA > rigaObiettivo(tesseraB, dimensione)) {
                        conflitti++;
                    }
                }
            }
        }

        return conflitti * 2;
    }

    private int rigaObiettivo(int tessera, int dimensione) {
        return (tessera - 1) / dimensione;
    }

    private int colonnaObiettivo(int tessera, int dimensione) {
        return (tessera - 1) % dimensione;
    }

    private int stimaPdb(int[] tessere, int dimensione) {
        return caricatorePdb.stima(tessere, dimensione);
    }
}
