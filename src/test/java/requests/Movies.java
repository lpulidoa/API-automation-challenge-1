package requests;

import endpoints.ListsEndpoints;
import endpoints.MoviesEndpoints;
import entities.lists.NewList;
import entities.movies.MovieRating;
import io.restassured.common.mapper.TypeRef;
import utils.JSONHelper;
import utils.TMDBApi;

import java.util.Map;
import java.util.logging.Logger;

public class Movies {

    private String sessionId;
    private static final Logger log = Logger.getLogger(Movies.class.getName());


    public Movies(String sessionId){
        this.sessionId = sessionId;
    }

    public Map<String,Object> getMovieDetails(String movieId) {
        return TMDBApi.getWithPathParams(MoviesEndpoints.GET_DETAILS.getPath(), "movie_id", movieId)
                .body().as(new TypeRef<Map<String,Object>>() {});
    }

    public Map<String,Object> rateMovie(String movieId) {
        log.info("Rating movie");
        MovieRating body = JSONHelper.fromJsonToObject("movies_data.json", "rateMovieBody", MovieRating.class);
        return TMDBApi.postWithBodyPathParam(MoviesEndpoints.RATE_MOVIE.getPath(), body, sessionId,
                        "movie_id", movieId)
                .body().as(new TypeRef<Map<String,Object>>() {});

    }
}
