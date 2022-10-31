package Cineplex;

import Movie.Movie;
import Service.DateTime;
import Service.TextDB;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Cinema {
    public enum CinemaType {
        Premium("Premium"),
        Regular("Regular");

        public final String CinemaType;

        CinemaType(String cinemaType) {
            this.CinemaType = cinemaType;
        }

        public String ToString() {
            return CinemaType;
        }
    }

    private static final long HOUR = 3600 * 1000; // in milli-seconds.

    // This will be for Cinema, to be declared in a list in cineplex
    private String cinemaName;
    private CinemaType cinemaType;

    private ArrayList<ShowTime> showTime = new ArrayList<>();
    private ArrayList<Movie> listOfMovies = new ArrayList<>();

    public Cinema(String cinemaName, CinemaType cinemaType) {

        this.cinemaName = cinemaName;
        this.cinemaType = cinemaType;
    }

    public void setShowTime(ArrayList<ShowTime> showTime) {
        this.showTime = showTime;
    }

    public String getCinemaName() {
        return this.cinemaName;
    }

    public CinemaType getCinemaType() {
        return cinemaType;
    }

    public ArrayList<ShowTime> getShowTime() {
        return showTime;
    }

    private void createShowTime(Date time, Movie movie) {

        if (cinemaType == CinemaType.Premium) {
            showTime.add(new ShowTime(6, 6, 2, 4, time, movie));
        } else {
            showTime.add(new ShowTime(3, 3, 1, 1, time, movie));
        }

        showTime.sort(Comparator.comparing(ShowTime::getTimeHour));
    }

    public void updateCinemaTime(int index, Date showTime, Movie movie) {
        deleteCinemaTime(index);
        addShowTime(showTime, movie);
    }

    public void deleteCinemaTime(int index) {
        listOfMovies.remove(index);
    }

    public boolean addShowTime(Date date, Movie movie) {
        var temp = this.getShowTime();
        int i = 0;
        ShowTime currentSTDate = null;
        if (temp.size() != 0) {
            //Loop through all showtime
            while (i < temp.size()) {
                currentSTDate = temp.get(i);
                if (currentSTDate.time.getTime() > date.getTime()) {
                    break;
                }
                i++;
            }
            //if show time + 2 hours still smaller than time to be added

            if (i - 1 > 0) {
                currentSTDate = temp.get(i - 1);
                if (currentSTDate.time.getTime() + 2 * HOUR < date.getTime()) {
                    this.createShowTime(date, movie);
                    return true;
                }
                else
                {
                    return false;
                }
            } else {
                this.createShowTime(date, movie);
                return true;
            }
        }
        this.createShowTime(date, movie);
        return true;
    }

}
