package src.Review;

public class Review {
	
	
	    private String reviewerRating;
	    private String review;
	    private String movieTitle;

	    public Review(String reviewerRating, String review,String movieTitle) {
	            this.reviewerRating = reviewerRating;
	            this.review = review;
	            this.movieTitle = movieTitle;
	            
	    }


	    public String getRating() {
	        return reviewerRating;
	    }

	    public String getReview() {
	        return review;
	    }
	    
	    public String getTitle() {
	        return movieTitle;
	    }


}
