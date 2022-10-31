package UserInterface;
import java.util.Scanner;
import Movie.*;

public class MovieUI {
    public static void MovieInterface(MovieTicket ticket) {
        // can be used to implement timing
        System.out.printf("You have selected the Movie: %s\n", ticket.getChosenMovie());
        System.out.println("Select the Movie Timing that you prefer:");
        // display movie timings.
        System.out.println("Select your Movie Seats:");
        // display movie seats.
        System.out.println("Selected movie timing of (Insert Movie Timing)");
        System.out.println("Selected movie seat ID (Insert Seat ID)");
        // return type ticket?
    }
}
