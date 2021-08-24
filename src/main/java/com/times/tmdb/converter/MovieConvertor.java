package com.times.tmdb.converter;

import com.times.tmdb.request.movie.MovieDTO;
import com.times.tmdb.response.movie.MovieDisplay;
import com.times.tmdb.exception.MovieServiceException;
import com.times.tmdb.model.Genre;
import com.times.tmdb.model.Movie;
import com.times.tmdb.repository.GenreRepository;
import com.times.tmdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MovieConvertor {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public MovieConvertor(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    public Movie dtoToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle().toLowerCase());
        movie.setSummary(movieDTO.getSummary());
        if(movieDTO.getCastList().isEmpty())
            throw new MovieServiceException("You must enter atleast one cast");
        movie.setCast(movieDTO.getCastList());
        movie.setRating(0.0);
        if(movieDTO.getGenreIdList().isEmpty())
            throw new MovieServiceException("You must select at least one genre");
        for (int id : movieDTO.getGenreIdList()) {
            Genre genre = genreRepository.findById(id).get();
            genre.addMovie(movie);
            movie.addGenre(genre);
        }
        return movie;
    }


    public Movie dtoToEntityForUpdating(MovieDTO movieDTO, int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        Movie movie = null;
        if (optionalMovie.isPresent()) {
            movie = optionalMovie.get();
            movie.setTitle(movieDTO.getTitle().toLowerCase());
            movie.setSummary(movieDTO.getSummary());
            movie.setCast(movieDTO.getCastList());
            movie.setGenres(new ArrayList<>());
            for (Integer integer : movieDTO.getGenreIdList()) {
                Genre genre = genreRepository.findById(integer).get();
                movie.addGenre(genre);
                genre.addMovie(movie);
            }
        } else
            throw new MovieServiceException("No movie associated with the given id");
        return movie;
    }

    public List<MovieDisplay> entityToDisplay(List<Movie> movies) {
        List<MovieDisplay> movieDisplayList = new ArrayList<>();
        for (Movie movie : movies) {
            MovieDisplay movieDisplay = new MovieDisplay();
            movieDisplay.setId(movie.getId());
            movieDisplay.setTitle(movie.getTitle());
            movieDisplay.setRating(movie.getRating());
            movieDisplay.setCount(movie.getCount());
            movieDisplayList.add(movieDisplay);
        }
        return movieDisplayList;
    }
}