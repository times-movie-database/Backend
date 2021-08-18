package com.times.tmdb.controller;

import com.times.tmdb.converter.GenreConverter;
import com.times.tmdb.converter.MovieConvertor;
import com.times.tmdb.dto.MovieDTO;
import com.times.tmdb.dto.MovieDisplay;
import com.times.tmdb.dto.BriefMovieDisplay;
import com.times.tmdb.exceptionHandling.MovieServiceException;
import com.times.tmdb.model.Movie;
import com.times.tmdb.model.Review;
import com.times.tmdb.service.MovieService;
import com.times.tmdb.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tmdb/movies")
public class MovieController {
    private final MovieService movieService;
    private final GenreConverter genreConverter;
    private final MovieConvertor movieConvertor;
    private final ReviewService reviewService;
    Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(MovieService movieService, GenreConverter genreConverter, MovieConvertor movieConvertor, ReviewService reviewService) {
        this.movieService = movieService;
        this.genreConverter = genreConverter;
        this.movieConvertor = movieConvertor;
        this.reviewService = reviewService;
    }

    // Mapping for getting all the movies from the database
    // pageNumber is for page Number
    @GetMapping("/find-all")
    public ResponseEntity<List<MovieDisplay>> findAllMovies(@RequestParam int pageNumber) {
        List<MovieDisplay> movieDisplays = movieConvertor.entityToDisplay(movieService.findAllMovies(pageNumber));
        if (movieDisplays != null)
            return ResponseEntity.ok(movieDisplays);
        else
            return new ResponseEntity<List<MovieDisplay>>((List<MovieDisplay>) null, HttpStatus.NO_CONTENT);
    }

    // Mapping for adding a rating to the movie on the mentioned movie id
    @PutMapping(value = "/{movieId}/rating")
    public ResponseEntity updateMovieRating(@PathVariable int movieId, @RequestBody double rating) {
        if (rating < 1.0)
            throw new MovieServiceException("Minimum rating to be given is 1.0");
        else if (rating > 5.0)
            throw new MovieServiceException("Maximum rating to be given is 5.0");
        else
            movieService.updateMovieRating(movieId, rating);
        return (ResponseEntity) ResponseEntity.ok();
    }

    // Mapping for adding a review to the movie on the mentioned movie id
    @PutMapping(value = "/{movieId}/review")
    public ResponseEntity updateMovieReview(@PathVariable int movieId, @RequestBody String review) {
        reviewService.updateMovieReview(movieId, review);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    // Mapping for getting the whole details of the movie on the given particular movie id
    @GetMapping(value = "/{movieId}")
    @ApiOperation(value = "Find Movie Details by id", notes = "Provide an id to look up for the specific movie", response = Movie.class)
    public ResponseEntity<BriefMovieDisplay> getMovieDetails(@ApiParam(value = "Id value for the movie you need to retrieve", required = true) @PathVariable int movieId) {
        BriefMovieDisplay briefMovieDisplay = movieService.findMovieDetails(movieId);
        return ResponseEntity.ok(briefMovieDisplay);
    }

    // Mapping for adding the new Movie
    @PostMapping()
    public ResponseEntity<Integer> addMovie(@ApiParam(value = "Details of the movie required the user want to add", required = true) @Valid @RequestBody MovieDTO movieDTO) {
        int id = movieService.addMovie(movieConvertor.dtoToEntity(movieDTO));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Mapping for updating the movie details for the given movie id
    @PutMapping("/{movieId}")
    public ResponseEntity update(@RequestBody MovieDTO movieDTO, @PathVariable("movieId") int movieId) {
        Movie movie = movieConvertor.dtoToEntityForUpdating(movieDTO, movieId);
        if (movie != null) {
            movieService.updateMovieById(movie);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else
            throw new MovieServiceException("No movie associated with the given id that can be updated");
    }

    // Mapping for getting the reviews that are made on the particular movie
    @GetMapping("/{movieId}/review")
    public ResponseEntity<List<Review>> findAllReviews(@PathVariable int movieId, @RequestParam int pageNumber) {
        List<Review> reviews = reviewService.findAllReviews(movieId, pageNumber);
        if (reviews.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return ResponseEntity.ok(reviews);
    }

    // Mapping for the searching the movie by title or/and Genre
    @GetMapping("/search")
    public ResponseEntity<List<MovieDisplay>> searchApi(@RequestParam String title, @RequestParam String genre, @RequestParam int pageNumber) {
        List<MovieDisplay> movieDisplays = movieConvertor.entityToDisplay(movieService.searchMovies(title, genre, pageNumber));
        if (movieDisplays.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return ResponseEntity.ok(movieDisplays);
    }
}