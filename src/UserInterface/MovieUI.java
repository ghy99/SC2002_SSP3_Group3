package UserInterface;

import java.util.ArrayList;
import Movie.*;
import Service.*;

/**
 * This is the Movie User Interface Class. Imported to call interface to get customer to select their seat
 *
 * @author EDDY CHENG KUAN QUAN, CHEW ZHI QI
 */
public class MovieUI {
    /**
     * This is the Movie Interface that allows user to select a movie.
     * @param movielist - Passes in a list of movie for user to select a movie.
     * @return - returns the movie selected by the user.
     */
    public static Movie MovieInterface(ArrayList<Movie> movielist) {
        int selectMovie = -1;
        if (movielist.size() == 0) {
            System.out.printf("There is not showing any movies at this moment.\n\n");
            return null;
        } else {
            System.out.println(Settings.ANSI_CYAN);
            System.out.println("*************************************************");
            System.out.println("*                Selecting Movies               *");
            System.out.println("*************************************************");
            System.out.println(Settings.ANSI_RESET);
            do {
                for (int i = 0; i < movielist.size(); i++) {
                    System.out.printf("%d)\n", i + 1);
                    movielist.get(i).printMovieDetails();
                }
                System.out.println("Select your Movie from the list above:");
                selectMovie = GetNumberInput.getInt(1, movielist.size(), -1) - 1;
                System.out.println("\nThis is the movie you have selected.");
                movielist.get(selectMovie).printMovieDetails();
                System.out.println("Would you like to choose another movie?");
                System.out.println("\t1) Yes\n\t2) No");
                int choice = GetNumberInput.getInt(1, 2, -1);
                if (choice == -1 || choice == 2) break;
                else if (choice == 1) continue;
                while (selectMovie < 0 || selectMovie >= movielist.size()) {
                    System.out.println("The number you keyed is out of range, please key again!");
                    System.out.println("Select your Movie from the list above: ");
                    selectMovie = GetNumberInput.getInt(1, movielist.size(), -1) - 1;
                }
            } while (selectMovie == -1);
        }
        return movielist.get(selectMovie);
    }
}