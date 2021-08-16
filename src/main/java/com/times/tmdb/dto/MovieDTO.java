package com.times.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.times.tmdb.model.Cast;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    @ApiModelProperty(notes = "Title of the movie")
    private String title;
    @ApiModelProperty(notes = "Summary of the movie")
    private String summary;
    @ApiModelProperty(notes = "Cast list of the movie")
    private List<Cast> castList;
    @ApiModelProperty(notes = "List of genres")
    private List<Integer> genreIdList;
}
