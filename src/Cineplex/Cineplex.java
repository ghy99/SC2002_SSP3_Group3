package Cineplex;

import Movie.Movie;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Gan Hao Yi, Chew Zhi Qi, Eddy
 * This is the Cineplex class.
 * It stores each cineplex with a list of cinemas it contains, and a list of movies it is displaying.
 */
public class Cineplex {
    /** Current absolute path for this cinepelex*/
    private final File cineplexDir;
    /** Current directory path for this cinepelex*/
    private final String dirName;

    /** Cinepelex name*/
    private String cineplexName;

    /** List of cinema for this cineplex*/
    private ArrayList<Cinema> listOfCinemas = new ArrayList<Cinema>();

    /**
     * Create a new cineplex also, set cinplexDir and dirName for textDB
     * @param name
     */
    public Cineplex(String name) {
        this.cineplexName = name;
        cineplexDir = new File(TextDB.getCurrentDirectory() + File.separator + this.getCineplexName().replace(' ', '_'));
        dirName = File.separator + this.getCineplexName().replace(' ', '_');
    }

    /**
     * Get cineplex name
     * @return
     */
    public String getCineplexName() {
        return this.cineplexName;
    }

    /**
     * @return - Return ArrayList of cinema
     */
    public ArrayList<Cinema> getListOfCinemas() {
        return this.listOfCinemas;
    }

    /**
     * @return Return number of cinema in current cineplex
     */
    public int getNoOfCinemas() {
        return this.listOfCinemas.size();
    }

    /**
     * Add new cinema
     * @param cinema - New Cienma
     */
    public void addCinema(Cinema cinema) {
        this.listOfCinemas.add(cinema);
    }

    /**
     * Create dir and movie.txt if entered cineplex name doesn't exist in DataStorage dir
     * @param listOfMovies List of movie for showtime to reference
     */
    public void InitializeMovies(ArrayList<Movie> listOfMovies) throws IOException {
        System.out.println("Initializing list of movies in cinemas\n...\n...");

        if (!cineplexDir.exists()) {
            cineplexDir.mkdirs();

            System.out.println("Created directory. Path :" + cineplexDir + File.separator);
        }

        try {
            for (Cinema c : listOfCinemas) {
                File cinema = new File(cineplexDir + File.separator + c.getCinemaName() + ".txt");
                if (!cinema.exists()) {
                    cinema.createNewFile();
                    c.setCinemaDir(dirName + File.separator + c.getCinemaName() + ".txt");
                }
                else {
                    c.setCinemaDir(dirName+ File.separator + c.getCinemaName() + ".txt");
                    c.setShowTime(TextDB.readFromFile(listOfMovies, c.getCinemaDir()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Movies are initialized.\n");
    }

    /**
     * Create a new cinema in specific cineplex
     * @param cinemaName Cinema Name
     * @param cinemaType Cinema Type
     */
    public void CreateNewCinema( String cinemaName , Cinema.CinemaType cinemaType) throws IOException {
        int cinemaAlpha = 'A';

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for(Cinema c : this.getListOfCinemas())
        {
            temp = String.valueOf(c.getCinemaCode().charAt(0));
            cinemaAlpha++;
        }

        sb.append(temp) ;
        sb.append(cinemaType.ToString().charAt(0));
        sb.append((char)++cinemaAlpha);

        Cinema newCinema = new Cinema(sb.toString()  , cinemaName , cinemaType);
        this.addCinema(newCinema);

        //Create txt
        File cinema = new File(cineplexDir + File.separator + newCinema.getCinemaName() + ".txt");
        if (!cinema.exists()) cinema.createNewFile();
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
