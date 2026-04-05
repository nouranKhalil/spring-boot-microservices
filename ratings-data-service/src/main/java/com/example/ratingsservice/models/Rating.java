package com.example.ratingsservice.models;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ratings")
@IdClass(Rating.RatingId.class)
public class Rating {

    @Id
    @Column(name = "user_id", nullable = false)
    @JsonIgnore
    private String userId;

    @Id
    @Column(name = "movie_id", nullable = false)
    private String movieId;

    @Column(name = "rating", nullable = false)
    private int rating;

    // Composite key class
    public static class RatingId implements Serializable {
        private String userId;
        private String movieId;

        public RatingId() {}

        public RatingId(String userId, String movieId) {
            this.userId = userId;
            this.movieId = movieId;
        }

        // equals and hashCode are REQUIRED for composite keys
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RatingId)) return false;
            RatingId that = (RatingId) o;
            return userId.equals(that.userId) && movieId.equals(that.movieId);
        }

        @Override
        public int hashCode() {
            return 31 * userId.hashCode() + movieId.hashCode();
        }
    }

    public Rating() {
    }

    public Rating(String userId, String movieId, int rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
