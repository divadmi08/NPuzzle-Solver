package com.mistri.puzzle_solver.PDB.runtime;

import java.util.Arrays;

public final class PatternIndexer {

    private static final int CELL_COUNT = 16;

    private final int[] tessere;
    private final int[] indiceTesseraNelPattern;
    private final long[] basiPermutazioni;

    public PatternIndexer(int... tiles) {
        this.tessere = tiles.clone();
        Arrays.sort(this.tessere);
        this.indiceTesseraNelPattern = creaIndiceTessera(this.tessere);
        this.basiPermutazioni = creaBasiPermutazioni(this.tessere.length + 1);
    }

    public long rankBoard(int[] board) {
        int[] posizioni = new int[tessere.length + 1];
        for (int index = 0; index < board.length; index++) {
            int value = board[index];
            if (value == 0) {
                posizioni[0] = index;
            } else {
                int indicePattern = indiceTesseraNelPattern[value];
                if (indicePattern >= 0) {
                    posizioni[indicePattern + 1] = index;
                }
            }
        }
        return rankPositions(posizioni);
    }

    public long rankPositions(int[] positions) {
        int[] disponibili = creaCelleDisponibili();
        long rank = 0;

        for (int i = 0; i < positions.length; i++) {
            int indiceDisponibile = indiceDi(disponibili, CELL_COUNT - i, positions[i]);
            rank += indiceDisponibile * basiPermutazioni[i];
            rimuoviA(disponibili, CELL_COUNT - i, indiceDisponibile);
        }

        return rank;
    }

    public void unrank(long rank, int[] positions) {
        int[] disponibili = creaCelleDisponibili();
        long rankRimanente = rank;

        for (int i = 0; i < positions.length; i++) {
            long base = basiPermutazioni[i];
            int indiceDisponibile = (int) (rankRimanente / base);
            rankRimanente %= base;
            positions[i] = disponibili[indiceDisponibile];
            rimuoviA(disponibili, CELL_COUNT - i, indiceDisponibile);
        }
    }

    public long stateCount() {
        return basiPermutazioni[0] * CELL_COUNT;
    }

    public int positionsLength() {
        return tessere.length + 1;
    }

    public int[] goalPositions() {
        int[] posizioni = new int[tessere.length + 1];
        posizioni[0] = CELL_COUNT - 1;
        for (int i = 0; i < tessere.length; i++) {
            posizioni[i + 1] = tessere[i] - 1;
        }
        return posizioni;
    }

    public String description() {
        return Arrays.toString(tessere);
    }

    private static int[] creaIndiceTessera(int[] tesserePattern) {
        int[] mappa = new int[CELL_COUNT];
        Arrays.fill(mappa, -1);
        for (int i = 0; i < tesserePattern.length; i++) {
            mappa[tesserePattern[i]] = i;
        }
        return mappa;
    }

    private static long[] creaBasiPermutazioni(int numeroElementi) {
        long[] basi = new long[numeroElementi];
        for (int i = 0; i < numeroElementi; i++) {
            long permutations = 1;
            for (int j = i + 1; j < numeroElementi; j++) {
                permutations *= CELL_COUNT - j;
            }
            basi[i] = permutations;
        }
        return basi;
    }

    private static int[] creaCelleDisponibili() {
        int[] disponibili = new int[CELL_COUNT];
        for (int i = 0; i < CELL_COUNT; i++) {
            disponibili[i] = i;
        }
        return disponibili;
    }

    private static int indiceDi(int[] valori, int lunghezza, int target) {
        for (int i = 0; i < lunghezza; i++) {
            if (valori[i] == target) {
                return i;
            }
        }
        throw new IllegalStateException("Target cell not found while ranking pattern database state");
    }

    private static void rimuoviA(int[] valori, int lunghezza, int indice) {
        for (int i = indice; i < lunghezza - 1; i++) {
            valori[i] = valori[i + 1];
        }
    }
}
