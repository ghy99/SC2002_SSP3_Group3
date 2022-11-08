package Review;

import Movie.Movie;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *  * @author CHEW ZHI QI
 *  * Review class for each review object
 */
public class Review {
    /** Username */
    private String userName;
    /** Review */
    private String review;
    /** Rating */
    private float rating;

    /**
     * Review constructor
     * @param userName Useranme
     * @param review Review
     * @param rating Rating
     */
    public Review(String userName, String review, float rating) {
        this.userName = userName;
        this.review = review;
        this.rating = rating;
    }

    /**
     * Get username
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get review
     * @return review
     */
    public String getReview() {
        return review;
    }

    /**
     * Get rating
     * @return rating
     */
    public float getRating() {
        return rating;
    }
}
