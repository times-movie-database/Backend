package com.times.tmdb.service.genre;

import com.times.tmdb.model.Genre;
import com.times.tmdb.repository.GenreRepository;
import com.times.tmdb.service.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    //Method to be invoked to find all the existing genre
    @Override
    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }

    //Method to be invoked to add a new genre to the database
    @Override
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
