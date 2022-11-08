package Movie;

import Cineplex.*;
import Service.GetNumberInput;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * @authors TAN JUE LIN, CHEW ZHI QI
 * This is the class for MovieSeats.
 * It initializes the movie seats in each cinema and displays the seats.
 */
public class MovieSeatsNew {
    /**
     * rows of seat matrix
     * columns of seat matrix
     * rows of double seats
     * aisle 1 row
     * aisle 2 row
     */
    int rows;
    int cols;
    int rowDoubleOne;
    int aisleOne;
    int aisleTwo;

    /**
     * 2D array to store all seats in array2D
     */
    ArrayList<ArrayList<IndividualSeats>> array2D = new ArrayList<ArrayList<IndividualSeats>>(rows);

    /**
     * Constructor method
     *
     * @param rows         - Row of Seat Matrix
     * @param cols         - Column of Seat Matrix
     * @param rowDoubleOne - Row of Double Seats
     * @param aisleOne     - Aisle 1
     * @param aisleTwo     - Aisle 2
     */
    public MovieSeatsNew(int rows, int cols, int rowDoubleOne, int aisleOne, int aisleTwo) {
        this.rows = rows;
        this.cols = cols;
        this.rowDoubleOne = rowDoubleOne;
        this.aisleOne = aisleOne;
        this.aisleTwo = aisleTwo;
    }

    /**
     * Get Method
     *
     * @return 2D Array of seats
     */
    public ArrayList<ArrayList<IndividualSeats>> getArray2D() {
        return array2D;
    }

    /**
     * This method creates each seat object in the 2D Array of seats.
     */
    public void SeatsCreation() {
        for (int j = 0; j < this.rows; j++) {
            ArrayList<IndividualSeats> rowSeatsDouble = new ArrayList<IndividualSeats>(); //new row array
            ArrayList<IndividualSeats> rowSeatsSingle = new ArrayList<IndividualSeats>();//new row array

            for (int i = 0; i < this.cols; i++) {
                if (j > this.rowDoubleOne) {
                    if (i == this.aisleOne || i == this.aisleTwo) {
                        IndividualSeats.SeatType aisleType = IndividualSeats.SeatType.Aisle;
                        IndividualSeats oneAisleSeat = new IndividualSeats("A1", aisleType, false);
                        rowSeatsDouble.add(oneAisleSeat);
                    } else {
                        IndividualSeats.SeatType doubleSeatType = IndividualSeats.SeatType.DoubleSeat;
                        IndividualSeats oneDoubleSeat = new IndividualSeats("A1", doubleSeatType, false);
                        rowSeatsDouble.add(oneDoubleSeat);
                    }
                } else {
                    if (i == this.aisleOne || i == this.aisleTwo) {
                        IndividualSeats.SeatType aisleType = IndividualSeats.SeatType.Aisle;
                        IndividualSeats oneAisleSeat = new IndividualSeats("A1", aisleType, false);
                        rowSeatsSingle.add(oneAisleSeat);
                    } else {
                        IndividualSeats.SeatType singleSeatType = IndividualSeats.SeatType.SingleSeat;
                        IndividualSeats oneSingleSeat = new IndividualSeats("A1", singleSeatType, false);
                        rowSeatsSingle.add(oneSingleSeat);
                    }
                }
            }
            if (j > rowDoubleOne) {
                array2D.add(rowSeatsDouble);
            } else {
                array2D.add(rowSeatsSingle);
            }
        }
        //For SeatID
        int endRowIDNum;
        char endRowIDChar;
        //Input SeatID of each seat
        for (int i = 0; i < this.array2D.size(); i++) { //each row
            endRowIDNum = (65 + this.rows - 1) - i; //go backwards from the last row
            endRowIDChar = (char) endRowIDNum;
            for (int j = 0; j < this.array2D.get(i).size(); j++) { //for each row, under each column
                String seatID = String.valueOf(endRowIDChar) + String.valueOf(j + 1);
                this.array2D.get(i).get(j).setSeatID(seatID);
            }
        }
    }

