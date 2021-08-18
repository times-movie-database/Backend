package com.times.tmdb.response.movie;

import com.times.tmdb.model.Cast;
import com.times.tmdb.model.Genre;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BriefMovieDisplay {

    private int id;
    private String title;
    private double rating;
    private String summary;
    private int count;
    private List<Genre> genres = new ArrayList<>();
    private List<Cast> cast;
}
