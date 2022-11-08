package UserInterface;

import Cineplex.Cinema;
import Cineplex.AllCineplex;
import Cineplex.Cineplex;
import Movie.Movie;
import Movie.MovieType;
import Service.DateTime;
import Service.GetNumberInput;

import java.io.IOException;
import java.util.Date;

/**
 * This is the Cinema Interface. Imported to call User interface to allow editing the showtime.
 *
 * @authors CHEW ZHI QI, GAN HAO YI
 */
public class CinemaUI {
    /**
     * This is the UserInterface. This allows the user to edit /add/update any showtime for any movie by accessing
     * the cineplexes and each cinema in them and the movies that are showing at the cinemas
     *
     * @param cineplexes = stores the list of movies and the showtimes and showdates of the movies listed in the cinemas
     * @throws IOException is thrown if there is an error in accessing the data from the cineplexes
     */
    public static void UserInterface(AllCineplex cineplexes, int choice, Movie movie) throws IOException {
        switch (choice) {
            // create new movie timing for all cineplex
            case 1 -> {
                System.out.printf("\n\nAdding ShowTime for movie: %s.", movie.getMovieTitle());
                System.out.println("Creating ShowTime for each Cineplex.\n");
                addNewShowTime(cineplexes, movie);
            }
            // update movie timing
            case 2 -> UpdateShowTime(cineplexes, movie);
        }
    }

