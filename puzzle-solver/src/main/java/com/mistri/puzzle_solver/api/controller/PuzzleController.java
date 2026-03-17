package com.mistri.puzzle_solver.api.controller;

import com.mistri.puzzle_solver.api.request.GenerateRequest;
import com.mistri.puzzle_solver.api.request.SolveRequest;
import com.mistri.puzzle_solver.api.response.PuzzleResponse;
import com.mistri.puzzle_solver.api.response.PuzzleSolutionResponse;
import com.mistri.puzzle_solver.api.service.PuzzleService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/puzzle")
public class PuzzleController {

    private final PuzzleService servizioPuzzle;

    public PuzzleController(PuzzleService servizioPuzzle) {
        this.servizioPuzzle = servizioPuzzle;
    }

    @PostMapping("/generate")
    public PuzzleResponse genera(@RequestBody GenerateRequest richiesta) {
        return servizioPuzzle.genera(richiesta);
    }

    @PostMapping("/solve")
    public PuzzleSolutionResponse risolvi(@RequestBody SolveRequest richiesta) {
        return servizioPuzzle.risolvi(richiesta);
    }
}
