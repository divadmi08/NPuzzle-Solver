package com.mistri.puzzle_solver.core.model;

import java.util.Arrays;

public class PuzzleState {

    private final int[] tessere;
    private final int dimensione;
    private final int posizioneZero;
    private final int codiceHash;

    public PuzzleState(int[][] griglia) {
        this(appiattisci(griglia), griglia.length);
    }

    private PuzzleState(int[] tessere, int dimensione) {
        this(tessere, dimensione, trovaZero(tessere));
    }

    private PuzzleState(int[] tessere, int dimensione, int posizioneZero) {
        this.tessere = tessere;
        this.dimensione = dimensione;
        this.posizioneZero = posizioneZero;
        this.codiceHash = Arrays.hashCode(tessere);
    }

    private static int[] appiattisci(int[][] griglia) {
        int dimensione = griglia.length;
        int[] lineare = new int[dimensione * dimensione];
        int indice = 0;
        for (int[] riga : griglia) {
            for (int valore : riga) {
                lineare[indice++] = valore;
            }
        }
        return lineare;
    }

    private static int trovaZero(int[] tessere) {
        for (int i = 0; i < tessere.length; i++) {
            if (tessere[i] == 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("Puzzle senza zero");
    }

    public boolean isGoal() {
        int ultimo = tessere.length - 1;
        for (int i = 0; i < ultimo; i++) {
            if (tessere[i] != i + 1) {
                return false;
            }
        }
        return tessere[ultimo] == 0;
    }

    public PuzzleState applicaMossa(Move mossa) {
        int rigaZero = posizioneZero / dimensione;
        int colonnaZero = posizioneZero % dimensione;
        int prossimaRiga = rigaZero + mossa.getDeltaRiga();
        int prossimaColonna = colonnaZero + mossa.getDeltaColonna();
        if (prossimaRiga < 0 || prossimaRiga >= dimensione || prossimaColonna < 0 || prossimaColonna >= dimensione) {
            return null;
        }

        int prossimaPosizioneZero = prossimaRiga * dimensione + prossimaColonna;
        int[] prossimeTessere = tessere.clone();
        prossimeTessere[posizioneZero] = prossimeTessere[prossimaPosizioneZero];
        prossimeTessere[prossimaPosizioneZero] = 0;
        return new PuzzleState(prossimeTessere, dimensione, prossimaPosizioneZero);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PuzzleState altro)) {
            return false;
        }
        return Arrays.equals(tessere, altro.tessere);
    }

    @Override
    public int hashCode() {
        return codiceHash;
    }

    public int getRigaZero() {
        return posizioneZero / dimensione;
    }

    public int getColonnaZero() {
        return posizioneZero % dimensione;
    }

    public int getDimensione() {
        return dimensione;
    }

    public int[][] getGriglia() {
        int[][] griglia = new int[dimensione][dimensione];
        for (int i = 0; i < tessere.length; i++) {
            griglia[i / dimensione][i % dimensione] = tessere[i];
        }
        return griglia;
    }

    public int[] getTessere() {
        return tessere.clone();
    }

    // Returns the internal array for read-only use to avoid copying in hot paths.
    public int[] getTessereSenzaCopia() {
        return tessere;
    }

    public int getTesseraInIndice(int indice) {
        return tessere[indice];
    }

    public int getPosizioneZero() {
        return posizioneZero;
    }

    public boolean isSolvable() {
        int inversioni = contaInversioni(tessere);
        if ((dimensione & 1) == 1) {
            return (inversioni & 1) == 0;
        }

        int rigaVuotaDalBasso = dimensione - (posizioneZero / dimensione);
        boolean vuotaSuRigaPariDalBasso = (rigaVuotaDalBasso & 1) == 0;
        boolean inversioniPari = (inversioni & 1) == 0;
        return vuotaSuRigaPariDalBasso != inversioniPari;

    }

    public static long codifica(int[] tessere) {
        long codificato = 0L;
        for (int i = 0; i < tessere.length; i++) {
            codificato |= ((long) tessere[i] & 0xFL) << (i * 4);
        }
        return codificato;
    }

    private static int contaInversioni(int[] tessere) {
        int inversioni = 0;
        for (int i = 0; i < tessere.length; i++) {
            if (tessere[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < tessere.length; j++) {
                if (tessere[j] != 0 && tessere[i] > tessere[j]) {
                    inversioni++;
                }
            }
        }
        return inversioni;
    }

    @Override
    public String toString() {
        StringBuilder costruttore = new StringBuilder();
        for (int i = 0; i < tessere.length; i++) {
            costruttore.append(tessere[i]).append(' ');
            if ((i + 1) % dimensione == 0) {
                costruttore.append('\n');
            }
        }
        return costruttore.toString();
    }
}
