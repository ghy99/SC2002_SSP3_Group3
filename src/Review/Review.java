package Review;

public class Review
{
    private float reviewerRating;
    private String review;

    public Review(float reviewerRating, String review) {
            this.reviewerRating = reviewerRating;
            this.review = review;
    }

    public float getReviewerRating() {
        return reviewerRating;
    }

    public String getReview() {
        return review;
    }
}
