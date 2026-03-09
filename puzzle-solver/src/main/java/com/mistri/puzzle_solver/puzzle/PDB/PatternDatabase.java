package com.mistri.puzzle_solver.puzzle.PDB;

import java.util.Arrays;

final class PatternDatabase implements PatternEstimator {

    private static final int BOARD_SIZE = 4;
    private static final int CELL_COUNT = BOARD_SIZE * BOARD_SIZE;
    private static final byte UNVISITED = (byte) 0xFF;
    private static final int[] MOVE_DELTAS = {-BOARD_SIZE, BOARD_SIZE, -1, 1};

    private final PatternIndexer indexer;
    private final byte[] distances;

    PatternDatabase(int... tiles) {
        this.indexer = new PatternIndexer(tiles);
        this.distances = buildDistances();
    }

    @Override
    public int estimate(int[] board) {
        return distances[Math.toIntExact(indexer.rankBoard(board))] & 0xFF;
    }

    @Override
    public long stateCount() {
        return distances.length;
    }

    @Override
    public String description() {
        return "in-memory " + indexer.description();
    }

    byte[] rawDistances() {
        return distances;
    }

    private byte[] buildDistances() {
        long stateCount = indexer.stateCount();
        if (stateCount > Integer.MAX_VALUE) {
            throw new IllegalStateException("In-memory PDB too large for this pattern: " + description());
        }

        byte[] dist = new byte[(int) stateCount];
        Arrays.fill(dist, UNVISITED);

        int[] positions = indexer.goalPositions();
        int goalRank = (int) indexer.rankPositions(positions);
        dist[goalRank] = 0;

        IntDeque deque = new IntDeque();
        deque.addFirst(goalRank);

        int[] currentPositions = new int[indexer.positionsLength()];

        while (!deque.isEmpty()) {
            int currentRank = deque.removeFirst();
            int currentCost = dist[currentRank] & 0xFF;
            indexer.unrank(currentRank, currentPositions);

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

                int nextRank = (int) indexer.rankPositions(currentPositions);
                int edgeCost = movedPatternIndex >= 0 ? 1 : 0;
                int newCost = currentCost + edgeCost;

                if ((dist[nextRank] & 0xFF) > newCost) {
                    dist[nextRank] = (byte) newCost;
                    if (edgeCost == 0) {
                        deque.addFirst(nextRank);
                    } else {
                        deque.addLast(nextRank);
                    }
                }

                currentPositions[0] = blankPos;
                if (movedPatternIndex >= 0) {
                    currentPositions[movedPatternIndex] = nextBlankPos;
                }
            }
        }

        return dist;
    }

    private int findPatternAt(int[] positions, int cell) {
        for (int i = 1; i < positions.length; i++) {
            if (positions[i] == cell) {
                return i;
            }
        }
        return -1;
    }

    private boolean isValidMove(int blankPos, int nextBlankPos, int delta) {
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

    private static final class IntDeque {
        private int[] values = new int[1024];
        private int head;
        private int tail;
        private int size;

        private void addFirst(int value) {
            ensureCapacity();
            head = (head - 1 + values.length) % values.length;
            values[head] = value;
            size++;
        }

        private void addLast(int value) {
            ensureCapacity();
            values[tail] = value;
            tail = (tail + 1) % values.length;
            size++;
        }

        private int removeFirst() {
            int value = values[head];
            head = (head + 1) % values.length;
            size--;
            return value;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        private void ensureCapacity() {
            if (size < values.length - 1) {
                return;
            }

            int[] next = new int[values.length * 2];
            for (int i = 0; i < size; i++) {
                next[i] = values[(head + i) % values.length];
            }
            values = next;
            head = 0;
            tail = size;
        }
    }
}
