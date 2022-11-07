package UserInterface;

import java.util.Scanner;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

import Cineplex.*;
import Movie.*;
import Service.*;

/**
 * This is the Movie User Interface Class. Imported to call interface to get customer to select their seat
 *
 * @author EDDY CHENG KUAN QUAN, CHEW ZHI QI
 */
public class MovieUI {

    public static Movie MovieInterface(ArrayList<Movie> movielist) {

        int selectMovie = 0;

        if (movielist.size() == 0) {
            System.out.printf("There is not showing any movies at this moment.\n\n");

            return null;
        } else {
            for (int i = 0; i < movielist.size(); i++) {
                System.out.printf("%d)\n", i + 1);
                movielist.get(i).printMovieDetails();
            }
            System.out.println("Select your Movie from the list above: ");
            selectMovie = GetNumberInput.getInt(1, movielist.size(), -1) - 1;
            while (selectMovie < 0 || selectMovie >= movielist.size()) {
                System.out.println("The number you keyed is out of range, please key again!");
                System.out.println("Select your Movie from the list above: ");
                selectMovie = GetNumberInput.getInt(1, movielist.size(), -1) - 1;
            }
        }

        return movielist.get(selectMovie);
    }

}
