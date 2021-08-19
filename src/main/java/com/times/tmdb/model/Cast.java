package com.times.tmdb.model;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@ApiModel(description = " CAST -> Group of people who make up a movie")
public class Cast {
    @Size(max = 50,message = "Summary must not exceed 50 characters")
    @ApiModelProperty(notes = " the group of actors who make up a film")
    String name;
}
