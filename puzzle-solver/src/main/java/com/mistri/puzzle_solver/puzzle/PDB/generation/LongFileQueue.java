package com.mistri.puzzle_solver.puzzle.PDB.generation;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

final class LongFileQueue implements AutoCloseable {

    private final Path percorso;
    private final RandomAccessFile file;
    private long posizioneLettura;

    LongFileQueue(Path path) throws IOException {
        this.percorso = path;
        Files.createDirectories(path.getParent());
        this.file = new RandomAccessFile(path.toFile(), "rw");
        this.posizioneLettura = 0L;
    }

    void add(long value) throws IOException {
        file.seek(file.length());
        file.writeLong(value);
    }

    boolean hasMore() throws IOException {
        return posizioneLettura < file.length();
    }

    long remove() throws IOException {
        file.seek(posizioneLettura);
        long value = file.readLong();
        posizioneLettura += Long.BYTES;
        return value;
    }

    boolean isEmpty() throws IOException {
        return file.length() == 0L;
    }

    void resetForReuse() throws IOException {
        file.setLength(0L);
        posizioneLettura = 0L;
    }

    void deleteFile() throws IOException {
        close();
        Files.deleteIfExists(percorso);
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}
