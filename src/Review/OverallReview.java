package Review;
import java.util.*;

public class OverallReview {
	
	private String count;
	private String avgRating;
	private String movieTitle;
	public OverallReview(String movieTitle, String avgRating, String count){
	
		
		this.movieTitle = movieTitle;
		this.avgRating = avgRating;
		this.count = count;
	}
	
	
	public String getMovieTitle() {
		return movieTitle;
	}
	
	public String getavgRating() {
		return avgRating;
	}
	
	public String getCount() {
		return count;
	}
	
	public void setavgRating(String s) {
		// TODO Auto-generated method stub
		this.avgRating= s;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	

}
