package UserInterface;

import Cineplex.AllCineplex;
import Service.GetNumberInput;

import java.io.IOException;
import java.util.Scanner;

public class ReviewUI {
    public static void UserInferface(AllCineplex cineplexes) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter username:");
        String userName = sc.nextLine();

        System.out.println("Please select a movie to review (Enter -1 to exit):");
        for(int i = 0; i < cineplexes.getListOfMovies().size(); i++)
        {
            System.out.printf("%s) %s" + "\n", i+1 ,cineplexes.getListOfMovies().get(i).getMovieTitle());
        }
        int selectedMovie = GetNumberInput.getInt(1, cineplexes.getListOfMovies().size(), -1)-1;
        if (selectedMovie == -1) return;
        System.out.println("Please input you rating (0.0-5.0):");
        float rating = (float) GetNumberInput.getDouble(0, 5, -1);

        System.out.println("Please input you review:");
        String reviews = sc.nextLine();

        cineplexes.getListOfMovies().get(selectedMovie).addReview(userName , rating,reviews, cineplexes.getListOfMovies());
    }
}
