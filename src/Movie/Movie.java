package Movie;

import Review.AllReviews;
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

    private MovieType.Type typeOfCinema;

    private MovieType.Genre MovieGenre;

    private MovieType.Dimension movie3D;

    private MovieType.Class movieClass;

    public enum MovieStatus {
        ComingSoon,
        Preview,
        NowShowing
    }

    public Movie() {

    }
    public Movie(String movieTitle, MovieStatus showingStatus, String synopsis, ArrayList<String> cast,
                 MovieType.Type movietype, MovieType.Genre moviecat,
                 MovieType.Dimension dimension, MovieType.Class movieClass) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.synopsis = synopsis;
        this.cast = cast;
        this.typeOfCinema = movietype;
        this.MovieGenre = moviecat;
        this.movie3D = dimension;
        this.movieClass = movieClass;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }
    public MovieStatus getShowingStatus() {
        return this.showingStatus;
    }
    public String getSynopsis() {
        return this.synopsis;
    }
    public ArrayList<String> getCast() {
        return this.cast;
    }
    public MovieType.Type getTypeOfCinema() { return this.typeOfCinema; }
    public MovieType.Genre getMovieGenre() { return this.MovieGenre; }
    public MovieType.Dimension getMovie3D() { return this.movie3D; }
    public MovieType.Class getMovieClass() {
        return this.movieClass;
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

    public void setMovieType(MovieType.Type type) {
        this.typeOfCinema = type;
    }

    public void setMovieGenre(MovieType.Genre genre) {
        this.MovieGenre = genre;
    }

    public void setMovie3D(MovieType.Dimension dim) {
        this.movie3D = dim;
    }

    public void setMovieClass(MovieType.Class movieclass) {
        this.movieClass = movieclass;
    }

    public void printMovieDetails() {
        System.out.printf("\t\tMovie Title: %s\n", this.movieTitle);
        System.out.printf("\t\tMovie Status: %s\n", this.showingStatus);
        System.out.printf("\t\tMovie Synopsis: %s\n", this.synopsis);
        System.out.printf("\t\tMovie Casts: %s\n", this.cast);
        System.out.printf("\t\tCinema Type: %s\n", this.typeOfCinema.toString());
        System.out.printf("\t\tMovie Category: %s\n", this.MovieGenre.toString());
        if (Objects.equals(this.movie3D.toString(), "Three")) {
            System.out.println("\t\tMovie is available in 3D!\n");
        }
        else {
            System.out.println("\t\tMovie is available in 2D\n");
        }
        System.out.printf("\t\tMinimum Age for Watching: %s\n", this.movieClass.toString());

    }
}
