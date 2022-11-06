package Admin;

import Cineplex.*;
import Movie.*;
import Movie.TicketCharges;
import Service.DateTime;
import Service.GetNumberInput;
import Service.SHA256;
import Service.TextDB;
import Review.Review;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static Service.TextDB.ReadFromFile;
import static Service.TextDB.UpdateAdmin;

/**
 * @author CHEW ZHI QI, GAN HAO YI, SANSKKRITI JAIN, TAN JUE LIN
 * This is the Admin class. It stores the username and password of the current admin user.
 */
public class Admin {
    private String username;
    private String password;

    /**
     * This method is used to return the username and hashed password
     * @param username = username the admin has entered
     * @param password = password the admin has entered
     * @param hashed   = used to keep the password secure
     * @throws NoSuchAlgorithmException This exception is thrown to ensure SHA256 is available
     */
    public Admin(String username, String password, boolean hashed) throws NoSuchAlgorithmException {
        this.username = username;
        if (hashed) {
            this.password = password;
        } else {
            this.password = SHA256.toString(password);
        }
    }

    /**
     * Accessor of Username
     * @return username as string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Accessor of Password
     * @return password as string
     */
    public String getPassword() {
        return password;
    }

    public void addAdmin(Admin admin) throws IOException, NoSuchAlgorithmException {
        TextDB.WriteToTextDB(TextDB.Files.Admin.toString(), admin);
    }

    /**
     * This is a function for the user to login
     *
     * @param username the username to be verified
     * @param password the password to be verified
     * @return boolean value depicting whether login was successful or not
     * @throws IOException              if file not found
     * @throws NoSuchAlgorithmException if SHA256 algorithm not available
     */
    public static int login(String username, String password) throws IOException, NoSuchAlgorithmException {
        //fetch data of admin info from txt storage
        ArrayList<Admin> emptyAdminList = new ArrayList<Admin>();
        ArrayList<Admin> filledAdminList = ReadFromFile(emptyAdminList, "admin.txt");

        password = SHA256.toString(password);

        int flagNum = 0; //used to indicate if successfully logged in

        String dataName;
        String dataPassword;

        for (int i = 0; i < filledAdminList.size(); i++) {
            dataName = filledAdminList.get(i).getUsername();
            dataPassword = filledAdminList.get(i).getPassword();
            if (username.equals(dataName) && password.equals(dataPassword)) {
                System.out.println("Welcome " + username + ". You have logged in successfully to the admin portal.");
                flagNum = 1; //logged in successfully
            }
        }
        if (flagNum == 0) {
            System.out.println("Incorrect Username or Password.");
        }
        return flagNum;
    }

    //implement function for ticket prices HERE

