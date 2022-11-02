package UserInterface;
import Movie.*;
import Cineplex.*;
import Service.GetNumberInput;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Done by Gan Hao Yi
 * Controls main through initializing everything needed.
 *
 */

public class MainUI {

    private static Double tid;

    private static ArrayList<Cineplex> cineplexes;
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
            }
        } while (option > 0);

    }



    public static void displayCineplexList() {
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("\t%d) %s\n", i + 1, cineplexes.get(i).getCineplexName());
        }
        System.out.println();
    }

    public static void displayMovieList() {
        ArrayList<Movie> movielist = cineplexes.get(0).getListOfMovies();
        for (int j = 0; j < movielist.size(); j++) {
            System.out.printf("%d)\n", j + 1);
            movielist.get(j).printMovieDetails();
        }
        System.out.println();
    }


    public static void displayMovieTimings() {
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("%s\n", cineplexes.get(i).getCineplexName());
            ArrayList<Movie> movielist = cineplexes.get(i).getListOfMovies();
            ArrayList<Cinema> listOfCinemas = cineplexes.get(i).getListOfCinemas();
            for (int j = 0; j < listOfCinemas.size(); j++) {
                System.out.printf("\t%s\n", movielist.get(j).getMovieTitle()); // movie
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

                for (int k = 0; k < allST.size(); k ++){
                    System.out.printf("\t\t%s\n", allST.get(k).getTime());
                }

            }

        }
    }
}


