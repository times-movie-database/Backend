package com.times.tmdb.service;

import com.times.tmdb.model.Genre;
import com.times.tmdb.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviews(int id, int pageNumber);
    void updateMovieReview(int id, String review);
}
