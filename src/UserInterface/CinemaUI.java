package UserInterface;

import Cineplex.*;
import Movie.Movie;
import Service.DateTime;
import Service.GetNumberInput;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class CinemaUI {
    public static void UserInterface(ArrayList<Cineplex> cineplexes) throws IOException {
        int choseShow, userInput, day, month, year, hour, min;

        //Get cineplex to edit
        System.out.println("Please enter which cineplex showtime to edit :");
        for (int i = 0; i < cineplexes.size(); i++) {
            System.out.printf("%s) %s", i + 1, cineplexes.get(i).getCineplexName() + "\n");
        }

        Cineplex chosenCineplex = cineplexes.get(GetNumberInput.getInt() - 1);

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
                for (int i = 0; i < chosenCineplex.getListOfMovies().size(); i++) {
                    System.out.printf("%s) %s" + "\n", i + 1, chosenCineplex.getListOfMovies().get(i).getMovieTitle());
                }

                choosenCinema.createShowTime(chosenCineplex, newDate, chosenCineplex.getListOfMovies().get(GetNumberInput.getInt() - 1));

                for (int i = 0; i < choosenCinema.getShowTime().size(); i++) {
                    System.out.printf("%s) %s %s" + "\n", i + 1, choosenCinema.getShowTime().get(i).getMovie().getMovieTitle(), DateTime.convertTime(choosenCinema.getShowTime().get(i).getTime().getTime()));
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

                choosenCinema.updateCinemaTime(choseShow, chosenCineplex, newDate, chosenCineplex.getListOfMovies().get(GetNumberInput.getInt() - 1));
            }
        }
    }
}
