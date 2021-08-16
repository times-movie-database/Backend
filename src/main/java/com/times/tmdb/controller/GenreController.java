package com.times.tmdb.controller;

import com.times.tmdb.converter.GenreConverter;
import com.times.tmdb.dto.GenreDTO;
import com.times.tmdb.model.Genre;
import com.times.tmdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/genre")
@RestController
public class GenreController {
    private final MovieService movieService;
    private final GenreConverter genreConverter;

    @Autowired
    public GenreController(MovieService movieService, GenreConverter genreConverter) {
        this.movieService = movieService;
        this.genreConverter = genreConverter;
    }

    @GetMapping()
    public List<Genre> findAllGenre() {
        return movieService.findAllGenre();
    }

    @PostMapping()
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDTO genreDTO) {
        return ResponseEntity.ok(movieService.addGenre(genreConverter.dtoToEntity(genreDTO)));
    }
}
