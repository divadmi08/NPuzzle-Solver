package com.mistri.puzzle_solver.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PuzzleMinMovesResponse {

    @JsonProperty("minMoves")
    private int mosseMinime;

    public PuzzleMinMovesResponse(int mosseMinime) {
        this.mosseMinime = mosseMinime;
    }

    public int getMosseMinime() {
        return mosseMinime;
    }

    public void setMosseMinime(int mosseMinime) {
        this.mosseMinime = mosseMinime;
    }
}
