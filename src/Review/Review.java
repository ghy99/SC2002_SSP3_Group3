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

    private String userName;

    public Review(String userName, String reviewer, float rating) {
        this.userName = userName;
        this.review = reviewer;
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public String getReview() {
        return review;
    }

    public float getRating() {
        return rating;
    }


}
