package com.times.tmdb.repository;

import com.times.tmdb.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer> {

    List<Review> findAllByMovieId(int movie_id, Pageable pageable);

}
