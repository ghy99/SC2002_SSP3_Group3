package Admin;

import Cineplex.*;
import Service.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


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

    /**
     * This is a function for the user to login
     * @param username the username to be verified
     * @param password the password to be verified
     * @return boolean value depicting whether login was successful or not
     * @throws IOException              if file not found
     * @throws NoSuchAlgorithmException if SHA256 algorithm not available
     */
    public static int login(String username, String password) throws IOException, NoSuchAlgorithmException {
        //fetch data of admin info from txt storage
        ArrayList<Admin> emptyAdminList = new ArrayList<Admin>();
        ArrayList<Admin> filledAdminList = TextDB.ReadFromFile(emptyAdminList, "admin.txt");
        password = SHA256.toString(password);
        int flagNum = 0; //used to indicate if successfully logged in
        String dataName;
        String dataPassword;

        for (Admin admin : filledAdminList) {
            dataName = admin.getUsername();
            dataPassword = admin.getPassword();
            if (username.equals(dataName) && password.equals(dataPassword)) {
                System.out.println("Welcome " + username + ". You have successfully logged in to the admin portal.");
                flagNum = 1; //logged in successfully
            }
        }
        if (flagNum == 0) {
            System.out.println("Incorrect Username or Password.");
        }
        return flagNum;
    }

    /**
     * This function is to change the ticket pricing charges
     * @param cineplex Current instance of cinepelx
     * @throws IOException thrown if reading data from TicketPrice causes error
     */
    public void EditTicket(AllCineplex cineplex) throws IOException {
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*            Ticket Price Interface             *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        System.out.println("\nTickets are charged in the following manner:");
        cineplex.getTicketCharges().printTicketCharges();
        int cat = 0;
        do {
            System.out.println("\nSelect category that you want to change:");
            System.out.println("1) Age");
            System.out.println("2) Day of the week");
            System.out.println("3) Movie Dimension");
            System.out.println("4) Type of Cinema");
            cat = GetNumberInput.getInt(1, 4, -1);
            switch (cat) {
                case 1 -> {
                    System.out.println("\nPlease select the price of different age to edit:");
                    System.out.println("\t1) Student price");
                    System.out.println("\t2) Adult price");
                    System.out.println("\t3) Senior Citizen price");

                    int choice = GetNumberInput.getInt(1, 3, -1) - 1;
                    System.out.println("What is the new value: (Range: 0 - 100)");
                    Double newvalue = GetNumberInput.getDouble(0, 100, -1);

                    cineplex.getTicketCharges().returnAge().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case 2 -> {
                    System.out.println("\nPlease select the price of the type of day to edit:");
                    System.out.println("\t1) Monday - Friday");
                    System.out.println("\t2) Saturday - Sunday");
                    System.out.println("\t3) Public Holiday");

                    int choice = GetNumberInput.getInt(1, 3, -1) - 1;
                    System.out.println("What is the new value: (Range 0 - 100)");
                    Double newvalue = GetNumberInput.getDouble(0, 100, -1);

                    cineplex.getTicketCharges().returnDay().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case 3 -> {
                    System.out.println("\nPlease select the price of the type of Movie to edit:");
                    System.out.println("\t1) 2D Movie");
                    System.out.println("\t2) 3D Movie");

                    int choice = GetNumberInput.getInt(1, 2, -1) - 1;
                    System.out.println("What is the new value:");
                    Double newvalue = GetNumberInput.getDouble(0, 100, -1);

                    cineplex.getTicketCharges().returnDim().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case 4 -> {
                    System.out.println("\nPlease select the price of the type of Cinema to edit:");
                    System.out.println("\t1) Regular Cinema");
                    System.out.println("\t2) Premium Cinema");

                    int choice = GetNumberInput.getInt(1, 2, -1) - 1;
                    System.out.println("What is the new value:");
                    Double newvalue = GetNumberInput.getDouble(0, 100, -1);

                    cineplex.getTicketCharges().returnType().get(choice).set(1, newvalue.toString());
                    TextDB.UpdateToTextDB(TextDB.Files.TicketPrice.toString(), cat, choice, newvalue);
                }
                case -1 -> {
                    System.out.println("Exiting...");
                }
            }
        } while (cat != -1);
    }

    /**
     * This function is used to manage the add / edit / delete functions for the Holiday Dates
     * @param cineplex Cureent instance of cineplex
     * @throws IOException
     */
    public void HolidayDateFunctions(AllCineplex cineplex) throws IOException {
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*             Edit Holiday Interface            *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        System.out.println("Current list of Holiday:");
        printHoliday(cineplex);
        System.out.println("\nHoliday Dates, Please select one of the following functions:");
        System.out.println("\t1) Add Holiday Dates");
        System.out.println("\t2) Edit Holiday Dates");
        System.out.println("\t3) Delete Holiday Dates");
        int choice2 = GetNumberInput.getInt(1, 3, -1);
        String confirmDate = "";
        Scanner scan = new Scanner(System.in);
        switch (choice2) {
            case 1 -> {
                String date;
                System.out.println("\nAdding Holiday Date:");
                System.out.println("Input Date in DD-MM-YYYY format:");
                do {
                    date = scan.nextLine();
                    if (DateTime.StringToDateOnly(date) != null) {
                        confirmDate = DateTime.convertDate(DateTime.StringToDateOnly(date).getTime());
                        if (date.equals(confirmDate)) {
                            cineplex.AddHoliday(date);
                            System.out.println("Date: " + date + " is added.");
                            break;
                        } else {
                            System.out.println("Invalid date: " + date + " is entered. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid date is entered.\nHoliday is not added.");
                    }
                } while (DateTime.StringToDateOnly(date) == null || !date.equals(confirmDate));
            }
            case 2 -> {
                System.out.println("\nEditing holiday date:");
                System.out.println("Select holiday dates to change:");
                printHoliday(cineplex);
                int index = GetNumberInput.getInt(1, cineplex.getHoliday().size(), -1) - 1;
                if (index == -2) break;
                String newDate;
                do {
                    System.out.println("Input New Date in DD-MM-YYYY format:");
                    newDate = scan.nextLine();
                    if (DateTime.StringToDateOnly(newDate) != null) {
                        confirmDate = DateTime.convertDate(DateTime.StringToDateOnly(newDate).getTime());
                        if (newDate.equals(confirmDate)) {
                            cineplex.editHoliday(index, newDate);
                            System.out.println("Date: " + newDate + " is edited.");
                            break;
                        } else {
                            System.out.println("Invalid date: " + newDate + " is entered. Please try again.");
                        }
                    } else System.out.println("Invalid date is entered.\nHoliday is not edited.");
                } while (DateTime.StringToDateOnly(newDate) == null || !newDate.equals(confirmDate));
            }
            case 3 -> {
                System.out.println("\nDelete Holiday Dates");
                System.out.println("Select holiday dates to change:");
                printHoliday(cineplex);
                int index = GetNumberInput.getInt(1, cineplex.getHoliday().size(), -1) - 1;
                if (index != -2)
                    cineplex.deleteHoliday(index);
                else {
                    System.out.println("Exiting...");
                }
            }
        }
    }

    /**
     * This function is used to get the users input on what they want to do in the setting menu
     * @throws IOException              is thrown if the reading of the input causes error
     * @throws NoSuchAlgorithmException is thrown if the function is not found
     */
    public void SettingFunctions(AllCineplex cineplex) throws IOException, NoSuchAlgorithmException {
        System.out.println(Settings.ANSI_CYAN);
        System.out.println("*************************************************");
        System.out.println("*               Settings Interface              *");
        System.out.println("*************************************************");
        System.out.println(Settings.ANSI_RESET);
        System.out.println("\nSelect one of the following functions:");
        System.out.println("\t1) Control the display of movie rankings to customers");
        System.out.println("\t2) Register new Administration Account");
        int choice2 = GetNumberInput.getInt(1, 2, -1);
        switch (choice2) {
            case 1 -> {
                System.out.println("\nControl the display of movie rankings to customers:");
                System.out.println("\t1) Display by rating");
                System.out.println("\t2) Display by ticket sales");
                System.out.println("\t3) Display both");
                System.out.println("\t4) Disable top rating display");
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
                        System.out.println("\nExiting...\n");
                    }
                }
            }
            case 2 -> {
                System.out.println("\nRegistering New Administration Account");
                CreateAdmin();
            }
        }
    }

    /**
     * Print list of holiday
     * @param cineplex - Get global list of cineplex to print
     */
    public void printHoliday(AllCineplex cineplex){
        for (int i = 0; i < cineplex.getHoliday().size(); i++) {
            System.out.printf("\t%d) %s\n", i + 1, cineplex.getHoliday().get(i));
        }
    }

    /**
     * This function is used to create new admin accounts
     * @throws IOException              is thrown to ensure admin database exists.
     * @throws NoSuchAlgorithmException ensure that hash algorithm exists.
     */
    private void CreateAdmin() throws IOException, NoSuchAlgorithmException {
        String filename = "admin.txt";
        ArrayList<Admin> AdminList = null;
        Admin newAdmin = null;

        AdminList = getAdminList(filename);
        newAdmin = AddnewAdmin();
        AdminList.add(newAdmin);
        TextDB.UpdateToTextDB(filename, AdminList,this);
    }

    /**
     * This function is used to read the data from the Admin.txt database
     * @param filename = the file admin.txt that needs to be accessed
     * @return the new created admin list object
     * @throws IOException              is thrown if the reading the content of the file causes error
     * @throws NoSuchAlgorithmException is thrown if accessing teh database causes error
     */
    public static ArrayList<Admin> getAdminList(String filename) throws IOException, NoSuchAlgorithmException {
        ArrayList<Admin> AdminList = new ArrayList<Admin>();
        TextDB.ReadFromFile(AdminList, filename);
        return AdminList;
    }

    /**
     * This is used to get the details from the admin of the new account they want to create
     * @return Admin Object with the new username and password
     * @throws NoSuchAlgorithmException is thrown if the reading the SHA256 algorithm causes an error
     */
    private static Admin AddnewAdmin() throws NoSuchAlgorithmException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Creating New Admin:");
        String username, password;
        System.out.println("\tEnter the new Admin username");
        username = scan.nextLine();
        System.out.println("\tEnter the new Admin password");
        password = SHA256.toString(scan.nextLine());
        return new Admin(username, password, true);
    }
}