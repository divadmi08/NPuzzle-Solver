package com.mistri.puzzle_solver.puzzle.PDB;

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

    private static final int[] PATTERN_A = {1, 2, 3, 4, 5, 6};
    private static final int[] PATTERN_B = {7, 8, 9, 10, 11, 12};
    private static final int[] PATTERN_C = {13, 14, 15};

    private final List<PatternEstimator> databases;

    public PDBLoader() {
        this("");
    }

    public PDBLoader(@Value("${puzzle.pdb.dir:}") String pdbDir) {
        long start = System.nanoTime();
        this.databases = loadDatabases(pdbDir);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        long totalStates = databases.stream().mapToLong(PatternEstimator::stateCount).sum();
        String mode = databases.stream()
                .map(PatternEstimator::description)
                .reduce((a, b) -> a + "; " + b)
                .orElse("none");
        System.out.println("PDB caricati: " + databases.size() + " pattern, " + totalStates + " stati in " + elapsedMs + " ms");
        System.out.println("PDB mode: " + mode);
    }

    public int estimate(int[] tiles, int size, Set<Integer> patternSet) {
        if (size != 4) {
            return 0;
        }

        int sum = 0;
        for (PatternEstimator database : databases) {
            sum += database.estimate(tiles);
        }
        return sum;
    }

    private List<PatternEstimator> loadDatabases(String pdbDir) {
        if (pdbDir != null && !pdbDir.isBlank()) {
            Path directory = Path.of(pdbDir);
            Path first = directory.resolve("pdb-6-6-3-a.bin");
            Path second = directory.resolve("pdb-6-6-3-b.bin");
            Path third = directory.resolve("pdb-6-6-3-c.bin");

            if (Files.isRegularFile(first) && Files.isRegularFile(second) && Files.isRegularFile(third)) {
                try {
                    return List.of(
                            new MappedPatternDatabase(List.of(first), PATTERN_A),
                            new MappedPatternDatabase(List.of(second), PATTERN_B),
                            new MappedPatternDatabase(List.of(third), PATTERN_C)
                    );
                } catch (IOException e) {
                    throw new IllegalStateException("Unable to load external 6-6-3 PDB files from " + directory, e);
                }
            }

            List<Path> firstSegments = loadSegments(directory, "pdb-6-6-3-a");
            List<Path> secondSegments = loadSegments(directory, "pdb-6-6-3-b");
            List<Path> thirdSegments = loadSegments(directory, "pdb-6-6-3-c");
            if (!firstSegments.isEmpty() && !secondSegments.isEmpty() && !thirdSegments.isEmpty()) {
                try {
                    return List.of(
                            new MappedPatternDatabase(firstSegments, PATTERN_A),
                            new MappedPatternDatabase(secondSegments, PATTERN_B),
                            new MappedPatternDatabase(thirdSegments, PATTERN_C)
                    );
                } catch (IOException e) {
                    throw new IllegalStateException("Unable to load segmented 6-6-3 PDB files from " + directory, e);
                }
            }
        }

        return List.of(
                new PatternDatabase(PATTERN_A),
                new PatternDatabase(PATTERN_B),
                new PatternDatabase(PATTERN_C)
        );
    }

    private List<Path> loadSegments(Path directory, String prefix) {
        List<Path> segments = new ArrayList<>();
        for (int i = 0; ; i++) {
            Path segment = directory.resolve(prefix + "." + i + ".bin");
            if (!Files.isRegularFile(segment)) {
                break;
            }
            segments.add(segment);
        }
        return segments;
    }
}
