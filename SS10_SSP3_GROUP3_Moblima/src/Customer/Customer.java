package Customer;

import Movie.MovieTicket;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;

import static Service.TextDB.Files.Customers;

/**
 * Customer class store name , mobile number , email , ticket
 */
public class Customer {
    /**
     * Name
     */
    private String MovieGoerName;
    /**
     * Mobile number
     */
    private String MobileNumber;
    /**
     * Email
     */
    private String Email;
    private int age;
    /**
     * Ticket
     */
    private ArrayList<MovieTicket> Ticket;

    /**
     * Constructor for Customer Object
     *
     * @param movieGoerName User's name
     * @param mobileNumber  Mobile number
     * @param email         Email
     */
    public Customer(String movieGoerName, String mobileNumber, String email) throws IOException {
        MovieGoerName = movieGoerName;
        MobileNumber = mobileNumber;
        Email = email;
    }

    /**
     * Empty Constructor to initialize Customer Object.
     */
    public Customer() {
    }

    /**
     * Set Method
     *
     * @param name - Name of Customer
     */
    public void setMovieGoerName(String name) {
        this.MovieGoerName = name;
    }

    /**
     * Set Method
     *
     * @param number - Mobile Number of Customer
     */
    public void setMobileNumber(String number) {
        this.MobileNumber = number;
    }

    /**
     * Set Method
     *
     * @param email - Email of Customer
     */
    public void setEmail(String email) {
        this.Email = email;
    }

    /**
     * Set Method
     *
     * @param tix - Object Ticket for Customer
     */
    public void setTicket(ArrayList<MovieTicket> tix) {
        this.Ticket = tix;
    }

    /**
     * Get Method
     *
     * @return Customer Name
     */
    public String getMovieGoerName() {
        return MovieGoerName;
    }

    /**
     * Get Method
     *
     * @return Customer Mobile Number
     */
    public String getMobileNumber() {
        return MobileNumber;
    }

    /**
     * Get Method
     *
     * @return Customer Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Get Method
     *
     * @return ArrayList of MovieTicket
     */
    public ArrayList<MovieTicket> getTicket() {
        return this.Ticket;
    }

    /**
     * Update User's name and store to database
     *
     * @param name              New name to be updated
     * @param customerArrayList customer list to update the database
     * @throws IOException Check if Customer Database exists
     */
    public void updateMovieGoerName(String name, ArrayList<Customer> customerArrayList) throws IOException {
        this.setMovieGoerName(name);
        TextDB.UpdateToTextDB(Customers.toString(), customerArrayList);
    }

    /**
     * Update User's mobile number and store to database
     *
     * @param mobileNumber      New mobile number to be updated
     * @param customerArrayList customer list to update database
     * @throws IOException Check if Customer Database exists
     */
    public void updateMobileNumber(String mobileNumber, ArrayList<Customer> customerArrayList) throws IOException {
        this.setMobileNumber(mobileNumber);
        TextDB.UpdateToTextDB(Customers.toString(), customerArrayList);
    }

    /**
     * Update User's mobile number and store to db
     *
     * @param email             New email to be updated
     * @param customerArrayList customer list to update database
     * @throws IOException Check if Customer Database exists
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
            for (MovieTicket ticket : this.Ticket) {
                ticket.printTicket();
            }
        }
    }
}