package com.mistri.puzzle_solver.puzzle.PDB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PatternDatabaseFileGenerator {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: PatternDatabaseFileGenerator <output-file> <comma-separated-tiles>");
        }

        Path output = Path.of(args[0]);
        int[] tiles = parseTiles(args[1]);
        if (tiles.length > 7) {
            throw new IllegalArgumentException("This in-memory generator supports at most 7 tiles.");
        }

        long start = System.nanoTime();
        PatternDatabase database = new PatternDatabase(tiles);
        Files.write(output, database.rawDistances());
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println("Generated PDB " + database.description()
                + " -> " + output
                + " (" + database.stateCount() + " bytes) in " + elapsedMs + " ms");
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
