package Customer;

import Cineplex.Cineplex;
import Movie.*;

import java.util.*;

public class Customer {
    private String MovieGoerName;
    private String MobileNumber;
    private String Email;
    //Purchase History
    private Double TID;
    private MovieTicket Ticket = null;

    public Customer(String movieGoerName, String mobileNumber, String email, Double TID) {
        MovieGoerName = movieGoerName;
        MobileNumber = mobileNumber;
        Email = email;
        this.TID = TID;
    }

    public void setMovieGoerName(String name) { this.MovieGoerName = name; }
    public void setMobileNumber(String number) { this.MobileNumber = number; }
    public void setEmail(String email) { this.Email = email; }
    public void setTID(Double transactionID) { this.TID = transactionID; }
    public void setTicket(MovieTicket tix) {
        this.Ticket = tix;
    }


    public String getMovieGoerName() {
        return MovieGoerName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getEmail() {
        return Email;
    }

    public Double getTID() {
        return TID;
    }

    public MovieTicket getTicket() {
        return this.Ticket;
    }

    public void printCustomerDetails() {
        System.out.printf("Name:\t%s\nNumber:\t%s\nEmail:\t%s\nTransaction ID:\t%.0f\n",
                this.MovieGoerName, this.MobileNumber, this.Email, this.TID);
        if (this.Ticket != null) {
            System.out.println("Ticket:\n");
            this.Ticket.printTicket();
        }
    }
}
