package Cineplex;

import Movie.Movie;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * @authors GAN HAO YI, CHEW ZHI QI, EDDY CHENG KUAN QUAN
 * This is the Cineplex class.
 * Each Cineplex stores a list of Cinemas that it contains, and a list of movies it is showing.
 */
public class Cineplex {
    /**
     * Current absolute path for this cineplex
     */
    private final File cineplexDir;
    /**
     * Current directory path for this cineplex
     */
    private final String dirName;
    /**
     * Name of Cineplex
     */
    private String cineplexName;
    /**
     * List of cinema for this cineplex
     */
    private ArrayList<Cinema> listOfCinemas = new ArrayList<Cinema>();

    /**
     * Constructor for Cineplex
     * Set cineplexDir and dirName for textDB
     *
     * @param name - Name of Cineplex
     */
    public Cineplex(String name) {
        this.cineplexName = name;
        cineplexDir = new File(TextDB.getCurrentDirectory() + File.separator + this.getCineplexName().replace(' ', '_'));
        dirName = File.separator + this.getCineplexName().replace(' ', '_');
    }

    /**
     * Get Method
     *
     * @return Name of Cineplex
     */
    public String getCineplexName() {
        return this.cineplexName;
    }

    /**
     * Get Method
     *
     * @return ArrayList of cinema
     */
    public ArrayList<Cinema> getListOfCinemas() {
        return this.listOfCinemas;
    }

    /**
     * Get Method
     *
     * @return number of Cinema in this Cineplex
     */
    public int getNoOfCinemas() {
        return this.listOfCinemas.size();
    }

    /**
     * This Method adds new Cinema to the list of cinema
     *
     * @param cinema - New Cienma
     */
    public void addCinema(Cinema cinema) {
        this.listOfCinemas.add(cinema);
    }

    /**
     * Create dir and movie.txt if entered cineplex name doesn't exist in DataStorage dir
     *
     * @param listOfMovies List of movie for showtime to reference
     */
    public void InitializeMovies(ArrayList<Movie> listOfMovies) {
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
                } else {
                    c.setCinemaDir(dirName + File.separator + c.getCinemaName() + ".txt");
                    c.setShowTime(TextDB.readFromFile(listOfMovies, c.getCinemaDir()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Movies are initialized.\n");
    }

    /**
     * Create a new Cinema in Specified Cineplex.
     *
     * @param cinemaName Cinema Name
     * @param cinemaType Cinema Type
     * @param cineplex   Specified Cineplex
     * @throws IOException Checks if Cineplex Database exists.
     */
    public void CreateNewCinema(String cinemaName, Cinema.CinemaType cinemaType,
                                AllCineplex cineplex) throws IOException {
        int cinemaAlpha = 'A';

        StringBuilder sb = new StringBuilder();
        String temp = "";
        for (Cinema c : this.getListOfCinemas()) {
            temp = String.valueOf(c.getCinemaCode().charAt(0));
            cinemaAlpha++;
        }
        sb.append(temp);
        sb.append(cinemaType.ToString().charAt(0));
        sb.append((char) ++cinemaAlpha);
        Cinema newCinema = new Cinema(sb.toString(), cinemaName, cinemaType);
        this.addCinema(newCinema);
        File cinema = new File(cineplexDir + File.separator + newCinema.getCinemaName() + ".txt");
        if (!cinema.exists()) cinema.createNewFile();
        newCinema.setCinemaDir(dirName + File.separator + newCinema.getCinemaName() + ".txt");
        TextDB.UpdateToTextDB(TextDB.Files.Cineplex.toString(), cineplex.getCineplexes(), cineplex);
    }

    /**
     * This Method displays the movie's Date and ShowTime for each Movie.
     */
    public void displayMovieTimings(ArrayList<Movie> listOfMovies) {
        System.out.printf("\n%s\n", this.cineplexName);
        ArrayList<Movie> movielist = listOfMovies;
        ArrayList<Cinema> listOfCinemas = this.listOfCinemas;
        for (int j = 0; j < movielist.size(); j++) {
            System.out.printf("\n\t%s\n", movielist.get(j).getMovieTitle()); // movie
            ArrayList<ShowTime> allST = new ArrayList<>();
            ArrayList<Cinema> cinemas = new ArrayList<>();
            for (Cinema c : listOfCinemas) {
                ArrayList<ShowTime> listOfShowtime = c.getShowTime();
                if (listOfShowtime.size() > 0) {
                    for (ShowTime st : listOfShowtime) {
                        if (Objects.equals(st.getMovie().getMovieTitle(), movielist.get(j).getMovieTitle())) {
                            allST.add(st);
                            cinemas.add(c);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        }
                    }
                }
            }


            if (allST.size() > 0) {
                //allST.sort(Comparator.comparing(ShowTime::getTimeHour));
                // need a sort function
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd-MM-YYYY");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String previousString = dateFormat.format(allST.get(0).getTime());
                System.out.printf("\t\t%s", previousString);
                for (int k = 0; k < allST.size(); k++) {
                    if (Objects.equals(previousString, dateFormat.format(allST.get(k).getTime()))) {
                        previousString = "";
                        System.out.printf("\t(%s)Cinema %s", cinemas.get(k).getCinemaType().ToString() ,cinemas.get(k).getCinemaName());
                        System.out.printf("%s:%s", previousString, timeFormat.format(allST.get(k).getTime()));
                        previousString = dateFormat.format(allST.get(k).getTime());
                    } else {
                        previousString = dateFormat.format(allST.get(k).getTime());
                        System.out.printf("\n\t\t%s\t(%s)Cinema %s:%s", previousString, cinemas.get(k).getCinemaType().ToString(), cinemas.get(k).getCinemaName(), timeFormat.format(allST.get(k).getTime()));
                    }
                }
                System.out.println();
            }
        }
    }
}