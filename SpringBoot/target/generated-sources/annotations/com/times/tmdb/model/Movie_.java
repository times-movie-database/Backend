package com.times.tmdb.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Movie.class)
public abstract class Movie_ {

	public static volatile SingularAttribute<Movie, String> summary;
	public static volatile ListAttribute<Movie, Cast> cast;
	public static volatile ListAttribute<Movie, Review> reviews;
	public static volatile ListAttribute<Movie, Genre> genres;
	public static volatile SingularAttribute<Movie, Double> rating;
	public static volatile SingularAttribute<Movie, Integer> count;
	public static volatile SingularAttribute<Movie, Integer> id;
	public static volatile SingularAttribute<Movie, String> title;

	public static final String SUMMARY = "summary";
	public static final String CAST = "cast";
	public static final String REVIEWS = "reviews";
	public static final String GENRES = "genres";
	public static final String RATING = "rating";
	public static final String COUNT = "count";
	public static final String ID = "id";
	public static final String TITLE = "title";

}

