package com.example.trendingmoviesservice.models;

import java.util.List;

public class TmdbTopMoviesResponse {
    private List<MovieSummary> results;

    public List<MovieSummary> getResults() {
        return results;
    }

    public void setResults(List<MovieSummary> results) {
        this.results = results;
    }

}
