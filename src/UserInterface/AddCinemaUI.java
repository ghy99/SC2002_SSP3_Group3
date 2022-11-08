package UserInterface;

import Admin.Admin;
import Cineplex.*;
import Service.GetNumberInput;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class AddCinemaUI {
    public static void AddCinemaInterface(Cineplex cineplex , AllCineplex cineplexes) throws IOException {
        Scanner sc = new Scanner(System.in);
        String cinema = "" ;
        System.out.println("5) Add Cinema (Enter -1 to exit):");

        System.out.println("\t Please enter cinema name:");
        do {
            if(cinema.equals("-1")) break;
            cinema = sc.nextLine();
        }while (Objects.equals(cinema, ""));

        System.out.println("\t Please enter cinema type:");
        System.out.println("\t\t 1)Premium:");
        System.out.println("\t\t 2)Regular:");

        int typeChoice = GetNumberInput.getInt(1,2,-1);

        cineplex.CreateNewCinema(cinema, Cinema.CinemaType.values()[typeChoice] , cineplexes);
    }
}
