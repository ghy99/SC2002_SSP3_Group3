package Cineplex;

import Movie.*;
import Service.TextDB;

import java.io.IOException;
import java.util.*;

/**
 * @author Chew Zhi Qi, Gan Hao Yi
 * This is the Cinema class.
 * It stores the type of the Cinema, and a list of ShowTime and the movie it is showing according to the ShowTime.
 */
public class Cinema {
    /**
     * This Enum stores the type of the Cinema. This affects the ticket price.
     */
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

    /**
     * For adding two hours to the current time to make sure time is blocked out so movie don't clashed
     */
    private static final long HOUR = 3600 * 1000; // in milli-seconds.

    /**
     * These variables will be used for Cinema, to be declared in a list in cineplex.
     * It stores the Cinema Code, name of the Cinema, and the type of the Cinema.
     * It will also store an ArrayList of ShowTime and Movies to be displayed in that Cinema for that day.
     */
    private String cinemaCode;
    private String cinemaName;
    private CinemaType cinemaType;

    private String cinemaDir;

    private ArrayList<ShowTime> showTime = new ArrayList<>();

    public Cinema(String cinemaCode, String cinemaName, CinemaType cinemaType) {
        this.cinemaCode = cinemaCode;
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

    public String getCinemaCode() {
        return cinemaCode;
    }

    public void setCinemaDir(String dir){
        this.cinemaDir = dir;
    }

    public String getCinemaDir() {
        return cinemaDir;
    }

    /**
     *
     * @param time: The ShowTime of the movie slot.
     * @param movie: The Movie that the Cinema is showing
     * @param dim: Is the movie a 2D or 3D movie.
     */
    private void createSeats(Date time, Movie movie, MovieType.Dimension dim) {

        if (cinemaType == CinemaType.Premium) {
            showTime.add(new ShowTime(5, 8, 3,2, 5, time, movie, dim));
        } else {
            showTime.add(new ShowTime(13,16,10,2,13, time, movie, dim));
        }

        showTime.sort(Comparator.comparing(ShowTime::getTimeHour));
    }

    /**
     *
     * @param index Selected index Show time to remove
     * @param cineplex Selected Cinema
     * @param date Selected Date
     * @param movie Selected Movie
     * @param dim Selected Dimension
     * @throws IOException
     */
    public void updateCinemaTime(int index,Cineplex cineplex, Date date, Movie movie, MovieType.Dimension dim) throws IOException {
        this.showTime.remove(index);
        createShowTime(date,movie,dim);
    }

    /**
     * Reserve function if need to delete away showtime
     * @param index Index to delete
     * @throws IOException
     */
    public void deleteCinemaTime(int index) throws IOException {
        this.showTime.remove(index);
//        TextDB.UpdateToTextDB(this.getCinemaDir() , this.showTime, null);
    }

    /**
     * Create a new showtime by getting the cinema, date, movie, 2D/3D and write to DB
     * @param cineplex Selected Cinema
     * @param date Selected Date
     * @param movie Selected Movie
     * @param dim Selected Dimension
     */
    public void createShowTime( Date date, Movie movie, MovieType.Dimension dim) throws IOException {
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
        //if show time + 2 hours still smaller than time to be added

        if (i - 1 > 0) {
            currentSTDate = temp.get(i - 1);
            if (currentSTDate.getTime().getTime() + 2 * HOUR < date.getTime()) {
                this.createSeats(date, movie, dim);
                System.out.println("Showtime created!");
//                TextDB.UpdateToTextDB( this.getCinemaDir() , this.showTime, dim);
            }
            else
            {
                System.out.println("New showtime clash with previous showtime!");
            }
        } else {
            System.out.println("Showtime created!");
            this.createSeats(date, movie, dim);
//            TextDB.UpdateToTextDB( this.getCinemaDir() , this.showTime, dim);
        }
    }


}
