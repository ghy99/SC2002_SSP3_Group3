package Cineplex;

import Movie.Movie;
import Movie.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ShowTime extends MovieSeats{
    private Date time;
    private Movie movie;
    private MovieType.Dimension dimension;

    public ShowTime(int row, int col, int aisleOne, int aisleTwo , Date time , Movie movie, MovieType.Dimension dim) {
        super(row, col, aisleOne, aisleTwo);
        this.time = time;
        this.movie = movie;
        this.dimension = dim;
        seatsCreation();
    }

    public ShowTime(Date time , Movie movie , ArrayList<ArrayList<String>> seats , int[] aisle, MovieType.Dimension dim) {
        super(seats.size() , seats.get(0).size() ,aisle );

        String[][] seat = new String[seats.size()][seats.get(0).size()];

        this.time = time;
        this.movie = movie;
        this.dimension = dim;

        for (int i =0;i<seats.size();i++)
        {
            for(int j =0;j<seats.get(i).size();j++)
            {
                seat[i][j] = seats.get(i).get(j);
            }
        }

        super.setSeats(seat);
    }

    public MovieType.Dimension getDimension() {
        return dimension;
    }
    public Movie getMovie() {
        return movie;
    }

    public Date getTime() {
        return time;
    }

    public Long getTimeHour() {
        return time.getTime();
    }

}
