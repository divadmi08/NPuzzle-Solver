package main.java.com.mistri.npuzzle.model;

import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleState {
    private final int[][] griglia;
    private int zeroX;
    private int zeroY;
    private int grandezza;
    private final int[][] GOAL;


    public PuzzleState(int[][] griglia) {
        this.griglia = griglia;
        int righe = griglia.length;
        int colonne = griglia[0].length;
        this.grandezza = griglia.length;
        trovaZero();
        this.GOAL = generaGoal(righe, colonne);
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


    private int[][] generaGoal(int righe, int colonne) {
        int[][] goal = new int[righe][colonne];
        int valore = 1;
        for (int y = 0; y < righe; y++) {
            for (int x = 0; x < colonne; x++) {
                if (y == righe - 1 && x == colonne - 1)
                    goal[y][x] = 0;
                else
                    goal[y][x] = valore++;
            }
        }
        return goal;
    }



    public boolean isGoal(){
        return Arrays.deepEquals(griglia,GOAL);
    }

    public PuzzleState applicaMossa(Move move) {
        int nx = zeroX + move.getMovimentoX();
        int ny = zeroY + move.getMovimentoY();
        if (nx < 0 || nx >= griglia[0].length || ny < 0 || ny >= griglia.length) return null;
        int[][] nuovaGriglia = copia();
        nuovaGriglia[zeroY][zeroX] = nuovaGriglia[ny][nx];
        nuovaGriglia[ny][nx] = 0;
        return new PuzzleState(nuovaGriglia);
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
