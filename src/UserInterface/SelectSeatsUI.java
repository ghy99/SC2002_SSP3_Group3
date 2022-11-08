package UserInterface;

import Cineplex.Cinema;
import Cineplex.Cineplex;
import Cineplex.ShowTime;
import Customer.Customer;
import Movie.Movie;
import Movie.MovieTicket;
import Service.DateTime;
import Service.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static Movie.MovieSeatsNew.right;

public class SelectSeatsUI {
    public static ArrayList<MovieTicket> SelectSeatsUserInterface(Customer customer, Cineplex cineplex,
                                                                  Movie chosenMovie, ArrayList<Object> sSTnC) throws IOException {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> movieseats;
        boolean bookSeat = false;
        String userInput;
        ShowTime specificST = (ShowTime) sSTnC.get(0);
        Cinema cinema = (Cinema) sSTnC.get(1);
        ArrayList<MovieTicket> allMovieTickets = new ArrayList<>();
        int endRowIDNum;
        char endRowIDChar;
        endRowIDNum = (65 + specificST.getArray2D().size() - 1);
        endRowIDChar = (char) endRowIDNum; //changes for when the number of rows changes
        ArrayList<MovieTicket> allMovieTikcet = new ArrayList<>();

        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*               Select your Seats               *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);

        specificST.PrintSeats();
        movieseats = specificST.SelectSeats();

        System.out.println("Do you want to book seats? (yes/no)\n");
        do {
            userInput = sc.nextLine();
            if (Objects.equals(userInput, "yes")) bookSeat = true;
        } while (!Objects.equals(userInput, "yes") && !Objects.equals(userInput, "no"));
        if (bookSeat) {
            movieseats = specificST.BookSeats(movieseats, true, cinema);
            for (String seat : movieseats) {
                int c = (endRowIDChar - '0') - (seat.charAt(0) - '0');
                String right = right(seat, seat.length() - 1);
                MovieTicket movieTicket = new MovieTicket(customer.getEmail(),
                        cineplex.getCineplexName(), cinema.getCinemaName(), chosenMovie.getMovieTitle(),
                        new DateTime().ToTID(cinema.getCinemaCode()), seat, specificST.getTime(),
                        specificST.getArray2D().get(c).get(Integer.parseInt(right)).getSeatType(),
                        cinema.getCinemaType(), specificST.getDimension(), specificST.getMovie().getBlockBuster());
                allMovieTikcet.add(movieTicket);
            }
        }
        return allMovieTikcet;
    }
}