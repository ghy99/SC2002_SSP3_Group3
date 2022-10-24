package Customer;

import Cineplex.Cineplex;
import Movie.*;

import java.util.*;

public class Customer {
    private String MovieGoerName;
    private String MobileNumber;
    private String Email;
    //Purchase History
    private int TID;
    private MovieTicket Ticket;

    public Customer(String movieGoerName, String mobileNumber, String email, int TID) {
        MovieGoerName = movieGoerName;
        MobileNumber = mobileNumber;
        Email = email;
        this.TID = TID;
    }

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

    public int getTID() {
        return TID;
    }

    public MovieTicket getTicket() {
        return this.Ticket;
    }



    public void printCustomerDetails() {
        System.out.printf("Name:\t%s\nNumber:\t%s\nEmail:\t%s\nTransaction ID:\t%d\n",
                this.MovieGoerName, this.MobileNumber, this.Email, this.TID);
    }
}
