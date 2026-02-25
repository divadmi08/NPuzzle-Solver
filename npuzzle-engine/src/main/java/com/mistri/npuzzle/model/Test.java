package main.java.com.mistri.npuzzle.model;

public class Test {
    public static void main(String[] args) {
        int[][] griglia = {
                {1, 2, 3},
                {8, 0, 10},
                {7, 7, 6}
        };
        int[][] griglia1 = {
                {1, 2, 3},
                {8, 0, 10},
                {7, 7, 6}
        };
        try {
            PuzzleState prova = new PuzzleState(griglia);
            PuzzleState prova1 = new PuzzleState(griglia1);
            System.out.println(prova.getZeroX());
            System.out.println(prova.getZeroY());
            System.out.println(prova.isGoal());
            System.out.println(prova.getGrandezza());
            for (int[][] g : prova.getVicini()){
                for (int[] l : g){
                    for (int n : l){
                        System.out.print(n + " ");
                    }
                    System.out.print("\n");
                }
                System.out.println();
            }
            System.out.println(prova.equals(prova1));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }


    }
}
