package com.times.tmdb.service.movie;

import com.times.tmdb.response.movie.MovieDisplay;
import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.exception.MovieServiceException;
import com.times.tmdb.model.*;
import com.times.tmdb.repository.GenreRepository;
import com.times.tmdb.repository.MovieRepository;
import com.times.tmdb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
    }

    //This Method gets invoked when user post his/her rating
    //Parameters-> movie_id - > unique identification of the movie in which rating is to be added and rating- > user rating
    @Override
    public void updateMovieRating(int movie_id, double rating) {
        Optional<Movie> optionalMovie = movieRepository.findById(movie_id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            int count = movie.getCount();
            double avgRating = movie.getRating();
            avgRating = ((avgRating * count) + rating) / (++count);
            double finalRating = (double) (Math.round(avgRating * 10.0) / 10.0);
            movieRepository.updateRating(finalRating, count, movie_id);
        } else
            throw new MovieServiceException("No movie associated with the given id");
    }

    //This method gets invoked when user wants to see full details of the movie.
    @Override
    public BriefMovieDisplay findMovieDetails(int movie_id) {
        Optional<Movie> optionalMovie = movieRepository.findById(movie_id);
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

    //Method to be called when user wants to add a new movie to the database
    @Override
    public int addMovie(Movie movie) {
        Movie movieAdded = null;
        movieAdded = movieRepository.save(movie);
        return movieAdded.getId();
    }

    //Method to be invoked when user wants to edit the existing movie
    @Override
    public Movie updateMovieById(Movie movie) {
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

    //Service to be invoked when user wants to search a movie by title or/and genre
    @Override
    public List<Movie> searchMovies(String title, String name, int pageNumber) {
        if (name.equalsIgnoreCase("all") && title.isEmpty())
            return movieRepository.findAll();
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