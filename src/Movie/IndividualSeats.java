package Movie;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

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

        //@Override
        public String SeatTypeToString() { return SeatType; };
    }

//    public enum rowID{
//        A(1),B(2),C(3),D(4),E(5),F(6),G(7),H(8),I(9),J(10),K(11),L(12),M(13);
//    }

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


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input row then column, only EVEN NUM PLSSSS");
        int rows = scan.nextInt();
        int cols = scan.nextInt();
        System.out.println("starting from what row you want double seats");
        int rowDoubleOne = scan.nextInt(); //make this versatile LATER


        System.out.println("what column you want aisle"); //asileOne must be from 3 columns onwards
        int aisleOne = scan.nextInt()-1;
        System.out.println("select another column you want aisle"); //asileTwo must be second last 3rd column
        int aisleTwo = scan.nextInt()-1;

        //Create one "SingleSeat" individual seat
        SeatType singleSeatType = SeatType.SingleSeat;
        IndividualSeats oneSingleSeat = new IndividualSeats("A1",singleSeatType,false);

        //Create one "DoubleSeat" individual seat
        SeatType doubleSeatType = SeatType.DoubleSeat;
        IndividualSeats oneDoubleSeat = new IndividualSeats("A1",doubleSeatType,false);

        SeatType aisleType = SeatType.Aisle;
        IndividualSeats oneAisleSeat = new IndividualSeats("A1",aisleType,false);


        //Create one huge ass cinema seats
        ArrayList<ArrayList<IndividualSeats>> array2D = new ArrayList<ArrayList<IndividualSeats>>(rows);


        ArrayList<IndividualSeats> rowSeatsSingle = new ArrayList<IndividualSeats>();
        ArrayList<IndividualSeats> rowSeatsDouble = new ArrayList<IndividualSeats>();
//        row.add(seat);
//        row.add(seat);
//        array2D.add(row);


        //ltr then play with double seats!!!!!!! so if i equal a certain column num specified, rowSeats.add(oneDoubleSeat)

        //create one row of single seats
        for (int i = 0; i<cols; i++){

            if(i==aisleOne || i ==aisleTwo){
                rowSeatsSingle.add(oneAisleSeat);
            } else{
                rowSeatsSingle.add(oneSingleSeat);;
            }

        }
        // create one row of double seats
        for (int i = 0; i<cols; i++){

            if(i==aisleOne || i ==aisleTwo){
                rowSeatsDouble.add(oneAisleSeat);
            } else{
                rowSeatsDouble.add(oneDoubleSeat);;
            }

        }

        for (int i = 0; i<rows ; i++){ //dont need to change this, this one jus add all rows tgt
            if(i>=rowDoubleOne){ //need to change this later
                //System.out.println("ANY");
                array2D.add(rowSeatsDouble);
            }
            else {
                array2D.add(rowSeatsSingle);
            }
        }

        //Used in printing
        System.out.print("  ");
        for (int i = 0; i<rows+2;i++){
            if(i==10){
                System.out.print((i+1));
            } else {
                System.out.print(" " + (i + 1) + " ");
            }
        }
        System.out.println("");

        //


        int endRowIDNum;
        char endRowIDChar;


        //last row is the double seats
        for (int i = 0; i < array2D.size() ; i++) { //each row

            endRowIDNum = (65+ rows-1) - i; //go backwards from the last row
            endRowIDChar = (char)endRowIDNum;
            System.out.print(endRowIDChar+" ");


            for (int j = 0; j < array2D.get(i).size(); j++) { //with each row, each column

                if(Objects.equals(array2D.get(i).get(j).getSeatType(),SeatType.SingleSeat)){

                    array2D.get(i).get(j).setSeatID(String.valueOf(endRowIDChar)+String.valueOf(j+1));
                    System.out.print("|"+array2D.get(i).get(j).getSeatID()+"|");
                    //System.out.print("|_|");
                } else if(Objects.equals(array2D.get(i).get(j).getSeatType(),SeatType.Aisle)){
                    System.out.print("|@|");
                } else { //pretty print double seats
                    array2D.get(i).get(j).setSeatID(String.valueOf(endRowIDChar)+String.valueOf(j+1));
                    if(j<aisleOne||j>aisleTwo){
                        if(j%2==0){
                            System.out.print("|"+array2D.get(i).get(j).getSeatID()+" ");
                        } else {
                            System.out.print(" "+array2D.get(i).get(j).getSeatID()+"|");
                        }
                    } else {
                        if(j%2==1){
                            System.out.print("|"+array2D.get(i).get(j).getSeatID()+" ");
                        } else {
                            System.out.print(" "+array2D.get(i).get(j).getSeatID()+"|");
                        }
                    }

//                    if(j<aisleOne||j>aisleTwo){
//                        if(j%2==0){
//                            System.out.print("|+ ");
//                        } else {
//                            System.out.print(" +|");
//                        }
//                    } else {
//                        if(j%2==1){
//                            System.out.print("|+ ");
//                        } else {
//                            System.out.print(" +|");
//                        }
//                    }

                }

            }
            System.out.println("");

        }


    }
}
