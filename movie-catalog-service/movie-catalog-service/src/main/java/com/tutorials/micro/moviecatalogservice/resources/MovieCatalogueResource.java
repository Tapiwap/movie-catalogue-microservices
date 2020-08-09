package com.tutorials.micro.moviecatalogservice.resources;

import com.tutorials.micro.moviecatalogservice.model.CatalogueItem;
import com.tutorials.micro.moviecatalogservice.model.Movie;
import com.tutorials.micro.moviecatalogservice.model.Rating;
import com.tutorials.micro.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogue")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogueItem> getCatalogue(@PathVariable("userId") String userId) {
        UserRating userRating = restTemplate.getForObject("http://movie-ratings-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogueItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());
    }
}


/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/
