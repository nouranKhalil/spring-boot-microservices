package com.example.movieinfoservice.repository;

import com.example.movieinfoservice.entity.MovieCacheEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MovieCacheRepository extends MongoRepository<MovieCacheEntity, String> {
    Optional<MovieCacheEntity> findByMovieId(String movieId);
    
    // find a non expired cached movie by movieID
    @Query("{ 'movieId': ?0, 'ttl': { $gt: ?1 } }")
    Optional<MovieCacheEntity> findValidCacheByMovieId(String movieId, LocalDateTime now);

    void deleteByMovieId(String movieId);

    // delete oldest entries based on lastAccessed time
    @Query(value = "{}", sort = "{ 'lastAccessed': 1 }", delete = true)
    void deleteOldestEntries(int limit);
}