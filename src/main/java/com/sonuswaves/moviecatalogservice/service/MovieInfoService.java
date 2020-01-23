package com.sonuswaves.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sonuswaves.moviecatalogservice.models.CatalogItem;
import com.sonuswaves.moviecatalogservice.models.Movie;
import com.sonuswaves.moviecatalogservice.models.Rating;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieInfoService {

    private RestTemplate restTemplate;

    //private WebClient.Builder builder;

    private static final String MOVIE_INFO = "http://movie-info-service/movies/";

    public MovieInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject(MOVIE_INFO + rating.getMovieId(), Movie.class);

//            Movie movie = builder.build() //builder pattern to give us a pattern
//                    .get()  //what method you need to call the service
//                    .uri(MOVIE_INFO +  rating.getMovieId()) // the URL
//                    .retrieve() // GO DO THE FETCH
//                    .bodyToMono(Movie.class)   // CONVERT IT TO MY INSTANCE CLASS MONO = reactive way of gettting te obj back in the future
//                    .block(); //
        //put them all together
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }


    //fallback methods for more granural use.
    public List<CatalogItem> getFallBackCatalogItem(Rating rating) {
        return Arrays.asList(new CatalogItem("Movie not found", "", rating.getRating()));
    }


}
