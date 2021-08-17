package com.times.tmdb.controller;

import com.times.tmdb.converter.GenreConverter;
import com.times.tmdb.dto.GenreDTO;
import com.times.tmdb.model.Genre;
import com.times.tmdb.service.GenreService;
import com.times.tmdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tmdb/genre")
@RestController
public class GenreController {
    private final MovieService movieService;
    private final GenreConverter genreConverter;
    private final GenreService genreService;

    @Autowired
    public GenreController(MovieService movieService, GenreConverter genreConverter, GenreService genreService) {
        this.movieService = movieService;
        this.genreConverter = genreConverter;
        this.genreService=genreService;
    }

    @GetMapping()
    public List<Genre> findAllGenre() {
        return genreService.findAllGenre();
    }

    @PostMapping()
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDTO genreDTO) {
        return ResponseEntity.ok(genreService.addGenre(genreConverter.dtoToEntity(genreDTO)));
    }
}
