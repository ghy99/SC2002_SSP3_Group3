import Customer.Customer;
import Movie.*;
import Service.DateTime;
import Cineplex.Cineplex;
import Service.TextDB;
import UserInterface.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // This will be the Moblima main page
    // I think this is supposed to be Cineplex?

//    private static ArrayList<Cineplex> cineplexes;
//
//    public static void InitializeCineplexes() throws IOException {
//
//        System.out.println("Initializing list of Cineplexes\n...\n...");
//        TextDB db = new TextDB();
//        cineplexes = new ArrayList<Cineplex>();
//        String filename = "Cineplexes.txt";
//        try {
//            cineplexes = db.readFromFile(cineplexes, filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (Cineplex cineplex : cineplexes) {
//            cineplex.InitializeMovies();
//        }
//        System.out.println("Cineplexes are initialized.\n");
//    }
    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(System.in);
        MainUI.start();
//        System.out.println("Welcome to Moblima!");
//        System.out.println("Initiating Cineplex..."); // Convert this portion to read from textfile
//        InitializeCineplexes();
//        int tid = 1;
//        int option = 1;
//        do {
//            System.out.println("Select option: \n1)Administrator\n2)Customer");
//            option = sc.nextInt();
//            sc.nextLine();
//            switch (option) {
//                case 1: // Do admin stuff
//                    for (Cineplex cineplex : cineplexes) {
//                        System.out.printf("Cineplex Name: %s\n", cineplex.getCineplexName());
//                        System.out.printf("Number of Cinemas available: %d\n", cineplex.getNoOfCinemas());
//                        ArrayList<Movie> tempListOfMovies = cineplex.getListOfMovies();
//                        for (Movie tempListOfMovie : tempListOfMovies) {
//                            tempListOfMovie.printMovieDetails();
//                        }
//                    }
//                    break;
//                case 2: // do Customer Stuff
//                    CustomerUI.CustomerInterface(cineplexes);
////                    System.out.println("Enter Your(Customer) Name, Phone Number, Email (Separated by ' | '):");
////                    String[] details = sc.nextLine().split(" [|] ");
////
////                    String name = details[0];
////                    String number = details[1];
////                    String email = details[2];
////                    Customer customer = new Customer(name, number, email, tid++);
////                    int choice;
////                    do {
////                        System.out.println("What would you like to do?");
////                        System.out.println("1) Select Cineplex");
////                        System.out.println("2) Change Cineplex");
////
////                        choice = sc.nextInt();
////                        switch (choice) {
////                            case 1 -> {
////                                MovieTicket ticket = new MovieTicket();
////                                for (int i = 0; i < cineplexes.size(); i++) {
////                                    System.out.printf("%d: Cineplex Name: %s\n", i + 1, cineplexes.get(i).getCineplexName());
////                                }
////                                int selectCineplex = sc.nextInt() - 1;
////                                ticket.setChosenCineplex(cineplexes.get(selectCineplex));
////                                System.out.println("These are the movies available:");
////                                ArrayList<Movie> movieList = ticket.getChosenCineplex().getListOfMovies();
////                                for (int i = 0; i < movieList.size(); i++) {
////                                    System.out.printf("%d) %s\n", i + 1, movieList.get(i).getMovieTitle());
////                                }
////                                System.out.println("Select Movie: ");
////                                int moviechoice = sc.nextInt() - 1;
////                                ticket.setChosenMovie(movieList.get(moviechoice));
////                                customer.setTicket(ticket);
////                                customer.printCustomerDetails();
////                                customer.getTicket().printTicket();
////                            }
////                            case 2 -> choice = 1;
////                            default -> choice = -1;
////                        }
////                    } while (choice > 0);
//                    break;
//            }
//        } while (option > 0);

    }
}
