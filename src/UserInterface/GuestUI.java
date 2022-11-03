package UserInterface;

import Admin.Admin;
import Cineplex.*;
import Customer.Customer;
import Movie.Movie;
import Movie.MovieTicket;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class GuestUI {
    public static void UserInterface(ArrayList<Cineplex> cineplex) throws IOException {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("*************Entering guest mode***************");
        Customer customer = new Customer("", "", "", false, true);

        do {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t1) Select Cineplex.");
            System.out.println("\t2) Print your Booking History.");
            System.out.println("\tEnter '11' to exit!");

            do {
                while (!sc.hasNextInt()) {
                    System.out.println("That's not a number!");
                    sc.next(); // this is important!
                }
                choice = sc.nextInt();
                sc.nextLine();
            } while (choice < -1);
            switch (choice) {
                case 1 -> {
                    customer.setTicket(CineplexUI.CineplexInterface(cineplex));
                    String email;
                    do {
                        System.out.println("Please enter your email : (So that you will be able to check your transaction later)");
                        email = sc.nextLine();
                    } while (Objects.equals(email, ""));
                    customer.setEmail(email);
                    customer.printCustomerDetails();
                    TextDB.WriteToTextDB(TextDB.Files.TransactionHistroy.toString(), customer, customer.getTicket());
                    System.out.println("Moving to payment (Not implemented yet).");
                    customer.getTicket().printTicket();
                }
                case 2 -> {
                    System.out.println("Please give input your email that used to booked:");
                    String Email = sc.next();
                    ArrayList<MovieTicket> movieTickets = TextDB.ReadFromFile(TextDB.Files.TransactionHistroy.toString(), Email);
                    for( MovieTicket mt : movieTickets)
                    {
                        mt.printTicket();
                    }
                }
            }
        } while (choice < 10);

    }
}