package com.times.tmdb.service.movie;

import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.model.Movie;

import java.util.List;

public interface MovieService {

    Movie addRating(int id, double rating);

    BriefMovieDisplay findMovieDetailsByMovieId(int id);

    int addMovie(Movie m);

    Movie updateMovieByMovieId(Movie movie);

    List<Movie> findAllMovies(int pageNumber);

    List<Movie> searchMovies(String title, String name, int pageNumber);
}
