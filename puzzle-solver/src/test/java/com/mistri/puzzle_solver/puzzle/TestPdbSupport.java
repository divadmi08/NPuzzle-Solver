package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.PDB.runtime.PDBLoader;
import com.mistri.puzzle_solver.core.algorithms.Heuristic;

import java.nio.file.Files;
import java.nio.file.Path;

final class TestPdbSupport {
    private static final String DIR_PDB_PREDEFINITA = "pdb";
    private static final String[] PREFISSI_RICHIESTI = {
            "pdb-6-6-3-a",
            "pdb-6-6-3-b",
            "pdb-6-6-3-c"
    };

    static final PDBLoader CARICATORE_PDB = new PDBLoader(risolviDirPdb());
    static final Heuristic EURISTICA = new Heuristic(CARICATORE_PDB);

    private TestPdbSupport() {
    }

    private static String risolviDirPdb() {
        String variabileAmbiente = System.getenv("PUZZLE_PDB_DIR");
        String dirPdb = (variabileAmbiente == null || variabileAmbiente.isBlank()) ? DIR_PDB_PREDEFINITA : variabileAmbiente;
        Path percorso = Path.of(dirPdb).toAbsolutePath();
        verificaFilePdbPresenti(percorso);
        return percorso.toString();
    }

    private static void verificaFilePdbPresenti(Path cartella) {
        if (!Files.isDirectory(cartella)) {
            throw new IllegalStateException("PDB directory not found for tests: " + cartella);
        }
        for (String prefisso : PREFISSI_RICHIESTI) {
            Path singolo = cartella.resolve(prefisso + ".bin");
            Path segmentato = cartella.resolve(prefisso + ".0.bin");
            if (!Files.isRegularFile(singolo) && !Files.isRegularFile(segmentato)) {
                throw new IllegalStateException("Missing PDB files for tests: expected "
                        + singolo + " or " + segmentato);
            }
        }
    }
}
