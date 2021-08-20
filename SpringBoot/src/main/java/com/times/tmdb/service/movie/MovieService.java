package com.times.tmdb.service.movie;

import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.model.Movie;

import java.util.List;

public interface MovieService {

    void updateMovieRating(int id, double rating);

    BriefMovieDisplay findMovieDetails(int id);

    int addMovie(Movie m);

    Movie updateMovieById(Movie movie);

    List<Movie> findAllMovies(int pageNumber);

    List<Movie> searchMovies(String title, String name, int pageNumber);
}
