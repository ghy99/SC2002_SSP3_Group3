package Cineplex;

import Movie.Movie;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Gan Hao Yi, Chew Zhi Qi, Eddy
 * This is the Cineplex class.
 * It stores each cineplex with a list of cinemas it contains, and a list of movies it is displaying.
 */
public class Cineplex {
    private static final TextDB db = new TextDB();
    //Create dir and movie.txt if entered cineplex name doesn't exist in DataStorage dir
    private final File cineplexDir;

    // This will be for Cineplexes
    private String cineplexName;
    private ArrayList<Cinema> listOfCinemas = new ArrayList<Cinema>();


    public Cineplex(String name) {
        this.cineplexName = name;
        cineplexDir = new File(TextDB.getCurrentDirectory() + File.separator + this.getCineplexName().replace(' ', '_'));
    }

    public String getCineplexName() {
        return this.cineplexName;
    }

    public int getNoOfCinemas() {
        return this.listOfCinemas.size();
    }

    public int getNoOfRegularCinemas() {
        int count = 0;
        for (int i = 0; i < listOfCinemas.size(); i++) {
            if (listOfCinemas.get(i).getCinemaType() == Cinema.CinemaType.Regular) {
                count++;
            }
        }
        return count;
    }

    public int getNoOfPremiumCinemas() {
        int count = 0;
        for (int i = 0; i < listOfCinemas.size(); i++) {
            if (listOfCinemas.get(i).getCinemaType() == Cinema.CinemaType.Premium) {
                count++;
            }
        }
        return count;
    }

    public void addCinema(Cinema cinema) {
        this.listOfCinemas.add(cinema);

        //write to file
    }

    public ArrayList<Cinema> getListOfCinemas() {
        return this.listOfCinemas;
    }

    public void updateListOfCinema(int index) {
        //write to file
    }


    public void InitializeMovies(ArrayList<Movie> listOfMovies) throws IOException {
        System.out.println("Initializing list of movies in cinemas\n...\n...");

        if (!cineplexDir.exists()) {
            cineplexDir.mkdirs();

            System.out.println("Created directory. Path :" + cineplexDir + File.separator);
        }

        try {
            for (Cinema c : listOfCinemas) {
                File cinema = new File(cineplexDir + File.separator + c.getCinemaName() + ".txt");
                if (!cinema.exists()) cinema.createNewFile();
                c.setShowTime(db.readFromFile(listOfMovies, File.separator + this.getCineplexName().replace(' ', '_') + File.separator + c.getCinemaName() + ".txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Movies are initialized.\n");
    }


    /**
     * This Method displays the movie date & time for each movie.
     */
    public void displayMovieTimings(ArrayList<Movie> listOfMovies) {
        System.out.printf("%s", this.cineplexName);
        ArrayList<Movie> movielist = listOfMovies;
        ArrayList<Cinema> listOfCinemas = this.listOfCinemas;
        for (int j = 0; j < listOfCinemas.size(); j++) {
            System.out.printf("\n\t%s\n", movielist.get(j).getMovieTitle()); // movie
            ArrayList<ShowTime> allST = new ArrayList<>();
            for (Cinema c : listOfCinemas) {
                ArrayList<ShowTime> listOfShowtime = c.getShowTime();
                if (listOfShowtime.size() > 0) {
                    for (ShowTime st : listOfShowtime) {
                        if (Objects.equals(st.getMovie().getMovieTitle(), movielist.get(j).getMovieTitle())) {
                            allST.add(st);
                        }
                    }
                }
            }

            if (allST.size() > 0) {
                allST.sort(Comparator.comparing(ShowTime::getTimeHour));

                // need a sort function
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd-MM-YYYY");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String previousString = dateFormat.format(allST.get(0).getTime());
                System.out.printf("\t\t %s ", previousString);
                //previousString = allST.get(0).getTime();
                for (int k = 0; k < allST.size(); k++) {
                    if (Objects.equals(previousString, dateFormat.format(allST.get(k).getTime()))) {
                        previousString = "";
                        System.out.printf("%s\t%s", previousString, timeFormat.format(allST.get(k).getTime()));
                        previousString = dateFormat.format(allST.get(k).getTime());
                    } else {
                        previousString = dateFormat.format(allST.get(k).getTime());
                        System.out.printf("\n\t\t %s \t%s", previousString, timeFormat.format(allST.get(k).getTime()));
                    }
                }
            }
        }
    }
}
