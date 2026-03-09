package com.mistri.puzzle_solver.puzzle.PDB;

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

    private final long segmentSize;
    private final List<FileChannel> channels;
    private final List<MappedByteBuffer> buffers;
    private final List<Path> segmentPaths;

    private MappedDistanceTable(long segmentSize, List<FileChannel> channels,
                                List<MappedByteBuffer> buffers, List<Path> segmentPaths) {
        this.segmentSize = segmentSize;
        this.channels = channels;
        this.buffers = buffers;
        this.segmentPaths = segmentPaths;
    }

    static MappedDistanceTable create(Path directory, String prefix, long stateCount) throws IOException {
        return create(directory, prefix, stateCount, DEFAULT_SEGMENT_SIZE_MB);
    }

    static MappedDistanceTable create(Path directory, String prefix, long stateCount, int segmentSizeMb) throws IOException {
        Files.createDirectories(directory);

        long segmentSize = segmentSizeMb * 1024L * 1024L;
        int segmentCount = (int) ((stateCount + segmentSize - 1) / segmentSize);

        List<FileChannel> channels = new ArrayList<>(segmentCount);
        List<MappedByteBuffer> buffers = new ArrayList<>(segmentCount);
        List<Path> paths = new ArrayList<>(segmentCount);

        for (int i = 0; i < segmentCount; i++) {
            long remaining = stateCount - (long) i * segmentSize;
            long currentSegmentSize = Math.min(segmentSize, remaining);
            Path path = directory.resolve(prefix + "." + i + ".bin");
            try (FileChannel initChannel = FileChannel.open(
                    path,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                writeFilled(initChannel, currentSegmentSize, (byte) 0xFF);
            }

            FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, currentSegmentSize);
            channels.add(channel);
            buffers.add(buffer);
            paths.add(path);
        }

        return new MappedDistanceTable(segmentSize, channels, buffers, paths);
    }

    byte get(long index) {
        int segmentIndex = (int) (index / segmentSize);
        int offset = (int) (index % segmentSize);
        return buffers.get(segmentIndex).get(offset);
    }

    void set(long index, byte value) {
        int segmentIndex = (int) (index / segmentSize);
        int offset = (int) (index % segmentSize);
        buffers.get(segmentIndex).put(offset, value);
    }

    List<Path> segmentPaths() {
        return List.copyOf(segmentPaths);
    }

    @Override
    public void close() throws IOException {
        IOException failure = null;
        for (FileChannel channel : channels) {
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
        byte[] chunk = new byte[1 << 20];
        if (value != 0) {
            for (int i = 0; i < chunk.length; i++) {
                chunk[i] = value;
            }
        }

        long written = 0;
        while (written < size) {
            int length = (int) Math.min(chunk.length, size - written);
            channel.write(java.nio.ByteBuffer.wrap(chunk, 0, length));
            written += length;
        }
    }
}
