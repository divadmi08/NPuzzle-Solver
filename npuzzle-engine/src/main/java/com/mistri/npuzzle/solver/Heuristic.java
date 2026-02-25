package main.java.com.mistri.npuzzle.solver;

import main.java.com.mistri.npuzzle.model.PuzzleState;

public class Heuristic {
    public static int manhattan(PuzzleState state){
        int distanza = 0;
        int grandezza = state.getGrandezza();

        for(int r=0;r<grandezza;r++){
            for(int c=0;c<grandezza;c++){

                int value = state.getGriglia()[r][c];

                if(value==0) continue;

                int targetRow = (value-1)/grandezza;
                int targetCol = (value-1)%grandezza;

                distanza += Math.abs(r-targetRow) + Math.abs(c-targetCol);
            }
        }
        return distanza;

    }
}