    /**
     * This function is to change the ticket pricing according
     *
     * @throws IOException thrown if reading data from TicketPrice causes error
     */
    public void EditTicket(AllCineplex cineplex) throws IOException {
        System.out.println("Tickets are charged in the following manner:");
        cineplex.getTicketCharges().printTicketCharges();

        int cat = 0;
        do {
            System.out.println("Select category that you want to change: (Enter -1 to exit)");
            System.out.println("1) Age");
            System.out.println("2) Day of the week");
            System.out.println("3) Movie Dimension");
            System.out.println("4) Type of Cinema");
            System.out.println("Which do you want to edit:");
            cat = GetNumberInput.getInt(1, 4, -1);
            switch (cat) {
                case 1 -> {
                    System.out.println("Please select age price to edit \n");
                    System.out.println("1) Student price");
                    System.out.println("2) Adult price");
                    System.out.println("3) Senior Citizen price");

                    int choice = GetNumberInput.getInt(1, 3, -1);
                    System.out.println("What is the new value:");
                    Double newvalue = GetNumberInput.getDouble(0, -1);

                    System.out.println("Please select age price to edit \n");

                    cineplex.getTicketCharges().returnAge().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case 2 -> {
                    System.out.println("1) Monday - Friday");
                    System.out.println("2) Saturday - Sunday");
                    System.out.println("3) Public Holiday");

                    int choice = GetNumberInput.getInt(1, 3, -1);
                    System.out.println("What is the new value:");
                    Double newvalue = GetNumberInput.getDouble(0, -1);

                    System.out.println("Please select age price to edit \n");

                    cineplex.getTicketCharges().returnDay().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case 3 -> {
                    System.out.println("1) 2D Movie");
                    System.out.println("2) 3D Movie");

                    int choice = GetNumberInput.getInt(1, 2, -1);
                    System.out.println("What is the new value:");
                    Double newvalue = GetNumberInput.getDouble(0, -1);

                    System.out.println("Please select age price to edit \n");

                    cineplex.getTicketCharges().returnDim().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case 4 -> {
                    System.out.println("1) Regular Cinema");
                    System.out.println("2) Premium Cinema");

                    int choice = GetNumberInput.getInt(1, 2, -1);
                    System.out.println("What is the new value:");
                    Double newvalue = GetNumberInput.getDouble(0, -1);

                    System.out.println("Please select age price to edit \n");

                    cineplex.getTicketCharges().returnType().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case -1 -> {
                    System.out.println("Exiting...");
                    break;
                }
            }

        } while (cat != -1);


    }

    /**
     * This function is used to manage the add/edit and delete functions for the Holiday Dates
     *
     * @param choice2 for the user to input what they want to do
     * @throws IOException to ensure the input has no error
     */
    public void HolidayDateFunctions(AllCineplex cineplex, int choice2) throws IOException {
        Scanner scan = new Scanner(System.in);
        switch (choice2) {
            case 1 -> {
                System.out.println("\tAdd Holiday Dates");
                System.out.println("\t\tInput Date in DD-MM-YYYY format");
                String date;
                do {
                    date = scan.nextLine();
                    if (DateTime.StringToDateOnly(date) != null) {
                        cineplex.AddHoliday(date);
                        break;
                    } else
                        System.out.println("Error time format holiday not added");

                } while (DateTime.StringToDateOnly(date) == null);
            }
            case 2 -> {
                System.out.println("\tEdit holiday dates");

                System.out.println("\t\tSelect holiday dates to change");
                for (int i = 0; i < cineplex.getHoliday().size(); i++) {
                    System.out.printf("%d) %s \n", i + 1, cineplex.getHoliday().get(i));
                }

                int index = GetNumberInput.getInt(1, cineplex.getHoliday().size(), -1) - 1;
                String newDate;
                do {
                    System.out.println("Input New Date in DD-MM-YYYY format");
                    newDate = scan.nextLine();
                    if (DateTime.StringToDateOnly(newDate) != null) {
                        cineplex.editHoliday(index, newDate);
                        break;
                    } else System.out.println("Error time format time not change");
                } while (DateTime.StringToDateOnly(newDate) == null);
            }
            case 3 -> {
                System.out.println("\tDelete Holiday Dates");

                System.out.println("\t\tSelect holiday dates to change (Enter -1 to exit):");
                for (int i = 0; i < cineplex.getHoliday().size(); i++) {
                    System.out.printf("%d) %s \n", i + 1, cineplex.getHoliday().get(i));
                }
                int index = GetNumberInput.getInt(1, cineplex.getHoliday().size(), -1) - 1;
                if (index != -1)
                    cineplex.deleteHoliday(index);
                else {
                    System.out.println("Exiting...");
                }
            }
        }
    }


    /**
     * This function is used to get the users input on what they want to do in the setting menu
     *
     * @param choice2 = the input of the function they want to do
     * @throws IOException              is thrown if the reading of the input causes error
     * @throws NoSuchAlgorithmException is thrown if the function is not found
     */
    public void SettingFunctions(AllCineplex cineplex, int choice2) throws IOException, NoSuchAlgorithmException {
        switch (choice2) {
            case 1 -> {
                System.out.println("\t 1. Control the display of movie rankings to customers (Enter -1 to exit):");
                System.out.println("\t\t1) Display by rating\n");
                System.out.println("\t\t2) Display by ticket sales\n");
                System.out.println("\t\t3) Display both\n");
                System.out.println("\t\t4) Disable top raking display\n");
                switch (GetNumberInput.getInt(1, 4, -1)) {
                    case 1 -> {
                        cineplex.setUserSort(false, true);
                    }
                    case 2 -> {
                        cineplex.setUserSort(true, false);
                    }
                    case 3 -> {
                        cineplex.setUserSort(true, true);
                    }
                    case 4 -> {
                        cineplex.setUserSort(false, false);
                    }
                    case -1 -> {
                        System.out.println("\n-1 was selected. Exiting...\n");
                    }
                }
            }
            case 2 -> {
                System.out.println("\t2) Help new staffs to register new Admin Account");
                CreateAdmin();
            }
        }
    }

    /**
     * This function is used to create new admin accounts
     *
     * @throws IOException              is thrown to ensure the
     * @throws NoSuchAlgorithmException
     */
    private void CreateAdmin() throws IOException, NoSuchAlgorithmException {
        String filename = "admin.txt";
        ArrayList<Admin> AdminList = null;

        AdminList = getAdminList(filename);
        Admin newAdmin = null;
        newAdmin = AddnewAdmin();
        AdminList.add(newAdmin);
        UpdateAdmin(filename, AdminList);
    }

    /**
     * This function is used to read the data from the Admin.txt database
     *
     * @param filename = the file admin.txt that needs to be accessed
     * @return the new created admin list object
     * @throws IOException              is thrown if the reading the content of the file causes error
     * @throws NoSuchAlgorithmException is thrown if accessing teh database causes error
     */
    public static ArrayList<Admin> getAdminList(String filename) throws IOException, NoSuchAlgorithmException {
        ArrayList<Admin> AdminList = new ArrayList<Admin>();
        AdminList = ReadFromFile(AdminList, filename);
        return AdminList;
    }

    /**
     * This is used to get the details from the admin of the new account they want to create
     *
     * @return Admin Object with the new username and password
     * @throws NoSuchAlgorithmException is thrown if the reading the SHA256 algorithm causes an error
     */
    private static Admin AddnewAdmin() throws NoSuchAlgorithmException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Create New Admin");
        String username, password;
        System.out.println("\t Enter the new admin username");
        username = scan.nextLine();
        System.out.println("\t Enter the new admin password");
        password = SHA256.toString(scan.nextLine());
        return new Admin(username, password, true);
    }
}
