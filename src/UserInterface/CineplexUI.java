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

/**
 * This is the Cineplex user Interface. Imported to call interface to view the movies at the cineplexes.
 * @authors CHEW ZHI QI, EDDY CHENG KUAN QUAN, GAN HAO YI
 */
public class CineplexUI {
    /**
     * This is the Cineplex Interface. This is used to get the customer to choose the cineplex they
     * want and view the movies listed there (if any). they can choose the date and showtimes available for the movie
     * @param cineplexes = stores the list of movies and the showtimes and showdates of the movies listed
     * @return the object ticket
     * @throws IOException is thrown if there is an error in reading from the file
     */
    public static MovieTicket CineplexInterface(AllCineplex cineplexes) throws IOException {

        int choice = 0;
        ArrayList<ShowTime> allST = new ArrayList<>();
        ArrayList<Cinema> cinemas = new ArrayList<>();
        ArrayList<ShowTime> specificST = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        MovieTicket ticket = new MovieTicket();
        for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
            System.out.printf("\t%d: Cineplex Name: %s\n", i + 1, cineplexes.getCineplexes().get(i).getCineplexName());
        }
        int selectCineplex = -1;
        int selectShowtime = -1;
        int selectDate = -1;

        while (selectCineplex >= -1) {
            System.out.println("Select your Cineplex: ");
            selectCineplex = sc.nextInt() - 1;
            while (selectCineplex  < 0 || selectCineplex >= cineplexes.getCineplexes().size()){
                System.out.println("The number you keyed is out of range, please key again!");
                System.out.println("Select your Cineplex: ");
                selectCineplex = sc.nextInt() - 1; // error checking for correct cineplex
            }
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
                while (selectMovie  < 0 || selectMovie >= movielist.size()){
                    System.out.println("The number you keyed is out of range, please key again!");
                    System.out.println("Select your Movie from the list above: ");
                    selectMovie = GetNumberInput.getInt() - 1;
                }



                //Ask for showtime
                while (selectShowtime >= -1) {
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
                        while (selectDate  < 0 || selectDate >= STDates.size()){
                            System.out.println("The number you keyed is out of range, please key again!");
                            System.out.println("Select your date from the list above: ");
                            selectDate = sc.nextInt() - 1; // error checking for correct cineplex
                        }


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
                    int three_d = 0;
                    MovieType.Dimension dim = null;
                    do {
                        System.out.println("Would you like to watch the movie in 3D?");
                        System.out.println("1) Yes\n2) No");
                        three_d = GetNumberInput.getInt();
                        if (three_d != 1 && three_d != 2) continue;
                        switch (three_d) {
                            case 1 -> {
                                // 3D
                                dim = MovieType.Dimension.THREE_D;
                            }
                            case 2 -> {
                                // 2D
                                dim = MovieType.Dimension.TWO_D;
                            }
                        }
                        int count = 0;
                        for(int i = 0; i < allST.size(); i++)
                        {
                            if (Objects.equals(selectedDate, DateTime.convertDate(allST.get(i).getTime().getTime())) &&
                                    Objects.equals(allST.get(i).getDimension(), dim)){
                                count++;
                            }
                        }
                        if (count == 0) {
                            System.out.printf("There is no available movies for a %s movie. Please select the other choice.\n", dim);
                            dim = null;
                        }
                    } while (dim == null);
                    int size = 0;

                    if (allST.size() > 0)
                    {
                        int count = 1;
                        for(int i = 0; i < allST.size(); i++)
                        {

                            if (Objects.equals(selectedDate, DateTime.convertDate( allST.get(i).getTime().getTime())) &&
                                    Objects.equals(allST.get(i).getDimension(), dim)){
                                System.out.printf("%s) %s %s %s\n", count++, cinemas.get(i).getCinemaName(), allST.get(i).getMovie().getMovieTitle() , DateTime.convertTime( allST.get(i).getTime().getTime()));
                                specificST.add(allST.get(i));
                                size++;
                            }
                        }
                    }
                    else {
                        System.out.printf("%s is no showtime for this movies. Please select another Date.\n\n", cineplexes.getCineplexes().get(selectCineplex).getCineplexName());
                        return null;
                    }


                    System.out.println("Select your Showtime from the list above: ");
                    selectShowtime = GetNumberInput.getInt() - 1;

                    while (selectShowtime  < 0 || selectShowtime >= size){
                        System.out.println("The number you keyed is out of range, please key again!");
                        System.out.println("Select your Showtime from the list above: ");
                        selectShowtime = GetNumberInput.getInt() - 1; // error checking for correct cineplex
                    }

                    System.out.println("\n\nYou have selected the following movie: ");
                    allST.get(selectShowtime).getMovie().printMovieDetails();
                    System.out.printf("\nCinema Room: %s\n", cinemas.get(selectShowtime).getCinemaName());
                    System.out.printf("Movie Timing: %s\n", DateTime.convertTime( specificST.get(selectShowtime).getTime().getTime()));

                    ticket.setCinema(cinemas.get(selectShowtime).getCinemaName());
                    ticket.setChosenMovie(specificST.get(selectShowtime).getMovie().getMovieTitle());
                    ticket.setMovieDateTime(specificST.get(selectShowtime).getTime());
                    ticket.setTID(new DateTime().ToTID(cinemas.get(selectShowtime).getCinemaCode()));
                    System.out.println("Ticket Created.");
                    break;
                }
                movielist.get(selectMovie).increaseMovieTotalSale(cineplexes.getListOfMovies()); //increase sales of movie

                System.out.println("\nMoving to MovieUI!\n");
                ticket.setSeatID(MovieUI.MovieInterface(ticket , specificST.get(selectShowtime))); // set ticket seats. change return type to ticket?

                TextDB.UpdateToTextDB(File.separator + cineplexes.getCineplexes().get(selectCineplex).getCineplexName().replace(' ','_')+ File.separator+cinemas.get(selectShowtime).getCinemaName()+".txt" ,
                        cinemas.get(selectShowtime).getShowTime(), specificST.get(selectShowtime).getDimension());

                break;
            }
        }
        return ticket;
    }
}