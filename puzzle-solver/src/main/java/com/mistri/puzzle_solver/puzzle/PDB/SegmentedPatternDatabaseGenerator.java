package com.mistri.puzzle_solver.puzzle.PDB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class SegmentedPatternDatabaseGenerator {

    private static final int BOARD_SIZE = 4;
    private static final int CELL_COUNT = BOARD_SIZE * BOARD_SIZE;
    private static final byte UNVISITED = (byte) 0xFF;
    private static final int[] MOVE_DELTAS = {-BOARD_SIZE, BOARD_SIZE, -1, 1};

    public static void main(String[] args) throws IOException {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException(
                    "Usage: SegmentedPatternDatabaseGenerator <output-dir> <comma-separated-tiles> [segment-size-mb]");
        }

        Path outputDir = Path.of(args[0]);
        int[] tiles = parseTiles(args[1]);
        int segmentSizeMb = args.length == 3 ? Integer.parseInt(args[2]) : 1024;

        PatternIndexer indexer = new PatternIndexer(tiles);
        long stateCount = indexer.stateCount();
        String prefix = buildPrefix(tiles);

        System.out.println("Generating PDB for pattern " + Arrays.toString(tiles));
        System.out.println("States: " + stateCount);
        System.out.println("Output dir: " + outputDir);
        System.out.println("Segment size MB: " + segmentSizeMb);

        Path workDir = outputDir.resolve(prefix + "-work");
        Files.createDirectories(workDir);

        long start = System.nanoTime();
        try (MappedDistanceTable distances = MappedDistanceTable.create(outputDir, prefix, stateCount, segmentSizeMb);
             LongFileQueue current = new LongFileQueue(workDir.resolve("current.queue"));
             LongFileQueue next = new LongFileQueue(workDir.resolve("next.queue"))) {

            long goalRank = indexer.rankPositions(indexer.goalPositions());
            distances.set(goalRank, (byte) 0);
            current.add(goalRank);

            int[] currentPositions = new int[indexer.positionsLength()];
            long visited = 1L;
            int depth = 0;

            while (!current.isEmpty()) {
                long layerStart = System.nanoTime();
                long processedThisLayer = 0L;

                while (current.hasMore()) {
                    long rank = current.remove();
                    processedThisLayer++;
                    indexer.unrank(rank, currentPositions);

                    int blankPos = currentPositions[0];
                    for (int delta : MOVE_DELTAS) {
                        int nextBlankPos = blankPos + delta;
                        if (!isValidMove(blankPos, nextBlankPos, delta)) {
                            continue;
                        }

                        int movedPatternIndex = findPatternAt(currentPositions, nextBlankPos);
                        currentPositions[0] = nextBlankPos;
                        if (movedPatternIndex >= 0) {
                            currentPositions[movedPatternIndex] = blankPos;
                        }

                        long nextRank = indexer.rankPositions(currentPositions);
                        if (distances.get(nextRank) == UNVISITED) {
                            int edgeCost = movedPatternIndex >= 0 ? 1 : 0;
                            distances.set(nextRank, (byte) (depth + edgeCost));
                            visited++;

                            if (edgeCost == 0) {
                                current.add(nextRank);
                            } else {
                                next.add(nextRank);
                            }
                        }

                        currentPositions[0] = blankPos;
                        if (movedPatternIndex >= 0) {
                            currentPositions[movedPatternIndex] = nextBlankPos;
                        }
                    }
                }

                long elapsedMs = (System.nanoTime() - layerStart) / 1_000_000;
                System.out.println("Depth " + depth
                        + " processed=" + processedThisLayer
                        + " visited=" + visited
                        + " elapsedMs=" + elapsedMs);

                current.resetForReuse();

                if (next.isEmpty()) {
                    break;
                }

                swapQueues(current, next);
                depth++;
            }

            long totalMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Completed pattern " + Arrays.toString(tiles) + " in " + totalMs + " ms");
            System.out.println("Output segments:");
            for (Path path : distances.segmentPaths()) {
                System.out.println(path);
            }
        }
    }

    private static void swapQueues(LongFileQueue current, LongFileQueue next) throws IOException {
        while (next.hasMore()) {
            current.add(next.remove());
        }
        next.resetForReuse();
    }

    private static String buildPrefix(int[] tiles) {
        if (Arrays.equals(tiles, new int[]{1, 2, 3, 4, 5, 6})) {
            return "pdb-6-6-3-a";
        }
        if (Arrays.equals(tiles, new int[]{7, 8, 9, 10, 11, 12})) {
            return "pdb-6-6-3-b";
        }
        if (Arrays.equals(tiles, new int[]{13, 14, 15})) {
            return "pdb-6-6-3-c";
        }
        return "pdb-custom-" + tiles.length;
    }

    private static int findPatternAt(int[] positions, int cell) {
        for (int i = 1; i < positions.length; i++) {
            if (positions[i] == cell) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isValidMove(int blankPos, int nextBlankPos, int delta) {
        if (nextBlankPos < 0 || nextBlankPos >= CELL_COUNT) {
            return false;
        }
        if (delta == -1) {
            return blankPos % BOARD_SIZE != 0;
        }
        if (delta == 1) {
            return blankPos % BOARD_SIZE != BOARD_SIZE - 1;
        }
        return true;
    }

    private static int[] parseTiles(String csv) {
        String[] parts = csv.split(",");
        int[] tiles = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            tiles[i] = Integer.parseInt(parts[i].trim());
        }
        return tiles;
    }
}
