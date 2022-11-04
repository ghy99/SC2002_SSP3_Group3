package UserInterface;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import Cineplex.*;
import Movie.*;
import Service.GetNumberInput;
import Service.TextDB;
import Service.DateTime;


public class CineplexUI {


    public static MovieTicket CineplexInterface(AllCineplex cineplexes) throws IOException {

        int choice = 0;
        ArrayList<ShowTime> allST = new ArrayList<>();
        ArrayList<Cinema> cinemas = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        MovieTicket ticket = new MovieTicket();
        for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
            System.out.printf("\t%d: Cineplex Name: %s\n", i + 1, cineplexes.getCineplexes().get(i).getCineplexName());
        }
        int selectCineplex = 0;
        int selectShowtime = 0;
        int selectDate = 0;

        while (selectCineplex >= 0) {
            System.out.println("Select your Cineplex: ");
            selectCineplex = sc.nextInt() - 1; // error checking for correct cineplex
            ticket.setChosenCineplex(cineplexes.getCineplexes().get(selectCineplex).getCineplexName()); // setting user's chosen Cineplex
            ArrayList<Movie> movielist = cineplexes.getListOfMovies();
            if (movielist.size() == 0) {
                System.out.printf("%s is not showing any movies at this moment. Please select another Cineplex.\n\n", cineplexes.getCineplexes().get(selectCineplex).getCineplexName());
//                return  null;
            }
            else {
                for (int i = 0; i < movielist.size(); i++) {
                    System.out.printf("%d)\n", i + 1);
                    movielist.get(i).printMovieDetails();
                }
                System.out.println("Select your Movie from the list above: ");
                int selectMovie = GetNumberInput.getInt() - 1;

                //Ask for showtime
                while (selectShowtime >= 0) {
                    ArrayList<Cinema> listOfCinemas = cineplexes.getCineplexes().get(selectCineplex).getListOfCinemas();
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


                    String selectedDate = "";
                    int dateChecking = -1;
                    while(dateChecking == -1) {
                        ArrayList<String> STDates = new ArrayList<>();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
                        LocalDate STDate = java.time.LocalDate.now();
                        for (int d = 0; d < 7; d++) {
                            System.out.printf("%s) %s\n", d + 1, formatter.format(STDate));
                            STDates.add(formatter.format(STDate));
                            STDate = STDate.plusDays(1);
                        }
                        System.out.println("Select your date from the list above: ");
                        selectDate = sc.nextInt() - 1;
                        selectedDate = STDates.get(selectDate);

                            for (int z = 0; z < allST.size(); z++) {
                                if (Objects.equals(selectedDate, DateTime.convertDate(allST.get(z).getTime().getTime()))) {
                                    dateChecking++;
                                    break;
                                }
                            }

                            if(dateChecking == -1) {
                                System.out.printf("%s no showtime for this movies on %s. Please select another Date.\n\n", cineplexes.getCineplexes().get(selectCineplex).getCineplexName(), selectedDate);
                            }

                    }

                    
                    if (allST.size() > 0)
                    {
                        int count = 1;
                        for(int i = 0; i < allST.size(); i++)
                        {
                            if (Objects.equals(selectedDate, DateTime.convertDate( allST.get(i).getTime().getTime()))){
                                System.out.printf("%s) %s %s %s\n", count++, cinemas.get(i).getCinemaName(), allST.get(i).getMovie().getMovieTitle() , DateTime.convertTime( allST.get(i).getTime().getTime()));
                            }
                        }
                    }else {
                        System.out.printf("%s is no showtime for this movies. Please select another Date.\n\n", cineplexes.getCineplexes().get(selectCineplex).getCineplexName());
                        return null;
                    }

                    System.out.println("Select your Showtime from the list above: ");
                    selectShowtime = GetNumberInput.getInt() - 1;
                    ticket.setCinema(cinemas.get(selectShowtime).getCinemaName());
                    ticket.setChosenMovie(allST.get(selectShowtime).getMovie().getMovieTitle());
                    ticket.setMovieDateTime(DateTime.convertTime(allST.get(selectShowtime).getTime().getTime()));
                    ticket.setTID(new DateTime().ToTID(cinemas.get(selectShowtime).getCinemaCode()));
                    break;
                }
                movielist.get(selectMovie).increaseMovieTotalSale(); //increase sales of movie
                System.out.println("\nMoving to MovieUI!\n");
                ticket.setSeatID(MovieUI.MovieInterface(ticket , allST.get(selectShowtime))); // set ticket seats. change return type to ticket?

                TextDB.UpdateToTextDB(File.separator + cineplexes.getCineplexes().get(selectCineplex).getCineplexName().replace(' ','_')+ File.separator+cinemas.get(selectShowtime).getCinemaName()+".txt" ,
                        movielist.get(selectMovie) , cinemas.get(selectShowtime).getShowTime());

                break;
            }
        }
        return ticket;
    }
}