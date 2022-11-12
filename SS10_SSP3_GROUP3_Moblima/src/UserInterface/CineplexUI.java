package UserInterface;

import Cineplex.AllCineplex;
import Cineplex.Cineplex;
import Movie.MovieTicket;
import Service.GetNumberInput;
import Service.Settings;

import java.util.ArrayList;

/**
 * This is the Cineplex user Interface. Imported to call interface to view the movies at the cineplexes.
 *
 * @authors CHEW ZHI QI, EDDY CHENG KUAN QUAN, GAN HAO YI
 */
public class CineplexUI {
    /**
     * This is the Cineplex Interface. This is used to get the customer to choose the cineplex they
     * want and view the movies listed there (if any). they can choose the date and showtimes available for the movie
     *
     * @param cineplexes - stores the list of movies and the showtimes and showdates of the movies listed
     * @return the object ticket
     */
    public static Cineplex CineplexInterface(AllCineplex cineplexes) {
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*               Cineplex Interface              *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        int selectCineplex = -1;
        ArrayList<MovieTicket> tickets = null;
        System.out.println("\nSelect your Cineplex: ");
        for (int i = 0; i < cineplexes.getCineplexes().size(); i++) {
            System.out.printf("\t%d: Cineplex Name: %s\n", i + 1, cineplexes.getCineplexes().get(i).getCineplexName());
        }
        selectCineplex = GetNumberInput.getInt(1, cineplexes.getCineplexes().size(), -1) - 1;
        if(selectCineplex > -1)
            return  cineplexes.getCineplexes().get(selectCineplex);
        else
            return null;
    }
}