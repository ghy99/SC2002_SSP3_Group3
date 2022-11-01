package UserInterface;
import Movie.*;
import Cineplex.Cineplex;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
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


        File movieFile = new File(TextDB.getCurrentDirectory() + File.separator + TextDB.Files.ShowTime.ToString());
        if(!movieFile.exists())movieFile.createNewFile();

        ArrayList<Movie> movieList = db.readFromFile(File.separator+TextDB.Files.Movies.ToString(), new ArrayList<>());

        for (Cineplex cineplex : cineplexes) {
            cineplex.setListOfMovies(movieList);
            cineplex.InitializeMovies();
        }
        System.out.println("Cineplexes are initialized\n");
    }

    public static void start() throws IOException {
        InitializeCineplexes();

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Moblima!");
        System.out.println("Initializing");
        tid = 1.0;
        int option = 1;
        do {
            System.out.println("Select option:");
            System.out.println("1) Display List of Cineplexes.");
            System.out.println("2) Display List of Movies.");
            System.out.println("3) Login with your Account.");
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
                case 3 -> {
                    UserUI.UserInterface(cineplexes, tid);
                }

//                case 1 -> {
//                    // Call adminUI
//                    System.out.println("Calling edit ticket");
//                    EditTicketPriceUI.EditTicket();
//                }
//                case 2 -> {
//                    CustomerUI.CustomerInterface(cineplexes, tid++);
//                }
            }
        } while (option > 0);

    }
}
