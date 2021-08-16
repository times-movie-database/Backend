package com.times.tmdb.service;

import com.times.tmdb.dto.MovieDisplay;
import com.times.tmdb.dto.BriefMovieDisplay;
import com.times.tmdb.model.*;
import com.times.tmdb.repository.GenreRepository;
import com.times.tmdb.repository.MovieRepository;
import com.times.tmdb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
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

    @Override
    public List<Review> findAllReviews(int id, int pageNumber) {
        return reviewRepository.findAllByMovieId(id, PageRequest.of(pageNumber, 5));
    }

    @Override
    public void updateMovieRating(int id, double rating) {
        double avgRating;
        Movie movie = movieRepository.findById(id).get();
        int count = movie.getCount();
        avgRating = movie.getRating();
        avgRating = ((avgRating * count) + rating) / (++count);
        movieRepository.updateRating(avgRating, count, id);
    }

    @Override
    public void updateMovieReview(int id, String review) {
        Movie movie = null;
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            movie = optionalMovie.get();
            Review review1 = new Review();//review, movie
            review1.setMovie(movie);
            review1.setReview(review);
            movie.addReview(review1);
            movieRepository.save(movie);
        }
    }

    public BriefMovieDisplay findMovieDetails(int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
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
            return null;
    }

    public ResponseEntity<Integer> addMovie(Movie movie) {
        Movie movie1 = movieRepository.save(movie);
        return ResponseEntity.ok((Integer) movie1.getId());
    }

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

    @Override
    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }

    @Override
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Movie> findAllMovies(int pageNumber) {
        return movieRepository.findAll(PageRequest.of(pageNumber, 20)).toList();
    }

    public static Specification<Movie> joinTest(String title, String name) {
        return (root, query, builder) -> {
            ListJoin<Movie, Genre> movieGenreJoin = root.join(Movie_.genres);
            Predicate predicate = builder.equal(movieGenreJoin.get(Genre_.NAME), name);

            if (title != null && title.trim() != "") {
                return builder.equal(root.get(Movie_.TITLE), title);
            }
            return predicate;
        };
    }

    public static Specification<Movie> genrePredicate(String title, String name) {

        return (root, query, builder) -> {
            if (name.equalsIgnoreCase("all")) {
                if (title != null && title.trim() != "")
                    return builder.like(root.get(Movie_.TITLE), title+"%");
                return builder.isTrue(builder.literal(true));
            }
            ListJoin<Movie, Genre> movieGenreJoin = root.join(Movie_.genres);

            Predicate genrePredicate = builder.equal(movieGenreJoin.get(Genre_.NAME), name);
            if (title != null && title.trim() != "") {
                Predicate titlePredicate = builder.like(root.get(Movie_.TITLE), title+"%");
                return builder.and(titlePredicate, genrePredicate);
            }
            return genrePredicate;
        };
    }

    @Override
    public List<Movie> searchMovies(String title, String name) {
        if (name.equalsIgnoreCase("all"))
            return movieRepository.findAll();
        return movieRepository.findAll(Specification.where(genrePredicate(title, name)), PageRequest.of(0, 20)).toList();
    }
}
