package UserInterface;
import Admin.Admin;
import Cineplex.Cineplex;
import Customer.Customer;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UserUI {
    private static final String customerFile = "Customers.txt";
    private static final String adminFile = "admin.txt";

    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    public static void UserInterface(ArrayList<Cineplex> cineplex, Double tid) throws IOException {
        String adminFile = "admin.txt";


        ArrayList<Admin> admins = new ArrayList<Admin>(); // to store list of admins
        ArrayList<Customer> customers = new ArrayList<Customer>(); // to store list of customers
        Customer customer = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Username:");
        String username = sc.nextLine();
        // Add if statement to check if username exist in admin.txt
        System.out.println("Enter your password:");
//        String password = sc.nextLine();
        // else, check if username exist in customer.txt
        if (checkCustomerName(username)) {
            System.out.println("Enter your phone number:");
            String number = sc.nextLine();
            if (checkCustomerNumber(username, number) != null) {
                customer = checkCustomerNumber(username, number);
            }
            CustomerUI.CustomerInterface(cineplex, customer);
            // if username does not exist in customer.txt, register account.

        }
    }

    public static boolean checkCustomerName(String name) throws IOException {
        TextDB db = new TextDB();
        Customer temp = null;
//        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers = db.readFromFile(customerFile, customers, temp);
        for (Customer customer : customers) {
            if (Objects.equals(customer.getMovieGoerName(), name)) {
                return true;
            }
        }
        return false;
    }

    public static Customer checkCustomerNumber(String name, String number) throws IOException {
        TextDB db = new TextDB();
        Customer temp = null;
//        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers = db.readFromFile(customerFile, customers, temp);
        for (Customer customer : customers) {
            if (
                    Objects.equals(customer.getMovieGoerName(), name)
                    && Objects.equals(customer.getMobileNumber(), number)
            ) {
                return customer;
            }
        }
        return null;
    }
}
