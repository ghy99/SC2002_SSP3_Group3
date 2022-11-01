package UserInterface;
import Movie.*;
import Cineplex.Cineplex;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Done by Gan Hao Yi
 * Controls main through initializing everything needed.
 *
 */

public class MainUI {
    private static ArrayList<Cineplex> cineplexes;
    private static Double tid;

    public static void InitializeCineplexes() throws IOException {
        System.out.println("Initializing Cineplexes...\n...\n...");
        TextDB db = new TextDB();
        cineplexes = new ArrayList<Cineplex>();
        String filename = "Cineplexes.txt";
        try {
            cineplexes = db.readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Cineplex cineplex : cineplexes) {
            cineplex.InitializeMovies();
        }
        System.out.println("Cineplexes are initialized\n");
    }

    public static void start() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Moblima!");
        System.out.println("Initializing");
        InitializeCineplexes();
        tid = 1.0;
        int option = 1;
        do {
            System.out.println("Select option: \n1) Administrator\n2) Customer");
            option = sc.nextInt();
//            sc.nextLine();
            switch(option) {
                case 1 -> {
                    // Call adminUI
                    System.out.println("Calling adminUI");
                    AdminUI.AdminInterface();
                }
                case 2 -> {
                    CustomerUI.CustomerInterface(cineplexes, tid++);
                }
            }
        } while (option > 0);

    }
}
