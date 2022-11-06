package UserInterface;

import Cineplex.*;
import Service.GetNumberInput;

import java.io.IOException;


/**
 * @authors GAN HAO YI, EDDY CHENG KUAN QUAN
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
     * @throws Exception when accessing env.txt to check for Customer / Guest rights to view
     *                   what kind of Top 5 listing method they are allowed to use.
     */

    public static void start() throws Exception {
        AllCineplex cineplexes = new AllCineplex();

        System.out.println("Welcome to Moblima!");

        int option = 1;
        do {
            System.out.println("Select option:");
            System.out.println("1) Display List of Cineplexes.");
            System.out.println("2) Display List of Movies.");
            System.out.println("3) Display List of Timing.");
            System.out.println("4) Book as guest.");
            System.out.println("5) Login with your Account.");
            if (cineplexes.isRating() || cineplexes.isSale()) {
                System.out.println("6) Display Movies by Ranking.");
                System.out.println("7) Add Review.");
            } else {
                System.out.println("6) Add Review.");
            }

            option = GetNumberInput.getInt();

//            sc.nextLine();
            switch (option) {
                case 1 -> {
                    //cineplexes.displayCineplexList();
                    cineplexes.getCineplexes().get(0).getListOfCinemas().get(0).getShowTime().get(0).PrintSeats();
                }
                case 2 -> {
                    AllCineplex.displayMovieList(cineplexes.getListOfMovies());
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
                case 6 -> {
                    if (cineplexes.isRating() || cineplexes.isSale()) {
                        if (cineplexes.isRating() && cineplexes.isSale()) {
                            System.out.println("1) View by top 5 sale ");
                            System.out.println("2) View by top 5 rating ");
                            int userInput = GetNumberInput.getInt();

                            switch (userInput) {
                                case 1 -> {
                                    cineplexes.printSortedList( AllCineplex.MovieSort.Top5Sales);
                                }
                                case 2 -> {
                                    cineplexes.printSortedList( AllCineplex.MovieSort.Top5Rating);
                                }
                            }

                        } else if (cineplexes.isSale()) {
                            cineplexes.printSortedList( AllCineplex.MovieSort.Top5Sales);
                        } else if (cineplexes.isRating()) {
                            cineplexes.printSortedList(AllCineplex.MovieSort.Top5Rating);
                        }
                    } else {
                        ReviewUI.UserInferface(cineplexes);
                    }
                }
                case 7 -> {
                    if (cineplexes.isRating() || cineplexes.isSale()) {
                        ReviewUI.UserInferface(cineplexes);
                    }

                }
            }
        } while (option > 0);
    }
}


