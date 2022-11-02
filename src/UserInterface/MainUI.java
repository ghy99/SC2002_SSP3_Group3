package UserInterface;
import Movie.*;
import Cineplex.Cineplex;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

        ArrayList<Movie> movieList = db.readFromFile(File.separator+TextDB.Files.Movies.toString(), new ArrayList<>());

        for (Cineplex cineplex : cineplexes) {
            cineplex.setListOfMovies(movieList);
            cineplex.InitializeMovies();
        }
        System.out.println("Cineplexes are initialized\n");
    }

    public static void start() throws IOException, NoSuchAlgorithmException {
        InitializeCineplexes();

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Moblima!");

        int option = 1;
        do {
            System.out.println("Select option:");
            System.out.println("1) Display List of Cineplexes.");
            System.out.println("2) Display List of Movies.");
            System.out.println("4) Book as guest.");
            System.out.println("5) Login with your Account.");
            option = sc.nextInt();
//            sc.nextLine();
            switch(option) {
                case 1 -> {
                    for (int i = 0; i < cineplexes.size(); i++) {
                        System.out.printf("%d) %s\n", i + 1, cineplexes.get(i).getCineplexName());
                    }
                }
                case 2 -> {
                    for (int i = 0; i < cineplexes.size(); i++) {
                        ArrayList<Movie> movielist =  cineplexes.get(i).getListOfMovies();
                        for (int j = 0; j < movielist.size(); j++) {
                            System.out.printf("%d)\n", j + 1);
                            movielist.get(j).printMovieDetails();
                        }
                    }
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
}
