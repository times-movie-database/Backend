package com.times.tmdb.service.movie;

import com.times.tmdb.response.movie.MovieDisplay;
import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.exception.MovieServiceException;
import com.times.tmdb.model.*;
import com.times.tmdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Returns an Movie object that can then be painted on the screen.
     * <p>
     * This method updates the average the rating of  movie and increase the count to 1
     *
     * @param movieId unique id of the movie on which rating is to be added
     * @param rating  the double value which user wants to rate to the movie
     * @return the Movie object to the controller
     */
    @Override
    public Movie addRating(int movieId, double rating) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            int count = movie.getCount();
            double avgRating = ((movie.getRating() * count) + rating) / (++count);
            double finalRating = (Math.round(avgRating * 10.0) / 10.0);
            movieRepository.updateRating(finalRating, count, movieId);
            movie.setRating(finalRating);
            movie.setCount(count);
            return movie;
        } else
            throw new MovieServiceException("No movie associated with the given id");
    }

    /**
     * Returns an Movie object that can then be painted on the screen.
     * <p>
     * This method finds the brief details of the movie as requested by the user
     *
     * @param movieId unique id of the movie of which details is to be retrieved
     * @return the Movie object containing brief details to the controller
     */
    @Override
    public BriefMovieDisplay findMovieDetailsByMovieId(int movieId) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        BriefMovieDisplay briefMovieDisplay = new BriefMovieDisplay();
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            briefMovieDisplay.setId(movie.getId());
            briefMovieDisplay.setTitle(movie.getTitle());
            briefMovieDisplay.setRating(movie.getRating());
            briefMovieDisplay.setCount(movie.getCount());
            briefMovieDisplay.setCast(movie.getCast());
            briefMovieDisplay.setGenres(movie.getGenres());
            briefMovieDisplay.setSummary(movie.getSummary());
            return briefMovieDisplay;
        } else
            throw new MovieServiceException("No movie associated with the given id");
    }

    /**
     * Returns unique id of the movie which is newly added by the user
     * <p>
     * This method add a new movie to the database
     *
     * @return the id to the controller
     */
    @Override
    public int addMovie(Movie movie) {
        Movie movieAdded = null;
        movieAdded = movieRepository.save(movie);
        return movieAdded.getId();
    }

    //Method to be invoked when user wants to edit the existing movie
    @Override
    public Movie updateMovieByMovieId(Movie movie) {
        return movieRepository.save(movie);
    }

    private List<MovieDisplay> getMovieDisplays(List<Movie> movies) {
        List<MovieDisplay> movieDisplays = new ArrayList<>();
        for (Movie movie : movies) {
            MovieDisplay movieDisplay = new MovieDisplay();
            movieDisplay.setId(movie.getId());
            movieDisplay.setTitle(movie.getTitle());
            movieDisplay.setRating(movie.getRating());
            movieDisplay.setCount(movie.getCount());
            movieDisplays.add(movieDisplay);
        }
        return movieDisplays;
    }

    //Method to find all movies present in the database
    @Override
    public List<Movie> findAllMovies(int pageNumber) {
        return movieRepository.findAll(PageRequest.of(pageNumber, 20)).toList();
    }

    /**
     * Returns List of movies based on the search criteria
     * <p>
     * This method search the movies by title or/and cast
     *
     * @param title      title of the movie to be searched
     * @param name       genre filter
     * @param pageNumber
     * @return the list of movies based on the criteria
     * Here we have used dynamic query using Specification Criteria
     */
    @Override
    public List<Movie> searchMovies(String title, String name, int pageNumber) {
        if (name.equalsIgnoreCase("all") && title.isEmpty())
            return movieRepository.findAll(PageRequest.of(pageNumber, 20)).toList();
        return movieRepository.findAll(Specification.where(genrePredicate(title, name)), PageRequest.of(pageNumber, 20)).toList();
    }

    //dynamic query for searchMovies API
    public static Specification<Movie> genrePredicate(String title, String name) {
        return (root, query, builder) -> {
            if (name.equalsIgnoreCase("all")) {
                if (title != null && title.trim() != "")
                    return builder.like(root.get(Movie_.TITLE), title + "%");
                return builder.isTrue(builder.literal(true));
            }
            ListJoin<Movie, Genre> movieGenreJoin = root.join(Movie_.genres);
            Predicate genrePredicate = builder.equal(movieGenreJoin.get(Genre_.NAME), name);
            if (title != null && title.trim() != "") {
                Predicate titlePredicate = builder.like(root.get(Movie_.TITLE), title + "%");
                return builder.and(titlePredicate, genrePredicate);
            }
            return genrePredicate;
        };
    }
}