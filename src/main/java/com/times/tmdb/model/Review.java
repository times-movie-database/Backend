package com.times.tmdb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String review;
    @ManyToOne
    @JoinColumn(nullable=false)
    @JsonIgnore
    private Movie movie;
    //movie.id = id;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss" ,timezone = "Asia/Kolkata")
    private Date createdAt;
    public Review(int reviewId,String review){
        this.id=reviewId;
        this.review=review;
    }
}
