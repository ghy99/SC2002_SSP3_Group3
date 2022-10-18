package Movie;

import Review.AllReviews;

import java.util.ArrayList;

public class Movie extends AllReviews{
    // Each Movie.Movie requires variables such as
    // Movie.Movie Title
    // Showing Status - Coming Soon, Preview, Now Showing
    // Synopsis
    // Director
    // Cast, at least 2
    // Overall Reviewer Rating (1 - 5)
    // Past review, Reviewer Rating
    private String movieTitle;
    private MovieStatus showingStatus;
    private String synopsis;
    private ArrayList<String> cast = new ArrayList<>();

    public enum MovieStatus{
        ComingSoon,
        Preview,
        NowShowing
    }

    public Movie(String movieTitle, MovieStatus showingStatus, String synopsis, ArrayList<String> cast) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.synopsis = synopsis;
        this.cast = cast;
    }
}
