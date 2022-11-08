package UserInterface;

import Cineplex.AllCineplex;
import Service.GetNumberInput;
import Service.Settings;
import java.io.IOException;
import java.util.Scanner;

public class ReviewUI {
    public static void UserInferface(AllCineplex cineplexes) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*                Review Interface               *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        System.out.println("\nPlease enter username:");
        String userName = sc.nextLine();

        System.out.println("Please select a movie to review:");
        for(int i = 0; i < cineplexes.getListOfMovies().size(); i++)
        {
            System.out.printf("\t%d) %s\n", i+1 ,cineplexes.getListOfMovies().get(i).getMovieTitle());
        }
        int selectedMovie = GetNumberInput.getInt(1, cineplexes.getListOfMovies().size(), -1)-1;
        if (selectedMovie == -1) return;
        System.out.printf("What is your rating for this Movie %s (0.0-5.0):", cineplexes.getListOfMovies().get(selectedMovie));
        float rating = (float) GetNumberInput.getDouble(0, 5, -1);
        if (rating == -1) return;
        System.out.println("Please type your review:");
        String reviews = sc.nextLine();

        cineplexes.getListOfMovies().get(selectedMovie).addReview(userName , rating,reviews, cineplexes.getListOfMovies());
    }
}
