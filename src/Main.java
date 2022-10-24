import Service.DateTime;

import java.util.Scanner;

public class Main {
    // This will be the Moblima main page
    // I think this is supposed to be Cineplex?
    public static void main(String[] args) {
        Cineplex cineplex;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Moblima!");
        System.out.println("Initiating Cineplex...");
        System.out.println("Enter number of cinemas available to this Cineplex:");
        cineplex = new Cineplex(sc.nextInt());

        System.out.println("Printing list of cinemas:");
        cineplex.printCinemas();
        cineplex.InitializeMovies();

    }
}
