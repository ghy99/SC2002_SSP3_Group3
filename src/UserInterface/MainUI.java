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
 */
public class MainUI {

    /**
     * This method initializes the cineplex. It reads the cineplex names stored
     * and load it into the Cineplex ArrayList.
     * @throws IOException to check if Cineplexes.txt exist.
     */


    /**
     * This function represents the starting page when the app loads.
     * It shows the list of options user can use when the app starts.
     *
     * @throws Exception when accessing env.txt to check for Customer / Guest rights to view
     *                   what kind of Top 5 listing method they are allowed to use.
     */
    public static void start() throws Exception {
        AllCineplex cineplexes = new AllCineplex();

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
                    cineplexes.displayCineplexList();
                }
                case 2 -> {
                    cineplexes.displayMovieList();
                }
                case 3 -> {
                    System.out.println("Which cineplex would you like to view:");
                    for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
                        System.out.printf("%d) Branch %s\n", i, cineplexes.getCineplexes().get(i).getCineplexName());
                    }
                    int choice = GetNumberInput.getInt();
                    cineplexes.getCineplexes().get(choice).displayMovieTimings(cineplexes.getListOfMovies());
                }
                case 4 -> {
                    GuestUI.UserInterface(cineplexes);
                }
                case 5 -> {
                    UserUI.UserInterface(cineplexes);
                }

//                case 6 -> {
//                    List data = TextDB.Read("env.txt");
//                    String env = (String) data.get(0);
//                    int envInt = Integer.parseInt(env);
//                    switch (envInt) {
//                        case 1 -> {
//                            Review.RankingByRating();
//                        }
//                        case 2 -> {
//                            Review.RankingByTicketSales();
//                        }
//                        case 3 -> {
//                            Review.RankingByRating();
//                            Review.RankingByTicketSales();
//                        }
//                    }
//                }
            }
        } while (option > 0);
    }
}


