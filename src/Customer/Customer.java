package Customer;

import Cineplex.Cineplex;
import Movie.*;
import Service.TextDB;

import java.io.IOException;
import java.util.*;

import static Service.TextDB.Files.Customers;

public class Customer {
    private String MovieGoerName;
    private String MobileNumber;
    private String Email;
    //Purchase History
    private MovieTicket Ticket = null;

    public Customer(String movieGoerName, String mobileNumber, String email , boolean createAccount) throws IOException {
        MovieGoerName = movieGoerName;
        MobileNumber = mobileNumber;
        Email = email;

        if(createAccount)
        {
            TextDB.WriteToTextDB("\\"+Customers, this);
        }
    }

    public Customer() {

    }

    public void setMovieGoerName(String name) { this.MovieGoerName = name; }
    public void setMobileNumber(String number) { this.MobileNumber = number; }
    public void setEmail(String email) { this.Email = email; }
    public void setTicket(MovieTicket tix) {
        this.Ticket = tix;
    }

    public void addTicket(MovieTicket tix) {
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

    public MovieTicket getTicket() {
        return this.Ticket;
    }

    public void printCustomerDetails() {
        System.out.printf("Name:\t%s\nNumber:\t%s\nEmail:\t%s\n",
                this.MovieGoerName, this.MobileNumber, this.Email);
        if (this.Ticket != null) {
            System.out.println("Ticket:\n");
            this.getTicket().printTicket();
        }
    }

}
