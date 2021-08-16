package com.times.tmdb.repository;

import com.times.tmdb.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByNameStartingWith(String genre);
}
