package UserInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Cineplex.Cineplex;
import Customer.Customer;

public class CustomerUI {
    public static void CustomerInterface(ArrayList<Cineplex> cineplex) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your (Customer) name: ");
        String name = sc.nextLine();
        System.out.println("Enter your phone number: ");
        String number = sc.nextLine();
        System.out.println("Enter your email (for tracking purpose): ");
        String email = sc.nextLine();

        Customer customer = new Customer(name, number, email, 1);
        customer.printCustomerDetails();
        do {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t1) Select Cineplex.");
            System.out.println("\t2) Change your name.");
            System.out.println("\t3) Change your number.");
            System.out.println("\t4) Change your email.");
            System.out.println("\t5) Print your details.");
            System.out.println("\tEnter '-1' to exit!");

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
                    customer.printCustomerDetails();
                    System.out.println("Moving to payment (Not implemented yet).");
                }
                case 2 -> {
                    System.out.println("Enter your new name: ");
                    String newName = sc.nextLine();
//                    sc.nextLine();
                    customer.setMovieGoerName(newName);
                }
                case 3 -> {
                    System.out.println("Enter your new number: ");
                    String newNumber = sc.nextLine();
//                    sc.nextLine();
                    customer.setMobileNumber(newNumber);
                }
                case 4 -> {
                    System.out.println("Enter your new email: ");
                    String newEmail = sc.nextLine();
//                    sc.nextLine();
                    customer.setEmail(newEmail);
                }
                case 5 -> {
                    customer.printCustomerDetails();
                }
                default -> {
                    System.out.println("Invalid Input. Try again.");
                }
            }
        } while (choice >= 0);
    }
}
