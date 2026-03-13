package com.mistri.puzzle_solver.PDB.generation;

import com.mistri.puzzle_solver.PDB.runtime.PatternIndexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class SegmentedPatternDatabaseGenerator {

    private static final int DIMENSIONE_GRIGLIA = 4;
    private static final int NUMERO_CELLE = DIMENSIONE_GRIGLIA * DIMENSIONE_GRIGLIA;
    private static final byte NON_VISITATO = (byte) 0xFF;
    private static final int[] DELTA_MOSSE = {-DIMENSIONE_GRIGLIA, DIMENSIONE_GRIGLIA, -1, 1};

    public static void main(String[] argomenti) throws IOException {
        if (argomenti.length < 2 || argomenti.length > 3) {
            throw new IllegalArgumentException(
                    "Usage: SegmentedPatternDatabaseGenerator <output-dir> <comma-separated-tiles> [segment-size-mb]");
        }

        Path cartellaOutput = Path.of(argomenti[0]);
        int[] tessere = leggiTessereDaCsv(argomenti[1]);
        int dimensioneSegmentoMb = argomenti.length == 3 ? Integer.parseInt(argomenti[2]) : 1024;

        PatternIndexer indicizzatore = new PatternIndexer(tessere);
        long numeroStati = indicizzatore.contaStati();
        String prefisso = costruisciPrefisso(tessere);

        System.out.println("Generating PDB for pattern " + Arrays.toString(tessere));
        System.out.println("States: " + numeroStati);
        System.out.println("Output dir: " + cartellaOutput);
        System.out.println("Segment size MB: " + dimensioneSegmentoMb);

        Path cartellaLavoro = cartellaOutput.resolve(prefisso + "-work");
        Files.createDirectories(cartellaLavoro);

        long inizio = System.nanoTime();
        try (MappedDistanceTable distanze = MappedDistanceTable.crea(cartellaOutput, prefisso, numeroStati, dimensioneSegmentoMb);
             LongFileQueue codaCorrente = new LongFileQueue(cartellaLavoro.resolve("current.queue"));
             LongFileQueue codaProssima = new LongFileQueue(cartellaLavoro.resolve("next.queue"))) {

            long rangoObiettivo = indicizzatore.calcolaRangoPosizioni(indicizzatore.posizioniObiettivo());
            distanze.scrivi(rangoObiettivo, (byte) 0);
            codaCorrente.aggiungi(rangoObiettivo);

            int[] posizioniCorrenti = new int[indicizzatore.lunghezzaPosizioni()];
            long visitati = 1L;
            int profondita = 0;

            while (!codaCorrente.isEmpty()) {
                long inizioStrato = System.nanoTime();
                long processatiStrato = 0L;

                while (codaCorrente.hasMore()) {
                    long rango = codaCorrente.rimuovi();
                    processatiStrato++;
                    indicizzatore.ricostruisciPosizioni(rango, posizioniCorrenti);

                    int posizioneVuoto = posizioniCorrenti[0];
                    for (int delta : DELTA_MOSSE) {
                        int prossimaPosizioneVuoto = posizioneVuoto + delta;
                        if (!mossaValida(posizioneVuoto, prossimaPosizioneVuoto, delta)) {
                            continue;
                        }

                        int indicePatternMosso = trovaPatternIn(posizioniCorrenti, prossimaPosizioneVuoto);
                        posizioniCorrenti[0] = prossimaPosizioneVuoto;
                        if (indicePatternMosso >= 0) {
                            posizioniCorrenti[indicePatternMosso] = posizioneVuoto;
                        }

                        long prossimoRango = indicizzatore.calcolaRangoPosizioni(posizioniCorrenti);
                        if (distanze.leggi(prossimoRango) == NON_VISITATO) {
                            int costoArco = indicePatternMosso >= 0 ? 1 : 0;
                            distanze.scrivi(prossimoRango, (byte) (profondita + costoArco));
                            visitati++;

                            if (costoArco == 0) {
                                codaCorrente.aggiungi(prossimoRango);
                            } else {
                                codaProssima.aggiungi(prossimoRango);
                            }
                        }

                        posizioniCorrenti[0] = posizioneVuoto;
                        if (indicePatternMosso >= 0) {
                            posizioniCorrenti[indicePatternMosso] = prossimaPosizioneVuoto;
                        }
                    }
                }

                long msTrascorsi = (System.nanoTime() - inizioStrato) / 1_000_000;
                System.out.println("Depth " + profondita
                        + " processed=" + processatiStrato
                        + " visited=" + visitati
                        + " elapsedMs=" + msTrascorsi);

                codaCorrente.resetPerRiuso();

                if (codaProssima.isEmpty()) {
                    break;
                }

                scambiaCode(codaCorrente, codaProssima);
                profondita++;
            }

            long msTotali = (System.nanoTime() - inizio) / 1_000_000;
            System.out.println("Completed pattern " + Arrays.toString(tessere) + " in " + msTotali + " ms");
            System.out.println("Output segments:");
            for (Path percorso : distanze.percorsiSegmenti()) {
                System.out.println(percorso);
            }
        }
    }

    private static void scambiaCode(LongFileQueue corrente, LongFileQueue prossima) throws IOException {
        while (prossima.hasMore()) {
            corrente.aggiungi(prossima.rimuovi());
        }
        prossima.resetPerRiuso();
    }

    private static String costruisciPrefisso(int[] tessere) {
        if (Arrays.equals(tessere, new int[]{1, 2, 3, 4, 5, 6})) {
            return "pdb-6-6-3-a";
        }
        if (Arrays.equals(tessere, new int[]{7, 8, 9, 10, 11, 12})) {
            return "pdb-6-6-3-b";
        }
        if (Arrays.equals(tessere, new int[]{13, 14, 15})) {
            return "pdb-6-6-3-c";
        }
        return "pdb-custom-" + tessere.length;
    }

    private static int trovaPatternIn(int[] posizioni, int cella) {
        for (int i = 1; i < posizioni.length; i++) {
            if (posizioni[i] == cella) {
                return i;
            }
        }
        return -1;
    }

    private static boolean mossaValida(int posizioneVuoto, int prossimaPosizioneVuoto, int delta) {
        if (prossimaPosizioneVuoto < 0 || prossimaPosizioneVuoto >= NUMERO_CELLE) {
            return false;
        }
        if (delta == -1) {
            return posizioneVuoto % DIMENSIONE_GRIGLIA != 0;
        }
        if (delta == 1) {
            return posizioneVuoto % DIMENSIONE_GRIGLIA != DIMENSIONE_GRIGLIA - 1;
        }
        return true;
    }

    private static int[] leggiTessereDaCsv(String csv) {
        String[] parti = csv.split(",");
        int[] tessere = new int[parti.length];
        for (int i = 0; i < parti.length; i++) {
            tessere[i] = Integer.parseInt(parti[i].trim());
        }
        return tessere;
    }
}
