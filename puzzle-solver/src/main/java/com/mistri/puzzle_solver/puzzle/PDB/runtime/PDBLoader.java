package com.mistri.puzzle_solver.puzzle.PDB.runtime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PDBLoader {

    private static final int[] TESSERE_A = {1, 2, 3, 4, 5, 6};
    private static final int[] TESSERE_B = {7, 8, 9, 10, 11, 12};
    private static final int[] TESSERE_C = {13, 14, 15};

    private final List<PatternEstimator> basiDati;
    private final long msCaricamento;
    private final long statiTotali;
    private final String descrizioneModalita;

    public PDBLoader() {
        this("");
    }

    public PDBLoader(@Value("${puzzle.pdb.dir:pdb}") String pdbDir) {
        long start = System.nanoTime();
        this.basiDati = caricaBasiDati(pdbDir);
        this.msCaricamento = (System.nanoTime() - start) / 1_000_000;
        this.statiTotali = basiDati.stream().mapToLong(PatternEstimator::stateCount).sum();
        this.descrizioneModalita = basiDati.stream()
                .map(PatternEstimator::description)
                .reduce((a, b) -> a + "; " + b)
                .orElse("none");
        System.out.println("PDB caricati: " + basiDati.size() + " pattern, " + statiTotali + " stati in " + msCaricamento + " ms");
        System.out.println("PDB mode: " + descrizioneModalita);
    }

    public int estimate(int[] tiles, int size, Set<Integer> patternSet) {
        if (size != 4) {
            return 0;
        }

        int sum = 0;
        for (PatternEstimator db : basiDati) {
            sum += db.estimate(tiles);
        }
        return sum;
    }

    private List<PatternEstimator> caricaBasiDati(String dirPdb) {
        String dirRisolta = (dirPdb == null || dirPdb.isBlank()) ? "pdb" : dirPdb;
        Path directory = Path.of(dirRisolta);

        List<Path> fileSingoli = List.of(
                directory.resolve("pdb-6-6-3-a.bin"),
                directory.resolve("pdb-6-6-3-b.bin"),
                directory.resolve("pdb-6-6-3-c.bin")
        );
        if (tuttiPresenti(fileSingoli)) {
            return creaDaFileSingoli(fileSingoli);
        }

        List<Path> segmentiA = caricaSegmenti(directory, "pdb-6-6-3-a");
        List<Path> segmentiB = caricaSegmenti(directory, "pdb-6-6-3-b");
        List<Path> segmentiC = caricaSegmenti(directory, "pdb-6-6-3-c");
        if (!segmentiA.isEmpty() && !segmentiB.isEmpty() && !segmentiC.isEmpty()) {
            return creaDaSegmenti(segmentiA, segmentiB, segmentiC);
        }

        throw new IllegalStateException("PDB files not found in " + directory
                + ". Expected pdb-6-6-3-a.bin/b/c.bin or segmented pdb-6-6-3-a.0.bin, etc.");
    }

    private boolean tuttiPresenti(List<Path> files) {
        for (Path file : files) {
            if (!Files.isRegularFile(file)) {
                return false;
            }
        }
        return true;
    }

    private List<PatternEstimator> creaDaFileSingoli(List<Path> fileSingoli) {
        try {
            return List.of(
                    new MappedPatternDatabase(List.of(fileSingoli.get(0)), TESSERE_A),
                    new MappedPatternDatabase(List.of(fileSingoli.get(1)), TESSERE_B),
                    new MappedPatternDatabase(List.of(fileSingoli.get(2)), TESSERE_C)
            );
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load external 6-6-3 PDB files from " + fileSingoli.get(0).getParent(), e);
        }
    }

    private List<PatternEstimator> creaDaSegmenti(List<Path> segmentiA, List<Path> segmentiB, List<Path> segmentiC) {
        try {
            return List.of(
                    new MappedPatternDatabase(segmentiA, TESSERE_A),
                    new MappedPatternDatabase(segmentiB, TESSERE_B),
                    new MappedPatternDatabase(segmentiC, TESSERE_C)
            );
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load segmented 6-6-3 PDB files from " + segmentiA.get(0).getParent(), e);
        }
    }

    private List<Path> caricaSegmenti(Path directory, String prefix) {
        List<Path> segmenti = new ArrayList<>();
        for (int i = 0; ; i++) {
            Path segment = directory.resolve(prefix + "." + i + ".bin");
            if (!Files.isRegularFile(segment)) {
                break;
            }
            segmenti.add(segment);
        }
        return segmenti;
    }

    public long getLoadElapsedMs() {
        return msCaricamento;
    }

    public long getTotalStates() {
        return statiTotali;
    }

    public String getModeDescription() {
        return descrizioneModalita;
    }
}
