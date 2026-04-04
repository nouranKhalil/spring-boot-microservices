package com.moviecatalogservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.grpc.TrendingMoviesServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {
    @Bean
    public TrendingMoviesServiceGrpc.TrendingMoviesServiceBlockingStub trendingMoviesServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8084)
                                                      .usePlaintext()
                                                      .build();
        return TrendingMoviesServiceGrpc.newBlockingStub(channel);
    }
}
