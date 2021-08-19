package com.times.tmdb.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ApiModel(description = "Details of the specific movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Movie id ")
    private int id;
    @Column(unique = true)
    @ApiModelProperty(notes = "Title of the movie")
    private String title;
    @ApiModelProperty(notes = "Plot of the movie")
    private String summary;
    @ApiModelProperty(notes = "Average rating")
    private double rating;
    @ApiModelProperty(notes = "Number of users rated till now")
    private int count;
    @ElementCollection
    @CollectionTable
    private List<Cast> cast;
    @OneToMany(targetEntity = Review.class, cascade = CascadeType.ALL, mappedBy = "movie")
    @BatchSize(size = 1)
    private List<Review> reviews;

    public void addReview(Review review) {
        reviews.add(review);
    }

    @ManyToMany(targetEntity = Genre.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "movie_genre",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> genres = new ArrayList<>();

    public void addGenre(Genre genre) {
        this.getGenres().add(genre);
    }
}
