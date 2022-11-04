package Review;
import Movie.Movie;
import Service.TextDB;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class AllReviews
{
    public enum SortType {
        NewToOld,
        OldToNew,
        HighestToLowest,
        LowestToHigest;
    }
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<Review> listOfReview = new ArrayList<>();
    private float overallRating = 0;

    public ArrayList<Review> getListOfReview() {
        return listOfReview;
    }

    public void setReview(String userName, float rating , String review){
        if(0 <= rating && rating <= 5) {
            listOfReview.add(new Review(userName, review,rating ));
        }
        else
        {
            System.out.println("Rating not added! Please enter rating between 1-5!");
        }
    }

    public void addReview(String userName, float rating , String review , ArrayList<Movie> movies) throws IOException {
        if(Objects.equals(userName,""))
        {
            userName = "Unknown";
        }
        if(0 <= rating && rating <= 5) {
            Review temp =  new Review(userName, review,rating );
            listOfReview.add(new Review(userName, review,rating ));
            TextDB.UpdateToTextDB(TextDB.Files.Movies.toString(), movies , temp);
            System.out.println("Rating added!");
        }
        else
        {
            System.out.println("Rating not added! Please enter rating between 1-5!");
        }
    }

    public void setOverallRating(float overallRating) {
        this.overallRating = overallRating;
    }

    public float getOverallRating() {
        return getOverAllReviwerRating();
    }

    public ArrayList<Review> sortReview(SortType sortType)
    {
        ArrayList<Review> tempReview = (ArrayList<Review>) this.listOfReview.clone();

        switch (sortType)
        {
            case NewToOld -> {
                Collections.reverse(tempReview);
                return tempReview;
            }
            case HighestToLowest -> {
                tempReview.sort(Comparator.comparing(Review::getRating));
                return tempReview;
            }
            case LowestToHigest -> {
                tempReview.sort(Comparator.comparing(Review::getRating));
                Collections.reverse(tempReview);
                return tempReview;
            }
            default -> {return this.listOfReview;}
        }
    }
    private float getOverAllReviwerRating() {
        if(listOfReview.size() < 1)
        {
            System.out.println("NA");
            return 0;
        }
        else {
            float oR = 0;
            for (Review review : listOfReview) {
                oR += review.getRating();
            }
            oR = oR/ listOfReview.size();

            return round(oR,1).floatValue();
        }
    }


    public static void printListOfReview(ArrayList<Review> list) {
        if(list.size() > 1)
        {
            for (Review review : list) {
                System.out.println("Review Rating: " + review.getRating() + " Review: " + review.getReview());
            }
        }
    }

    private static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace);
        return bd;
    }
}