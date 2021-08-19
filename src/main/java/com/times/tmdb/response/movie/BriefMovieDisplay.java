package com.times.tmdb.response.movie;

import com.times.tmdb.model.Cast;
import com.times.tmdb.model.Genre;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Information about the Movie along with its meta-data & data")
public class BriefMovieDisplay {
    private int id;
    private String title;
    private double rating;
    private String summary;
    private int count;
    private List<Genre> genres = new ArrayList<>();
    private List<Cast> cast;
}
