package com.mistri.puzzle_solver.PDB.generation;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

final class LongFileQueue implements AutoCloseable {

    private final Path percorso;
    private final RandomAccessFile file;
    private long posizioneLettura;

    LongFileQueue(Path percorso) throws IOException {
        this.percorso = percorso;
        Files.createDirectories(percorso.getParent());
        this.file = new RandomAccessFile(percorso.toFile(), "rw");
        this.posizioneLettura = 0L;
    }

    void aggiungi(long valore) throws IOException {
        file.seek(file.length());
        file.writeLong(valore);
    }

    boolean hasMore() throws IOException {
        return posizioneLettura < file.length();
    }

    long rimuovi() throws IOException {
        file.seek(posizioneLettura);
        long valore = file.readLong();
        posizioneLettura += Long.BYTES;
        return valore;
    }

    boolean isEmpty() throws IOException {
        return file.length() == 0L;
    }

    void resetPerRiuso() throws IOException {
        file.setLength(0L);
        posizioneLettura = 0L;
    }

    void eliminaFile() throws IOException {
        close();
        Files.deleteIfExists(percorso);
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}
