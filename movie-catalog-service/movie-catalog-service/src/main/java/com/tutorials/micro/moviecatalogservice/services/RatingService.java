package com.tutorials.micro.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tutorials.micro.moviecatalogservice.model.Rating;
import com.tutorials.micro.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RatingService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userId) {
        UserRating userRating = restTemplate.getForObject("http://movie-ratings-service/ratingsdata/user/" + userId, UserRating.class);
        return userRating;
    }

    private UserRating getFallbackUserRating(String userId) {
        return new UserRating(userId, Arrays.asList(
                new Rating("", 0)
        ));
    }
}
