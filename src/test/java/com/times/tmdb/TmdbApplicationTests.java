package com.times.tmdb;

import com.times.tmdb.controller.MovieController;
import com.times.tmdb.model.Cast;
import com.times.tmdb.model.Genre;
import com.times.tmdb.model.Movie;
import com.times.tmdb.model.Review;
import com.times.tmdb.repository.MovieRepository;
import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.service.movie.MovieServiceImpl;
import com.times.tmdb.service.review.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmdbApplication.class)
class TmdbApplicationTests {
    @InjectMocks
    MovieController movieController;
    @Mock
    ReviewServiceImpl reviewService;
    @Mock
    MovieServiceImpl movieService;
    Movie movie;
    BriefMovieDisplay briefMovieDisplay;
    Genre genre;
    Review review;
    @Mock
    MovieRepository movieRepository;
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
    void getMovieDetailsByMovieId() {
        when(movieService.findMovieDetails(1)).thenReturn(briefMovieDisplay);
        BriefMovieDisplay briefMovieDisplay = movieService.findMovieDetails(1);
        assertEquals(213, briefMovieDisplay.getCount());
    }
    @Test
    void getMovieReviewsByMovieId() {
        when(reviewService.findAllReviews(1, 0, 3)).thenReturn(new ArrayList<Review>());
        List<Review> reviews = reviewService.findAllReviews(1, 0, 3);
        System.out.println(reviews.isEmpty());
        assertEquals("nicee", reviews.get(0).getReview());
    }

    @Test
    void addRating() {
        when(movieService.updateMovieRating(anyInt(), anyDouble())).thenReturn(movie);
        Movie movie = movieService.updateMovieRating(1, 3.5);
        assertEquals(4.0, movie.getRating());
    }

}
