package UserInterface;
import Movie.*;
import Cineplex.*;
import Service.GetNumberInput;
import Service.TextDB;
import Review.Review;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Done by Gan Hao Yi & Eddy Cheng
 * Controls main through initializing everything needed.
 *
 */
public class MainUI {
    /**
     * This variable stores an ArrayList of cineplex to be passed into
     * guest booking movies / members booking movies.
     */
    private static ArrayList<Cineplex> cineplexes;

    /**
     * This method initializes the cineplex. It reads the cineplex names stored
     * and load it into the Cineplex ArrayList.
     * @throws IOException to check if Cineplexes.txt exist.
     */
    public static void InitializeCineplexes() throws IOException {
        System.out.println("Initializing Cineplexes...\n...\n...");
        TextDB db = new TextDB();
        cineplexes = new ArrayList<Cineplex>();
        String filename = "Cineplexes.txt";
        try {
            cineplexes = db.readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File movieFile = new File(TextDB.getCurrentDirectory() + File.separator + TextDB.Files.Movies.toString());
        if(!movieFile.exists())movieFile.createNewFile();


        ArrayList<Movie> movieList = db.readFromFile(File.separator + TextDB.Files.Movies.toString(), new ArrayList<>());

        for (Cineplex cineplex : cineplexes) {
            cineplex.setListOfMovies(movieList);
            cineplex.InitializeMovies();
        }
        System.out.println("Cineplexes are initialized\n");
    }

    /**
     * This function represents the starting page when the app loads.
     * It shows the list of options user can use when the app starts.
     * @throws Exception when accessing env.txt to check for Customer / Guest rights to view
     * what kind of Top 5 listing method they are allowed to use.
     */
    public static void start() throws Exception {
        InitializeCineplexes();

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Moblima!");

        int option = 1;
        do {
            System.out.println("Select option:");
            System.out.println("1) Display List of Cineplexes.");
            System.out.println("2) Display List of Movies.");
            System.out.println("3) Display List of Timing.");
            System.out.println("4) Book as guest.");
            System.out.println("5) Login with your Account.");
            System.out.println("6) Display Movies by Ranking.");
            option = GetNumberInput.getInt();

//            sc.nextLine();
            switch (option) {
                case 1 -> {
                    displayCineplexList();
                }
                case 2 -> {
                   displayMovieList();
                }
               case 3 -> {
                    displayMovieTimings();
                }
                case 4 -> {
                    GuestUI.UserInterface(cineplexes);
                }
                case 5 -> {
                    UserUI.UserInterface(cineplexes);
                }

                case 6 ->{

                    List data = TextDB.Read("env.txt");
                    String env = (String) data.get(0);
                    int envInt = Integer.parseInt(env);
                    switch(envInt) {
                        case 1 -> {
                            Review.RankingByRating();

                        }

                        case 2 -> {
                            Review.RankingByTicketSales();
                        }

                        case 3 -> {
                            Review.RankingByRating();
                            Review.RankingByTicketSales();
                        }
                    }

                }




            }
        } while (option > 0);

    }

    /**
    Done by : Eddy Cheng
     * This Method displays the list of cineplex(Branches) available.
     */
    public static void displayCineplexList() {
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("\t%d) %s\n", i + 1, cineplexes.get(i).getCineplexName());
        }
        System.out.println();
    }

    /**
     * This Method displays the List of Movies currently available. User will only see Movie Title.
     * After this method, user will be able to select which movie to display more details.
     */
    public static void displayMovieList() {
        ArrayList<Movie> movielist = cineplexes.get(0).getListOfMovies();
        for (int j = 0; j < movielist.size(); j++) {
            System.out.printf("%d)\n", j + 1);
            movielist.get(j).printMovieDetails();
        }
        System.out.println();
    }

    /**
     * This Method displays the movie date & time for each movie.
     */
    public static void displayMovieTimings() {
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("%s", cineplexes.get(i).getCineplexName());
            ArrayList<Movie> movielist = cineplexes.get(i).getListOfMovies();
            ArrayList<Cinema> listOfCinemas = cineplexes.get(i).getListOfCinemas();
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

}


