package Movie;
import Cineplex.*;
import Service.DateTime;
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

/**
 *  * @author CHEW ZHI QI, GAN HAO YI
 *  * Movie Ticket class for reading from db / writing to db
 *  Movie Ticket is used to calculate ticket price as well.
 */
public class MovieTicket {
    /**
     * Movie ticket properties email, choosenCineplex, cinema, movie, TID, SeatID, MovieDateTime
     */
    private String Email;
    private String ChosenCineplex;
    private String Cinema;
    private String Movie;
    private String TID;
    private String SeatID;
    private Date MovieDateTime;

    private IndividualSeats.SeatType seattype;
    private Cinema.CinemaType cinematype;
    private int age;
    private MovieType.Dimension dim;
    private MovieType.Blockbuster blockbuster;

    /**
     *
     */
    public MovieTicket() {
    }

    public MovieTicket(
            String email ,String chosenCineplex, String cinema, String movie,
            String TID, String seatID, Date movieDateTime,
            IndividualSeats.SeatType seattype, Cinema.CinemaType cinType, int age,
            MovieType.Dimension dim, MovieType.Blockbuster blockbuster) {
        this.Email = email;
        this.ChosenCineplex = chosenCineplex;
        this.Cinema = cinema;
        this.Movie = movie;
        this.TID = TID;
        this.SeatID = seatID;
        this.MovieDateTime = movieDateTime;
        this.seattype = seattype;
        this.cinematype = cinType;
        this.age = age;
        this.dim = dim;
        this.blockbuster = blockbuster;
    }

    public void setChosenCineplex(String cineplex) {
        this.ChosenCineplex = cineplex;
    }
    public String getChosenCineplex() {
        return this.ChosenCineplex;
    }

    public String getChosenMovie() {
        return this.Movie;
    }
    public void setChosenMovie(String movie) {
        this.Movie = movie;
    }

    public String getMovieSeats(){
        return this.SeatID;
    }
    public void setMovieSeats(String seatID){
        this.SeatID = seatID;
    }
    public String getCinema() {
        return Cinema;
    }
    public void setCinema(String cinema) {
        this.Cinema = cinema;
    }
    public Date getShowtime(){
        return this.MovieDateTime;
    }
    public void setMovieDateTime(Date movieDateTime) {
        this.MovieDateTime = movieDateTime;
    }
    public String getTID() {
        return TID;
    }
    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getSeatID() {
        return SeatID;
    }

    public void setSeatID(String seat) {
        this.SeatID = seat;
    }

    public IndividualSeats.SeatType getSeattype() {
        return seattype;
    }

    public void setSeattype(IndividualSeats.SeatType seattype) {
        this.seattype = seattype;
    }

    public Cinema.CinemaType getCinematype() {
        return cinematype;
    }

    public void setCinematype(Cinema.CinemaType cinematype) {
        this.cinematype = cinematype;
    }

    public MovieType.Blockbuster getBlockbuster() {
        return blockbuster;
    }

    public void setBlockbuster(MovieType.Blockbuster blockbuster) {
        this.blockbuster = blockbuster;
    }

    public MovieType.Dimension getDim() {
        return dim;
    }

    public void setDim(MovieType.Dimension dim) {
        this.dim = dim;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

        System.out.printf("CINEPLEX: %s\n", this.ChosenCineplex);
        System.out.printf("MOVIE: %s\n", this.getChosenMovie());
        System.out.printf("MOVIE DATE:TIME: %s\n", this.getShowtime());
        System.out.printf("SEAT: %s\n", this.SeatID);
        System.out.printf("TIME: %s\n", this.MovieDateTime);
        System.out.printf("TICKET ID: %s\n", this.getTID());
        System.out.println();
    }
}
