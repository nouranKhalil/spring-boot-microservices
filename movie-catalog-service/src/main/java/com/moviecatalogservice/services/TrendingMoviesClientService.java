package com.moviecatalogservice.services;

import org.springframework.stereotype.Service;

import com.example.grpc.TrendingMoviesServiceGrpc.TrendingMoviesServiceBlockingStub;
import com.example.grpc.GetTrendingMoviesRequest;
import com.example.grpc.GetTrendingMoviesResponse;


@Service
public class TrendingMoviesClientService  {

    // @GrpcClient("trending-movies-service")
    private final TrendingMoviesServiceBlockingStub trendingMoviesServiceBlockingStub;  
    
    public TrendingMoviesClientService(TrendingMoviesServiceBlockingStub trendingMoviesServiceBlockingStub) {
        this.trendingMoviesServiceBlockingStub = trendingMoviesServiceBlockingStub;
    }
    public GetTrendingMoviesResponse getTrendingMovies(GetTrendingMoviesRequest request) {
        return trendingMoviesServiceBlockingStub.getTrendingMovies(request);
    }
}