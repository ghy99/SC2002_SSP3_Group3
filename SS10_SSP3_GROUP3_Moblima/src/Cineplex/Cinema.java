package Cineplex;

import Movie.Movie;
import Movie.MovieType;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * @authors CHEW ZHI QI, GAN HAO YI
 * This is the Cinema class.
 * It stores the type of the Cinema, and a list of ShowTime and
 * The movie it is showing according to the ShowTime.
 */
public class Cinema {
    /**
     * This Enum stores the type of the Cinema. This affects the ticket price.
     */
    public enum CinemaType {
        Regular("Regular"),
        Premium("Premium");
        public final String CinemaType;

        CinemaType(String cinemaType) {
            this.CinemaType = cinemaType;
        }

        public String ToString() {
            return CinemaType;
        }
    }

    /**
     * For adding two hours to the current time to make sure time is blocked out so Movie Timing don't clash
     */
    private static final long HOUR = 3600 * 1000; // in milli-seconds.

    /**
     * These variables will be used for Cinema, to be declared in a list in cineplex.
     * It stores the Cinema Code, name of the Cinema, the type of the Cinema and the name of the Directory for Cinema.
     * It will also store an ArrayList of ShowTime and Movies to be displayed in that Cinema for that day.
     */
    private String cinemaCode;
    private String cinemaName;
    private CinemaType cinemaType;
    private String cinemaDir;
    private ArrayList<ShowTime> showTime = new ArrayList<>();

    /**
     * Constructor for Cinema
     *
     * @param cinemaCode - Cinema Code
     * @param cinemaName - Cinema Name
     * @param cinemaType - Type of Cinema (Regular / Premium)
     */
    public Cinema(String cinemaCode, String cinemaName, CinemaType cinemaType) {
        this.cinemaCode = cinemaCode;
        this.cinemaName = cinemaName;
        this.cinemaType = cinemaType;
    }

    /**
     * Get Method
     *
     * @return Cinema Name
     */
    public String getCinemaName() {
        return this.cinemaName;
    }

    /**
     * Get Method
     *
     * @return Type of Cinema (Regular / Premium)
     */
    public CinemaType getCinemaType() {
        return cinemaType;
    }

    /**
     * Get Method
     *
     * @return ArrayList of ShowTime
     */
    public ArrayList<ShowTime> getShowTime() {
        return showTime;
    }

    /**
     * Get Method
     *
     * @return Cinema Code
     */
    public String getCinemaCode() {
        return cinemaCode;
    }

    /**
     * Get Method
     *
     * @return Cinema Directory for Database
     */
    public String getCinemaDir() {
        return cinemaDir;
    }

    /**
     * Set Method
     *
     * @param dir - Set the Directory path in this Variable
     */
    public void setCinemaDir(String dir) {
        this.cinemaDir = dir;
    }

    /**
     * Set Method
     *
     * @param showTime Stores the ArrayList of ShowTime
     */
    public void setShowTime(ArrayList<ShowTime> showTime) {
        this.showTime = showTime;
    }

    /**
     * Create the ShowTime of the Movie it is showing, initializing the 2D Array for the Cinema.
     *
     * @param time  - The ShowTime of the movie slot.
     * @param movie - The Movie that the Cinema is showing
     * @param dim   - Is the movie a 2D or 3D movie.
     */
    private void createSeats(Date time, Movie movie, MovieType.Dimension dim) {
        if (cinemaType == CinemaType.Premium) {
            showTime.add(new ShowTime(5, 8, 3, 2, 5, time, movie, dim));
        } else {
            showTime.add(new ShowTime(13, 16, 10, 2, 13, time, movie, dim));
        }
        showTime.sort(Comparator.comparing(ShowTime::getTimeHour));
    }

    /**
     * This method removes the selected ShowTime of selected Movie and add in a new ShowTime
     *
     * @param index Selected index of the ShowTime to be removed
     * @param date  New Selected Date Time of Movie to be updated
     * @param movie Selected Movie to be updated
     * @param dim   Selected Dimension to be updated
     * @throws IOException To check that ShowTime exists in Database
     */
    public void updateCinemaTime(int index, Date date, Movie movie, MovieType.Dimension dim) throws IOException {
        this.showTime.remove(index);
        createShowTime(date, movie, dim);
    }

    /**
     * This method deletes a selected ShowTime of Selected Movie
     *
     * @param index Index of ShowTime to delete
     * @throws IOException To check if ShowTime exists in Database
     */
    public void deleteCinemaTime(int index) throws IOException {
        this.showTime.remove(index);
        TextDB.UpdateToTextDB(this.getCinemaDir(), this.showTime, MovieType.Dimension.THREE_D);
    }

    /**
     * Create a new showtime by getting the cinema, date, movie, 2D/3D and write to DB
     *
     * @param date  Selected Date
     * @param movie Selected Movie
     * @param dim   Selected Dimension
     */
    public void createShowTime(Date date, Movie movie, MovieType.Dimension dim) throws IOException {
        var temp = this.getShowTime();
        int i = 0;
        ShowTime currentSTDate = null;
        //Loop through all showtime
        while (i < temp.size()) {
            currentSTDate = temp.get(i);
            if (currentSTDate.getTime().getTime() > date.getTime()) {
                break;
            }
            i++;
        }
        if (i - 1 > 0) {
            currentSTDate = temp.get(i - 1);
            if (currentSTDate.getTime().getTime() + 2 * HOUR < date.getTime()) {
                this.createSeats(date, movie, dim);
                System.out.println("Showtime created!");
                TextDB.UpdateToTextDB(this.getCinemaDir(), this.showTime, dim);
            } else {
                System.out.println("New showtime clashes with previous showtime!");
            }
        } else {
            System.out.println("Showtime created!");
            this.createSeats(date, movie, dim);
            TextDB.UpdateToTextDB(this.getCinemaDir(), this.showTime, dim);
        }
    }
}