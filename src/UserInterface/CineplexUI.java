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
        System.out.println("Select your Cineplex: ");
        int selectCineplex = sc.nextInt() - 1;
        ticket.setChosenCineplex(cineplexes.get(selectCineplex)); // setting user's chosen Cineplex
        ArrayList<Movie> movielist = ticket.getChosenCineplex().getListOfMovies();
        for (int i = 0; i < movielist.size(); i++) {
            System.out.printf("%d)\n", i + 1);
            movielist.get(i).printMovieDetails();
        }
        System.out.println("Select your Movie from the list above: ");
        int selectMovie = sc.nextInt() - 1;
        ticket.setChosenMovie(movielist.get(selectMovie));
        return ticket;
    }
}