    /**
     * This method adds new ShowTimes to each cinema that each cineplex has.
     * This is used right after a new movie has been created. User MUST create new ShowTime for all cinemas.
     *
     * @param cineplexes - Update new ShowTime in global storage.
     * @throws IOException - To check if ShowTime can be written into text file.
     */
    public static void addNewShowTime(AllCineplex cineplexes, Movie movie) throws IOException {
        for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
            Cineplex chosenCineplex = cineplexes.getCineplexes().get(i);
            System.out.printf("Creating ShowTime for Cineplex %s\n", chosenCineplex.getCineplexName());
            for (int j = 0; j < chosenCineplex.getListOfCinemas().size(); j++) {
                Cinema chosenCinema = chosenCineplex.getListOfCinemas().get(j);
                System.out.printf("Creating ShowTime for Cinema %s\n", chosenCinema.getCinemaName());
                System.out.println("\nHow many ShowTime would you like to create? (0 - 5 at a time):");
                int newshowtimes = GetNumberInput.getInt(0, 5, -1);
                for (int k = 0; k < newshowtimes; k++) {
                    System.out.printf("\nShowTime %d\n", k + 1);
                    Date newDate = null;
                    do {
                        System.out.println("\nPlease enter year (2022 - 2023):");
                        int year = GetNumberInput.getInt(2022, 2023, -1);
                        if (year < 2022) {
                            System.out.println("Invalid Year. Starting over date input.");
                            continue;
                        }
                        System.out.println("\nPlease enter month (1-12):");
                        int month = GetNumberInput.getInt(1, 12, -1);
                        if (month < 1 || month > 12) {
                            System.out.println("Invalid month. Starting over date input.");
                            continue;
                        }
                        System.out.println("\nPlease enter day (1-30/31) :");
                        int day = GetNumberInput.getInt(1, 31, -1);
                        if (day < 1 || day > 31) {
                            System.out.println("Invalid day. Starting over date input.");
                            continue;
                        }
                        if (month == 2 && day > 28) {
                            System.out.println("Invalid day. Starting over date input.");
                            continue;
                        }
                        System.out.println("\nPlease enter hour (0-24):");
                        int hour = GetNumberInput.getInt(0, 24, -1);
                        if (hour < 0 || hour > 24) {
                            System.out.println("Invalid hour. Starting over date input.");
                            continue;
                        }
                        System.out.println("\nPlease enter minutes (0-59):");
                        int min = GetNumberInput.getInt(0, 59, -1);
                        if (min < 0 || min > 59) {
                            System.out.println("Invalid minutes. Starting over date input.");
                            continue;
                        }
                        String date = day + "-" + month + "-" + year + ";" + hour + ":" + min;
                        newDate = DateTime.StringToDate(date);
                    } while (newDate == null);

                    int three_d = 0;
                    MovieType.Dimension dim = null;
                    do {
                        System.out.println("Will this Cinema show the movie in 3D?");
                        System.out.println("\t1) Yes\n\t2) No");
                        three_d = GetNumberInput.getInt(1, 2, -1);
                        if (three_d != 1 && three_d != 2) continue;
                        switch (three_d) {
                            case 1 -> dim = MovieType.Dimension.THREE_D;
                            case 2 -> dim = MovieType.Dimension.TWO_D;
                        }
                    } while (dim == null);
                    chosenCinema.createShowTime(newDate, movie, dim);
                    System.out.println("New ShowTime has been created:");
                }
                for (int k = 0; k < chosenCinema.getShowTime().size(); k++) {
                    System.out.printf(
                            "\n%s) %s %s\n", k + 1,
                            chosenCinema.getShowTime().get(k).getMovie().getMovieTitle(),
                            DateTime.convertTime(chosenCinema.getShowTime().get(k).getTime().getTime())
                    );
                }
            }
        }
    }

    /**
     * This method edits the current available ShowTime selected by user.
     *
     * @param cineplexes is passed into the method to update the global list of movies used by the app.
     * @throws IOException is used when updating the database.
     */
    public static void UpdateShowTime(AllCineplex cineplexes, Movie movie) throws IOException {
        System.out.println("Please enter which cineplex showtime to edit :");
        for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
            System.out.printf("\t%s) %s\n", i + 1, cineplexes.getCineplexes().get(i).getCineplexName());
        }
        int choice1 = GetNumberInput.getInt(1, cineplexes.getCineplexes().size(), -1) - 1;
        if (choice1 == -2) {
            System.out.println("Exiting.");
            return;
        }
        Cineplex chosenCineplex = cineplexes.getCineplexes().get(choice1);

        System.out.println("Please enter which cinema showtime to edit :");
        for (int i = 0; i < chosenCineplex.getNoOfCinemas(); i++) {
            System.out.printf("\t%s) %s Cinema %s\n", i + 1, chosenCineplex.getListOfCinemas().get(i).getCinemaType(), chosenCineplex.getListOfCinemas().get(i).getCinemaName());
        }
        int choice2 = GetNumberInput.getInt(1, chosenCineplex.getNoOfCinemas(), -1) - 1;
        if (choice2 == -2) {
            System.out.println("Exiting.");
            return;
        }
        Cinema chosenCinema = chosenCineplex.getListOfCinemas().get(choice2);

        System.out.printf("Current showtime in %s\n", chosenCinema.getCinemaName());
        for (int i = 0; i < chosenCinema.getShowTime().size(); i++) {
            System.out.printf("\t%s) %s %s\n", i + 1, chosenCinema.getShowTime().get(i).getMovie().getMovieTitle(), DateTime.convertTime(chosenCinema.getShowTime().get(i).getTime().getTime()));
        }
        System.out.println("Please select ShowTime to edit:");
        int chosenShow = GetNumberInput.getInt(1, chosenCinema.getShowTime().size(), -1) - 1;
        if (chosenShow == -1) {
            System.out.println("Exiting.");
            return;
        }
        Date newDate = null;
        do {
            System.out.println("Please enter year (2022-2023):");
            int year = GetNumberInput.getInt(2022, 2023, -1);
            if (year < 2022) {
                System.out.println("Invalid Year. Starting over date input.");
                continue;
            }
            System.out.println("Please enter month (1-12):");
            int month = GetNumberInput.getInt(1, 12, -1);
            if (month < 1 || month > 12) {
                System.out.println("Invalid month. Starting over date input.");
                continue;
            }
            System.out.println("Please enter day (1-30/31) :");
            int day = GetNumberInput.getInt(1, 31, -1);
            if (day < 1 || day > 31) {
                System.out.println("Invalid day. Starting over date input.");
                continue;
            }
            if (month == 2 && day > 28) {
                System.out.println("Invalid day. Starting over date input.");
                continue;
            }
            System.out.println("Please enter hour (0-24):");
            int hour = GetNumberInput.getInt(0, 24, -1);
            if (hour < 1 || hour > 24) {
                System.out.println("Invalid hour. Starting over date input.");
                continue;
            }
            System.out.println("Please enter minutes (0-59):");
            int min = GetNumberInput.getInt(0, 59, -1);
            if (min < 1 || min > 59) {
                System.out.println("Invalid minutes. Starting over date input.");
                continue;
            }

            String date = day + "-" + month + "-" + year + ";" + hour + ":" + min;
            newDate = DateTime.StringToDate(date);
        } while (newDate == null);

        chosenCinema.updateCinemaTime(
                chosenShow, chosenCineplex, newDate, movie,
                chosenCinema.getShowTime().get(chosenShow).getDimension()
        );
        for (int k = 0; k < chosenCinema.getShowTime().size(); k++) {
            System.out.printf("\t%s) %s %s\n",
                    k + 1, chosenCinema.getShowTime().get(k).getMovie().getMovieTitle(),
                    DateTime.convertTime(chosenCinema.getShowTime().get(k).getTime().getTime())
            );
        }
    }

    /**
     * This method is used to remove Show Times in selected Cineplex's Cinema.
     *
     * @param cineplexes - Used to update global list of ShowTime.
     * @throws IOException - Checks if Cineplex's / Cinema's ShowTime database is empty.
     */
    public static void removeShowTime(AllCineplex cineplexes) throws IOException {
        System.out.println("Please enter which cineplex showtime to edit :");
        for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
            System.out.printf("\t%s) %s\n", i + 1, cineplexes.getCineplexes().get(i).getCineplexName());
        }
        int choice1 = GetNumberInput.getInt(1, cineplexes.getCineplexes().size(), -1) - 1;
        if (choice1 == -2) {
            System.out.println("Exiting.");
            return;
        }
        Cineplex chosenCineplex = cineplexes.getCineplexes().get(choice1);
        if (chosenCineplex.getNoOfCinemas() == 0) {
            System.out.println("This Cinema does not have any shows currently.");
            return;
        }
        System.out.println("Please enter which cinema showtime to edit :");
        for (int i = 0; i < chosenCineplex.getNoOfCinemas(); i++) {
            System.out.printf("\t%s) %s Cinema %s\n", i + 1, chosenCineplex.getListOfCinemas().get(i).getCinemaType(), chosenCineplex.getListOfCinemas().get(i).getCinemaName());
        }
        int choice2 = GetNumberInput.getInt(1, chosenCineplex.getNoOfCinemas(), -1) - 1;
        if (choice2 == -2) {
            System.out.println("Exiting.");
            return;
        }
        Cinema chosenCinema = chosenCineplex.getListOfCinemas().get(choice2);
        if (chosenCinema.getShowTime().size() == 0) {
            System.out.println("There is no ShowTime in this Cinema.");
            return;
        }
        System.out.printf("Current showtime in Cinema %s\n", chosenCinema.getCinemaName());
        for (int i = 0; i < chosenCinema.getShowTime().size(); i++) {
            System.out.printf("\t%s) %s %s\n", i + 1, chosenCinema.getShowTime().get(i).getMovie().getMovieTitle(), DateTime.convertTime(chosenCinema.getShowTime().get(i).getTime().getTime()));
        }
        System.out.println("Please select ShowTime to edit:");
        int chosenShow = GetNumberInput.getInt(1, chosenCinema.getShowTime().size(), -1) - 1;
        if (chosenShow == -2) {
            System.out.println("Exiting.");
            return;
        }
        chosenCinema.deleteCinemaTime(chosenShow);
    }
}