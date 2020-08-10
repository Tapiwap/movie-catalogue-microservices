package com.tutorials.micro.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tutorials.micro.moviecatalogservice.model.CatalogueItem;
import com.tutorials.micro.moviecatalogservice.model.Movie;
import com.tutorials.micro.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogueService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogueItem")
    public CatalogueItem getCatalogueItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogueItem(movie.getName(), "Description", rating.getRating());
    }

    private CatalogueItem getFallbackCatalogueItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogueItem("No Movie", "No Description", rating.getRating());
    }
}
