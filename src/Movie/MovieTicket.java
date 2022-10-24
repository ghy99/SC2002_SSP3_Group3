package Movie;
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
    private Movie movie;
    private MovieSeats seatID;
    private Date movieDateTime;
    private int movieTicketID;

    public MovieTicket(Movie movie, MovieSeats seatid, Date moviedatetime, int movieTicketID) {
        this.movie = movie;
        this.seatID = seatid;
        this.movieDateTime = moviedatetime;
        this.movieTicketID = movieTicketID;
    }
    public double CalculatePrice() {
        Calendar c = Calendar.getInstance();
        c.setTime(movieDateTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int chargeDay = 0;
        if (dayOfWeek < 6) { // if weekday charge $2, weekend charge $4
            chargeDay = chargeDay + 2;
        }
        else {
            chargeDay = chargeDay + 4;
        }

        return 0;
    }

    public void printTicket() {
        System.out.printf("MOVIE: %s", this.movie.getMovieTitle());
        System.out.printf("SEAT: %s", this.seatID);
        System.out.printf("TIME: %s", this.movieDateTime.toString());
        System.out.printf("TICKET ID: %d", this.movieTicketID);
    }
}
