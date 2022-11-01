package UserInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import Cineplex.*;
import Movie.*;
import Service.TextDB;
import Service.DateTime;


public class CineplexUI {


    public static MovieTicket CineplexInterface(ArrayList<Cineplex> cineplexes) throws IOException {

        int choice = 0;
        Scanner sc = new Scanner(System.in);
        MovieTicket ticket = new MovieTicket();
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("\t%d: Cineplex Name: %s\n", i + 1, cineplexes.get(i).getCineplexName());
        }
        int selectCineplex = 0;
        int selectShowtime = 0;
        while (selectCineplex >= 0) {
            System.out.println("Select your Cineplex: ");
            selectCineplex = sc.nextInt() - 1;
            ticket.setChosenCineplex(cineplexes.get(selectCineplex)); // setting user's chosen Cineplex
            ArrayList<Movie> movielist = ticket.getChosenCineplex().getListOfMovies();
            if (movielist.size() == 0) {
                System.out.printf("%s is not showing any movies at this moment. Please select another Cineplex.\n\n", ticket.getChosenCineplex().getCineplexName());
                return  null;
            }
            else {
                for (int i = 0; i < movielist.size(); i++) {
                    System.out.printf("%d)\n", i + 1);
                    movielist.get(i).printMovieDetails();
                }
                System.out.println("Select your Movie from the list above: ");
                int selectMovie = sc.nextInt() - 1;
                ticket.setChosenMovie(movielist.get(selectMovie));

                //Ask for showtime
                while (selectShowtime >= 0) {
                    ArrayList<Cinema> listOfCinemas = ticket.getChosenCineplex().getListOfCinemas();
                    ArrayList<ShowTime> allST = new ArrayList<>();
                    ArrayList<Cinema> cinemas = new ArrayList<>();
                    //filter out showtime
                    for (Cinema c : listOfCinemas) {
                        ArrayList<ShowTime> listOfShowtime = c.getShowTime();
                        if (listOfShowtime.size() > 0) {
                            for (ShowTime st : listOfShowtime) {
                                if (Objects.equals(st.getMovie().getMovieTitle(), ticket.getChosenMovie().getMovieTitle())) {
                                    allST.add(st);
                                    cinemas.add(c);
                                }
                            }
                        } else {
                            System.out.printf("%s is no showtime for this movies. Please select another Cinema.\n\n", ticket.getChosenCineplex().getCineplexName());
                            return null;
                        }
                    }

                    for(int i = 0; i < listOfCinemas.size(); i++)
                    {
                        System.out.printf("%s %s , %s \n", i+1 , allST.get(i).getMovie().getMovieTitle() , DateTime.convertTime(allST.get(i).time.getTime()));
                    }

                    System.out.println("Select your Showtime from the list above: ");
                    selectShowtime = sc.nextInt() - 1;
                    ticket.setCinema(cinemas.get(selectShowtime));
                    ticket.setChosenMovie(allST.get(selectShowtime).getMovie());
                }
                movielist.get(selectMovie).increaseMovieTotalSale(); //increase sales of movie
                System.out.println("\nMoving to MovieUI!\n");
                ticket.setSeatID(MovieUI.MovieInterface(ticket)); // set ticket seats. change return type to ticket?
                // call movieUI
                break;
            }
        }
        return ticket;
    }
}
