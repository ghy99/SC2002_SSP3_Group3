package UserInterface;
import Customer.Customer;
import Movie.MovieTicket;
import Service.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentUI {
    public static String PaymentInterface(Customer customer) throws IOException {
        DateTime datetime = new DateTime();
        MovieTicket currTicket = customer.getTicket();
        double ticketPrice = MovieTicket.CalculatePrice(currTicket); // Get movie datetime
        System.out.printf("Your Ticket Price is: %.2f\n", ticketPrice);
        System.out.println("Ticket cost will automatically be deducted from your registered PayLah account.");
        System.out.println("Please do not exit this window or enter any values.\n...Paying...\n...Paying...\n...Paying...\n");
        System.out.print("Payment done! Your transaction ID is: ");
        currTicket.setTID(currTicket.getTID());
        System.out.printf("%s\n", currTicket.getTID());
        return currTicket.getTID();
    }
}
