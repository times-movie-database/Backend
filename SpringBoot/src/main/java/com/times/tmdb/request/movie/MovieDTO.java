package com.times.tmdb.request.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.times.tmdb.model.Cast;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message = "Title cannot be null")
    @Size(max = 100,message = "title cant be more than 100 characters")
    private String title;
    @ApiModelProperty(notes = "Summary of the movie")
    @NotNull(message = "Summary cannot be null")
    @Size(max = 500, message = "Summary can't be more than 500 words")
    private String summary;
    @ApiModelProperty(notes = "Cast list of the movie")
    @NotNull(message = "CastList cannot be null")
    private List<Cast> castList;
    @ApiModelProperty(notes = "List of genres")
    @NotNull(message = "GenreList cannot be null")
    private List<Integer> genreIdList;
}
