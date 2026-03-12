package com.mistri.puzzle_solver.PDB.generation;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

final class MappedDistanceTable implements AutoCloseable {

    private static final int DEFAULT_SEGMENT_SIZE_MB = 1024;

    private final long dimensioneSegmento;
    private final List<FileChannel> canali;
    private final List<MappedByteBuffer> buffer;
    private final List<Path> percorsiSegmenti;

    private MappedDistanceTable(long dimensioneSegmento, List<FileChannel> canali,
                                List<MappedByteBuffer> buffer, List<Path> percorsiSegmenti) {
        this.dimensioneSegmento = dimensioneSegmento;
        this.canali = canali;
        this.buffer = buffer;
        this.percorsiSegmenti = percorsiSegmenti;
    }

    static MappedDistanceTable create(Path directory, String prefix, long stateCount) throws IOException {
        return create(directory, prefix, stateCount, DEFAULT_SEGMENT_SIZE_MB);
    }

    static MappedDistanceTable create(Path directory, String prefix, long stateCount, int segmentSizeMb) throws IOException {
        Files.createDirectories(directory);

        long dimensioneSegmento = segmentSizeMb * 1024L * 1024L;
        int numeroSegmenti = (int) ((stateCount + dimensioneSegmento - 1) / dimensioneSegmento);

        List<FileChannel> canali = new ArrayList<>(numeroSegmenti);
        List<MappedByteBuffer> buffer = new ArrayList<>(numeroSegmenti);
        List<Path> percorsi = new ArrayList<>(numeroSegmenti);

        for (int i = 0; i < numeroSegmenti; i++) {
            long rimanenti = stateCount - (long) i * dimensioneSegmento;
            long dimensioneCorrente = Math.min(dimensioneSegmento, rimanenti);
            Path path = directory.resolve(prefix + "." + i + ".bin");
            try (FileChannel initChannel = FileChannel.open(
                    path,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                writeFilled(initChannel, dimensioneCorrente, (byte) 0xFF);
            }

            FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
            MappedByteBuffer bufferMappato = channel.map(FileChannel.MapMode.READ_WRITE, 0, dimensioneCorrente);
            canali.add(channel);
            buffer.add(bufferMappato);
            percorsi.add(path);
        }

        return new MappedDistanceTable(dimensioneSegmento, canali, buffer, percorsi);
    }

    byte get(long index) {
        int indiceSegmento = (int) (index / dimensioneSegmento);
        int offset = (int) (index % dimensioneSegmento);
        return buffer.get(indiceSegmento).get(offset);
    }

    void set(long index, byte value) {
        int indiceSegmento = (int) (index / dimensioneSegmento);
        int offset = (int) (index % dimensioneSegmento);
        buffer.get(indiceSegmento).put(offset, value);
    }

    List<Path> segmentPaths() {
        return List.copyOf(percorsiSegmenti);
    }

    @Override
    public void close() throws IOException {
        IOException failure = null;
        for (FileChannel channel : canali) {
            try {
                channel.close();
            } catch (IOException e) {
                if (failure == null) {
                    failure = e;
                }
            }
        }
        if (failure != null) {
            throw failure;
        }
    }

    private static void writeFilled(FileChannel channel, long size, byte value) throws IOException {
        byte[] blocco = new byte[1 << 20];
        if (value != 0) {
            for (int i = 0; i < blocco.length; i++) {
                blocco[i] = value;
            }
        }

        long scritti = 0;
        while (scritti < size) {
            int length = (int) Math.min(blocco.length, size - scritti);
            channel.write(java.nio.ByteBuffer.wrap(blocco, 0, length));
            scritti += length;
        }
    }
}
