package Movie;
import Cineplex.*;
import Service.*;
import Service.TextDB;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 *  @author CHEW ZHI QI, GAN HAO YI
 *  Movie Ticket class for reading from db / writing to db
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
     * Constructor
     * @param email - User email for tracking
     * @param chosenCineplex - User's selected Cineplex to watch movie
     * @param cinema - Which Cinema they are watching movie in
     * @param movie - Which Movie they are watching
     * @param TID - Transaction ID
     * @param seatID - Seat ID
     * @param movieDateTime - Date Time of movie
     * @param seattype - Single / Double Seat
     * @param cinType - Type of Cinema: Premium or Regular
     * @param dim
     * @param blockbuster
     */
    public MovieTicket(
            String email ,String chosenCineplex, String cinema, String movie,
            String TID, String seatID, Date movieDateTime,
            IndividualSeats.SeatType seattype, Cinema.CinemaType cinType,
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void printTicket() {
        System.out.printf("CINEPLEX: %s\n", this.ChosenCineplex);
        System.out.printf("CINEMA: %s\n", this.Cinema);
        System.out.printf("MOVIE: %s\n", this.getChosenMovie());
        System.out.printf("MOVIE DATE:TIME: %s\n", this.getShowtime());
        System.out.printf("SEAT: %s\n", this.SeatID);
        System.out.printf("Transaction ID: %s\n", this.getTID());
        System.out.println();
    }
}
