package com.times.tmdb.response.review;

import com.times.tmdb.model.Review;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Reviews display")
public class ReviewDisplay {
    private List<Review> reviews;
}
