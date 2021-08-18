package com.times.tmdb.response.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDisplay {
    @ApiModelProperty(notes = "Movie id")
    private int id;
    @ApiModelProperty(notes = "Title of the movie")
    private String title;
    @ApiModelProperty(notes = "Average rating of the movie")
    private double rating;
    @ApiModelProperty(notes = "Number of users rated till now")
    private int count;
}