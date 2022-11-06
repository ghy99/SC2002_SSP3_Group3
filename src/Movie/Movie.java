package Movie;

import Review.*;
import Service.TextDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *  * @author CHEW ZHI QI, GAN HAO YI
 *  * Movie class extends from AllReview to link up each movie will have a list of reviews
 */
public class Movie extends AllReviews {
    /**
     * enum movie status
     */
    public enum MovieStatus {
        ComingSoon,
        Preview,
        NowShowing,
        EndOfShowing
    }

    /**
     * Movies attributes: Movie title, Movie showing status, Movie director, Movie synopsis,
     * Movie casts, Movie genre, blockbuster, Movie genre (Class), Movie's total sale
     */
    private String movieTitle;
    private MovieStatus showingStatus;
    private String director;
    private String synopsis;
    private ArrayList<String> cast;
    private MovieType.Genre MovieGenre;
    private MovieType.Blockbuster BlockBuster;
    private MovieType.Class movieClass;
    private int movieTotalSales = 0;
    /**
     * Constructor for new movies
     * @param movieTitle Movie Title
     * @param showingStatus Movie's Showing Status
     * @param director Movie Director
     * @param synopsis Movie Synopsis
     * @param cast List of Movie Casts
     * @param moviecat Movie
     * @param blockbuster Is Blockbuster
     * @param movieClass Movie Genre
     */
    public Movie(String movieTitle, MovieStatus showingStatus, String director,
                 String synopsis, ArrayList<String> cast,
                 MovieType.Genre moviecat,
                 MovieType.Blockbuster blockbuster, MovieType.Class movieClass) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.director = director;
        this.synopsis = synopsis;
        this.cast = cast;
        this.MovieGenre = moviecat;
        this.BlockBuster = blockbuster;
        this.movieClass = movieClass;
    }

    /**
     * Constructor for reading from DB
     * @param movieTitle Movie Title
     * @param showingStatus Movie's Showing Status
     * @param director Movie Director
     * @param synopsis Movie Synopsis
     * @param cast List of Movie Casts
     * @param moviecat Movie
     * @param blockbuster Is Blockbuster
     * @param movieClass Movie Genre
     * @param movieTotalSales Movie sales
     */
    public Movie(
            String movieTitle, MovieStatus showingStatus, String director,
            String synopsis, ArrayList<String> cast,
            MovieType.Genre moviecat,
            MovieType.Blockbuster blockbuster, MovieType.Class movieClass,int movieTotalSales
    ) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.director = director;
        this.synopsis = synopsis;
        this.cast = cast;
        this.MovieGenre = moviecat;
        this.movieClass = movieClass;
        this.BlockBuster = blockbuster;
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
    public MovieType.Genre getMovieGenre() { return this.MovieGenre; }
    public MovieType.Blockbuster getBlockBuster() {
        return BlockBuster;
    }
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
    public void setMovieGenre(MovieType.Genre movieGenre) {
        MovieGenre = movieGenre;
    }
    public void setBlockBuster(MovieType.Blockbuster blockBuster) {
        BlockBuster = blockBuster;
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

    /**
     * Increase movie sale and update DB
     * @param movies all list for movie to update db
     */
    public void increaseMovieTotalSale(ArrayList<Movie> movies) throws IOException {
        this.movieTotalSales++;
        TextDB.UpdateToTextDB(TextDB.Files.Movies.toString(),movies , new Review("" ,"",0));
    }

    /**
     * Print movie details
     */
    public void printMovieDetails() {
        System.out.printf("\t\tMovie Title: %s\n", this.movieTitle);
        System.out.printf("\t\tMovie Status: %s\n", this.showingStatus);
        System.out.printf("\t\tMovie Director: %s\n", this.director);
        System.out.println("\t\tMovie Synopsis:");
        printSynopsis();
        System.out.printf("\t\tMovie Casts: %s\n", this.cast);
        System.out.printf("\t\tMovie Category: %s\n", this.MovieGenre.toString());
        System.out.printf("\t\tMinimum Age for Watching: %s\n", this.movieClass.toString());
        if (Objects.equals(getBlockBuster(), MovieType.Blockbuster.BLOCKBUSTER)) {
            System.out.printf("\t\t%s\n", this.getBlockBuster());
        }
    }

    /**
     * Print synopsis
     */
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