package Cineplex;

import Movie.Movie;
import Movie.MovieSeatsNew;
import Movie.MovieType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author  Chew Zhi Qi , Gan Hao Yi
 * ShowTime class inheriting from MovieSeats.
 * For each showtime consist of time, movie instance, dimension, list of seasts
 */
public class ShowTime extends MovieSeatsNew {
    /**
     * Date
     */
    private Date time;

    /**
     * Movie instance
     */
    private Movie movie;
    /**
     * 2D/3D
     */
    private MovieType.Dimension dimension;

    /**
     * ShowTime Constructor
     * @param row Amount of role
     * @param col Amount of column
     * @param aisleOne Index of first aisle should be at
     * @param aisleTwo Index of Second aisle should be at
     * @param time Time of the new showtime
     * @param movie Movie of the new showtime
     * @param dim Dimension of the new showtime
     */
    public ShowTime(int row, int col, int rowDoubleOne, int aisleOne, int aisleTwo , Date time , Movie movie, MovieType.Dimension dim) {
        super(row, col, rowDoubleOne, aisleOne,aisleTwo);
        this.time = time;
        this.movie = movie;
        this.dimension = dim;
        SeatsCreation();
    }

    /**
     * Construct Seats when reading from Database
     * @param time Time of the new showtime
     * @param movie Movie of the new showtime
     * @param seats Current seats allocation
     * @param aisle Aisle 1 and 2
     * @param dim Dimension of the new showtime
     */
    public ShowTime(Date time , Movie movie , ArrayList<String> seats, int row , int column , int sitDouble , int[] aisle, MovieType.Dimension dim) throws IOException {
        super(row, column, sitDouble ,aisle[0], aisle[1] );
        this.time = time;
        this.movie = movie;
        this.dimension = dim;
        SeatsCreation();
        super.BookSeats(seats, false, null);
    }

    /**
     * @return Current showtime dimension
     */
    public MovieType.Dimension getDimension() {
        return dimension;
    }

    /**
     * @return Movie Instance
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @return Date instance
     */
    public Date getTime() {
        return time;
    }

    /**
     * @return Datetime in millisecond
     */
    public Long getTimeHour() {
        return time.getTime();
    }
}