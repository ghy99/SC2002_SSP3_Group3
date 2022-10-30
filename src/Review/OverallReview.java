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
	
	
//	public boolean equals(Object o) {
//		OverallReview overallReview = (OverallReview) o;
//		if(overallReview.movieTitle == this.movieTitle && overallReview.avgRating == this.avgRating) {
//			return true;
//		}
//		return false;
//	}
//
//	public int compareTo(Object o) {
//		
//		boolean sameobject = false;
//		int namecomp;
//		OverallReview overallReview = (OverallReview) o;
//		
//		if(equals(o) == true) {
//			System.out.println("Found matching objects");
//			return 0;
//		}
//		else {
//			namecomp = overallReview.movieTitle.compareTo(movieTitle);
//			
//			if(Double.parseDouble(this.avgRating) > Double.parseDouble(overallReview.avgRating)) {
//				System.out.println("Current object avgRating is higher");
//				return 1;
//			}
//			else if(Double.parseDouble(this.avgRating) < Double.parseDouble(overallReview.avgRating)) {
//				System.out.println("Next object avgRating is higher");
//				return -1;
//			}
//			else if(Double.parseDouble(this.avgRating) == Double.parseDouble(overallReview.avgRating)) {
//				return (namecomp);
//			}
//			return 0;
//		}
//	}
//
//
//	@Override
//	public int compareTo(OverallReview o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	
	
	


	

}
