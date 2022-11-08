package UserInterface;

import Cineplex.*;
import Service.GetNumberInput;
import Service.Settings;


/**
 * @authors GAN HAO YI, EDDY CHENG KUAN QUAN
 * Controls main through initializing everything needed.
 */
public class MainUI {
    /**
     * This function represents the starting page when the app loads.
     * It shows the list of options user can use when the app starts.
     *
     * @throws Exception when accessing env.txt to check for Customer / Guest rights to view
     *                   What kind of Top 5 listing method they are allowed to use.
     */
    public static void start() throws Exception {
        AllCineplex cineplexes = new AllCineplex();
        cineplexes.clearScreen();
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*              Welcome to Moblima!              *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);

        int option = 1;
        do {
            System.out.println("Select option (Enter -1 to exit):");
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

            option = GetNumberInput.getInt(1, 7, -1);

            if (option == -1) {
                System.out.println("Exiting...");
                break;
            }
            cineplexes.clearScreen();
            switch (option) {
                case 1 -> {
                    System.out.println("Displaying List of Cineplex.");
                    cineplexes.displayCineplexList();
                }
                case 2 -> {
                    System.out.println("Displaying List of Movies.");
                    AllCineplex.displayMovieList(cineplexes.getListOfMovies());
                }
                case 3 -> {
                    System.out.println("Displaying List of Timing.");
                    System.out.println("Which cineplex would you like to view:");
                    for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
                        System.out.printf("\t%d) Branch %s\n", i + 1, cineplexes.getCineplexes().get(i).getCineplexName());
                    }
                    int choice = GetNumberInput.getInt(1, cineplexes.getCineplexes().size(), -1) - 1;
                    cineplexes.getCineplexes().get(choice).displayMovieTimings(cineplexes.getListOfMovies());
                }
                case 4 -> {
                    GuestUI.UserInterface(cineplexes);
                }
                case 5 -> {
                    UserUI.UserInterface(cineplexes);
                }
                case 6 -> {
                    System.out.println("Displaying Movies by Ranking.");
                    if (cineplexes.isRating() || cineplexes.isSale()) {
                        if (cineplexes.isRating() && cineplexes.isSale()) {
                            System.out.println("\t1) View by top 5 sale ");
                            System.out.println("\t2) View by top 5 rating ");
                            int userInput = GetNumberInput.getInt(1, 2, -1);
                            switch (userInput) {
                                case 1 -> {
                                    cineplexes.printSortedList(AllCineplex.MovieSort.Top5Sales);
                                }
                                case 2 -> {
                                    cineplexes.printSortedList(AllCineplex.MovieSort.Top5Rating);
                                }
                                case -1 -> {
                                    System.out.println("Exiting...");
                                }
                            }
                        } else if (cineplexes.isSale()) {
                            cineplexes.printSortedList(AllCineplex.MovieSort.Top5Sales);
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


