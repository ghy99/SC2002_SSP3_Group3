package Movie;

import Cineplex.Cinema;

import java.util.Date;

/**
 * @author CHEW ZHI QI, GAN HAO YI
 * Movie Ticket class for reading from db / writing to db for Transaction History
 * Movie Ticket is used to calculate ticket price as well.
 */
public class MovieTicket {
    /**
     * Movie ticket properties including:
     * Email, Chosen Cineplex, Chosen Cinema, Chosen Movie, Transaction ID, Seat ID, DATE TIME of Movie
     * Seat Type of selected seat, Cinema Type (Premium / Regular) of selected Cinema
     * Movie Dimension (2D, 3D), Blockbuster status of movie.
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
    private MovieType.Dimension dim;
    private MovieType.Blockbuster blockbuster;

    /**
     * Constructor
     *
     * @param email          - User email for tracking
     * @param chosenCineplex - User's selected Cineplex to watch movie
     * @param cinema         - Which Cinema they are watching movie in
     * @param movie          - Which Movie they are watching
     * @param TID            - Transaction ID
     * @param seatID         - Seat ID
     * @param movieDateTime  - Date Time of movie
     * @param seattype       - Single / Double Seat
     * @param cinType        - Type of Cinema: Premium or Regular
     * @param dim            - Movie Dimension (2D / 3D)
     * @param blockbuster    If movie is a blockbuster
     */
    public MovieTicket(
            String email, String chosenCineplex, String cinema, String movie,
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

    /**
     * Get Method
     *
     * @return Chosen Cineplex
     */
    public String getChosenCineplex() {
        return this.ChosenCineplex;
    }

    /**
     * Get Method
     *
     * @return Chosen Movie
     */
    public String getChosenMovie() {
        return this.Movie;
    }

    /**
     * Get Method
     *
     * @return Chosen Cinema
     */
    public String getCinema() {
        return Cinema;
    }

    /**
     * Get Method
     *
     * @return - Selected Date and Time for Movie
     */
    public Date getShowtime() {
        return this.MovieDateTime;
    }

    /**
     * Get Method
     *
     * @return - Get Transaction ID
     */
    public String getTID() {
        return TID;
    }

    /**
     * Get Method
     *
     * @return - Get Seat ID
     */
    public String getSeatID() {
        return SeatID;
    }

    /**
     * Get Method
     *
     * @return - Get Type of Seat (Single / Double)
     */
    public IndividualSeats.SeatType getSeattype() {
        return seattype;
    }

    /**
     * Get Method
     *
     * @return - Get Type of Cinema (Premium / Regular)
     */
    public Cinema.CinemaType getCinematype() {
        return cinematype;
    }

    /**
     * Get Method
     *
     * @return - Get Blockbuster Status (Blockbuster / Not Blockbuster)
     */
    public MovieType.Blockbuster getBlockbuster() {
        return blockbuster;
    }

    /**
     * Get Method
     *
     * @return - Get Dimension of Movie (2D / 3D)
     */
    public MovieType.Dimension getDim() {
        return dim;
    }

    /**
     * This method prints the details of the ticket.
     */
    public void printTicket() {
        System.out.printf("\n\tEMAIL: %s\n", this.Email);
        System.out.printf("\tCINEPLEX: %s\n", this.ChosenCineplex);
        System.out.printf("\tCINEMA: %s\tTYPE: %s\n", this.Cinema, this.cinematype);
        System.out.printf("\tMOVIE: %s\tTYPE: %s\n", this.getChosenMovie(), this.dim);
        System.out.printf("\tMOVIE DATE:TIME: %s\n", this.getShowtime());
        System.out.printf("\tSEAT: %s\n", this.SeatID);
        System.out.printf("\tTransaction ID: %s\n\n", this.getTID());
    }
}