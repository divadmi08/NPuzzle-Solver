package com.mistri.puzzle_solver.PDB.runtime;

import java.util.Arrays;

public final class PatternIndexer {

    private static final int NUMERO_CELLE = 16;

    private final int[] tessere;
    private final int[] indiceTesseraNelPattern;
    private final long[] basiPermutazioni;

    public PatternIndexer(int... tesserePattern) {
        this.tessere = tesserePattern.clone();
        Arrays.sort(this.tessere);
        this.indiceTesseraNelPattern = creaIndiceTessera(this.tessere);
        this.basiPermutazioni = creaBasiPermutazioni(this.tessere.length + 1);
    }

    public long calcolaRangoGriglia(int[] griglia) {
        int[] posizioni = new int[tessere.length + 1];
        for (int indice = 0; indice < griglia.length; indice++) {
            int valore = griglia[indice];
            if (valore == 0) {
                posizioni[0] = indice;
            } else {
                int indicePattern = indiceTesseraNelPattern[valore];
                if (indicePattern >= 0) {
                    posizioni[indicePattern + 1] = indice;
                }
            }
        }
        return calcolaRangoPosizioni(posizioni);
    }

    public long calcolaRangoPosizioni(int[] posizioni) {
        int[] disponibili = creaCelleDisponibili();
        long rango = 0;

        for (int i = 0; i < posizioni.length; i++) {
            int indiceDisponibile = indiceDi(disponibili, NUMERO_CELLE - i, posizioni[i]);
            rango += indiceDisponibile * basiPermutazioni[i];
            rimuoviA(disponibili, NUMERO_CELLE - i, indiceDisponibile);
        }

        return rango;
    }

    public void ricostruisciPosizioni(long rango, int[] posizioni) {
        int[] disponibili = creaCelleDisponibili();
        long rangoRimanente = rango;

        for (int i = 0; i < posizioni.length; i++) {
            long base = basiPermutazioni[i];
            int indiceDisponibile = (int) (rangoRimanente / base);
            rangoRimanente %= base;
            posizioni[i] = disponibili[indiceDisponibile];
            rimuoviA(disponibili, NUMERO_CELLE - i, indiceDisponibile);
        }
    }

    public long contaStati() {
        return basiPermutazioni[0] * NUMERO_CELLE;
    }

    public int lunghezzaPosizioni() {
        return tessere.length + 1;
    }

    public int[] posizioniObiettivo() {
        int[] posizioni = new int[tessere.length + 1];
        posizioni[0] = NUMERO_CELLE - 1;
        for (int i = 0; i < tessere.length; i++) {
            posizioni[i + 1] = tessere[i] - 1;
        }
        return posizioni;
    }

    public String descrizione() {
        return Arrays.toString(tessere);
    }

    private static int[] creaIndiceTessera(int[] tesserePattern) {
        int[] mappa = new int[NUMERO_CELLE];
        Arrays.fill(mappa, -1);
        for (int i = 0; i < tesserePattern.length; i++) {
            mappa[tesserePattern[i]] = i;
        }
        return mappa;
    }

    private static long[] creaBasiPermutazioni(int numeroElementi) {
        long[] basi = new long[numeroElementi];
        for (int i = 0; i < numeroElementi; i++) {
            long permutazioni = 1;
            for (int j = i + 1; j < numeroElementi; j++) {
                permutazioni *= NUMERO_CELLE - j;
            }
            basi[i] = permutazioni;
        }
        return basi;
    }

    private static int[] creaCelleDisponibili() {
        int[] disponibili = new int[NUMERO_CELLE];
        for (int i = 0; i < NUMERO_CELLE; i++) {
            disponibili[i] = i;
        }
        return disponibili;
    }

    private static int indiceDi(int[] valori, int lunghezza, int valoreCercato) {
        for (int i = 0; i < lunghezza; i++) {
            if (valori[i] == valoreCercato) {
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
