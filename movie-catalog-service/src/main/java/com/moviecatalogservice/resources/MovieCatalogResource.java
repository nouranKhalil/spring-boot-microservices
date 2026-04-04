package com.moviecatalogservice.resources;

import com.example.grpc.GetTrendingMoviesRequest;
import com.example.grpc.GetTrendingMoviesResponse;
import com.moviecatalogservice.models.CatalogItem;
import com.moviecatalogservice.models.Movie;
import com.moviecatalogservice.models.Rating;
import com.moviecatalogservice.models.UserRating;
import com.moviecatalogservice.services.MovieInfoService;
import com.moviecatalogservice.services.TrendingMoviesClientService;
import com.moviecatalogservice.services.UserRatingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;

    private final MovieInfoService movieInfoService;

    private final UserRatingService userRatingService;

    private final TrendingMoviesClientService trendingMoviesClientServiceStub;

    public MovieCatalogResource(RestTemplate restTemplate,
            MovieInfoService movieInfoService,
            UserRatingService userRatingService,
            TrendingMoviesClientService trendingMoviesClientServiceStub) {

        this.restTemplate = restTemplate;
        this.movieInfoService = movieInfoService;
        this.userRatingService = userRatingService;
        this.trendingMoviesClientServiceStub = trendingMoviesClientServiceStub;
    }

    /**
     * Makes a call to MovieInfoService to get movieId, name and description,
     * Makes a call to RatingsService to get ratings
     * Accumulates both data to create a MovieCatalog
     * 
     * @param userId
     * @return CatalogItem that contains name, description and rating
     */
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        List<Rating> ratings = userRatingService.getUserRating(userId).getRatings();
        return ratings.stream().map(movieInfoService::getCatalogItem).collect(Collectors.toList());
    }

    /**
     * Makes a call to TrendingMoviesService to get top 10 trending movies by rating
     * 
     * @param null
     * @return CatalogItem that contains name, description and rating
     */
    @RequestMapping("/trending")
    public List<Movie> getTrendingMovies() {
        GetTrendingMoviesResponse response = trendingMoviesClientServiceStub.getTrendingMovies(
                GetTrendingMoviesRequest
                        .newBuilder()
                        .build());
        return response.getMoviesList().stream()
                       .map(m -> new Movie(m.getMovieId(), m.getName(), m.getDescription()))
                       .toList();

    }
}
