package com.times.tmdb.controller;

import com.times.tmdb.converter.GenreConverter;
import com.times.tmdb.request.genre.GenreDTO;
import com.times.tmdb.model.Genre;
import com.times.tmdb.response.movie.MovieDisplay;
import com.times.tmdb.service.genre.GenreService;
import com.times.tmdb.service.movie.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tmdb/genre")
@RestController
@Api(value = "/tmdb/genre", tags = "Genre Management")
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
    @ApiOperation(value = "Find all the Genres", response = Genre.class)
    public ResponseEntity<List<Genre>> findAllGenre() {
        return  ResponseEntity.ok(genreService.findAllGenre());
    }

    //Mapping for adding new Genre
    @PostMapping()
    @ApiOperation(value = "Add a Genre", response = Genre.class)
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDTO genreDTO) {
        return new ResponseEntity(genreService.addGenre(genreConverter.dtoToEntity(genreDTO)),HttpStatus.CREATED);
    }
}
