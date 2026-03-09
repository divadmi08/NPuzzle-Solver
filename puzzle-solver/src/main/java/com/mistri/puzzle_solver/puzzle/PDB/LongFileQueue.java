package com.mistri.puzzle_solver.puzzle.PDB;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

final class LongFileQueue implements AutoCloseable {

    private final Path path;
    private final RandomAccessFile file;
    private long readPosition;

    LongFileQueue(Path path) throws IOException {
        this.path = path;
        Files.createDirectories(path.getParent());
        this.file = new RandomAccessFile(path.toFile(), "rw");
        this.readPosition = 0L;
    }

    void add(long value) throws IOException {
        file.seek(file.length());
        file.writeLong(value);
    }

    boolean hasMore() throws IOException {
        return readPosition < file.length();
    }

    long remove() throws IOException {
        file.seek(readPosition);
        long value = file.readLong();
        readPosition += Long.BYTES;
        return value;
    }

    boolean isEmpty() throws IOException {
        return file.length() == 0L;
    }

    void resetForReuse() throws IOException {
        file.setLength(0L);
        readPosition = 0L;
    }

    void deleteFile() throws IOException {
        close();
        Files.deleteIfExists(path);
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}
