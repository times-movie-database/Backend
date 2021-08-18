package com.times.tmdb.response.review;

import com.times.tmdb.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDisplay {
    private List<Review> reviews;
}
