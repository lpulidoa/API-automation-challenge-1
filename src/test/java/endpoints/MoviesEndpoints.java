package endpoints;

public enum MoviesEndpoints {
    GET_DETAILS("/movie/{movie_id}"),
    RATE_MOVIE("/movie/{movie_id}/rating");


    private String path;

    MoviesEndpoints(String reqPath) {
        this.path = reqPath;
    }

    public String getPath() {
        return path;
    }
}
