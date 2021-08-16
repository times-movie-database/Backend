package com.times.tmdb.model;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Cast {

    @Size(max = 50,message = "Summary must not exceed 50 characters")
    String name;
}
