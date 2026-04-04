package com.example.trendingmoviesservice.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.web.client.RestTemplate;

import com.example.grpc.GetTrendingMoviesRequest;
import com.example.grpc.GetTrendingMoviesResponse;
import com.example.grpc.Movie;
import com.example.grpc.TrendingMoviesServiceGrpc;
import com.example.trendingmoviesservice.models.TmdbTopMoviesResponse;

import io.grpc.stub.StreamObserver;

@GrpcService
public class TrendingMovies extends TrendingMoviesServiceGrpc.TrendingMoviesServiceImplBase {

    @Value("${api.key}")
    private String apiKey;

    @Value("${topk.movies}")
    private int topKMovies;

    private RestTemplate restTemplate;

    public TrendingMovies(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void getTrendingMovies(GetTrendingMoviesRequest request,
            StreamObserver<GetTrendingMoviesResponse> responseObserver) {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + apiKey;
        TmdbTopMoviesResponse tmdbResponse = restTemplate.getForObject(url, TmdbTopMoviesResponse.class);

        GetTrendingMoviesResponse.Builder responsBuilder = GetTrendingMoviesResponse.newBuilder();
        tmdbResponse.getResults()
                .stream()
                .limit(topKMovies)
                .forEach(m -> responsBuilder.addMovies(
                        Movie.newBuilder()
                                .setMovieId(m.getId())
                                .setName(m.getTitle())
                                .setDescription(m.getOverview())
                                .setRating(m.getVote_average())
                                .build()));

        responseObserver.onNext(responsBuilder.build());
        responseObserver.onCompleted();
    }
}
