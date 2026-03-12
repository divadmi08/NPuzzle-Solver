package com.mistri.puzzle_solver.PDB.runtime;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

final class MappedPatternDatabase implements PatternEstimator {

    private static final long SEGMENT_SIZE = 1L << 30;

    private final PatternIndexer indicizzatore;
    private final List<MappedByteBuffer> segmenti;
    private final String descrizione;

    MappedPatternDatabase(List<Path> percorsi, int... tessere) throws IOException {
        this.indicizzatore = new PatternIndexer(tessere);
        this.descrizione = "mapped " + indicizzatore.descrizione();

        long dimensioneAttesa = indicizzatore.contaStati();
        long dimensioneTotale = 0;
        List<MappedByteBuffer> mappati = new ArrayList<>();
        for (Path percorso : percorsi) {
            try (FileChannel canale = FileChannel.open(percorso, StandardOpenOption.READ)) {
                dimensioneTotale += canale.size();
                mappati.add(canale.map(FileChannel.MapMode.READ_ONLY, 0, canale.size()));
            }
        }

        if (dimensioneTotale != dimensioneAttesa) {
            throw new IOException("Unexpected segmented PDB size for pattern " + indicizzatore.descrizione()
                    + ": expected " + dimensioneAttesa + " bytes, found " + dimensioneTotale);
        }
        this.segmenti = List.copyOf(mappati);
    }

    @Override
    public int stima(int[] tessere) {
        long rango = indicizzatore.calcolaRangoGriglia(tessere);
        int indiceSegmento = (int) (rango / SEGMENT_SIZE);
        int scostamento = (int) (rango % SEGMENT_SIZE);
        return segmenti.get(indiceSegmento).get(scostamento) & 0xFF;
    }

    @Override
    public long contaStati() {
        return indicizzatore.contaStati();
    }

    @Override
    public String descrizione() {
        return descrizione;
    }
}
