package UserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Cineplex.*;
import Customer.Customer;
import Movie.MovieTicket;
import Service.TextDB;
import Service.GetNumberInput;

public class CustomerUI {
    public static void CustomerInterface(AllCineplex cineplexes, ArrayList<Customer> customerArrayList, Customer customer) throws IOException {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        customer.printCustomerDetails();
        do {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t1) Select Cineplex.");
            System.out.println("\t2) Change your name.");
            System.out.println("\t3) Change your number.");
            System.out.println("\t4) Change your email.");
            System.out.println("\t5) Print your details.");
            System.out.println("\t6) Print your Booking History.");
            System.out.println("\tEnter '11' to exit!");

            choice = GetNumberInput.getInt();
            switch (choice) {
                case 1 -> {
                    customer.setTicket(CineplexUI.CineplexInterface(cineplexes));
                    customer.printCustomerDetails();
                    System.out.println("Moving to payment (Not implemented yet).");
                    TextDB.WriteToTextDB(TextDB.Files.TransactionHistroy.toString(), customer, customer.getTicket());
                    customer.getTicket().printTicket();
//                   PaymentUI.PaymentInterface(customer); // CHANGE TID TO DOUBLE / STRING. INT CANT CONTAIN.
                    //customer.setTID(PaymentUI.PaymentInterface(customer));
                }
                case 2 -> {
                    System.out.println("Enter your new name: ");
                    String newName = sc.nextLine();
//                    sc.nextLine();
                    customer.updateMovieGoerName(newName, customerArrayList);
                }
                case 3 -> {
                    System.out.println("Enter your new number: ");
                    String newNumber = sc.nextLine();
//                    sc.nextLine();
                    customer.updateMobileNumber(newNumber, customerArrayList);
                }
                case 4 -> {
                    System.out.println("Enter your new email: ");
                    String newEmail = sc.nextLine();
//                    sc.nextLine();
                    customer.updateEmail(newEmail, customerArrayList);
                }
                case 5 -> {
                    customer.printCustomerDetails();
                }
                case 6 -> {
                    ArrayList<MovieTicket> movieTickets = TextDB.ReadFromFile(TextDB.Files.TransactionHistroy.toString(), customer.getEmail());
                    for( MovieTicket mt : movieTickets)
                    {
                        mt.printTicket();
                    }
                }
                default -> {
                    break;
//                    System.out.println("Invalid Input. Try again.");
                }
            }
        } while (choice < 10);
    }

}
