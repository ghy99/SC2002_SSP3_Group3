package Review;

import java.util.ArrayList;

public abstract class AllReviews
{
    private ArrayList<Review> listOfReview = new ArrayList<>();
    private float overAllReviwerRating;

    public void AddReview(float rating , String review){
        if(1 < rating && rating < 5) {
            listOfReview.add(new Review(rating, review));
        }
        else
        {
            System.out.println("Rating not added! Please enter rating between 1-5!");
        }
    }

    public void getOverAllReviwerRating() {
        if(listOfReview.size() < 1)
        {
            System.out.println("NA");
        }
        else {
            float oR = 0;
            for (Review review : listOfReview) {
                oR += review.getReviewerRating();
            }
            oR = oR/ listOfReview.size();

            System.out.println("Overall Rating: " + String.format("%.1f",oR));
        }
    }

    public void getListOfReview() {
        if(listOfReview.size() > 1)
        {
            for (Review review : listOfReview) {
                System.out.println("Review Rating: " + String.format("%.1f", review.getReviewerRating()) + " Review: " + review.getReview());
            }
        }
    }
}
