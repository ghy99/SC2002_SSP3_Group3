package Customer;

import Cineplex.Cineplex;
import Movie.*;

import java.util.*;

public class Customer {
    private String MovieGoerName;
    private String MobileNumber;
    private String Email;
    //Purchase History
    private ArrayList<MovieTicket> Ticket = new ArrayList<MovieTicket>();

    public Customer(String movieGoerName, String mobileNumber, String email) {
        MovieGoerName = movieGoerName;
        MobileNumber = mobileNumber;
        Email = email;
    }

    public void setMovieGoerName(String name) { this.MovieGoerName = name; }
    public void setMobileNumber(String number) { this.MobileNumber = number; }
    public void setEmail(String email) { this.Email = email; }
    public void setTicket(ArrayList<MovieTicket> tix) {
        this.Ticket = tix;
    }

    public void addTicket(MovieTicket tix) {
        this.Ticket.add(tix) ;
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


    public ArrayList<MovieTicket> getTicket() {
        return this.Ticket;
    }

    public void printCustomerDetails() {
        System.out.printf("Name:\t%s\nNumber:\t%s\nEmail:\t%s\n",
                this.MovieGoerName, this.MobileNumber, this.Email);
        if (this.Ticket.size() != 0) {
            for(MovieTicket mt : this.getTicket())
            {
                System.out.println("Ticket:\n");
                mt.printTicket();
            }
        }
    }
}
