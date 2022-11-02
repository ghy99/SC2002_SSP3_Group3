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

    private static ArrayList<Customer> customers = new ArrayList<Customer>();

    public static void UserInterface(ArrayList<Cineplex> cineplex) throws Exception {
//        String adminFile = "admin.txt";
//        ArrayList<Admin> admins = new ArrayList<Admin>(); // to store list of admins
//        ArrayList<Customer> customers = new ArrayList<Customer>(); // to store list of customers
//        Customer customer = null;
        Scanner sc = new Scanner(System.in);
        String username, number;

        customers = TextDB.readFromFile(TextDB.Files.Customers.toString(), customers, null);
        do {
            System.out.println("Enter your Username: (input -1 to go back to menu)");
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
            System.out.println("Enter your phone number: (input -1 to go back to menu)");
            number = sc.nextLine();


            if (Objects.equals(number, "-1")) {

                return;
            }

            // if username exist but wrong password keep asking for password
        } while (!checkCustomerName(username, number));

        // else, check if username exist in customer.txt or new username
        CustomerUI.CustomerInterface(cineplex, customers, checkCustomerNumber(username, number));
    }

    public static boolean checkCustomerName(String name, String number) throws IOException {
//        ArrayList<Customer> customers = new ArrayList<Customer>();
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

//        ArrayList<Customer> customers = new ArrayList<Customer>();
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
