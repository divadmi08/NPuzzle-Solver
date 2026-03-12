package com.mistri.puzzle_solver.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolveRequest {

    @JsonProperty("grid")
    private int[][] griglia;

    public int[][] getGriglia() {
        return griglia;
    }

    public void setGriglia(int[][] griglia) {
        this.griglia = griglia;
    }
}
