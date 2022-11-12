package UserInterface;

import Cineplex.AllCineplex;
import Service.GetNumberInput;
import Service.Settings;

import java.io.IOException;
import java.util.Scanner;

/**
 * @authors CHEW ZHI QI, GAN HAO YI
 * This Class is the Interface for Reviewing Movies.
 */
public class ReviewUI {
    /**
     * This User Interface controls how Users can leave reviews for movies.
     *
     * @param cineplexes - Used to get List of Movies to write reviews.
     * @throws IOException Check that Reviews Database exists.
     */
    public static void UserInferface(AllCineplex cineplexes) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*                Review Interface               *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        System.out.println("\nPlease enter username:");
        String userName = sc.nextLine();

        System.out.println("\nPlease select a movie to review:");
        for (int i = 0; i < cineplexes.getListOfMovies().size(); i++) {
            System.out.printf("\t%d) %s\n", i + 1, cineplexes.getListOfMovies().get(i).getMovieTitle());
        }
        int selectedMovie = GetNumberInput.getInt(1, cineplexes.getListOfMovies().size(), -1) - 1;
        if (selectedMovie == -2) return;

        System.out.printf("\nWhat is your rating for this Movie %s (0.0-5.0):", cineplexes.getListOfMovies().get(selectedMovie));
        float rating = (float) GetNumberInput.getDouble(0, 5, -1);
        if (rating == -1) return;
        System.out.println("\nPlease type your review:");
        String reviews = sc.nextLine();

        cineplexes.getListOfMovies().get(selectedMovie).addReview(userName, rating, reviews, cineplexes.getListOfMovies());
    }
}