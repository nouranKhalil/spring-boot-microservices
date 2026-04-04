package com.example.trendingmoviesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class TrendingMoviesServiceApplication {

	private final int TIMEOUT = 3000; // 3 seconds


    @Bean
    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectionRequestTimeout(TIMEOUT);   // Set the timeout to 3 seconds
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(TrendingMoviesServiceApplication.class, args);
	}

}
