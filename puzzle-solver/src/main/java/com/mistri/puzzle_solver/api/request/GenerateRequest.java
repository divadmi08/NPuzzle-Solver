package com.mistri.puzzle_solver.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateRequest {

    @JsonProperty("size")
    private int dimensione;

    public int getDimensione() {
        return dimensione;
    }

    public void setDimensione(int dimensione) {
        this.dimensione = dimensione;
    }
}
