package UserInterface;
import java.util.Scanner;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

import Cineplex.Cinema;
import Cineplex.ShowTime;
import Movie.*;
import Service.*;

/**
 * This is the Movie User Interface Class. Imported to call interface to get customer to select their seat
 * @author EDDY CHENG KUAN QUAN, CHEW ZHI QI
 */
public class MovieUI {

    public static String MovieInterface(MovieTicket ticket , ShowTime showTime) {
        // can be used to implement timing

        Scanner sc = new Scanner(System.in);

//        showTime.printSeats();
       String movieseats;

        // display movie timings.
        // display movie seats.
//        char row, col;
//        do {
//            System.out.println("Select your Movie Seats:");
//            movieseats = sc.next();
//            row = movieseats.charAt(0);
//            col = movieseats.charAt(1);
//        } while(showTime.checkSeats(row, Integer.parseInt(String.valueOf(col))) != 1);

    
//        System.out.println("Selected movie seat ID(" + row +""+ col +")" );
//        // return type ticket?
//        return movieseats;
        return null;
    }
}