package Review;

import Movie.Movie;
import Service.Settings;
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
     * @param rating   Rating must be ">= 0 and <=5"
     * @param review   Review
     * @param movies   Movie to review
     * @throws IOException - Check if Reviews Database is updated.
     */
    public void addReview(String userName, float rating, String review, ArrayList<Movie> movies) throws IOException {
        if (Objects.equals(userName, "")) {
            userName = "Unknown";
        }
        if (0 <= rating && rating <= 5) {
            listOfReview.add(new Review(userName, review, rating));
            TextDB.UpdateToTextDB(movies, TextDB.Files.Movies.toString());
            System.out.println("Rating added!");
        } else {
            System.out.println("Rating not added! Please enter rating between 1-5!");
        }
    }

    /**
     * This method returns overall Rating
     *
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
                System.out.println(Settings.ANSI_CYAN);
                System.out.println("*************************************************");
                System.out.println("*  Reviews sorted from Lowest Rating to Highest *");
                System.out.println("*************************************************");
                System.out.println(Settings.ANSI_RESET);
                Collections.reverse(tempReview);
                return tempReview;
            }
            case LowestToHigest -> {
                System.out.println("*************************************************");
                System.out.println("*       Lowest rating to highest review         *");
                System.out.println("*************************************************");
                tempReview.sort(Comparator.comparing(Review::getRating));
                return tempReview;
            }
            case HighestToLowest -> {
                System.out.println(Settings.ANSI_CYAN);
                System.out.println("*************************************************");
                System.out.println("*  Reviews sorted from Highest Rating to Lowest *");
                System.out.println("*************************************************");
                System.out.println(Settings.ANSI_RESET);
                tempReview.sort(Comparator.comparing(Review::getRating));
                Collections.reverse(tempReview);
                return tempReview;
            }
            default -> {
                System.out.println(Settings.ANSI_CYAN);
                System.out.println("*************************************************");
                System.out.println("*      Reviews sorted from Oldest to Newest     *");
                System.out.println("*************************************************");
                System.out.println(Settings.ANSI_RESET);
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
     *
     * @param sortType Review sort type
     */
    public void printSortedReview(ReviewSort sortType) {

        ArrayList<Review> tempReview = sortReview(sortType);

        for (Review review : tempReview) {
            System.out.println("\nUsername: " + review.getUserName());
            System.out.println("Review Rating: " + review.getRating() + "\tReview: " + review.getReview());
        }
        System.out.println();
    }

    /**
     * Big decimal for rounding overall rating
     *
     * @param d
     * @return
     */
    private static BigDecimal round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.remainder(BigDecimal.TEN).round(MathContext.DECIMAL32);
        return bd;
    }
}