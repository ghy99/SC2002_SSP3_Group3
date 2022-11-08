package UserInterface;

import Cineplex.AllCineplex;
import Cineplex.Cineplex;
import Customer.Customer;
import Movie.Movie;
import Movie.MovieTicket;
import Service.Settings;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is the Guest Interface class. Imported to call the User Interface for the customer to login without an account.
 *
 * @authors CHEW ZHI QI, GAN HAO YI
 */
public class GuestUI {
    /**
     * This is the User Interface to allow the guest customer to make a movie booking without logging in.
     *
     * @param cineplexes = show the list of movies showing in each cineplex
     * @throws IOException is thrown if reading the input data or reading data from the file causes an error.
     */
    public static void UserInterface(AllCineplex cineplexes) throws IOException {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        Customer customer = new Customer();
        ArrayList<Object> sTnC = null, sSTnC = null;
        ArrayList<MovieTicket> allMovieTicket = null;
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*           Welcome to the Guest Portal         *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);

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

        String name, number, email;
        do {
            do {
                System.out.println("Please enter your name:");
                name = sc.nextLine();
            } while (Objects.equals(name, ""));
            do {
                System.out.println("Please enter your number:");
                number = sc.nextLine();
            } while (Objects.equals(number, ""));
            do {
                System.out.println("Please enter your email: (So that you will be able to check your transaction later)");
                email = sc.nextLine();
            } while (Objects.equals(email, ""));
        } while (Objects.equals(email, ""));
        customer.setMovieGoerName(name);
        customer.setMobileNumber(number);
        customer.setEmail(email);
        customer.printCustomerDetails();
        cineplexes.createCustomerAccount(customer); // Not checking if customer created account
        PaymentUI.PaymentInterface(cineplexes, allMovieTicket, chosenMovie, sSTnC);
        for (MovieTicket tix : allMovieTicket) {
            TextDB.WriteToTextDB(TextDB.Files.TransactionHistory.toString(), customer, tix);
            tix.printTicket();
        }
        chosenMovie.increaseMovieTotalSale(cineplexes.getListOfMovies(), allMovieTicket.size());
    }
}