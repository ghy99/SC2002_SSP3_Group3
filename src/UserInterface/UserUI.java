package UserInterface;

import Admin.Admin;
import Cineplex.Cineplex;
import Customer.Customer;
import Service.TextDB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static UserInterface.AdminUI.AdminInterface;

public class UserUI {
    private static final String customerFile = "Customers.txt";
    private static final String adminFile = "admin.txt";

    private static ArrayList<Customer> customers = new ArrayList<Customer>();

    public static void UserInterface(ArrayList<Cineplex> cineplex, Double tid) throws IOException, NoSuchAlgorithmException {
//        String adminFile = "admin.txt";
//        ArrayList<Admin> admins = new ArrayList<Admin>(); // to store list of admins
//        ArrayList<Customer> customers = new ArrayList<Customer>(); // to store list of customers
//        Customer customer = null;
        Scanner sc = new Scanner(System.in);
        String username, number;

        do {
            System.out.println("Enter your Username:");
            username = sc.nextLine();

            if (Objects.equals(username, "-1")) {
                return;
            }
            else if (username.lastIndexOf("admin/") >= 0) {
                System.out.println("Enter your password:");
                number = sc.nextLine();
                String temp = username.substring(username.lastIndexOf("admin/") +6);
                AdminInterface(Admin.login(temp,number) , temp, number);
                return;
            }

            // Add if statement to check if username exist in admin.txt
            //System.out.println("Enter your password:");
//        String password = sc.nextLine();
            System.out.println("Enter your phone number:");
            number = sc.nextLine();


            if (Objects.equals(number, "-1")) {
                return;
            }

            // if username exist but wrong password keep asking for password
        } while (!checkCustomerName(username, number));

        // else, check if username exist in customer.txt or new username
        CustomerUI.CustomerInterface(cineplex, checkCustomerNumber(username, number));
    }

    public static boolean checkCustomerName(String name, String number) throws IOException {
        TextDB db = new TextDB();
        Customer temp = null;
        boolean userNameFlag = false, passwordFlag = false;

//        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers = db.readFromFile(customerFile, customers, temp);
        for (Customer customer : customers) {
            if (Objects.equals(customer.getMovieGoerName(), name)) {
                if (Objects.equals(customer.getMobileNumber(), number)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
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
