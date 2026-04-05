package com.example.movieinfoservice.entity;

import com.example.movieinfoservice.models.Movie;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "movie_cache")
public class MovieCacheEntity {

    @Id
    private String id;  

    @Indexed(unique = true) 
    @Field("movieId")
    private String movieId;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("cachedAt")
    private LocalDateTime cachedAt;

    @Field("ttl")
    private LocalDateTime ttl;

    @Field("accessCount")
    private int accessCount;

    @Field("lastAccessed")
    private LocalDateTime lastAccessed;

    public MovieCacheEntity() {
    }

    public MovieCacheEntity(Movie movie) {
        this.movieId = movie.getMovieId();
        this.name = movie.getName();
        this.description = movie.getDescription();
        this.cachedAt = LocalDateTime.now();
        this.ttl = this.cachedAt.plusHours(24);  
        this.accessCount = 0;
        this.lastAccessed = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCachedAt() {
        return cachedAt;
    }

    public void setCachedAt(LocalDateTime cachedAt) {
        this.cachedAt = cachedAt;
    }

    public LocalDateTime getTtl() {
        return ttl;
    }

    public void setTtl(LocalDateTime ttl) {
        this.ttl = ttl;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public Movie toMovie() {
        return new Movie(this.movieId, this.name, this.description);
    }
}