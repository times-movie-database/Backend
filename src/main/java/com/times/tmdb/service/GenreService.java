package com.times.tmdb.service;

import com.times.tmdb.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAllGenre();
    Genre addGenre(Genre genre);
}
