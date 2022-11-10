package UserInterface;

import Cineplex.Cinema;
import Cineplex.Cineplex;
import Cineplex.ShowTime;
import Movie.MovieType;
import Service.DateTime;
import Service.GetNumberInput;
import Service.Settings;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author GAN HAO YI, CHEW ZHI QI
 * This class stores an interface for user to choose to watch a 2D or 3D movie.
 */
public class SelectDimensionUI {
    /**
     * This Interface allows Customer to select the Type of movie. Customer can choose between a 2D or a 3D movie.
     *
     * @param cineplex - Passed in to get the Global list of Cinema, Movie and ShowTime.
     * @param sTnC     - Stores Specific ShowTime of Movie and the Cinema it is showing in.
     * @return ArrayList of Selected ShowTime and Cinema
     */
    public static ArrayList<Object> SelectDimensionUserInterface(Cineplex cineplex, ArrayList<Object> sTnC) {
        int three_d = 0;
        int selectShowtime = -1;
        ArrayList<ShowTime> allST = (ArrayList<ShowTime>) sTnC.get(0);
        ArrayList<Cinema> cinemas = (ArrayList<Cinema>) sTnC.get(1);
        String selectedDate = (String) sTnC.get(2);
        ArrayList<ShowTime> specificST = new ArrayList<>();
        ArrayList<Cinema> specificCin = new ArrayList<Cinema>();
        ArrayList<Object> res = new ArrayList<>();
        MovieType.Dimension dim = null;

        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*              Select Type of Movie             *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        do {
            System.out.println("\nWould you like to watch the movie in 3D?");
            System.out.println("\t1) Yes\n\t2) No");
            three_d = GetNumberInput.getInt(1, 2, -1);
            if (three_d != 1 && three_d != 2) continue;
            switch (three_d) {
                case 1 -> // 3D
                        dim = MovieType.Dimension.THREE_D;
                case 2 -> // 2D
                        dim = MovieType.Dimension.TWO_D;
            }
            int count = 0;
            for (ShowTime showTime : allST) {
                if (Objects.equals(selectedDate, DateTime.convertDate(showTime.getTime().getTime())) && Objects.equals(showTime.getDimension(), dim)) {
                    count++;
                }
            }
            if (count == 0) {
                System.out.printf("There is no available movies for a %s movie. Please select the other choice.\n", dim);
                dim = null;
            }
        } while (dim == null);

        int size = 0;

        if (allST.size() > 0) {
            int count = 1;
            for (int i = 0; i < allST.size(); i++) {
                if (Objects.equals(selectedDate, DateTime.convertDate(allST.get(i).getTime().getTime())) && Objects.equals(allST.get(i).getDimension(), dim)) {
                    System.out.printf("\t%s) Cinema %s: %s %s\n", count++, cinemas.get(i).getCinemaName(), allST.get(i).getMovie().getMovieTitle(), DateTime.convertTime(allST.get(i).getTime().getTime()));
                    specificST.add(allST.get(i));
                    specificCin.add(cinemas.get(i));
                    size++;
                }
            }
        } else {
            System.out.printf("%s is no showtime for this movies. Please select another Date.\n\n", cineplex.getCineplexName());
            return null;
        }
        System.out.println("Select your Showtime from the list above: ");
        selectShowtime = GetNumberInput.getInt(1, size, -1) - 1;

        while (selectShowtime < 0 || selectShowtime >= size) {
            System.out.println("Value is out of range, please try again.");
            System.out.println("Select your Showtime from the list above: ");
            selectShowtime = GetNumberInput.getInt(1, allST.size(), -1) - 1; // error checking for correct cineplex
        }

        System.out.println("\n\nYou have selected the following movie: ");
        allST.get(selectShowtime).getMovie().printMovieDetails();
        System.out.printf("\nCinema Room: %s\n", specificCin.get(selectShowtime).getCinemaName());
        System.out.printf("Movie Timing: %s\n", DateTime.convertTime(specificST.get(selectShowtime).getTime().getTime()));

        res.add(specificST.get(selectShowtime));
        res.add(specificCin.get(selectShowtime));

        return res;
    }
}