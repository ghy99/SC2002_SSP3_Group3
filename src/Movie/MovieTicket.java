package Movie;
import Cineplex.*;
import Service.TextDB;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.random.RandomGenerator;
import java.time.*;
import java.util.Hashtable;
import java.util.Set;

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
    private Cinema Cinema;
    private Movie Movie;
    private String TID;
    private MovieSeats SeatID;
    private Date MovieDateTime;
    private int MovieTicketID;




    public MovieTicket() {
        System.out.println("Ticket Created.");
    }
    public MovieTicket( Movie movie, MovieSeats seatid, Date moviedatetime, int movieTicketID) {
        this.Movie = movie;
        this.SeatID = seatid;
        this.MovieDateTime = moviedatetime;
        this.MovieTicketID = movieTicketID;
    }

    public void setChosenCineplex(Cineplex cineplex) {
        this.ChosenCineplex = cineplex;
    }

    public void setCinema(Cinema cinema) {
        this.Cinema = cinema;
    }

    public void setChosenMovie(Movie movie) {
        this.Movie = movie;
    }

    public void setSeatID(MovieSeats seat) {
        this.SeatID = seat;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public Cinema getCinema() {
        return Cinema;
    }

    public Cineplex getChosenCineplex() {
        return this.ChosenCineplex;
    }

    public Movie getChosenMovie() {
        return this.Movie;
    }

    public String getTID() {
        return TID;
    }

    public boolean checkHoliday() throws IOException {
    	LocalDate tdyDate = LocalDate.now();

    	ArrayList<String> holidayList = (ArrayList<String>) TextDB.Read("HolidayDates.txt"); //extract list of holiday dates from storage
    	
    	for (int i = 0; i<holidayList.size();i++) {
	    	if (tdyDate.toString().equals(holidayList.get(i))) {
	    		System.out.println(tdyDate.toString());
	    		return true;
	    	}
	    }
    	
    	return false;
    	
    }



    public static double CalculatePrice(MovieTicket ticket) throws IOException {
        System.out.println("Calculate Price\n");
        System.out.println("Initializing Prices to compare\n");
        TicketCharges charges = new TicketCharges();
        int userAge = 20;
        int day = 2;
        int dim = 2;
        int type = 1;
        double ageCost = charges.getPriceByAge(userAge); // get user age
        double dayCost = charges.getPriceByDay(day); // change to get day of week through datetime
        double dimCost = charges.getPriceByDim(dim); // get dimension of movie
        double typeCost = charges.getPriceByType(type); // get cinema type
        if (ageCost == -1) {
            System.out.println("Age is invalid. please enter a new value.");
            return -1;
        }
        if (dayCost == -1) {
            System.out.println("Day of the week is invalid. please enter a new value.");
            return -1;
        }
        if (dimCost == -1) {
            System.out.println("Movie Dimension is invalid. please enter a new value.");
            return -1;
        }
        if (typeCost == -1) {
            System.out.println("Cinema Type is invalid. please enter a new value.");
            return -1;
        }
        return ageCost + dayCost + dimCost + typeCost;
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
