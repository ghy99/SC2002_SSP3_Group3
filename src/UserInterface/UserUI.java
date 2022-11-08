package UserInterface;

import Admin.Admin;
import Cineplex.AllCineplex;
import Customer.Customer;
import Service.GetNumberInput;
import Service.Settings;
import Service.TextDB;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import static UserInterface.AdminUI.AdminInterface;

/**
 * This is the User Interface class. Imported to call interface for logging in.
 *
 * @authors GAN HAO YI, CHEW ZHI QI
 */
public class UserUI {
    /**
     * A list of customers is loaded in this variable when database is accessed.
     */
    private static ArrayList<Customer> customers = new ArrayList<Customer>();

    /**
     * This is the User Interface. It is called in mainUI and it controls the interface for the Login option.
     *
     * @param cineplexes This object is passed into the Customer Interface to start the ticket booking process.
     * @throws Exception This Exception is thrown if reading customer database causes error.
     */
    public static void UserInterface(AllCineplex cineplexes) throws Exception {
        Scanner sc = new Scanner(System.in);
        String username, number;
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*                Login Interface                *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        System.out.println("Select an option:");
        System.out.println("\t1) Login\n\t2) Create an account");
        int choice = GetNumberInput.getInt(1,2 , -1);
        if (choice == -1) return;
        switch(choice) {
            case 1 -> {
                customers = TextDB.readFromFile(TextDB.Files.Customers.toString(), customers, null);
                do {
                    System.out.println("Enter your Username: (input -1 to go back to menu)");
                    username = sc.nextLine();

                    if (Objects.equals(username, "-1")) {
                        return;
                    } else if (username.lastIndexOf("admin/") >= 0) {
                        System.out.println("Enter your password:");
                        number = sc.nextLine();
                        String temp = username.substring(username.lastIndexOf("admin/") + 6);

                        AdminInterface(Admin.login(temp, number), temp, number, cineplexes);
                        return;
                    }
                    System.out.println("Enter your phone number: (input -1 to go back to menu)");
                    number = sc.nextLine();

                    if (Objects.equals(number, "-1")) {
                        return;
                    }

                    // if username exist but wrong password keep asking for password
                } while (!checkCustomerName(username, number));

                // else, check if username exist in customer.txt or new username
                CustomerUI.CustomerInterface(cineplexes, customers, checkCustomerNumber(username, number));
            }
            case 2 -> {
                System.out.println("\nCreating an Account:");
                String name;
                do {
                    System.out.println("Enter your Username:");
                    name = sc.nextLine();
                } while (Objects.equals(name, ""));

                String newnumber;
                do {
                    System.out.println("Enter your phone number:");
                    newnumber = sc.nextLine();
                } while (Objects.equals(newnumber, ""));

                String newEmail;
                do {
                    System.out.println("Enter your email:");
                    newEmail = sc.nextLine();
                } while (Objects.equals(newEmail, ""));

                Customer customer = new Customer();
                customer.setMovieGoerName(name);
                customer.setMobileNumber(newnumber);
                customer.setEmail(newEmail);
                customer.printCustomerDetails();
                cineplexes.createCustomerAccount(customer); // Not checking if customer created account
            }
        }
    }

    /**
     * This method checks that customer name exists in database
     * @param name   = name that user entered and used to check if the name exists
     * @param number = number that user entered to match with the database
     * @return boolean result
     */
    public static boolean checkCustomerName(String name, String number) {
//        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (Customer customer : customers) {
            if (Objects.equals(customer.getMovieGoerName(), name)) {
                if (Objects.equals(customer.getMobileNumber(), number)) {
                    return true;
                } else {
                    System.out.println("Password is wrong!");
                    return false;
                }
            }
        }
        System.out.println("Username and password is wrong!");
        return false;
    }

    /**
     * This method returns a Customer object if name and number that was entered matches a value in database
     * @param name   = the customers name
     * @param number used to check if the number is correct and matches the value in the database.
     * @return the customer object
     */
    public static Customer checkCustomerNumber(String name, String number) {
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
