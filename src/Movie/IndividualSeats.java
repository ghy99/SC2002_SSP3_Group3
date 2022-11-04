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
        DoubleSeat("Double");
        public final String SeatType;
        SeatType(String single) {
            this.SeatType = single;
        }

        @Override
        public String toString() { return SeatType; };
    }

    /**
     * SeatID is the seatID required for booking.
     * seatType shows if the seat is a single or a double seat.
     * isOccupied shows if the seat has been booked.
     */
    private String seatID;
    private SeatType seatType;

    private boolean isOccupied;

    public IndividualSeats (String seatid, SeatType seatType, boolean occupied) {
        this.seatID = seatid;
        this.seatType = seatType;
        this.isOccupied = occupied;

    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    public String getSeatID() {
        return seatID;
    }
    public SeatType getSeatType() {
        return seatType;
    }
    public boolean getSeatOccupied() { return isOccupied; };
}
