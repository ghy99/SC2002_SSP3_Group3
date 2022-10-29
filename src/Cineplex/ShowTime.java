package Cineplex;

import Movie.MovieSeats;

import java.util.Date;

public class ShowTime extends MovieSeats{
    public Date time;
    private MovieSeats moveSeats;


    public ShowTime(int row, int col, int aisleOne, int aisleTwo , Date time) {
        super(row, col, aisleOne, aisleTwo);
        this.time = time;
    }

    public ShowTime(Date time) {
        super();
        this.time = time;
    }

    public Long getTimeHour() {
        return time.getTime();
    }
}
