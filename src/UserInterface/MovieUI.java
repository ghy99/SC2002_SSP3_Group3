package UserInterface;
import java.util.ArrayList;
import java.util.Scanner;
import Cineplex.*;
import Movie.*;
import Service.*;



public class MovieUI {
    public static void MovieInterface(MovieTicket ticket) {
        // Calling of cineplex and movie is in CineplexUI.java
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Selected Cineplex: " + ticket.getChosenCineplex()); 
        System.out.println("Selected Movie: " + ticket.getChosenMovie());
        

        Cinema c;
        ArrayList<ShowTime> showTime = c.getShowTime(ticket.getChosenCineplex(), ticket.getChosenMovie());


        for (int i = 0; i < showTime.size(); i++) {
            System.out.printf("%d)\n", i + 1);
            showTime.get(i).printMovieTiming(); // havent print this shit
        }



        System.out.println("Please select your timing from the list above: " );
        int selectTiming = sc.nextInt()- 1; 

        ticket.setMovieDateTime(showTime.get(selectTiming));// store Timing into ticket. 

        //print for movieSeats



        System.out.println("Please select your movie seats from the list above: ");
        int selectedSeats = sc.nextInt();
        ticket.setMovieSeats(movieSeats.get(selectSeats));
        // store Seats into ticket
        return ticket;


        // can be used to implement timing




    }
}
