package com.example.movieinfoservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.movieinfoservice.entity.MovieCacheEntity;
import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.repository.MovieCacheRepository;

@Service
public class MovieCacheService {
    
    private static final Logger logger = LoggerFactory.getLogger(MovieCacheService.class);
    
    @Autowired
    private MovieCacheRepository cacheRepository;
    
    @Value("${movie.cache.enabled:true}")
    private boolean cacheEnabled;

    /*
    get movie from cache first
    flow:
    - check if cache is enabled
    - query mongodb 
    - if found return cached movie
    - if not found return empty
     */
    public Optional<Movie> getFromCache(String movieId){
        if(!cacheEnabled){
            logger.debug("cache is not enabled for movie {}",movieId);
            return Optional.empty();
        }
        long startTime = System.currentTimeMillis();
        try{
            Optional<MovieCacheEntity> cachedEntity = cacheRepository.findValidCacheByMovieId(movieId,LocalDateTime.now());
            long timeTaken = System.currentTimeMillis() - startTime;
            
            if (cachedEntity.isPresent()){
                MovieCacheEntity entity = cachedEntity.get();
                // update access count and last accessed time and save back to mongodb
                entity.setAccessCount(entity.getAccessCount() + 1);
                entity.setLastAccessed(LocalDateTime.now());
                cacheRepository.save(entity); 
                logger.info("cache hit for movie {}, latency={}ms", movieId, timeTaken);
                return Optional.of(entity.toMovie());
            }else{
                logger.info("cache miss for movie {}, latency={}ms", movieId, timeTaken);
                return Optional.empty();
            }
        }catch(Exception e){
            logger.error("cache error: {}",e.getMessage());
            return Optional.empty();
        }
    }
    public void putInCache(Movie movie){
        if(!cacheEnabled){
            return;
        }
        try{
            MovieCacheEntity entity = new MovieCacheEntity(movie);
            cacheRepository.save(entity);
            logger.info("movie {} cached successfully", movie.getMovieId());
        }catch(Exception e){
            logger.error("failed to cache: {}",e.getMessage());
        }
    }
    public void deleteFromCache(String movieId){
        try{
            cacheRepository.deleteByMovieId(movieId);
            logger.info("movie {} removed from cache", movieId);
        }catch(Exception e){
            logger.error("failed to remove from cache: {}",e.getMessage());
        }
    }
}
