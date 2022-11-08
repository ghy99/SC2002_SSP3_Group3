package UserInterface;

import Cineplex.*;
import Service.GetNumberInput;
import Service.Settings;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author CHEW ZHI QI, GAN HAO YI
 * This User Interface is used to add cinemas.
 */
public class AddCinemaUI {
    /**
     * This method is the User Interface to add Cinemas.
     *
     * @param cineplex   - used to get a list of cinemas stored in this cineplex.
     * @param cineplexes - used to update the global list of cinemas when new cinema is added.
     * @throws IOException - Required to check if cineplex database is empty.
     */
    public static void AddCinemaInterface(Cineplex cineplex, AllCineplex cineplexes) throws IOException {
        Scanner sc = new Scanner(System.in);
        String cinema = "";

        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*            Adding Cinema Interface            *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        System.out.println("Adding Cinema:");

        System.out.printf("\nPlease enter Cinema number (Range: %d onwards):", cineplex.getNoOfCinemas());
        do {
            if (cinema.equals("-1")) continue;
            if (Integer.parseInt(cinema) < cineplex.getNoOfCinemas()) continue;
            cinema = sc.nextLine();
        } while (Objects.equals(cinema, ""));

        System.out.println("\nPlease select the type of Cinema:");
        System.out.println("\t1) Premium:");
        System.out.println("\t2) Regular:");

        int typeChoice = GetNumberInput.getInt(1, 2, -1) - 1;

        cineplex.CreateNewCinema(cinema, Cinema.CinemaType.values()[typeChoice], cineplexes);
    }
}
