package Movie;

import Review.*;
import Service.DateTime;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * @author CHEW ZHI QI, GAN HAO YI
 * Movie class extends from AllReview to link up each movie will have a list of reviews
 */
public class Movie extends AllReviews {
    /**
     * enum Movie Status
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
    private Date startDate;
    private ArrayList<String> cast;
    private MovieType.Genre MovieGenre;
    private MovieType.Blockbuster BlockBuster;
    private MovieType.Class movieClass;
    private int movieTotalSales = 0;

    /**
     * Constructor for new movies
     *
     * @param movieTitle    Movie Title
     * @param showingStatus Movie's Showing Status
     * @param director      Movie Director
     * @param synopsis      Movie Synopsis
     * @param cast          List of Movie Casts
     * @param moviecat      Movie Genre
     * @param blockbuster   Is Blockbuster
     * @param movieClass    Movie Ratings
     */
    public Movie(String movieTitle, MovieStatus showingStatus, String director,
                 String synopsis, ArrayList<String> cast,
                 MovieType.Genre moviecat,
                 MovieType.Blockbuster blockbuster, MovieType.Class movieClass, Date startDate) {
        this.movieTitle = movieTitle;
        this.showingStatus = showingStatus;
        this.director = director;
        this.synopsis = synopsis;
        this.cast = cast;
        this.MovieGenre = moviecat;
        this.BlockBuster = blockbuster;
        this.movieClass = movieClass;
        this.startDate = startDate;
    }

    /**
     * Constructor for reading from DB
     *
     * @param movieTitle      Movie Title
     * @param showingStatus   Movie's Showing Status
     * @param director        Movie Director
     * @param synopsis        Movie Synopsis
     * @param cast            List of Movie Casts
     * @param moviecat        Movie Genre
     * @param blockbuster     Is Blockbuster
     * @param movieClass      Movie Rating
     * @param movieTotalSales Movie Total Sales
     */
    public Movie(
            String movieTitle, MovieStatus showingStatus, String director,
            String synopsis, ArrayList<String> cast,
            MovieType.Genre moviecat,
            MovieType.Blockbuster blockbuster, MovieType.Class movieClass, int movieTotalSales, Date startDate
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
        this.startDate = startDate;
    }

    /**
     * Get Method
     *
     * @return Movie Title
     */
    public String getMovieTitle() {
        return this.movieTitle;
    }

    /**
     * Get Method
     *
     * @return Movie's Showing Status
     */
    public MovieStatus getShowingStatus() {
        return this.showingStatus;
    }

    /**
     * Get Method
     *
     * @return Director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Get Method
     *
     * @return Synopsis
     */
    public String getSynopsis() {
        return this.synopsis;
    }

    /**
     * Get Method
     *
     * @return casts
     */
    public ArrayList<String> getCast() {
        return this.cast;
    }

    /**
     * Get Method
     *
     * @return Movie's Genre
     */
    public MovieType.Genre getMovieGenre() {
        return this.MovieGenre;
    }

    /**
     * Get Method
     *
     * @return Movie's Blockbuster status
     */
    public MovieType.Blockbuster getBlockBuster() {
        return BlockBuster;
    }

    /**
     * Get Method
     *
     * @return Movie's Ratings
     */
    public MovieType.Class getMovieClass() {
        return this.movieClass;
    }

    /**
     * Get Method
     *
     * @return Date when Movie starts showing.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Get Method
     *
     * @return Movie total sale
     */
    public int getMovieTotalSales() {
        return this.movieTotalSales;
    }

    /**
     * Set Method
     *
     * @param movieTitle - Set Movie Title
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Set Method
     *
     * @param showingStatus - Set Showing Status
     */
    public void setShowingStatus(MovieStatus showingStatus) {
        this.showingStatus = showingStatus;
    }

    /**
     * Set Method
     *
     * @param director - Set Director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Set Method
     *
     * @param synopsis - Set Synopsis
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Set Method
     *
     * @param cast - Set list of casts
     */
    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    /**
     * Set Method
     *
     * @param movieGenre - Set Movie Genre
     */
    public void setMovieGenre(MovieType.Genre movieGenre) {
        MovieGenre = movieGenre;
    }

    /**
     * Set Method
     *
     * @param blockBuster - Set Blockbuster
     */
    public void setBlockBuster(MovieType.Blockbuster blockBuster) {
        BlockBuster = blockBuster;
    }

    /**
     * Set Method
     *
     * @param movieClass - Set Movie Ratings
     */
    public void setMovieClass(MovieType.Class movieClass) {
        this.movieClass = movieClass;
    }

    /**
     * Update Method
     *
     * @param showingStatus - Update Movie Showing Status
     */
    public void updateShowingStatus(MovieStatus showingStatus) {
        this.showingStatus = showingStatus;
    }

    /**
     * Increase movie sale and update DB
     *
     * @param movies all list for movie to update db
     */
    public void increaseMovieTotalSale(ArrayList<Movie> movies, int ticketnum) throws IOException {
        this.movieTotalSales += ticketnum;
        TextDB.UpdateToTextDB(movies,TextDB.Files.Movies.toString() );
    }

    /**
     * Print movie details of this Movie.
     */
    public void printMovieDetails() {
        System.out.printf("\t\tMovie Title: %s\n", this.movieTitle);
        System.out.printf("\t\tMovie Status: %s\tStart Date: %s\n", this.showingStatus,
                DateTime.convertDate(this.startDate.getTime()));
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
     * Print synopsis of this Movie.
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