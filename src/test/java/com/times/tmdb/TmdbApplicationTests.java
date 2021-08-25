package com.times.tmdb;

import com.times.tmdb.controller.MovieController;
import com.times.tmdb.exception.MovieServiceException;
import com.times.tmdb.model.Cast;
import com.times.tmdb.model.Genre;
import com.times.tmdb.model.Movie;
import com.times.tmdb.model.Review;
import com.times.tmdb.repository.MovieRepository;
import com.times.tmdb.repository.ReviewRepository;
import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.service.movie.MovieService;
import com.times.tmdb.service.movie.MovieServiceImpl;
import com.times.tmdb.service.review.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TmdbApplicationTests {
    @InjectMocks
    MovieController movieController;
    @Autowired
    ReviewServiceImpl reviewService;

    Movie movie;
    BriefMovieDisplay briefMovieDisplay;
    Genre genre;
    Review review;
    @Mock
    MovieRepository movieRepository;

    @Mock
    ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        briefMovieDisplay = new BriefMovieDisplay();
        genre = new Genre();
        review = new Review();
        briefMovieDisplay.setId(1);
        briefMovieDisplay.setTitle("3Idiots");
        briefMovieDisplay.setRating(4.5);
        briefMovieDisplay.setCount(213);
        briefMovieDisplay.setSummary("fvwffferf");
        genre.setName("Comedy");
        List<Cast> castList = new ArrayList<>();
        castList.add(new Cast("Amir Khan"));
        briefMovieDisplay.setCast(castList);
        movie = new Movie();
        movie.setReviews(new ArrayList<Review>());
        movie.setId(1);
        movie.setTitle("3Idiots");
        movie.setRating(4.5);
        movie.setCount(1);
        movie.setSummary("fvwffferf");
        review.setReview("nicee");
        movie.addReview(review);
        review.setCreatedAt(new Date());
        review.setMovie(movie);
        movie.addGenre(genre);
        movie.setCast(castList);
    }

    @Test
    void addRating() {
        MovieServiceImpl movieService = new MovieServiceImpl(movieRepository);
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.updateRating(4.0, movie.getCount() + 1, 1)).thenReturn(1);
        Movie resultMovie = movieService.addRating(1, 3.5);
        assertEquals(4.0, resultMovie.getRating());
    }

    @Test
    void addRatingWithException() {
        MovieService movieService = new MovieServiceImpl(movieRepository);
        Mockito.when(movieRepository.findById(4)).thenReturn(Optional.empty());
        MovieServiceException movieServiceException = assertThrows(MovieServiceException.class, () -> movieService.addRating(4, 3.5));
        assertTrue(movieServiceException.getMessage().contains("No movie associated with the given id"));
    }

    @Test
    void findMovieDetailsByMovieId() {
        MovieService movieService = new MovieServiceImpl(movieRepository);
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        BriefMovieDisplay briefMovieDisplay = movieService.findMovieDetailsByMovieId(1);
        assertEquals("3Idiots", briefMovieDisplay.getTitle());
    }

    @Test
    void findMovieDetailsByMovieIdWithException() {
        MovieService movieService = new MovieServiceImpl(movieRepository);
        Mockito.when(movieRepository.findById(4)).thenReturn(Optional.empty());
        MovieServiceException movieServiceException = assertThrows(MovieServiceException.class, () -> movieService.findMovieDetailsByMovieId(4));
        assertTrue(movieServiceException.getMessage().contains("No movie associated with the given id"));
    }

}
