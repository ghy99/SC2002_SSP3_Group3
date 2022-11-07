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

/**
 * This is the Customer User Interface. This is imported to call the Customer Interface to access their account.
 * @authors GAN HAO YI, CHEW ZHI QI
 */
public class CustomerUI {
    /**
     * This is the Customer Interface for the customer to access their account and make changes to their details or
        print their details and booking history.
     * @param cineplexes = This object is passed into the Customer Interface to start the ticket booking process.
     * @param customerArrayList = this is passed to update the customer details in the customers file.
     * @param customer = This is passed to access the customer details.
     * @throws IOException is thrown if there is an error in reading the customer file
     */
    public static void CustomerInterface(AllCineplex cineplexes, ArrayList<Customer> customerArrayList, Customer customer) throws IOException {
        System.out.println("************* Entering Customer Mode ***************");
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

            choice = GetNumberInput.getInt(1, 6, 11);
            switch (choice) {
                case 1 -> {
                    //customer.setTicket(CineplexUI.CineplexInterface(cineplexes));
                    customer.printCustomerDetails();
                    System.out.println("Moving to payment (Not implemented yet).");
                    TextDB.WriteToTextDB(TextDB.Files.TransactionHistory.toString(), customer, customer.getTicket());
                    customer.getTicket().printTicket();
//                   PaymentUI.PaymentInterface(customer); // CHANGE TID TO DOUBLE / STRING. INT CANT CONTAIN.
                    //customer.setTID(PaymentUI.PaymentInterface(customer));
                }
                case 2 -> {
                    System.out.println("Enter your new name: ");
                    String newName = sc.nextLine();
                    customer.updateMovieGoerName(newName, customerArrayList);
                }
                case 3 -> {
                    System.out.println("Enter your new number: ");
                    String newNumber = sc.nextLine();
                    customer.updateMobileNumber(newNumber, customerArrayList);
                }
                case 4 -> {
                    System.out.println("Enter your new email: ");
                    String newEmail = sc.nextLine();
                    customer.updateEmail(newEmail, customerArrayList);
                }
                case 5 -> {
                    customer.printCustomerDetails();
                }
                case 6 -> {
                    ArrayList<MovieTicket> movieTickets = TextDB.ReadFromFile(TextDB.Files.TransactionHistory.toString(), customer.getEmail());
                    for( MovieTicket mt : movieTickets)
                    {
                        mt.printTicket();
                    }
                }
                default -> {
                    System.out.println("Invalid Input. Try again.");
                }
            }
        } while (choice < 10);
    }

}
