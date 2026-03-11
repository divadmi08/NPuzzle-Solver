package com.mistri.puzzle_solver.puzzle.PDB.generation;

import com.mistri.puzzle_solver.puzzle.PDB.runtime.PatternIndexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class SegmentedPatternDatabaseGenerator {

    private static final int DIMENSIONE_GRIGLIA = 4;
    private static final int NUMERO_CELLE = DIMENSIONE_GRIGLIA * DIMENSIONE_GRIGLIA;
    private static final byte NON_VISITATO = (byte) 0xFF;
    private static final int[] DELTA_MOSSE = {-DIMENSIONE_GRIGLIA, DIMENSIONE_GRIGLIA, -1, 1};

    public static void main(String[] args) throws IOException {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException(
                    "Usage: SegmentedPatternDatabaseGenerator <output-dir> <comma-separated-tiles> [segment-size-mb]");
        }

        Path directoryOutput = Path.of(args[0]);
        int[] tessere = parseTessere(args[1]);
        int dimensioneSegmentoMb = args.length == 3 ? Integer.parseInt(args[2]) : 1024;

        PatternIndexer indicizzatore = new PatternIndexer(tessere);
        long numeroStati = indicizzatore.stateCount();
        String prefisso = costruisciPrefisso(tessere);

        System.out.println("Generating PDB for pattern " + Arrays.toString(tessere));
        System.out.println("States: " + numeroStati);
        System.out.println("Output dir: " + directoryOutput);
        System.out.println("Segment size MB: " + dimensioneSegmentoMb);

        Path directoryLavoro = directoryOutput.resolve(prefisso + "-work");
        Files.createDirectories(directoryLavoro);

        long start = System.nanoTime();
        try (MappedDistanceTable distanze = MappedDistanceTable.create(directoryOutput, prefisso, numeroStati, dimensioneSegmentoMb);
             LongFileQueue codaCorrente = new LongFileQueue(directoryLavoro.resolve("current.queue"));
             LongFileQueue codaProssima = new LongFileQueue(directoryLavoro.resolve("next.queue"))) {

            long rankGoal = indicizzatore.rankPositions(indicizzatore.goalPositions());
            distanze.set(rankGoal, (byte) 0);
            codaCorrente.add(rankGoal);

            int[] posizioniCorrenti = new int[indicizzatore.positionsLength()];
            long visitati = 1L;
            int profondita = 0;

            while (!codaCorrente.isEmpty()) {
                long inizioLayer = System.nanoTime();
                long processatiLayer = 0L;

                while (codaCorrente.hasMore()) {
                    long rank = codaCorrente.remove();
                    processatiLayer++;
                    indicizzatore.unrank(rank, posizioniCorrenti);

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

                        long prossimoRank = indicizzatore.rankPositions(posizioniCorrenti);
                        if (distanze.get(prossimoRank) == NON_VISITATO) {
                            int costoArco = indicePatternMosso >= 0 ? 1 : 0;
                            distanze.set(prossimoRank, (byte) (profondita + costoArco));
                            visitati++;

                            if (costoArco == 0) {
                                codaCorrente.add(prossimoRank);
                            } else {
                                codaProssima.add(prossimoRank);
                            }
                        }

                        posizioniCorrenti[0] = posizioneVuoto;
                        if (indicePatternMosso >= 0) {
                            posizioniCorrenti[indicePatternMosso] = prossimaPosizioneVuoto;
                        }
                    }
                }

                long elapsedMs = (System.nanoTime() - inizioLayer) / 1_000_000;
                System.out.println("Depth " + profondita
                        + " processed=" + processatiLayer
                        + " visited=" + visitati
                        + " elapsedMs=" + elapsedMs);

                codaCorrente.resetForReuse();

                if (codaProssima.isEmpty()) {
                    break;
                }

                scambiaCode(codaCorrente, codaProssima);
                profondita++;
            }

            long totalMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Completed pattern " + Arrays.toString(tessere) + " in " + totalMs + " ms");
            System.out.println("Output segments:");
            for (Path path : distanze.segmentPaths()) {
                System.out.println(path);
            }
        }
    }

    private static void scambiaCode(LongFileQueue corrente, LongFileQueue prossima) throws IOException {
        while (prossima.hasMore()) {
            corrente.add(prossima.remove());
        }
        prossima.resetForReuse();
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

    private static int[] parseTessere(String csv) {
        String[] parti = csv.split(",");
        int[] tessere = new int[parti.length];
        for (int i = 0; i < parti.length; i++) {
            tessere[i] = Integer.parseInt(parti[i].trim());
        }
        return tessere;
    }
}
