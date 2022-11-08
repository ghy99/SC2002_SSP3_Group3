package Movie;

/**
 * @author Gan Hao Yi
 * This is the object class for each Seat in the Cinema.
 */
public class IndividualSeats {
    /**
     * This variable is an Enum to store the type of seat.
     */
    public enum SeatType {
        SingleSeat("Single"),
        DoubleSeat("Double"),
        Aisle("Aisle");
        public final String SeatType;

        private SeatType(String SeatType) {
            this.SeatType = SeatType;
        }
    }

    /**
     * SeatID is the seatID required for booking.
     * seatType shows if the seat is a single or a double seat.
     * isOccupied shows if the seat has been booked.
     */
    private String seatID;
    private SeatType seatType;
    private boolean isOccupied;

    /**
     * Constructor for each Individual Seat.
     *
     * @param seatid   - Seat ID of this seat.
     * @param seatType - Seat Type of this seat.
     * @param occupied - If Seat is occupied.
     */
    public IndividualSeats(String seatid, SeatType seatType, boolean occupied) {
        this.seatID = seatid;
        this.seatType = seatType;
        this.isOccupied = occupied;
    }

    /**
     * Set Method
     *
     * @param seatID - Seat ID of this Seat.
     */
    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    /**
     * Set Method
     *
     * @param occupied - Occupancy of this Seat.
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Get Method
     *
     * @return - Seat ID
     */
    public String getSeatID() {
        return seatID;
    }

    /**
     * Get Method
     *
     * @return Seat Type of this Seat (Single / Double)
     */
    public SeatType getSeatType() {
        return seatType;
    }

    /**
     * Get Method
     *
     * @return - Get Status of this Seat (Occupied / Not Occupied)
     */
    public boolean getSeatOccupied() {
        return isOccupied;
    }
}