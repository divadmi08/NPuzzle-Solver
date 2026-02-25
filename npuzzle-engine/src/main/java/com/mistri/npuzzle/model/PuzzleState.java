package main.java.com.mistri.npuzzle.model;

import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleState {
    private final int[][] griglia;
    private int zeroX;
    private int zeroY;
    private int grandezza;
    private final int[][] GOAL = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    public PuzzleState(int[][] griglia) {
        try{
            this.griglia = griglia;
            grandezza = griglia.length*griglia[0].length;
            trovaZero();
        } catch (RuntimeException e) {
            throw new RuntimeException("inserisci una griglia valida");
        }

    }

    private void trovaZero(){
        for (int y = 0; y < griglia.length; y++){
            for (int x = 0; x < griglia[y].length; x++){
                if (griglia[y][x] == 0){
                    this.zeroX = x;
                    this.zeroY = y;
                    y = griglia.length;
                    break;
                }
            }
        }
    }



    public boolean isGoal(){
        return Arrays.deepEquals(griglia,GOAL);
    }

    public ArrayList<int[][]> getVicini() {
        ArrayList<int[][]> lista = new ArrayList<>();
        for (Move m : Move.values()) {
            int nx = zeroX + m.getMovimentoX();
            int ny = zeroY + m.getMovimentoY();
            if (nx >= 0 && nx < griglia[0].length && ny >= 0 && ny < griglia.length) {
                int[][] copia = copia();
                copia[zeroY][zeroX] = copia[ny][nx];
                copia[ny][nx] = 0;
                lista.add(copia);
            }
        }
        return lista;
    }

    private int[][] copia(){
        int[][] copia = new int[griglia.length][griglia[0].length];
        for (int i = 0; i < griglia.length; i++){
            copia[i] = griglia[i].clone();
        }
        return copia;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        PuzzleState other = (PuzzleState) obj;

        return Arrays.deepEquals(griglia, other.griglia);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(griglia);
    }


    public int getZeroY() {
        return zeroY;
    }

    public int getZeroX() {
        return zeroX;
    }

    public int getGrandezza() {
        return grandezza;
    }

    public int[][] getGriglia() {
        return griglia;
    }

    @Override
    public String toString(){
        String stringa = "";
        for (int[] l : griglia){
            for (int n : l){
                stringa += n + " ";
            }
            stringa += "\n";
        }
        return stringa;
    }

}
