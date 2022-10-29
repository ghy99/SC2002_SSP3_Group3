package Cineplex;

import Movie.Movie;
import Movie.MovieSeats;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ShowTime extends MovieSeats{
    public Date time;
    private Movie movie;

    public ShowTime(int row, int col, int aisleOne, int aisleTwo , Date time , Movie movie) {
        super(row, col, aisleOne, aisleTwo);
        this.time = time;
        this.movie = movie;
    }

    public ShowTime(Date time , Movie movie , ArrayList<ArrayList<String>> seats , int[] aisle) {
        super(seats.size() , seats.get(0).size() ,aisle );

        String[][] seat = new String[seats.size()][seats.get(0).size()];

        this.time = time;
        this.movie = movie;

        for (int i =0;i<seats.size();i++)
        {
            for(int j =0;j<seats.get(i).size();j++)
            {
                seat[i][j] = seats.get(i).get(j);
            }
        }

        super.setSeats(seat);

    }

    public Long getTimeHour() {
        return time.getTime();
    }

    public static void main(String[] args) {
        ShowTime st = new ShowTime(5,5,3,3,new Date(),new Movie());

    }
}
