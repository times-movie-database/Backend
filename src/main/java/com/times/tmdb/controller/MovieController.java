package com.times.tmdb.controller;

import com.times.tmdb.converter.GenreConverter;
import com.times.tmdb.converter.MovieConvertor;
import com.times.tmdb.request.movie.MovieDTO;
import com.times.tmdb.response.movie.MovieDisplay;
import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.exception.MovieServiceException;
import com.times.tmdb.model.Movie;
import com.times.tmdb.model.Review;
import com.times.tmdb.service.movie.MovieService;
import com.times.tmdb.service.review.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
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

    @GetMapping("/find-all")
    @ApiOperation(value = "Find all the movies", response = MovieDisplay.class)
    public ResponseEntity<List<MovieDisplay>> findAllMovies(@RequestParam int pageNumber) {
        List<MovieDisplay> movieDisplays = movieConvertor.entityToDisplay(movieService.findAllMovies(pageNumber));
        if (movieDisplays != null)
            return ResponseEntity.ok(movieDisplays);
        else
            return new ResponseEntity<List<MovieDisplay>>((List<MovieDisplay>) null, HttpStatus.NO_CONTENT);
    }

    // Mapping for adding a rating to the movie on the mentioned movie id
    @PostMapping(value = "/{movieId}/rating")
    @ApiOperation(value = "Add a rating to the movie by providing the movie id", notes = "Provide an id to add a review to a specific movie", response = ResponseEntity.class)
    public ResponseEntity updateMovieRating(@PathVariable int movieId, @RequestBody double rating) {
        if (rating < 1.0)
            throw new MovieServiceException("Minimum rating to be given is 1.0");
        else if (rating > 5.0)
            throw new MovieServiceException("Maximum rating to be given is 5.0");
        else
            movieService.updateMovieRating(movieId, rating);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    // Mapping for adding a review to the movie on the mentioned movie id
    @PostMapping(value = "/{movieId}/review")
    @ApiOperation(value = "Add review to a specific movie by movie id", notes = "Provide an id to add review", response = ResponseEntity.class)
    public ResponseEntity updateMovieReview(@PathVariable int movieId,@Size(max = 1000, message = "only 500 characters allowed") @RequestBody String review) {
        reviewService.updateMovieReview(movieId, review);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    // Mapping for getting the whole details of the movie on the given particular movie id
    @GetMapping(value = "/{movieId}")
    @ApiOperation(value = "Find Movie Details by id", notes = "Provide an id to look up for the specific movie", response = BriefMovieDisplay.class)
    public ResponseEntity<BriefMovieDisplay> getMovieDetails(@ApiParam(value = "Id value for the movie you need to retrieve", required = true) @PathVariable int movieId) {
        BriefMovieDisplay briefMovieDisplay = movieService.findMovieDetails(movieId);
        return ResponseEntity.ok(briefMovieDisplay);
    }

    // Mapping for adding the new Movie
    @PostMapping()
    @ApiOperation(value = "Add a new movie", notes = "Provide title,summary,casts and genre to add ", response = ResponseEntity.class)
    public ResponseEntity<Integer> addMovie(@ApiParam(value = "Details of the movie required the user want to add", required = true) @Valid @RequestBody MovieDTO movieDTO) {
        int id = movieService.addMovie(movieConvertor.dtoToEntity(movieDTO));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Mapping for updating the movie details for the given movie id
    @PutMapping("/{movieId}")
    @ApiOperation(value = "Update Movie Details by id", notes = "Provide an id to update the specific movie", response = ResponseEntity.class)
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
    @ApiOperation(value = "Find all the reviews by specific movie id", notes = "Provide an id to look up for all the reviews of specific movie", response = Review.class)
    public ResponseEntity<List<Review>> findAllReviews(@PathVariable int movieId, @RequestParam int pageNumber) {
        List<Review> reviews = reviewService.findAllReviews(movieId, pageNumber);
        if (reviews.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return ResponseEntity.ok(reviews);
    }

    // Mapping for the searching the movie by title or/and Genre
    @GetMapping("/search")
    @ApiOperation(value = "Search movies by title or/and genre", notes = "Provide title or genre of the movie to look up for the associated movies with them", response = MovieDisplay.class)
    public ResponseEntity<List<MovieDisplay>> searchApi(@RequestParam String title, @RequestParam String genre, @RequestParam int pageNumber) {
        List<MovieDisplay> movieDisplays = movieConvertor.entityToDisplay(movieService.searchMovies(title, genre, pageNumber));
        if (movieDisplays.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
//            return ResponseEntity.ok(movieDisplays);
        return new ResponseEntity<>(movieDisplays,HttpStatus.OK);
    }
}