package Movie;
import Cineplex.Cineplex;
import Movie.MovieType;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.random.RandomGenerator;

public class MovieTicket {
    // Linked to each Cinema
    // Diff category such as
    // Different ticket prices based on:
        // type of movie - Action, Romance, Horror
        // Cinema Class - Gold, Premium, Platinum, Regular
        // Age of buyer - Elderly, Adult, Child
        // Day of the week - weekday, weekend, public holiday
    // text file format:
        // Student, Adult, Senior Citizen | Weekday, Weekend, Public Holiday | 2D, 3D | Regular, Platinum
        // Blockbusters + $1
    private Cineplex ChosenCineplex;
    private Movie Movie;
    private MovieSeats SeatID;
    private Date MovieDateTime;
    private int MovieTicketID;

    public MovieTicket() {
        System.out.println("Ticket Created.");
    }
    public MovieTicket(Cineplex cineplex, Movie movie, MovieSeats seatid, Date moviedatetime, int movieTicketID) {
        this.Movie = movie;
        this.SeatID = seatid;
        this.MovieDateTime = moviedatetime;
        this.MovieTicketID = movieTicketID;
    }

    public void setChosenCineplex(Cineplex cineplex) {
        this.ChosenCineplex = cineplex;
    }

    public void setChosenMovie(Movie movie) {
        this.Movie = movie;
    }

    public Cineplex getChosenCineplex() {
        return this.ChosenCineplex;
    }

    public Movie getChosenMovie() {
        return this.Movie;
    }

    public double CalculatePrice(MovieTicket ticket) throws IOException {
        ArrayList<ArrayList<Integer>> ticketPrices = new ArrayList<>();
        String filename = "TicketPrice.txt";
        ticketPrices = TextDB.readFromFile(filename, ticket);
        for (ArrayList<Integer> prices : ticketPrices) {
            for (int cat : prices) {
                System.out.printf("%d\t", cat);
            }
            System.out.println();
        }
//        Calendar c = Calendar.getInstance();
//        c.setTime(MovieDateTime);
//        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//        int chargeDay = 0;
//        if (dayOfWeek < 6) { // if weekday charge $2, weekend charge $4
//            chargeDay = chargeDay + 2;
//        }
//        else {
//            chargeDay = chargeDay + 4;
//        }
//        MovieType.Type CinemaType = this.Movie.getTypeOfCinema();
//        System.out.printf("You are watching in a %s Theatre!", CinemaType.toString());
        return 0;
    }

    public void printTicket() {
        System.out.printf("CINEPLEX: %s\n", this.ChosenCineplex.getCineplexName());
        System.out.printf("MOVIE: %s\n", this.Movie.getMovieTitle());
        System.out.printf("SEAT: %s\n", this.SeatID);
        if (this.MovieDateTime != null)
            System.out.printf("TIME: %s\n", this.MovieDateTime.toString());
        System.out.printf("TICKET ID: %d\n", this.MovieTicketID);
    }
}
