package Movie;
import Cineplex.Cineplex;
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
    private Movie Movie;
    private MovieSeats SeatID;
    private Date MovieDateTime;
    private int MovieTicketID;

    private Hashtable<Integer, Double> priceByAge;
    private Hashtable<Integer, Double> priceByDay;
    private Hashtable<Integer, Double> priceByMovieDim;
    private Hashtable<Integer, Double> priceByCinemaType;

    private Boolean blockbuster;


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

    public void setMovieSeats(MovieSeats seatID){
        this.SeatID = seatID;
    }

    public void setMovieDateTime(Date movieDateTime){
        this.MovieDateTime = movieDateTime;
    }

    public Cineplex getChosenCineplex() {
        return this.ChosenCineplex;
    }

    public Movie getChosenMovie() {
        return this.Movie;
    }

    public MovieSeats getMovieSeats(){
        return this.SeatID;
    }

    public Date getMovieTiming(){
        return this.MovieDateTime;
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

    public void setPriceByAge(ArrayList<Double> age) {
        this.priceByAge = new Hashtable<Integer, Double>();
        for (int i = 0; i < age.size(); i++) {
            if (i == 0) {
                this.priceByAge.put(21, age.get(i)); // Below 21 y.o.
            }
            else if (i == 1) {
                this.priceByAge.put(65, age.get(i)); // Below 65, above 21 y.o.
            }
            else if (i == 2) {
                this.priceByAge.put(66, age.get(i)); // Above 65 y.o.
            }
        }
    }

    public void setPriceByDay(ArrayList<Double> day) {
        this.priceByDay = new Hashtable<Integer, Double>();
        for (int i = 0; i < day.size(); i++) {
            if (i == 0) {
                this.priceByDay.put(1, day.get(i)); // Monday - Friday
                this.priceByDay.put(2, day.get(i)); // Monday - Friday
                this.priceByDay.put(3, day.get(i)); // Monday - Friday
                this.priceByDay.put(4, day.get(i)); // Monday - Friday
                this.priceByDay.put(5, day.get(i)); // Monday - Friday

            }
            else if (i == 1) {
                this.priceByDay.put(6, day.get(i)); // Saturday - Sunday
                this.priceByDay.put(7, day.get(i)); // Saturday - Sunday
            }
            else if (i == 2) {
                this.priceByDay.put(0, day.get(i)); // Public Holiday
            }
        }
    }

    public void setPriceByMovieDim(ArrayList<Double> moviedim) {
        this.priceByMovieDim = new Hashtable<Integer, Double>();
        for (int i = 0; i < moviedim.size(); i++) {
            if (i == 0) {
                this.priceByMovieDim.put(2, moviedim.get(i)); // 2D
            }
            else if (i == 1) {
                this.priceByMovieDim.put(3, moviedim.get(i)); // 3D
            }
        }
    }

    public void setPriceByCinemaType(ArrayList<Double> cinematype) {
        this.priceByCinemaType = new Hashtable<Integer, Double>();
        for (int i = 0; i < cinematype.size(); i++) {
            if (i == 0) {
                this.priceByCinemaType.put(1, cinematype.get(i)); // Regular
            }
            else if (i == 1) {
                this.priceByCinemaType.put(2, cinematype.get(i)); // Premium
            }
        }
    }

    public void initTicketprices() throws IOException {
        ArrayList<ArrayList<Double>> ticketPrices = new ArrayList<>();
        String filename = "TicketPrice.txt";
        ticketPrices = TextDB.readFromFile(filename, (MovieTicket) null);
        for (int i = 0; i < ticketPrices.size(); i++) {
            if (i == 0) {
                setPriceByAge(ticketPrices.get(i));
            }
            else if (i == 1) {
                setPriceByDay(ticketPrices.get(i));
            }
            else if (i == 2) {
                setPriceByMovieDim(ticketPrices.get(i));
            }
            else if (i == 3) {
                setPriceByCinemaType(ticketPrices.get(i));
            }
        }
    }

    public double getPriceByAge(int userage) {
        Set<Integer> ages = this.priceByAge.keySet();
        for (Integer key : ages) {
            if (userage < key) {
                return priceByAge.get(key);
            }
        }
        return -1;
    }

    public double getPriceByDay(int day) {
        Set<Integer> days = this.priceByDay.keySet();
        for (Integer key : days) {
            if (key == day) {
                return priceByDay.get(key);
            }
        }
        return -1;
    }

    public double getPriceByDim(int dim) {
        Set<Integer> dims = this.priceByMovieDim.keySet();
        for (Integer key: dims) {
            if (key == 2 || key == 3) {
                return priceByMovieDim.get(key);
            }
        }
        return -1;
    }

    public double getPriceByType(int type) {
        Set<Integer> types = this.priceByCinemaType.keySet();
        for (Integer key: types) {
            if (key == 1 || key == 2) {
                return priceByCinemaType.get(key);
            }
        }
        return -1;
    }

    public double CalculatePrice(MovieTicket ticket) throws IOException {
        System.out.println("Calculate Price\n");
        System.out.println("Initializing Prices to compare\n");
        initTicketprices();
        int userAge = 20;
        int day = 2;
        int dim = 2;
        int type = 1;
        double ageCost = getPriceByAge(userAge); // get user age
        double dayCost = getPriceByDay(day); // change to get day of week through datetime
        double dimCost = getPriceByDim(dim); // get dimension of movie
        double typeCost = getPriceByType(type); // get cinema type
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
