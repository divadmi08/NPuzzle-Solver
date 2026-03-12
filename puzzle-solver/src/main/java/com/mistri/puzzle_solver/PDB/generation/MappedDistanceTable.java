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

    private static final int DIMENSIONE_SEGMENTO_PREDEFINITA_MB = 1024;

    private final long dimensioneSegmento;
    private final List<FileChannel> canali;
    private final List<MappedByteBuffer> bufferMappati;
    private final List<Path> percorsiSegmenti;

    private MappedDistanceTable(long dimensioneSegmento, List<FileChannel> canali,
                                List<MappedByteBuffer> bufferMappati, List<Path> percorsiSegmenti) {
        this.dimensioneSegmento = dimensioneSegmento;
        this.canali = canali;
        this.bufferMappati = bufferMappati;
        this.percorsiSegmenti = percorsiSegmenti;
    }

    static MappedDistanceTable crea(Path cartella, String prefisso, long numeroStati) throws IOException {
        return crea(cartella, prefisso, numeroStati, DIMENSIONE_SEGMENTO_PREDEFINITA_MB);
    }

    static MappedDistanceTable crea(Path cartella, String prefisso, long numeroStati, int dimensioneSegmentoMb) throws IOException {
        Files.createDirectories(cartella);

        long dimensioneSegmento = dimensioneSegmentoMb * 1024L * 1024L;
        int numeroSegmenti = (int) ((numeroStati + dimensioneSegmento - 1) / dimensioneSegmento);

        List<FileChannel> canali = new ArrayList<>(numeroSegmenti);
        List<MappedByteBuffer> bufferMappati = new ArrayList<>(numeroSegmenti);
        List<Path> percorsi = new ArrayList<>(numeroSegmenti);

        for (int i = 0; i < numeroSegmenti; i++) {
            long rimanenti = numeroStati - (long) i * dimensioneSegmento;
            long dimensioneCorrente = Math.min(dimensioneSegmento, rimanenti);
            Path percorso = cartella.resolve(prefisso + "." + i + ".bin");
            try (FileChannel canaleIniziale = FileChannel.open(
                    percorso,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                scriviRiempito(canaleIniziale, dimensioneCorrente, (byte) 0xFF);
            }

            FileChannel canale = FileChannel.open(percorso, StandardOpenOption.READ, StandardOpenOption.WRITE);
            MappedByteBuffer bufferMappato = canale.map(FileChannel.MapMode.READ_WRITE, 0, dimensioneCorrente);
            canali.add(canale);
            bufferMappati.add(bufferMappato);
            percorsi.add(percorso);
        }

        return new MappedDistanceTable(dimensioneSegmento, canali, bufferMappati, percorsi);
    }

    byte leggi(long indice) {
        int indiceSegmento = (int) (indice / dimensioneSegmento);
        int scostamento = (int) (indice % dimensioneSegmento);
        return bufferMappati.get(indiceSegmento).get(scostamento);
    }

    void scrivi(long indice, byte valore) {
        int indiceSegmento = (int) (indice / dimensioneSegmento);
        int scostamento = (int) (indice % dimensioneSegmento);
        bufferMappati.get(indiceSegmento).put(scostamento, valore);
    }

    List<Path> percorsiSegmenti() {
        return List.copyOf(percorsiSegmenti);
    }

    @Override
    public void close() throws IOException {
        IOException failure = null;
        for (FileChannel canale : canali) {
            try {
                canale.close();
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

    private static void scriviRiempito(FileChannel canale, long dimensione, byte valore) throws IOException {
        byte[] blocco = new byte[1 << 20];
        if (valore != 0) {
            for (int i = 0; i < blocco.length; i++) {
                blocco[i] = valore;
            }
        }

        long scritti = 0;
        while (scritti < dimensione) {
            int lunghezza = (int) Math.min(blocco.length, dimensione - scritti);
            canale.write(java.nio.ByteBuffer.wrap(blocco, 0, lunghezza));
            scritti += lunghezza;
        }
    }
}
