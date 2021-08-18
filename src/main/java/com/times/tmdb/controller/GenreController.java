package com.times.tmdb.controller;

import com.times.tmdb.converter.GenreConverter;
import com.times.tmdb.requestDTO.genre.GenreDTO;
import com.times.tmdb.model.Genre;
import com.times.tmdb.service.genre.GenreService;
import com.times.tmdb.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Mapping for getting all the genres
    @GetMapping()
    public ResponseEntity<List<Genre>> findAllGenre() {
        return  ResponseEntity.ok(genreService.findAllGenre());
    }

    //Mapping for adding new Genre
    @PostMapping()
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDTO genreDTO) {
        return new ResponseEntity(genreService.addGenre(genreConverter.dtoToEntity(genreDTO)),HttpStatus.CREATED);
    }
}
