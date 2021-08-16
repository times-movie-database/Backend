package com.times.tmdb.converter;

import com.times.tmdb.dto.GenreDTO;
import com.times.tmdb.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public Genre dtoToEntity(GenreDTO genreDTO){
        Genre genre=new Genre();
        genre.setName(genreDTO.getName().toLowerCase());
        return genre;
    }
}
