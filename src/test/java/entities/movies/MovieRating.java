package entities.movies;

public class MovieRating {
    private float value;

    public MovieRating(){

    }

    public MovieRating(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
