package com.sonuswaves.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sonuswaves.moviecatalogservice.models.Rating;
import com.sonuswaves.moviecatalogservice.models.UserRating;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class MovieRatingService {

    private RestTemplate restTemplate;

    private static final String RATING_INFO = "http://rating-data-service/ratingsdata/users/";

    public MovieRatingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallBackUserRating")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject(RATING_INFO + userId,
                UserRating.class);
    }

    /**
     *  FallbackMethod from Hysterix
     * @param userId user comming from the user
     * @return mocked value
     */
    public UserRating getFallBackUserRating(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRating(Arrays.asList(new Rating("0", 0)));
        return userRating;
    }
}

