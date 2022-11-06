package UserInterface;
import Customer.Customer;
import Movie.MovieTicket;
import Service.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the Payment Interface Class. Imported to call interface for the making the payment
 * @author GAN HAO YI, CHEW ZHI QI
 */
public class PaymentUI {
    /**
     * This method is the Payment Interface where customer's ticket price is calculated and the payment process is completed.
     * @param customer - tells me ticket information. (Might have to pass in Arraylist of Ticket.
     * @return change this to void.
     * @throws IOException
     */
    public static String PaymentInterface(Customer customer) throws IOException {
        // Check if movie is a blockbuster. If yes, +$1, else, no charge.
        // Get movie date, check holiday, if not holiday, check weekday / weekend, calc ticket price (Compare to settings.ticketcharges)
        // Check Cinema type (Regular Premium), check movie Dimensions(2D, 3D), calc ticket price (Compare to settings.ticketcharges)
        // Get number of tickets.
        // get movie genre, if movie genre < NC16, ask for how many children / adult / senior citizen price, cap at number of tickets.
        //                  if movie genre >= NC16, ask for adult / senior citizen price, cap at number of tickets.
        // Count number of seats booked, compare to age price, sum up and return price.
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
