package com.times.tmdb.repository;

import com.times.tmdb.model.Genre;
import com.times.tmdb.model.Movie;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {


    @Query(value = "select * from movie m  inner join movie_genre mg on mg.movie_id=m.id  inner join genres g on g.id=mg.genre_id where g.name like  :genre% ", nativeQuery = true)
    List<Movie> findAllByGenres(@Param("genre") String genre, Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.rating = ?1, m.count = ?2 WHERE m.id = ?3")
    int updateRating(@Param("avgRating") double rating, @Param("count") int count, @Param("id") int id);

}
