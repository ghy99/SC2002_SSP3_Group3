package UserInterface;

import Admin.Admin;
import Cineplex.*;
import Customer.Customer;
import Movie.Movie;
import Movie.MovieTicket;
import Service.GetNumberInput;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is the Guest Interface class. Imported to call the User Interface for the customer to login without an account.
 * @authors CHEW ZHI QI, GAN HAO YI
 */
public class GuestUI {

    /**
     * This is the User Interface function to allow the guest customer to make a movie booking without logging in
     * @param cineplexes = show the list of movies showing in each cineplex
     * @throws IOException is thrown if reading the input data or reading data from the file causes an error.
     */

    public static void UserInterface(AllCineplex cineplexes) throws IOException {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("************* Entering Guest Mode ***************");
        Customer customer = new Customer();

        do {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t1) Select Cineplex.");
            System.out.println("\t2) Print your Booking History.");
            System.out.println("\tEnter '11' to exit!");

            choice = GetNumberInput.getInt(1, 2, 11);
            switch (choice) {
                case 1 -> {
                    ArrayList<Object> sTnC = null, sSTnC = null;
                    ArrayList<MovieTicket> allMovieTicket = null;
                    Cineplex choosenCineplex = CineplexUI.CineplexInterface(cineplexes);

                    Movie chosenMovie = MovieUI.MovieInterface(cineplexes.getListOfMovies());
                    do {
                        sTnC = SelectDateUI.SelectDateInterFace(choosenCineplex , chosenMovie);
                    }while (sTnC == null);

                    do {
                        sSTnC = SelectDimensionUI.SelectDimensionUserInterface(choosenCineplex, sTnC);
                    }while (sSTnC == null);

                    do {
                        allMovieTicket = SelectSeatsUI.SelectSeatsUserInterface(customer, choosenCineplex, chosenMovie ,sSTnC);
                    }while (allMovieTicket == null);

                    String name, number, email;
                    do {
                        System.out.println("Please enter your name:");
                        name = sc.nextLine();
                        System.out.println("Please enter your number:");
                        number = sc.nextLine();
                        System.out.println("Please enter your email: (So that you will be able to check your transaction later)");
                        email = sc.nextLine();
                    } while (Objects.equals(email, ""));
                    customer.setMovieGoerName(name);
                    customer.setMobileNumber(number);
                    customer.setEmail(email);
                    customer.printCustomerDetails();
                    cineplexes.createCustomerAccount(customer); // Not checking if customer created account
                    PaymentUI.PaymentInterface(cineplexes, customer, allMovieTicket, choosenCineplex, chosenMovie ,sSTnC);
                    for (MovieTicket tix : allMovieTicket) {
                        TextDB.WriteToTextDB(TextDB.Files.TransactionHistory.toString(), customer, tix);
                        tix.printTicket();
                    }
                }
                case 2 -> {
                    System.out.println("Please give input your email that used to booked:");
                    String Email = sc.nextLine();
                    ArrayList<MovieTicket> movieTickets = TextDB.ReadFromFile(TextDB.Files.TransactionHistory.toString(), Email);
                    for( MovieTicket mt : movieTickets)
                    {
                        mt.printTicket();
                    }
                }
            }
        } while (choice < 10);
    }
}
