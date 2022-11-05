package Review;

import Movie.Movie;
import Service.TextDB;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author CHEW ZHI QI
 * All review class contains a list of comments, overallRaing
 */
public class AllReviews {
    /**
     * Enum sort type by top 5 sale or top 5 rating
     */
    public enum ReviewSort {
        NewToOld,
        OldToNew,
        HighestToLowest,
        LowestToHigest;
    }

    /**
     * List of review for each movie
     */
    private ArrayList<Review> listOfReview = new ArrayList<>();

    /**
     * @return Get list of review
     */
    public ArrayList<Review> getListOfReview() {
        return listOfReview;
    }

    /**
     * Add review from db
     *
     * @param userName Name of reviwer
     * @param rating   Rating
     * @param review   Review
     */
    public void setReview(String userName, float rating, String review) {
        listOfReview.add(new Review(userName, review, rating));
    }

    /**
     * Add a new review
     *
     * @param userName Name of reviewer
     * @param rating   Rating must be >= 0 and <=5
     * @param review   Review
     * @param movies   Movie to review
     * @throws IOException
     */
    public void addReview(String userName, float rating, String review, ArrayList<Movie> movies) throws IOException {
        if (Objects.equals(userName, "")) {
            userName = "Unknown";
        }
        if (0 <= rating && rating <= 5) {
            listOfReview.add(new Review(userName, review, rating));
            TextDB.UpdateTextDB(TextDB.Files.Movies.toString(), movies);
            System.out.println("Rating added!");
        } else {
            System.out.println("Rating not added! Please enter rating between 1-5!");
        }
    }

    /**
     * @return Float overall rating
     */
    public float getOverallRating() {
        return getOverAllReviwerRating();
    }

    /**
     * Sort review by type
     *
     * @param sortType Sort type
     * @return
     */
    private ArrayList<Review> sortReview(ReviewSort sortType) {
        ArrayList<Review> tempReview = (ArrayList<Review>) this.listOfReview.clone();

        switch (sortType) {
            case NewToOld -> {
                System.out.println("##########Old to new review###########");
                Collections.reverse(tempReview);
                return tempReview;
            }
            case LowestToHigest -> {
                System.out.println("####Lowest rating to highest review####");
                tempReview.sort(Comparator.comparing(Review::getRating));
                return tempReview;
            }
            case HighestToLowest -> {
                System.out.println("####Higest rating to lowest review####");
                tempReview.sort(Comparator.comparing(Review::getRating));
                Collections.reverse(tempReview);
                return tempReview;
            }
            default -> {
                System.out.println("#########New to old review#########");
                return this.listOfReview;
            }
        }
    }

    /**
     * Calcuate overall review rating
     *
     * @return Float overall review rating
     */
    private float getOverAllReviwerRating() {
        if (listOfReview.size() < 1) {
            return 0;
        } else {
            float oR = 0;
            for (Review review : listOfReview) {
                oR += review.getRating();
            }
            oR = oR / listOfReview.size();

            return round(oR).floatValue();
        }
    }

    /**
     * Print sorted list with given sort type
     * @param sortType Review sort type
     */
    public void printSortedReview(ReviewSort sortType) {

        ArrayList<Review> tempReview = sortReview(sortType);

        for (Review review : tempReview) {
            System.out.println("Review Rating: " + review.getRating() + " Review: " + review.getReview());
        }
        System.out.println();
    }

    /**
     * Big decimal for rounding overall rating
     * @param d
     * @return
     */
    private static BigDecimal round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.remainder(BigDecimal.TEN).round(MathContext.DECIMAL32);
        return bd;
    }
}