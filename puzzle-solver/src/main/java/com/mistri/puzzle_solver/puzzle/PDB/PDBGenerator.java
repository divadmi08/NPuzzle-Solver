package com.mistri.puzzle_solver.puzzle.PDB;

import java.io.*;
import java.util.*;

public class PDBGenerator {

    static final int N = 16;
    static final int SIZE = 1 << 29; // abbastanza grande per 8 pattern

    static Set<Integer> pattern;

    static class State {
        byte[] board;
        int blank;

        State(byte[] b, int blank){
            this.board = b.clone();
            this.blank = blank;
        }
    }

    static int[] moves = {-4,4,-1,1};

    public static void main(String[] args) throws Exception {

        pattern = Set.of(1,2,3,4,5,6,7); // cambia per pdb8

        byte[] pdb = new byte[SIZE];
        Arrays.fill(pdb,(byte)255);

        byte[] goal = {
                1,2,3,4,
                5,6,7,8,
                9,10,11,12,
                13,14,15,0
        };

        Queue<State> q = new ArrayDeque<>();

        State start = new State(goal,15);

        int startIdx = index(start.board,start.blank);

        pdb[startIdx] = 0;

        q.add(start);

        while(!q.isEmpty()){

            State s = q.poll();

            int idx = index(s.board,s.blank);
            int cost = pdb[idx] & 0xFF;

            for(int m : moves){

                int nb = s.blank + m;

                if(nb < 0 || nb >= 16) continue;

                if(m == -1 && s.blank % 4 == 0) continue;
                if(m == 1 && s.blank % 4 == 3) continue;

                byte[] newBoard = s.board.clone();

                int tile = newBoard[nb];

                newBoard[s.blank] = (byte)tile;
                newBoard[nb] = 0;

                int newCost = cost + (pattern.contains(tile) ? 1 : 0);

                int newIdx = index(newBoard,nb);

                if(pdb[newIdx] == (byte)255){

                    pdb[newIdx] = (byte)newCost;

                    q.add(new State(newBoard,nb));
                }
            }
        }

        FileOutputStream f = new FileOutputStream("pdb.bin");
        f.write(pdb);
        f.close();

        System.out.println("done");
    }

    static int index(byte[] board,int blank){

        int h = blank;

        for(int i=0;i<16;i++){
            int t = board[i];
            if(pattern.contains(t))
                h = h*31 + t*17 + i;
        }

        return Math.abs(h) % SIZE;
    }
}
