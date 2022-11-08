package UserInterface;

import Cineplex.Cinema;
import Cineplex.Cineplex;
import Cineplex.ShowTime;
import Movie.MovieType;
import Service.DateTime;
import Service.GetNumberInput;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SelectDimensionUI {
    public static ArrayList<Object> SelectDimensionUserInterface(Cineplex cineplex, ArrayList<Object> sTnC) {
        int three_d = 0;
        int selectShowtime = -1;
        ArrayList<ShowTime> allST = (ArrayList<ShowTime>) sTnC.get(0);
        ArrayList<Cinema> cinemas = (ArrayList<Cinema>) sTnC.get(1);
        String selectedDate = (String) sTnC.get(2);
        ArrayList<ShowTime> specificST = new ArrayList<>();
        ArrayList<Cinema> specificCin = new ArrayList<Cinema>();
        ArrayList<Object> res=  new ArrayList<>();

        MovieType.Dimension dim = null;
        do {
            System.out.println("Would you like to watch the movie in 3D?");
            System.out.println("1) Yes\n2) No");
            three_d = GetNumberInput.getInt(1, 2, -1);
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
            for (int i = 0; i < allST.size(); i++) {
                if (Objects.equals(selectedDate, DateTime.convertDate(allST.get(i).getTime().getTime())) && Objects.equals(allST.get(i).getDimension(), dim)) {
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
                    System.out.printf("%s) %s %s %s\n", count++, cinemas.get(i).getCinemaName(), allST.get(i).getMovie().getMovieTitle(), DateTime.convertTime(allST.get(i).getTime().getTime()));
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
            System.out.println("The number you keyed is out of range, please key again!");
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

