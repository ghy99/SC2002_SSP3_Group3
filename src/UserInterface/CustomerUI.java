package UserInterface;


import Cineplex.AllCineplex;
import Cineplex.Cineplex;
import Customer.Customer;
import Movie.Movie;
import Movie.MovieTicket;
import Service.GetNumberInput;
import Service.Settings;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is the Customer User Interface. This is imported to call the Customer Interface to access their account.
 *
 * @authors GAN HAO YI, CHEW ZHI QI
 */
public class CustomerUI {
    /**
     * This is the Customer Interface for the customer to access their account and make changes to their details or
     * print their details and booking history.
     *
     * @param cineplexes        - This object is passed into the Customer Interface to start the ticket booking process.
     * @param customerArrayList - this is passed to update the customer details in the customers file.
     * @param customer          - This is passed to access the customer details.
     * @throws IOException is thrown if there is an error in reading the customer file
     */
    public static void CustomerInterface(AllCineplex cineplexes, ArrayList<Customer> customerArrayList,
                                         Customer customer) throws IOException {
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*         Welcome to the Customer Portal        *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        customer.printCustomerDetails();
        do {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t1) Select Cineplex.");
            System.out.println("\t2) Change your name.");
            System.out.println("\t3) Change your number.");
            System.out.println("\t4) Change your email.");
            System.out.println("\t5) Print your details.");
            System.out.println("\t6) Print your Booking History.");
            choice = GetNumberInput.getInt(1, 6, -1);
            if (choice == -1) break;
            switch (choice) {
                case 1 -> {
                    ArrayList<Object> sTnC = null, sSTnC = null;
                    ArrayList<MovieTicket> allMovieTicket = null;
                    Cineplex chosenCineplex = CineplexUI.CineplexInterface(cineplexes);

                    Movie chosenMovie = MovieUI.MovieInterface(cineplexes.getListOfMovies());
                    do {
                        sTnC = SelectDateUI.SelectDateInterFace(chosenCineplex, chosenMovie);
                    } while (sTnC == null);

                    do {
                        sSTnC = SelectDimensionUI.SelectDimensionUserInterface(chosenCineplex, sTnC);
                    } while (sSTnC == null);

                    do {
                        allMovieTicket = SelectSeatsUI.SelectSeatsUserInterface(customer, chosenCineplex, chosenMovie, sSTnC);
                    } while (allMovieTicket == null);

                    if(allMovieTicket.size() == 0)
                    {
                        System.out.println("You canceled your booking! Thank you!");
                    }
                    else
                    {
                        customer.printCustomerDetails();
                        PaymentUI.PaymentInterface(cineplexes, allMovieTicket, chosenMovie, sSTnC);
                        for (MovieTicket tix : allMovieTicket) {
                            TextDB.WriteToTextDB(TextDB.Files.TransactionHistory.toString(), customer, tix);
                            tix.printTicket();
                        }
                        chosenMovie.increaseMovieTotalSale(cineplexes.getListOfMovies(), allMovieTicket.size());
                    }

                }
                case 2 -> {
                    String newName;
                    do {
                        System.out.println("Enter your new name: ");
                        newName = sc.nextLine();
                    } while (Objects.equals(newName, ""));
                    customer.updateMovieGoerName(newName, customerArrayList);
                }
                case 3 -> {
                    String newNumber;
                    do {
                        System.out.println("Enter your new number: ");
                        newNumber = sc.nextLine();
                    } while (Objects.equals(newNumber, ""));
                    customer.updateMobileNumber(newNumber, customerArrayList);
                }
                case 4 -> {
                    String newEmail;
                    do {
                        System.out.println("Enter your new email: ");
                        newEmail = sc.nextLine();
                    } while (Objects.equals(newEmail, ""));
                    customer.updateEmail(newEmail, customerArrayList);
                }
                case 5 -> {
                    customer.printCustomerDetails();
                }
                case 6 -> {
                    ArrayList<MovieTicket> movieTickets = TextDB.readFromFile(TextDB.Files.TransactionHistory.toString(), customer.getEmail());
                    for (MovieTicket mt : movieTickets) {
                        mt.printTicket();
                    }
                }
                default -> System.out.println("Invalid Input. Try again.");
            }
        } while (choice < 6 && choice > 1);
    }
}