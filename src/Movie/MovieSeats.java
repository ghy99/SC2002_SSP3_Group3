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


import Cineplex.Cinema;
import Cineplex.ShowTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MovieSeats  {
   
  //enum Name = {PLATINUM, 2D};
  private int row;
  private int col;
  private int aisleOne;
  private int aisleTwo;
  private String[][] seats; //seat layout in 2D array
  private int startRow; //for row labels

  public MovieSeats(int row, int col, int aisleOne, int aisleTwo) {
    this.row = row+1; //additional 1 row for row labels
    this.col = col+3; //additional 3 col: 2 for col labels, 1 for layout
    this.aisleOne = aisleOne+1;
    this.aisleTwo = aisleTwo+1;
    this.seats = new String[this.row][this.col];
    this.startRow = 65+row-1;
  }

  public MovieSeats(int row, int col, int[] aisle) {
    this.row = row; //additional 1 row for row labels
    this.col = col; //additional 3 col: 2 for col labels, 1 for layout
    this.aisleOne = aisleOne;
    this.aisleTwo = aisleTwo;
    this.startRow = 65+row-1;
  }

     public MovieSeats() {

     }

     public void seatsCreation(){ //Set up seats layout.
    
    //ClassOfCinema class calls movieSeat.seatsCreation() in Main
    //eg: ClassOfCinema Platinum = new ClassOfCinema(PLATINUM)
    //    Platinum.createSeats()
    //    Under ClassOfCinema, public void createSeats{ seats.seatsCreation() }
    //    Even though we only really create seats after ClassOfCinema object is created by calling its own methods -> its still composition association, since MovieSeats object is created when ClassOfCinema object is created

    //store row labels (A,B,C,D ...)
    for(int i=1; i<this.row; i++) {
      char charStartRow = (char) this.startRow; //convert int startRow to char
      this.seats[i][0] = Character.toString(charStartRow); //convert to string in order to store in Seats array
      this.seats[i][this.col - 1] = seats[i][0];
      startRow--;
    }
    
    //store col labels (1,2,3,4...)
    for(int j=1; j<this.col-2; j++) {
      if(j>9){
        this.seats[0][j] = " " + Integer.toString(j) + ""; 
      } else {
        this.seats[0][j] = " " + Integer.toString(j) + " "; }
      
    }
    
    //create labels for seats
    for(int i=1; i<this.row; i++) {
      for (int j = 1; j < this.col - 1; j++) {
        this.seats[i][j] = "  |";
      }
    }
    
    //create aisle (must be after creating labels for seats). 
    //set col 3 and 6 as aisle
    for(int i=1; i<this.row; i++) {
      this.seats[i][aisleOne] = "@ |";
      this.seats[i][aisleTwo] = "@ |";
    }

    if(this.row==6){
      //this.seats[4][4] = "   ";
      this.seats[4][5] = "   ";
    }
  }



  public void printSeats(){
    System.out.println("        |SCREEN|          ");
    System.out.println("-----------------------");

    for (int i=0; i<this.row; i++) {
      for (int j=0; j<this.col; j++) {
        if(this.seats[i][j] == null){
          System.out.print("    ");
        }
        else {
            System.out.print(this.seats[i][j] );
        }
    }
      System.out.println();
      System.out.println("-----------------------");
    }

    System.out.println("        |ENTRANCE|          ");
    System.out.println("-----------------------");
    System.out.println("");
    System.out.println("Legend:");
    System.out.print("|@| Aisle  ");
    System.out.print("| | Available  ");
    System.out.print("|X| Seat Taken");
    System.out.println("");
    System.out.println("");
  }

  public int checkSeats(char i, int j){

    //printSeats(); //show current seats available
    //int rows = this.row -(i - '0'-16); //i-'0' convert to integer; '0' = 48; -16 to ...
    int rows = (17+this.row-1) - (i - '0');
    if (this.seats[rows][j+1] == " X|" || this.seats[rows][j+1] == "@ |"){
      printSeats();
      System.out.println("Sorry, seat: " + i + j + " is not available.");
      return 0; //return 0 to calling method (from classOfCinema class), which will ask user to re-select another Seat
      } else {
      this.seats[rows][j+1] = " X|"; //mark seat as booked
      System.out.println("Updated seats:");
      printSeats();
      System.out.println("Successfully booked seat: " + i + j + ".Thank you!");
      System.out.println("");
      return 1; //return 1 to calling method to say seat is successfully booked
      }
	}

  public String[][] getSeats() {
    return seats;
  }

  protected void setSeats(String[][] seats) {
    this.seats = seats;
  }
}
	




  

  