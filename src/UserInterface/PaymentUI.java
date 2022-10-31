package UserInterface;
import Customer.Customer;
import Movie.MovieTicket;
import Service.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentUI {
    public static Double PaymentInterface(Customer customer) throws IOException {
        DateTime datetime = new DateTime();
        double ticketPrice = customer.getTicket().CalculatePrice(customer.getTicket()); // Get movie datetime
        System.out.printf("Your Ticket Price is: %.2f\n", ticketPrice);
        System.out.println("Ticket cost will automatically be deducted from your registered PayLah account.");
        System.out.println("Please do not exit this window or enter any values.\n...Paying...\n...Paying...\n...Paying...\n");
        System.out.print("Payment done! Your transaction ID is: ");
        Double tid = Double.parseDouble(datetime.ToTID(String.valueOf(customer.getTID())));
        System.out.printf("%.0f\n", tid);
        return tid;
    }
}
