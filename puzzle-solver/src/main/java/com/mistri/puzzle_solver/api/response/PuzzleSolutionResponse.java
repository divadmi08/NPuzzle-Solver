package com.mistri.puzzle_solver.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PuzzleSolutionResponse {
    @JsonProperty("moves")
    private List<String> mosse;

    public PuzzleSolutionResponse(List<String> mosse) {
        this.mosse = mosse;
    }

    public List<String> getMosse() {
        return mosse;
    }

    public void setMosse(List<String> mosse) {
        this.mosse = mosse;
    }
}
