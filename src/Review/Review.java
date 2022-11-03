package Review;

import Movie.Movie;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	public static void RankingByRating() throws IOException {
		TextDB textDB = new TextDB();
		ArrayList<OverallReview> overallReviewList = textDB.ReadFromFile("Consolidatedreview.txt");
		System.out.println("The top 5 movies by ratings are: ");

		if (overallReviewList.size()<5){
			for(int i = 0; i<overallReviewList.size();i++) {
				System.out.println((i+1)+")" +overallReviewList.get(i).getMovieTitle() + " -> Rating: "+ overallReviewList.get(i).getavgRating());
			}
		} else{
			for(int i = 0; i<5;i++) {
				System.out.println((i+1)+")" +overallReviewList.get(i).getMovieTitle() + " -> Rating: "+ overallReviewList.get(i).getavgRating());
			}
		}

		System.out.println("");
	}

	public static void RankingByTicketSales () throws IOException{
		TextDB textDB = new TextDB();
		ArrayList<Movie> movieList = textDB.readFromFile("Movies.txt",new ArrayList<>());
		movieList.sort(Comparator.comparing(Movie::getMovieTotalSales));
		Collections.reverse(movieList);
		System.out.println("The top 5 movies by ticket sales are: ");

		if (movieList.size()<5){
			for (int i = 0; i<movieList.size();i++){
				String movieTitle = movieList.get(i).getMovieTitle();
				int ticketSales = movieList.get(i).getMovieTotalSales();
				System.out.println((i+1)+")" + movieTitle + " -> Ticket Sales: " + ticketSales);
			}
		} else {
			for (int i = 0; i<5;i++){
				String movieTitle = movieList.get(i).getMovieTitle();
				int ticketSales = movieList.get(i).getMovieTotalSales();
				System.out.println((i+1)+")" + movieTitle + " -> Ticket Sales: " + ticketSales);
			}
		}

		System.out.println("");



	}





}
