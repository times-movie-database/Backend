package com.times.tmdb.service;
import com.times.tmdb.dto.BriefMovieDisplay;
import com.times.tmdb.model.Genre;
import com.times.tmdb.model.Movie;
import com.times.tmdb.model.Review;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface MovieService {

    void updateMovieRating(int id, double rating);

    void updateMovieReview(int id, String review);

    BriefMovieDisplay findMovieDetails(int id);

    ResponseEntity<Integer> addMovie(Movie m);

    Movie updateMovieById(Movie movie);


    List<Genre> findAllGenre();

    List<Review> findAllReviews(int id, int pageNumber);

    Genre addGenre(Genre genre);

    List<Movie> findAllMovies(int pageNumber);
    List<Movie> searchMovies(String title,String name);
}
