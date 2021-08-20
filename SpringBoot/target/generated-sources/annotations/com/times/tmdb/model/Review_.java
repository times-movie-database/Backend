package com.times.tmdb.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Review.class)
public abstract class Review_ {

	public static volatile SingularAttribute<Review, Date> createdAt;
	public static volatile SingularAttribute<Review, Movie> movie;
	public static volatile SingularAttribute<Review, String> review;
	public static volatile SingularAttribute<Review, Integer> id;

	public static final String CREATED_AT = "createdAt";
	public static final String MOVIE = "movie";
	public static final String REVIEW = "review";
	public static final String ID = "id";

}

