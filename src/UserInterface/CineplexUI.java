package UserInterface;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
        ArrayList<ShowTime> allST = new ArrayList<>();
        ArrayList<Cinema> cinemas = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        MovieTicket ticket = new MovieTicket();
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("\t%d: Cineplex Name: %s\n", i + 1, cineplexes.get(i).getCineplexName());
        }
        int selectCineplex = 0;
        int selectShowtime = 0;
        int selectDate = 0;

        while (selectCineplex >= 0) {
            System.out.println("Select your Cineplex: ");
            selectCineplex = sc.nextInt() - 1;
            ticket.setChosenCineplex(cineplexes.get(selectCineplex).getCineplexName()); // setting user's chosen Cineplex
            ArrayList<Movie> movielist = cineplexes.get(selectCineplex).getListOfMovies();
            if (movielist.size() == 0) {
                System.out.printf("%s is not showing any movies at this moment. Please select another Cineplex.\n\n", cineplexes.get(selectCineplex).getCineplexName());
//                return  null;
            }
            else {
                for (int i = 0; i < movielist.size(); i++) {
                    System.out.printf("%d)\n", i + 1);
                    movielist.get(i).printMovieDetails();
                }
                System.out.println("Select your Movie from the list above: ");
                int selectMovie = sc.nextInt() - 1;

                //Ask for showtime
                while (selectShowtime >= 0) {
                    ArrayList<Cinema> listOfCinemas = cineplexes.get(selectCineplex).getListOfCinemas();
                    //filter out showtime
                    for (Cinema c : listOfCinemas) {
                        ArrayList<ShowTime> listOfShowtime = c.getShowTime();
                        if (listOfShowtime.size() > 0) {
                            for (ShowTime st : listOfShowtime) {
                                if (Objects.equals(st.getMovie().getMovieTitle(), movielist.get(selectMovie).getMovieTitle())) {
                                    allST.add(st);
                                    cinemas.add(c);
                                }
                            }
                        }
                    }
                    if (allST.size() > 0)
                    {
                        for(int i = 0; i < allST.size(); i++)
                        {
                            System.out.printf("%s) %s %s %s\n", i+1, cinemas.get(i).getCinemaName(), allST.get(i).getMovie().getMovieTitle() , DateTime.convertDate( allST.get(i).getTime().getTime()));
                        }
                    }else {
                        System.out.printf("%s is no showtime for this movies. Please select another Cinema.\n\n", cineplexes.get(selectCineplex).getCineplexName());
                        return null;
                    }

                    System.out.println("Select your date from the list above: ");
                    selectDate = sc.nextInt() - 1;
                    String selectedDate = DateTime.convertDate(allST.get(selectDate).getTime().getTime());


                    if (allST.size() > 0)
                    {
                        for(int i = 0; i < allST.size(); i++)
                        {
                            if (Objects.equals(selectedDate, DateTime.convertDate( allST.get(i).getTime().getTime()))){
                            System.out.printf("%s) %s %s %s\n", i+1, cinemas.get(i).getCinemaName(), allST.get(i).getMovie().getMovieTitle() , DateTime.convertTime( allST.get(i).getTime().getTime()));
                        }}
                    }else {
                        System.out.printf("%s is no showtime for this movies. Please select another Cinema.\n\n", cineplexes.get(selectCineplex).getCineplexName());
                        return null;
                    }


                    System.out.println("Select your Showtime from the list above: ");
                    selectShowtime = sc.nextInt() - 1;
                    ticket.setCinema(cinemas.get(selectShowtime).getCinemaName());
                    ticket.setChosenMovie(allST.get(selectShowtime).getMovie().getMovieTitle());
                    ticket.setMovieDateTime(DateTime.convertTime(allST.get(selectShowtime).getTime().getTime()));
                    ticket.setTID(new DateTime().ToTID(cinemas.get(selectShowtime).getCinemaCode()));
                    break;
                }
                movielist.get(selectMovie).increaseMovieTotalSale(); //increase sales of movie
                System.out.println("\nMoving to MovieUI!\n");
                ticket.setSeatID(MovieUI.MovieInterface(ticket , allST.get(selectShowtime))); // set ticket seats. change return type to ticket?

                TextDB.UpdateToTextDB(File.separator + cineplexes.get(selectCineplex).getCineplexName().replace(' ','_')+ File.separator+cinemas.get(selectShowtime).getCinemaName()+".txt" ,
                        movielist.get(selectMovie) , cinemas.get(selectShowtime).getShowTime());

                break;
            }
        }
        return ticket;
    }
}