package UserInterface;
import java.util.ArrayList;
import java.util.Scanner;
import Cineplex.Cineplex;
import Movie.*;

public class CineplexUI {
    public static MovieTicket CineplexInterface(ArrayList<Cineplex> cineplexes) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        MovieTicket ticket = new MovieTicket();
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("\t%d: Cineplex Name: %s\n", i + 1, cineplexes.get(i).getCineplexName());
        }
        int selectCineplex = 0;
        while (selectCineplex >= 0) {
            System.out.println("Select your Cineplex: ");
            selectCineplex = sc.nextInt() - 1;
            ticket.setChosenCineplex(cineplexes.get(selectCineplex)); // setting user's chosen Cineplex
            ArrayList<Movie> movielist = ticket.getChosenCineplex().getListOfMovies();
            if (movielist.size() == 0) {
                System.out.printf("%s is not showing any movies at this moment. Please select another Cineplex.\n\n", ticket.getChosenCineplex().getCineplexName());
            }
            else {
                for (int i = 0; i < movielist.size(); i++) {
                    System.out.printf("%d)\n", i + 1);
                    movielist.get(i).printMovieDetails();
                }
                System.out.println("Select your Movie from the list above: ");
                int selectMovie = sc.nextInt() - 1;
                ticket.setChosenMovie(movielist.get(selectMovie));
                System.out.println("\nMoving to MovieUI!\n");
                ticket.setSeatID(MovieUI.MovieInterface(ticket)); // set ticket seats. change return type to ticket?
                // call movieUI
                break;
            }
        }
        return ticket;
    }
}
