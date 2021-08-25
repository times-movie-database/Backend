package com.times.tmdb.service.review;

import com.times.tmdb.exception.MovieServiceException;
import com.times.tmdb.model.Movie;
import com.times.tmdb.model.Review;
import com.times.tmdb.repository.MovieRepository;
import com.times.tmdb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    //Method to be invoked when user posts a review to the movie
    @Override
    public void addReview(int movie_id, String review) {
        Optional<Movie> optionalMovie = movieRepository.findById(movie_id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            Review freshReview = new Review();//review, movie
            freshReview.setMovie(movie);
            freshReview.setReview(review);
            movie.addReview(freshReview);
            movieRepository.save(movie);
        } else
            throw new MovieServiceException("No movie associated with the given id");
    }

    //Function to be called when to find all the reviews related to the particular movie
    @Override
    public List<Review> findAllReviews(int movie_id, Integer pageNumber, Integer pageSize) {
        Optional<Movie> optionalMovie = movieRepository.findById(movie_id);
        if (optionalMovie.isPresent()) {
            if (pageSize != null)
                return reviewRepository.findAllByMovieId(movie_id, PageRequest.of(pageNumber, pageSize));
            else
                return reviewRepository.findAllByMovieId(movie_id, PageRequest.of(pageNumber, 5));
        } else
            throw new MovieServiceException("No movie associated with the given id");
    }
}
