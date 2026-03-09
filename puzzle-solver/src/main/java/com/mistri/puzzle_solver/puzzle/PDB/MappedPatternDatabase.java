package com.mistri.puzzle_solver.puzzle.PDB;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

final class MappedPatternDatabase implements PatternEstimator {

    private static final long SEGMENT_SIZE = 1L << 30;

    private final PatternIndexer indexer;
    private final List<MappedByteBuffer> segments;
    private final String description;

    MappedPatternDatabase(List<Path> paths, int... tiles) throws IOException {
        this.indexer = new PatternIndexer(tiles);
        this.description = "mapped " + indexer.description();

        long expectedSize = indexer.stateCount();
        long totalSize = 0;
        List<MappedByteBuffer> mapped = new ArrayList<>();
        for (Path path : paths) {
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
                totalSize += channel.size();
                mapped.add(channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size()));
            }
        }

        if (totalSize != expectedSize) {
            throw new IOException("Unexpected segmented PDB size for pattern " + indexer.description()
                    + ": expected " + expectedSize + " bytes, found " + totalSize);
        }
        this.segments = List.copyOf(mapped);
    }

    @Override
    public int estimate(int[] board) {
        long rank = indexer.rankBoard(board);
        int segmentIndex = (int) (rank / SEGMENT_SIZE);
        int offset = (int) (rank % SEGMENT_SIZE);
        return segments.get(segmentIndex).get(offset) & 0xFF;
    }

    @Override
    public long stateCount() {
        return indexer.stateCount();
    }

    @Override
    public String description() {
        return description;
    }
}
