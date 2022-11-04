package Review;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public Boolean addReview(float rating , String review){
        if(0 <= rating && rating <= 5) {
            listOfReview.add(new Review(review,rating ));
            return true;
        }
        else
        {
            System.out.println("Rating not added! Please enter rating between 1-5!");
            return false;
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

    public void printListOfReview() {
        if(listOfReview.size() > 1)
        {
            for (Review review : listOfReview) {
                System.out.println("Review Rating: " + String.format("%.1f", review.getRating()) + " Review: " + review.getReview());
            }
        }
    }

    private static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace);
        return bd;
    }
}