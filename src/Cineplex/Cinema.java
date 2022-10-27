package Cineplex;
import Movie.Movie;
import Service.TextDB;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Cinema {
    public enum CinemaType{
        Premium("Premium"),
        Regular("Regular")
                ;

        public final String CinemaType;

        CinemaType(String cinemaType) {
            this.CinemaType = cinemaType;
        }

        public String ToString(){
            return CinemaType;
        }
    }

    private static final long HOUR = 3600*1000; // in milli-seconds.

    // This will be for Cinema, to be declared in a list in cineplex
    private String cinemaName;
    private CinemaType cinemaType;

    private ArrayList<ShowTime> showTime = new ArrayList<>();
    private ArrayList<Movie> listOfMovies= new ArrayList<>();

    public Cinema(String cinemaName,  CinemaType cinemaType) {

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

    public void addShowTime(Date time) {

        if(cinemaType == CinemaType.Premium)
        {
            showTime.add(new ShowTime(0,0,0,0,time));
        }
        else
        {
            showTime.add(new ShowTime(0,0,0,0,time));
        }

        showTime.sort(Comparator.comparing(ShowTime::getTimeHour));
    }

    public void updateCinemaTime(int index , Date showTime) {
        deleteCinemaTime(index);
        addShowTime(showTime);
    }

    public void deleteCinemaTime(int index)
    {
        listOfMovies.remove(index);
    }

    public void updateShowTime(Date date ) {
        var temp = this.getShowTime();

        //if no showtime just add
        if (temp.size() != 0) {
            //Loop through all showtime
            for (int i = 0; i < temp.size(); i++) {
                //if show time + 2 hours still smaller than time to be added
                if (temp.get(i).getTimeHour() + 2 * HOUR < date.getTime()) {
                    //if time still in range
                    if (i + 1 < temp.size()) {
                        long remainTime = temp.get(i + 1).getTimeHour() - temp.get(i).getTimeHour();
                        if (date.getTime() + 2 * HOUR < remainTime) {
                            this.addShowTime(date);
                            return;
                        }
                    }
                }

            }
        }
        else
        {
            this.addShowTime(date);
        }
    }
}
