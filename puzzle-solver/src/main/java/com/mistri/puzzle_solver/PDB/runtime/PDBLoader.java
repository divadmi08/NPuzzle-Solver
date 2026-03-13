package com.mistri.puzzle_solver.PDB.runtime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public PDBLoader(@Value("${puzzle.pdb.dir:pdb}") String dirPdb) {
        long inizio = System.nanoTime();
        this.basiDati = caricaBasiDati(dirPdb);
        this.msCaricamento = (System.nanoTime() - inizio) / 1_000_000;
        this.statiTotali = basiDati.stream().mapToLong(PatternEstimator::contaStati).sum();
        this.descrizioneModalita = basiDati.stream()
                .map(PatternEstimator::descrizione)
                .reduce((a, b) -> a + "; " + b)
                .orElse("none");
        System.out.println("PDB caricati: " + basiDati.size() + " pattern, " + statiTotali + " stati in " + msCaricamento + " ms");
        System.out.println("PDB mode: " + descrizioneModalita);
    }

    public int stima(int[] tessere, int dimensione) {
        if (dimensione != 4) {
            return 0;
        }

        int somma = 0;
        for (PatternEstimator stimatore : basiDati) {
            somma += stimatore.stima(tessere);
        }
        return somma;
    }

    private List<PatternEstimator> caricaBasiDati(String dirPdb) {
        String dirEffettiva = (dirPdb == null || dirPdb.isBlank()) ? "pdb" : dirPdb;
        Path cartella = Path.of(dirEffettiva);

        List<Path> fileSingoli = List.of(
                cartella.resolve("pdb-6-6-3-a.bin"),
                cartella.resolve("pdb-6-6-3-b.bin"),
                cartella.resolve("pdb-6-6-3-c.bin")
        );
        if (tuttiPresenti(fileSingoli)) {
            return creaDaFileSingoli(fileSingoli);
        }

        List<Path> segmentiA = caricaSegmenti(cartella, "pdb-6-6-3-a");
        List<Path> segmentiB = caricaSegmenti(cartella, "pdb-6-6-3-b");
        List<Path> segmentiC = caricaSegmenti(cartella, "pdb-6-6-3-c");
        if (!segmentiA.isEmpty() && !segmentiB.isEmpty() && !segmentiC.isEmpty()) {
            return creaDaSegmenti(segmentiA, segmentiB, segmentiC);
        }

        throw new IllegalStateException("PDB files not found in " + cartella
                + ". Expected pdb-6-6-3-a.bin/b/c.bin or segmented pdb-6-6-3-a.0.bin, etc.");
    }

    private boolean tuttiPresenti(List<Path> file) {
        for (Path percorsoFile : file) {
            if (!Files.isRegularFile(percorsoFile)) {
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

    private List<Path> caricaSegmenti(Path cartella, String prefisso) {
        List<Path> segmenti = new ArrayList<>();
        for (int i = 0; ; i++) {
            Path segmento = cartella.resolve(prefisso + "." + i + ".bin");
            if (!Files.isRegularFile(segmento)) {
                break;
            }
            segmenti.add(segmento);
        }
        return segmenti;
    }

    public long getMsCaricamento() {
        return msCaricamento;
    }

    public long getStatiTotali() {
        return statiTotali;
    }

    public String getDescrizioneModalita() {
        return descrizioneModalita;
    }
}
