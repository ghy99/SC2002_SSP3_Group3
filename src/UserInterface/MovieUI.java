package UserInterface;
import java.util.Scanner;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

import Cineplex.Cinema;
import Movie.*;
import Service.*;





public class MovieUI {
    public static String MovieInterface(MovieTicket ticket) {
        // can be used to implement timing

        Scanner sc = new Scanner(System.in);

        ticket.getShowtime().printSeats();
       String movieseats;

        // display movie timings.
        // display movie seats.
        char row, col;
        do {
            System.out.println("Select your Movie Seats:");
            movieseats = sc.next();
            row = movieseats.charAt(0);
            col = movieseats.charAt(1);
        } while(ticket.getShowtime().checkSeats(row, Integer.parseInt(String.valueOf(col))) != 1);

    
        System.out.println("Selected movie seat ID(" + row +""+ col +")" );
        // return type ticket?
        return movieseats;
    }
}