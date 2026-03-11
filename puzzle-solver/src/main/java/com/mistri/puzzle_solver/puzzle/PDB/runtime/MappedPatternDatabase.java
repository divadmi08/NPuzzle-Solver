package com.mistri.puzzle_solver.puzzle.PDB.runtime;

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

    MappedPatternDatabase(List<Path> paths, int... tiles) throws IOException {
        this.indicizzatore = new PatternIndexer(tiles);
        this.descrizione = "mapped " + indicizzatore.description();

        long dimensioneAttesa = indicizzatore.stateCount();
        long dimensioneTotale = 0;
        List<MappedByteBuffer> mappati = new ArrayList<>();
        for (Path path : paths) {
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
                dimensioneTotale += channel.size();
                mappati.add(channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size()));
            }
        }

        if (dimensioneTotale != dimensioneAttesa) {
            throw new IOException("Unexpected segmented PDB size for pattern " + indicizzatore.description()
                    + ": expected " + dimensioneAttesa + " bytes, found " + dimensioneTotale);
        }
        this.segmenti = List.copyOf(mappati);
    }

    @Override
    public int estimate(int[] board) {
        long rank = indicizzatore.rankBoard(board);
        int indiceSegmento = (int) (rank / SEGMENT_SIZE);
        int offset = (int) (rank % SEGMENT_SIZE);
        return segmenti.get(indiceSegmento).get(offset) & 0xFF;
    }

    @Override
    public long stateCount() {
        return indicizzatore.stateCount();
    }

    @Override
    public String description() {
        return descrizione;
    }
}
