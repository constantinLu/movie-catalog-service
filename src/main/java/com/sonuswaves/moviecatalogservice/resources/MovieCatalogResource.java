package com.sonuswaves.moviecatalogservice.resources;

import com.sonuswaves.moviecatalogservice.models.CatalogItem;
import com.sonuswaves.moviecatalogservice.models.Movie;
import com.sonuswaves.moviecatalogservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private RestTemplate restTemplate;

    //private WebClient.Builder builder;


    private static final String MOVIE_INFO = "http://movie-info-service/movies/";

    private static final String RATING_INFO = "http://rating-data-service/ratingsdata/users/";

    public MovieCatalogResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        //this.builder = builder;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = restTemplate.getForObject(RATING_INFO + userId,
                UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            // For each movie ID, call movie info service and get details
            Movie movie = restTemplate.getForObject(MOVIE_INFO + rating.getMovieId(), Movie.class);

//            Movie movie = builder.build() //builder pattern to give us a pattern
//                    .get()  //what method you need to call the service
//                    .uri(MOVIE_INFO +  rating.getMovieId()) // the URL
//                    .retrieve() // GO DO THE FETCH
//                    .bodyToMono(Movie.class)   // CONVERT IT TO MY INSTANCE CLASS MONO = reactive way of gettting te obj back in the future
//                    .block(); //
//
            //put them all together
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        })
        .collect(Collectors.toList());




    }
}
