package Movie;
import Cineplex.Cinema;
import Review.*;
import java.util.ArrayList;
import java.util.Objects;

public class Movie {
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
    private String director;
    private String synopsis;
    private ArrayList<String> cast = new ArrayList<>();

    private Cinema.CinemaType typeOfCinema;

    private MovieType.Genre MovieGenre;

    private MovieType.Dimension movie3D;

    private MovieType.Class movieClass;

    private int movieTotalSales = 0;

    public enum MovieStatus {
        ComingSoon,
        Preview,
        NowShowing,
        EndOfShowing
    }

    public Movie(String movieTitle, MovieStatus showingStatus, String director,
                 String synopsis, ArrayList<String> cast,
                 Cinema.CinemaType movietype, MovieType.Genre moviecat,
                 MovieType.Dimension dimension, MovieType.Class movieClass) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.director = director;
        this.synopsis = synopsis;
        this.cast = cast;
        this.typeOfCinema = movietype;
        this.MovieGenre = moviecat;
        this.movie3D = dimension;
        this.movieClass = movieClass;
    }

    public Movie(String movieTitle, MovieStatus showingStatus, String director,
                 String synopsis, ArrayList<String> cast,
                 Cinema.CinemaType movietype, MovieType.Genre moviecat,
                 MovieType.Dimension dimension, MovieType.Class movieClass,int movieTotalSales) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.director = director;
        this.synopsis = synopsis;
        this.cast = cast;
        this.typeOfCinema = movietype;
        this.MovieGenre = moviecat;
        this.movie3D = dimension;
        this.movieClass = movieClass;
        this.movieTotalSales = movieTotalSales;
    }


    public String getMovieTitle() {
        return this.movieTitle;
    }
    public MovieStatus getShowingStatus() {
        return this.showingStatus;
    }

    public String getDirector() {
        return director;
    }

    public String getSynopsis() {
        return this.synopsis;
    }
    public ArrayList<String> getCast() {
        return this.cast;
    }
    public Cinema.CinemaType getTypeOfCinema() { return this.typeOfCinema; }
    public MovieType.Genre getMovieGenre() { return this.MovieGenre; }
    public MovieType.Dimension getMovie3D() { return this.movie3D; }
    public MovieType.Class getMovieClass() {
        return this.movieClass;
    }
    public int getMovieTotalSales() { return this.movieTotalSales;}

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public void setShowingStatus(MovieStatus showingStatus) {
        this.showingStatus = showingStatus;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }
    public void setTypeOfCinema(Cinema.CinemaType typeOfCinema) {
        this.typeOfCinema = typeOfCinema;
    }
    public void setMovieGenre(MovieType.Genre movieGenre) {
        MovieGenre = movieGenre;
    }
    public void setMovie3D(MovieType.Dimension movie3D) {
        this.movie3D = movie3D;
    }
    public void setMovieClass(MovieType.Class movieClass) {
        this.movieClass = movieClass;
    }
    public void setMovieTotalSales(int movieTotalSales) {
        this.movieTotalSales = movieTotalSales;
    }

    public void updateShowingStatus(MovieStatus showingStatus) {
        this.showingStatus = showingStatus;
    }

    public void increaseMovieTotalSale()
    {
        this.movieTotalSales++;
    }

    public void printMovieDetails() {
        System.out.printf("\t\tMovie Title: %s\n", this.movieTitle);
        System.out.printf("\t\tMovie Status: %s\n", this.showingStatus);
        System.out.printf("\t\tMovie Director: %s\n", this.director);
        System.out.println("\t\tMovie Synopsis:");
        printSynopsis();
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

    public void printSynopsis() {
        String[] words = this.synopsis.split(" ");
        int count = 0;
        System.out.print("\t\t\t");
        for (String word : words) {
            if (count == 20) {
                System.out.print("\n\t\t\t");
                count = 0;
            }
            System.out.printf("%s ", word);
            count++;
        }
        System.out.println("\n");
    }
}