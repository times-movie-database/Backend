package com.times.tmdb.controller;
import com.times.tmdb.converter.GenreConverter;
import com.times.tmdb.converter.MovieConvertor;
import com.times.tmdb.dto.GenreDTO;
import com.times.tmdb.dto.MovieDTO;
import com.times.tmdb.dto.MovieDisplay;
import com.times.tmdb.dto.BriefMovieDisplay;
import com.times.tmdb.model.Genre;
import com.times.tmdb.model.Movie;
import com.times.tmdb.model.Review;
import com.times.tmdb.service.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tmdb/movies")
public class MovieController {
    private final MovieService movieService;
    private final GenreConverter genreConverter;
    private final MovieConvertor movieConvertor;
    Logger logger = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    public MovieController(MovieService movieService, GenreConverter genreConverter, MovieConvertor movieConvertor) {
        this.movieService = movieService;
        this.genreConverter = genreConverter;
        this.movieConvertor = movieConvertor;
    }

    @GetMapping("/find-all")
    public List<MovieDisplay> findAllMovies(@RequestParam int pageNumber) {
        return movieConvertor.entityToDisplay(movieService.findAllMovies(pageNumber));
    }

    @PutMapping(value = "/{movieId}/rating")
    public void updateMovieRating(@PathVariable int movieId, @RequestBody double rating) {
         movieService.updateMovieRating(movieId, rating);
    }

    @PutMapping(value = "/{movieId}/review")
    public void updateMovieReview(@PathVariable int movieId, @RequestBody String review) {
         movieService.updateMovieReview(movieId, review);
    }

    @GetMapping(value = "/{movieId}")
    @ApiOperation(value = "Find Movie Details by id", notes = "Provide an id to look up for the specific movie", response = Movie.class)
    public BriefMovieDisplay getMovieDetails(@ApiParam(value = "Id value for the movie you need to retrieve", required = true) @PathVariable int movieId) {
        return movieService.findMovieDetails(movieId);
    }

    @PostMapping()
    public ResponseEntity<Integer> addMovie(@ApiParam(value = "Details of the movie required the user want to add", required = true) @RequestBody MovieDTO movieDTO) {
        return movieService.addMovie(movieConvertor.dtoToEntity(movieDTO));
    }

    @PutMapping("/{movieId}")
    public Movie update(@RequestBody MovieDTO movieDTO, @PathVariable("movieId") int movieId) {
        Movie movie = movieConvertor.dtoToEntityForUpdating(movieDTO, movieId);
        if (movie != null) {
            return movieService.updateMovieById(movie);
        } else
            return null;
    }

    @GetMapping("/genre")
    public List<Genre> findAllGenre() {
        return movieService.findAllGenre();
    }

    @GetMapping("/{movieId}/review")
    public List<Review> findAllReviews(@PathVariable int movieId, @RequestParam int pageNumber) {
        return movieService.findAllReviews(movieId, pageNumber);
    }

    @PostMapping("/add-genre")
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDTO genreDTO) {
        return ResponseEntity.ok(movieService.addGenre(genreConverter.dtoToEntity(genreDTO)));
    }
    @GetMapping("/search")
    public List<MovieDisplay> searchApi(@RequestParam String title, @RequestParam String genre) {
        return movieConvertor.entityToDisplay(movieService.searchMovies(title, genre));
    }
}