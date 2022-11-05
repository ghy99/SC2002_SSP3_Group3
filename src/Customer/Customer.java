package Customer;

import Cineplex.Cineplex;
import Movie.*;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static Service.TextDB.Files.Customers;

/**
 * Customer class store name , mobile number , email , ticket
 */
public class Customer {
    /** Name */
    private String MovieGoerName;
    /** Mobile number */
    private String MobileNumber;
    /** Email */
    private String Email;
    /** Ticket */
    private MovieTicket Ticket;

    /**
     * If new account
     * @param movieGoerName User's name
     * @param mobileNumber Mobile number
     * @param email Email
     * @param createAccount Is account created
     */
    public Customer(String movieGoerName, String mobileNumber, String email, boolean createAccount) throws IOException {
        MovieGoerName = movieGoerName;
        MobileNumber = mobileNumber;
        Email = email;


        if(createAccount)
        {
            TextDB.WriteToTextDB(File.separator + Customers.toString(), this);
        }
    }

    //
    public void setMovieGoerName(String name) throws IOException {
        this.MovieGoerName = name;
    }

    public void setMobileNumber(String number) {
        this.MobileNumber = number;
    }

    public void setEmail(String email) {
        this.Email = email;
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

    public MovieTicket getTicket() {
        return this.Ticket;
    }

    /**
     * Update User's name and store to db
     * @param name New name
     * @param customerArrayList customer list for update whole db
     * @throws IOException
     */
    public void updateMovieGoerName(String name, ArrayList<Customer> customerArrayList) throws IOException {
        this.setMovieGoerName(name);
        TextDB.UpdateToTextDB(Customers.toString(), customerArrayList);
    }

    /**
     * Update User's mobile number and store to db
     * @param mobileNumber New mobile number
     * @param customerArrayList customer list for update whole db
     * @throws IOException
     */
    public void updateMobileNumber(String mobileNumber, ArrayList<Customer> customerArrayList) throws IOException {
        this.setMobileNumber(mobileNumber);
        TextDB.UpdateToTextDB(Customers.toString(), customerArrayList);
    }

    /**
     * Update User's mobile number and store to db
     * @param email New email
     * @param customerArrayList customer list for update whole db
     * @throws IOException
     */
    public void updateEmail(String email, ArrayList<Customer> customerArrayList) throws IOException {
        this.setEmail(email);
        TextDB.UpdateToTextDB(Customers.toString(), customerArrayList);
    }

    /**
     * Print customer details with tickets
     */
    public void printCustomerDetails() {
        System.out.printf("Name:\t%s\nNumber:\t%s\nEmail:\t%s\n", this.MovieGoerName, this.MobileNumber, this.Email);
        if (this.Ticket != null) {
            System.out.println("Ticket:\n");
            this.getTicket().printTicket();
        }
    }

}
