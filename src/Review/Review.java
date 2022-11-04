package Review;

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
