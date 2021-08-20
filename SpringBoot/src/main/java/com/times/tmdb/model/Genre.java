package com.times.tmdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @NotNull(message = "Genre can't be null")
    @Size(max = 20,message = "Summary must not exceed 20 words")
    @ApiModelProperty(notes = "thematic category of the movie")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "genres")
    @JsonIgnore
    private List<Movie> movies=new ArrayList<>();

    public void addMovie(Movie m) {
        this.getMovies().add(m);
    }
}
