package Movie;

import Review.AllReviews;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Movie extends AllReviews {
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

    private MovieType movieType;

    private MovieCategory movieCat;

    private MovieDimension movie3D;

    public enum MovieStatus{
        ComingSoon,
        Preview,
        NowShowing
    }

    public enum MovieType {
        Premium, Regular
    }

    public enum MovieCategory {
        Action, Anime, Comedy, Horror, Thriller, Romance
    }

    public enum MovieDimension {
        Two, Three
    }

    public Movie() {

    }
    public Movie(String movieTitle, MovieStatus showingStatus, String synopsis, ArrayList<String> cast,
                 MovieType movietype, MovieCategory moviecat, MovieDimension dimension) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.synopsis = synopsis;
        this.cast = cast;
        this.movieType = movietype;
        this.movieCat = moviecat;
        this.movie3D = dimension;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }

    public void setMovieTitle(String movieName) {
        this.movieTitle = movieName;
    }

    public void setShowingStatus(MovieStatus status) {
        this.showingStatus = status;
    }

    public void setSynopsis(String synop) {
        this.synopsis = synop;
    }

    public void setCast(ArrayList<String> casts) {
        this.cast.addAll(casts);
    }

    public void setMovieType(MovieType type) {
        this.movieType = type;
    }

    public void setMovieCat(MovieCategory cat) {
        this.movieCat = cat;
    }

    public void setMovie3D(MovieDimension dim) {
        this.movie3D = dim;
    }

    public void printMovieDetails() {
        System.out.printf("\t\tMovie Title: %s\n", this.movieTitle);
        System.out.printf("\t\tMovie Status: %s\n", this.showingStatus);
        System.out.printf("\t\tMovie Synopsis: %s\n", this.synopsis);
        System.out.printf("\t\tMovie Casts: %s\n", this.cast);
//        System.out.printf(": %s\n", this.movieType);
        System.out.printf("\t\tMovie Category: %s\n", this.movieCat);
        if (Objects.equals(this.movie3D.toString(), "Three")) {
            System.out.println("\t\tMovie is available in 3D!\n");
        }
        else {
            System.out.println("\t\tMovie is available in 2D\n");
        }
    }
}
