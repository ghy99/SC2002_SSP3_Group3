package Cineplex;

import Movie.Movie;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Chew Zhi Qi, Gan Hao Yi
 * This is the Cinema class.
 * It stores the type of the Cinema, and a list of ShowTime and the movie it is showing according to the ShowTime.
 */
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
    private String cinemaCode;
    private String cinemaName;
    private CinemaType cinemaType;

    private ArrayList<ShowTime> showTime = new ArrayList<>();
    private ArrayList<Movie> listOfMovies = new ArrayList<>();

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

    private void createSeats(Date time, Movie movie) {

        if (cinemaType == CinemaType.Premium) {
            showTime.add(new ShowTime(6, 6, 2, 4, time, movie));
        } else {
            showTime.add(new ShowTime(3, 3, 1, 1, time, movie));
        }

        showTime.sort(Comparator.comparing(ShowTime::getTimeHour));
    }

    public void updateCinemaTime(int index,Cineplex cineplex, Date date, Movie movie) throws IOException {
        deleteCinemaTime(index);
        createShowTime(cineplex,date, movie);
    }

    public void deleteCinemaTime(int index) {
        this.showTime.remove(index);
    }

    public boolean addShowTime(Date date, Movie movie) {
        var temp = this.getShowTime();
        int i = 0;
        ShowTime currentSTDate = null;
        if (temp.size() != 0) {
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
                    this.createSeats(date, movie);
                    System.out.println("Showtime create!");
                    return true;
                }
                else
                {
                    System.out.println("New showtime clash with previous showtime!");
                    return false;
                }
            } else {
                System.out.println("Showtime create!");
                this.createSeats(date, movie);
                return true;
            }
        }
        this.createSeats(date, movie);



        return true;
    }

    public boolean createShowTime(Cineplex cineplex, Date date, Movie movie ) throws IOException {
        var temp = this.getShowTime();
        int i = 0;
        ShowTime currentSTDate = null;
        if (temp.size() != 0) {
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
                    this.createSeats(date, movie);
                    System.out.println("Showtime create!");
                    return true;
                }
                else
                {
                    System.out.println("New showtime clash with previous showtime!");
                    return false;
                }
            } else {
                System.out.println("Showtime create!");
                this.createSeats(date, movie);
                return true;
            }
        }
        this.createSeats(date, movie);

        TextDB.UpdateToTextDB( File.separator + cineplex.getCineplexName().replace(' ','_')+ File.separator+this.getCinemaName()+".txt" , this.showTime, null);


        return true;
    }
    public static void CreateNewCinema(Cineplex cineplex , String cinemaName , CinemaType cinemaType)
    {
        int cinemaAlpha = 'A';

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for(Cinema c : cineplex.getListOfCinemas())
        {
            temp = String.valueOf(c.cinemaCode.charAt(0));
            cinemaAlpha++;
        }

        sb.append(temp) ;
        sb.append(cinemaType.ToString().charAt(0));
        sb.append((char)++cinemaAlpha);

        cineplex.addCinema(new Cinema(sb.toString()  , cinemaName , cinemaType));
    }

}
