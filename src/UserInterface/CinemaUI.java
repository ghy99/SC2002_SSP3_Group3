package UserInterface;

import Cineplex.*;
import Movie.*;
import Service.DateTime;
import Service.GetNumberInput;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * This is the Cinema Interface. Imported to call User interface to allow editing the showtime.
 * @authors CHEW ZHI QI, GAN HAO YI
 */
public class CinemaUI {
    /**
     * This is the UserInterface. This allows the user to edit /add/update any showtime for any movie by accessing
     * the cineplexes and each cinema in them and the movies that are showing at the cinemas
     * @param cineplexes = stores the list of movies and the showtimes and showdates of the movies listed in the cinemas
     * @throws IOException is thrown if there is an error in accessing the data from the cineplexes
     */
    public static void UserInterface(AllCineplex cineplexes) throws IOException {
        int choseShow, userInput, day, month, year, hour, min;

        //Get cineplex to edit
        System.out.println("Please enter which cineplex showtime to edit :");
        for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
            System.out.printf("%s) %s", i + 1, cineplexes.getCineplexes().get(i).getCineplexName() + "\n");
        }

        Cineplex chosenCineplex = cineplexes.getCineplexes().get(GetNumberInput.getInt() - 1);

        System.out.println("Please enter which cinema showtime to edit :");
        for (int i = 0; i < chosenCineplex.getListOfCinemas().size(); i++) {
            System.out.printf("%s) %s %s %s" + "\n", i + 1, chosenCineplex.getListOfCinemas().get(i).getCinemaCode(), chosenCineplex.getListOfCinemas().get(i).getCinemaName(), chosenCineplex.getListOfCinemas().get(i).getCinemaType());
        }

        Cinema choosenCinema = chosenCineplex.getListOfCinemas().get(GetNumberInput.getInt() - 1);


        System.out.println("1)\tAdd showtime\n2)\tUpdate showtime:");
        userInput = GetNumberInput.getInt();

        System.out.printf("Current showtime in %s" + "\n", choosenCinema.getCinemaName());
        for (int i = 0; i < choosenCinema.getShowTime().size(); i++) {
            System.out.printf("%s) %s %s" + "\n", i + 1, choosenCinema.getShowTime().get(i).getMovie().getMovieTitle(), DateTime.convertTime(choosenCinema.getShowTime().get(i).getTime().getTime()));
        }

        switch (userInput - 1) {
            case 0 -> {
                System.out.println("Please enter day :");
                day = GetNumberInput.getInt();
                System.out.println("Please enter month :");
                month = GetNumberInput.getInt();
                System.out.println("Please enter year :");
                year = GetNumberInput.getInt();
                System.out.println("Please enter hour :");
                hour = GetNumberInput.getInt();
                System.out.println("Please enter minutes :");
                min = GetNumberInput.getInt();

                String date = day + "-" + month + "-" + year + ";" + hour + ":" + min;
                Date newDate = DateTime.StringToDate(date);

                System.out.println("Please enter movie to add showtime:");
                for (int i = 0; i < cineplexes.getListOfMovies().size(); i++) {
                    System.out.printf("%s) %s" + "\n", i + 1, cineplexes.getListOfMovies().get(i).getMovieTitle());
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
                } while (dim == null);

                choosenCinema.createShowTime(chosenCineplex, newDate, cineplexes.getListOfMovies().get(GetNumberInput.getInt() - 1), dim);

                for (int i = 0; i < choosenCinema.getShowTime().size(); i++) {
                    System.out.printf("%s) %s %s" + "\n", i + 1,
                            choosenCinema.getShowTime().get(i).getMovie().getMovieTitle(),
                            DateTime.convertTime(choosenCinema.getShowTime().get(i).getTime().getTime())
                    );
                }

            }
            case 1 -> {

                System.out.println("Please enter showtime to add showtime:");
                choseShow = GetNumberInput.getInt() - 1;

                System.out.println("Please enter day :");
                day = GetNumberInput.getInt();
                System.out.println("Please enter month :");
                month = GetNumberInput.getInt();
                System.out.println("Please enter year :");
                year = GetNumberInput.getInt();
                System.out.println("Please enter hour :");
                hour = GetNumberInput.getInt();
                System.out.println("Please enter minutes :");
                min = GetNumberInput.getInt();

                String date = day + "-" + month + "-" + year + ";" + hour + ":" + min;
                Date newDate = DateTime.StringToDate(date);



                choosenCinema.updateCinemaTime(
                        choseShow, chosenCineplex, newDate,
                        cineplexes.getListOfMovies().get(GetNumberInput.getInt() - 1),
                        choosenCinema.getShowTime().get(choseShow).getDimension()
                );
            }
        }
    }
}