    /**
     * This method prints the seats in the Cinema.
     */
    public void PrintSeats() {
        for (int i = 0; i < this.array2D.size(); i++) {
            for (int j = 0; j < this.array2D.get(i).size(); j++) {

                if (this.array2D.get(i).get(j).getSeatOccupied() == true) {
                    System.out.print("| X |");
                } else {
                    if (Objects.equals(this.array2D.get(i).get(j).getSeatType(), IndividualSeats.SeatType.SingleSeat)) {
                        System.out.print("|" + this.array2D.get(i).get(j).getSeatID() + "|");
                    } else if (Objects.equals(this.array2D.get(i).get(j).getSeatType(), IndividualSeats.SeatType.Aisle)) {
                        System.out.print("| @ |");
                    } else { //pretty print double seats
                        if (j < this.aisleOne || j > this.aisleTwo) {
                            if (j % 2 == 0) {
                                System.out.print("|" + this.array2D.get(i).get(j).getSeatID() + " ");
                            } else {
                                System.out.print(" " + this.array2D.get(i).get(j).getSeatID() + "|");
                            }
                        } else {
                            if (j % 2 == 1) {
                                System.out.print("|" + this.array2D.get(i).get(j).getSeatID() + " ");
                            } else {
                                System.out.print(" " + this.array2D.get(i).get(j).getSeatID() + "|");
                            }
                        }
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("Legend:");
        System.out.print("|@| Aisle  ");
        System.out.print("|_| Single Seat Available  ");
        System.out.print("|_ _| Double Seat Available  ");
        System.out.print("|X| Seat Taken");
        System.out.println("");
    }

    /**
     * This method checks if the seat is available.
     *
     * @param seatID - Seat ID of Seat to be checked
     * @return True if seat is booked, False if seat is not booked.
     */
    public boolean CheckSeat(String seatID) {
        boolean bookedStatus = true;

        for (int i = 0; i < this.array2D.size(); i++) { //each row
            for (int j = 0; j < this.array2D.get(i).size(); j++) { //for each row, under each column
                if (!((this.array2D.get(i).get(j).getSeatType()).equals(IndividualSeats.SeatType.Aisle))
                        && (this.array2D.get(i).get(j).getSeatID()).equals(seatID)
                        && this.array2D.get(i).get(j).getSeatOccupied() == false) {

                    if (this.array2D.get(i).get(j).getSeatType().equals(IndividualSeats.SeatType.SingleSeat)) {
                        System.out.println("One Single Seat '" + seatID + "' is available");
                    } else {
                        System.out.println("One Double Seat '" + seatID + "' is available");
                    }
                    bookedStatus = false;
                }
            }
        }
        return bookedStatus;
    }

    /**
     * This method selects the seats chosen and returns an ArrayList of seats chosen.
     *
     * @return An ArrayList of Seat ID as String
     */
    public ArrayList<String> SelectSeats() {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> seatsSelected = new ArrayList<String>();
        int num = -1;
        do {
            System.out.println("\nPlease input the number of seats to be selected:");
            num = GetNumberInput.getInt(1, 99, -1);
            if (num == -1) continue;
            else break;
        } while (num == -1);
        int i = 0;
        boolean isSeat = true;

        while (i < num) {
            String SeatID = "";
            while (isSeat) {
                System.out.println("Select a seat:");
                SeatID = scan.nextLine();
                if (SeatID.length() == 2 || SeatID.length() == 3) {
                    String right = right(SeatID, SeatID.length() - 1);
                    if (Character.isAlphabetic(SeatID.charAt(0))) {
                        if ((int) SeatID.charAt(0) > 64 && (int) SeatID.charAt(0) <= (65 + this.rows - 1)) {
                            if (Integer.parseInt(right) >= 0 && Integer.parseInt(right) <= this.cols) {
                                isSeat = false;
                            }
                        }
                    }
                }
                if (isSeat) {
                    System.out.print("Invalid seat!");
                }
            }
            Boolean isSeatAva = true;
            while (isSeatAva) {//seat ava
                if (!CheckSeat(SeatID)) {
                    i++;
                    System.out.println("\n" + SeatID + " is selected.");
                    seatsSelected.add(SeatID);
                    isSeat = true;
                    isSeatAva = false;
                } else {
                    System.out.println("\n" + SeatID + " is not available. Please select another seat.");
                    SeatID = scan.nextLine();
                }
            }
        }
        System.out.println("The seats selected are:");
        for (int j = 0; j < num; j++) {
            System.out.printf("\t%s\n", seatsSelected.get(j));
        }
        return seatsSelected;
    }

    /**
     * Return Number part of the Seat.
     *
     * @param value  - Seat ID.
     * @param length - Length of Seat ID minus alphabet of Seat ID.
     * @return Number of the Seat.
     */
    public static String right(String value, int length) {
        // To get right characters from a string, change beginIndex.
        return value.substring(value.length() - length);
    }

    /**
     * Check if seat ID has been added into the booked list of seats previously.
     *
     * @param c           - Column of Seat Array
     * @param d           - Row of Seat Array
     * @param overallList - ArrayList of booked Seats.
     * @return
     */
    public ArrayList<String> checkCounter(int c, int d, ArrayList<String> overallList) {
        boolean flagOne = false;
        for (String s : overallList) {
            if (this.array2D.get(c).get(d).getSeatID().equals(s)) {
                flagOne = true;
                break;
            }
        }
        if (flagOne == false) {
            overallList.add(this.array2D.get(c).get(d).getSeatID());
        }
        return overallList;
    }

    /**
     * This method books the seat and writes to database that seat is selected.
     *
     * @param seatsSelected - ArrayList of Seats selected.
     * @param isWrite       - If true, update database, else, used to check which seat was booked.
     * @param cinema        - Cinema that Seat is stored.
     * @return An ArrayList of Seats that were booked.
     * @throws IOException Checks if Cinema Seat exist in database.
     */
    public ArrayList<String> BookSeats(ArrayList<String> seatsSelected, Boolean isWrite, Cinema cinema) throws IOException {
        int endRowIDNum;
        char endRowIDChar;
        endRowIDNum = (65 + this.rows - 1);
        endRowIDChar = (char) endRowIDNum; //changes for when the number of rows changes
        ArrayList<String> overallList = new ArrayList<String>();

        for (int k = 0; k < seatsSelected.size(); k++) {
            char rowLetterChar = seatsSelected.get(k).charAt(0);
            int c = (endRowIDChar - '0') - (rowLetterChar - '0'); //gives the relative position
            String colnum = right(seatsSelected.get(k), seatsSelected.get(k).length() - 1);
            int colnumInt = Integer.parseInt(colnum) - 1;
            //checks if each seat selected is a doubleseat
            if (Objects.equals(this.array2D.get(c).get(colnumInt).getSeatType(), IndividualSeats.SeatType.DoubleSeat)) {
                if (colnumInt < aisleOne || colnumInt > aisleTwo) {  //if its a doubleseat, check if its before or after aisle
                    if (colnumInt % 2 == 0) {
                        this.array2D.get(c).get(Integer.parseInt(colnum) - 1).setOccupied(true); //if double seat, then need cross two consecutive seats
                        this.array2D.get(c).get(Integer.parseInt(colnum)).setOccupied(true);
                        overallList = checkCounter(c, Integer.parseInt(colnum) - 1, overallList);
                        overallList = checkCounter(c, Integer.parseInt(colnum), overallList);
                    } else {
                        this.array2D.get(c).get(Integer.parseInt(colnum) - 1).setOccupied(true); //if double seat, then need cross two consecutive seats
                        this.array2D.get(c).get(Integer.parseInt(colnum) - 1 - 1).setOccupied(true);
                        overallList = checkCounter(c, Integer.parseInt(colnum) - 1, overallList);
                        overallList = checkCounter(c, Integer.parseInt(colnum) - 1 - 1, overallList);
                    }
                } else {
                    if (colnumInt % 2 == 1) {
                        this.array2D.get(c).get(Integer.parseInt(colnum) - 1).setOccupied(true); //if double seat, then need cross two consecutive seats
                        this.array2D.get(c).get(Integer.parseInt(colnum)).setOccupied(true);
                        overallList = checkCounter(c, Integer.parseInt(colnum) - 1, overallList);
                        overallList = checkCounter(c, Integer.parseInt(colnum), overallList);

                    } else {
                        this.array2D.get(c).get(Integer.parseInt(colnum) - 1).setOccupied(true); //if double seat, then need cross two consecutive seats
                        this.array2D.get(c).get(Integer.parseInt(colnum) - 1 - 1).setOccupied(true);
                        overallList = checkCounter(c, Integer.parseInt(colnum) - 1, overallList);
                        overallList = checkCounter(c, Integer.parseInt(colnum) - 1 - 1, overallList);
                    }
                }
            } else { //else means a single seat is selected
                this.array2D.get(c).get(Integer.parseInt(colnum) - 1).setOccupied(true);
                overallList = checkCounter(c, Integer.parseInt(colnum) - 1, overallList);
            }
        }
        if(isWrite) {
            TextDB.UpdateToTextDB(cinema.getCinemaDir(),cinema.getShowTime(), MovieType.Dimension.THREE_D);
        }
        return overallList;
    }
}