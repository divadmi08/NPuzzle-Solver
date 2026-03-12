package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.PDB.runtime.PDBLoader;
import com.mistri.puzzle_solver.core.algorithms.Heuristic;

import java.nio.file.Files;
import java.nio.file.Path;

final class TestPdbSupport {
    private static final String DEFAULT_PDB_DIR = "pdb";
    private static final String[] REQUIRED_PREFIXES = {
            "pdb-6-6-3-a",
            "pdb-6-6-3-b",
            "pdb-6-6-3-c"
    };

    static final PDBLoader PDB_LOADER = new PDBLoader(resolvePdbDir());
    static final Heuristic HEURISTIC = new Heuristic(PDB_LOADER);

    private TestPdbSupport() {
    }

    private static String resolvePdbDir() {
        String env = System.getenv("PUZZLE_PDB_DIR");
        String dir = (env == null || env.isBlank()) ? DEFAULT_PDB_DIR : env;
        Path path = Path.of(dir).toAbsolutePath();
        ensurePdbFilesPresent(path);
        return path.toString();
    }

    private static void ensurePdbFilesPresent(Path directory) {
        if (!Files.isDirectory(directory)) {
            throw new IllegalStateException("PDB directory not found for tests: " + directory);
        }
        for (String prefix : REQUIRED_PREFIXES) {
            Path single = directory.resolve(prefix + ".bin");
            Path segmented = directory.resolve(prefix + ".0.bin");
            if (!Files.isRegularFile(single) && !Files.isRegularFile(segmented)) {
                throw new IllegalStateException("Missing PDB files for tests: expected "
                        + single + " or " + segmented);
            }
        }
    }
}
