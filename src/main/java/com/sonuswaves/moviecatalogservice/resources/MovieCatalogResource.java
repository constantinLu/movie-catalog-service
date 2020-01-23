package com.sonuswaves.moviecatalogservice.resources;

import com.sonuswaves.moviecatalogservice.models.CatalogItem;
import com.sonuswaves.moviecatalogservice.models.UserRating;
import com.sonuswaves.moviecatalogservice.service.MovieInfoService;
import com.sonuswaves.moviecatalogservice.service.MovieRatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private MovieInfoService movieInfoService;

    private MovieRatingService movieRatingService;


    public MovieCatalogResource(MovieInfoService movieInfoService, MovieRatingService movieRatingService) {
        this.movieInfoService = movieInfoService;
        this.movieRatingService = movieRatingService;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = movieRatingService.getFallBackUserRating(userId);
        // For each movie ID, call movie info service and get details
        return ratings.getUserRating().stream()
                .map(rating -> movieInfoService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }
}
