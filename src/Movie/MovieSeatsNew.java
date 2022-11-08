
package Movie;

// Draw out matrix for movie theatres
// Need to block out rows / columns for aisle
// Different Seat Types such as
// Wheelchair
// Free Single seat
// Occupied Single seat
// Free Couple seat
// Occupied Couple seat
// Elite seats
// Gold / Platinum / Ultima(?) seats
// System does not allow unoccupied seats between selected seats


import Cineplex.*;
import Service.GetNumberInput;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Jue Lin
 * This is the class for MovieSeats. It initializes the movie seats in each cinema and displays the seats.
 */
public class MovieSeatsNew {

    int rows;
    int cols;
    int rowDoubleOne;
    int aisleOne;
    int aisleTwo;


    //2D array to store all seats in array2D
    ArrayList<ArrayList<IndividualSeats>> array2D = new ArrayList<ArrayList<IndividualSeats>>(rows);


    public MovieSeatsNew(int rows, int cols, int rowDoubleOne, int aisleOne, int aisleTwo) {
        this.rows = rows;
        this.cols = cols;
        this.rowDoubleOne = rowDoubleOne;
        this.aisleOne = aisleOne;
        this.aisleTwo = aisleTwo;

    }

    public ArrayList<ArrayList<IndividualSeats>> getArray2D() {
        return array2D;
    }

    public void SeatsCreation() {

        //Create individual seats in 2D array
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


    public void PrintSeats() {

        int endRowIDNum;
        char endRowIDChar;

        for (int i = 0; i < this.array2D.size(); i++) {

            endRowIDNum = (65 + this.rows - 1) - i;
            endRowIDChar = (char) endRowIDNum;

            for (int j = 0; j < this.array2D.get(i).size(); j++) {

                if (this.array2D.get(i).get(j).getSeatOccupied() == true) {
                    System.out.print("|X|");
                } else {
                    if (Objects.equals(this.array2D.get(i).get(j).getSeatType(), IndividualSeats.SeatType.SingleSeat)) {

                        System.out.print("|" + this.array2D.get(i).get(j).getSeatID() + "|");
                    } else if (Objects.equals(this.array2D.get(i).get(j).getSeatType(), IndividualSeats.SeatType.Aisle)) {
                        System.out.print("|@|");
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

//    public boolean isDouble(String seatID){
//
//
//        int i = (65+ this.rows-1) - seatID.charAt(0);
//        int j = Integer.parseInt(seatID.charAt(1))-1;
//
//        if((this.array2D.get(i).get(j).getSeatType()).equals(IndividualSeats.SeatType.SingleSeat)){
//            //System.out.println("One Single Seat '" + seatID+ "' is available");
//            return false;
//        } else {
//            //System.out.println("One Double Seat '"+ seatID+ "' is available");
//            return true;
//        }
//    }

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

    public ArrayList<String> SelectSeats() {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> seatsSelected = new ArrayList<String>();
        System.out.println("Please input number of seats to be select");
        int num = GetNumberInput.getInt(1, 99, -1);
        int i = 0;
        boolean isSeat = true;

        while (i < num) {
            String SeatID = "";

            while (isSeat) {
                System.out.println("Select a seat");
                SeatID = scan.nextLine();
                if (SeatID.length() == 2 || SeatID.length() == 3) {
                    String right = right(SeatID, SeatID.length() - 1);
                    if (Character.isAlphabetic(SeatID.charAt(0))) {
                        if ((int) SeatID.charAt(0) > 64 && (int) SeatID.charAt(0) <= (65 + this.rows - 1)) {
                            if (Integer.parseInt(right) >= 0 && Integer.parseInt(right) < this.cols) {
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
                    System.out.println(SeatID + " is selected");
                    seatsSelected.add(SeatID);
                    isSeat = true;
                    isSeatAva = false;
                } else {
                    System.out.println(SeatID + " is not available. Please reselect another seat");
                    SeatID = scan.nextLine();
                }
            }
        }

        System.out.println("The seats selected are:");
        for (int j = 0; j < num; j++) {
            System.out.print(seatsSelected.get(j) + " ");
        }

        return seatsSelected;
    }

    public static String right(String value, int length) {
        // To get right characters from a string, change the begin index.
        return value.substring(value.length() - length);
    }


    public ArrayList<String> checkCounter(int c, int d, ArrayList<String> overallList) {

        boolean flagOne = false;
        for (int i = 0; i < overallList.size(); i++) {
            if (this.array2D.get(c).get(d).getSeatID().equals(overallList.get(i))) {
                flagOne = true;
                break;
            }
        }
        if (flagOne == false) {
            overallList.add(this.array2D.get(c).get(d).getSeatID());
        }

        return overallList;
    }


    public ArrayList<String> BookSeats(ArrayList<String> seatsSelected, Boolean isWrite, Cinema cinema) throws IOException {

        int endRowIDNum;
        char endRowIDChar;
        endRowIDNum = (65 + this.rows - 1);
        endRowIDChar = (char) endRowIDNum; //changes for when the number of rows changes

        ArrayList<String> overallList = new ArrayList<String>();


        for (int k = 0; k < seatsSelected.size(); k++) {
            char rowLetterChar = seatsSelected.get(k).charAt(0);
            String rowLetterString = String.valueOf(rowLetterChar);
            //System.out.println("row Letter" + String.valueOf(rowLetterString));

            int c = (endRowIDChar - '0') - (rowLetterChar - '0'); //gives the relative position
            //System.out.println("row Letter" + c);

            String colnum = right(seatsSelected.get(k), seatsSelected.get(k).length() - 1);
            //System.out.println("HERE"+colnum);
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
            System.out.println("Writing seats to DB!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            TextDB.UpdateToTextDB(cinema.getCinemaDir(), this, cinema.getShowTime());
        }
        return overallList; //returns all the seats that are booked.
        // OverallList and seatsSelected lists are different.
        // OverallList includes the counterparts of the doubleseats that was not taken into account when booking
    }


    public static void main(String[] args) {
        MovieSeatsNew movieSeatsNew = new MovieSeatsNew(5, 5, 4, 2, 3);
        movieSeatsNew.SeatsCreation();
        movieSeatsNew.PrintSeats();

        System.out.println(movieSeatsNew.SelectSeats());

    }


}












