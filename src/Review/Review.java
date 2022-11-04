package Review;

import Movie.Movie;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Review {


    private String review;
    private float rating;

    public Review(String reviewer, float rating) {
        this.review = reviewer;
        this.rating = rating;

    }

    public String getReview() {
        return review;
    }


    public float getRating() {
        return rating;
    }


}
