package requests;

import endpoints.MoviesEndpoints;
import entities.movies.MovieRating;
import io.restassured.response.Response;
import utils.JSONHelper;
import utils.TMDBApi;

import java.util.Map;
import java.util.logging.Logger;

public class Movies extends Requests{

    private static final Logger log = Logger.getLogger(Movies.class.getName());
    public Movies(String sessionId){
        super(sessionId);
    }

    public Map<String,Object> getMovieDetails(String movieId) {
        log.info("Getting movie details");
        Response response = TMDBApi.getWithPathParams(MoviesEndpoints.GET_DETAILS.getPath(), "movie_id", movieId);
        Map<String, Object> responseMap = turnIntoMap(response);

        return responseMap;
    }


    public Map<String,Object> rateMovie(String movieId) {
        log.info("Rating movie");
        MovieRating body = JSONHelper.fromJsonToObject("movies_data.json", "rateMovieBody", MovieRating.class);
        Response response =  TMDBApi.postWithBodyPathParam(MoviesEndpoints.RATE_MOVIE.getPath(), body, sessionId,
                        "movie_id", movieId);
        Map<String, Object> responseMap = turnIntoMap(response);

        return responseMap;
    }
}
