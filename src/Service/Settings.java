package Service;

import Customer.Customer;
import Movie.TicketCharges;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @authors CHEW ZHI QI, GAN HAO YI, TAN JUE LIN
 * Setting class to hold environmental global variable
 */
public class Settings {
    /**
     * Set Print Colours
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    /**
     * Flag for user be able to see top 5 sale movie
     */
    private boolean isSale;
    /**
     * Flag for user be able to see top 5 rating movie
     */
    private boolean isRating;
    /**
     * Stores Global list of holidays to check
     */
    private ArrayList<String> holiday;
    /**
     * Store current ticket charges
     */
    private TicketCharges ticketCharges;
    /**
     * Store current list of customers for login
     */
    private ArrayList<Customer> customerlist = new ArrayList<Customer>();

    public Settings() throws IOException {
        Boolean[] env = TextDB.ReadFromFile(File.separator + TextDB.Files.Env);
        setSale(env[0]);
        setRating(env[1]);
        System.out.println("ENV variable loaded!! \n\n");

        Customer temp = new Customer();
        holiday = TextDB.ReadFromFile(TextDB.Files.Holiday.toString(), this);
        TextDB.readFromFile(TextDB.Files.Customers.toString(), this.customerlist, temp);
        ticketCharges = new TicketCharges();
    }

    public ArrayList<Customer> getCustomerlist() {
        return customerlist;
    }

    /**
     * @return {@link #isRating}
     */
    public boolean isRating() {
        return isRating;
    }

    /**
     * @param rating {@link #isRating}
     */
    public void setRating(boolean rating) {
        isRating = rating;
    }

    /**
     * @return {@link #isSale}
     */
    public boolean isSale() {
        return isSale;
    }

    /**
     * @param sale {@link #isSale}
     */
    public void setSale(boolean sale) {
        isSale = sale;
    }

    public ArrayList<String> getHoliday() {
        return holiday;
    }

    public TicketCharges getTicketCharges() {
        return ticketCharges;
    }

    /**
     * Update both isSale and isRating together
     *
     * @param sale     Top 5 sales flag
     * @param isRating Top 5 rating flag
     */
    public void setUserSort(Boolean sale, Boolean isRating) throws IOException {
        setRating(isRating);
        setRating(sale);
        TextDB.UpdateToTextDB(TextDB.Files.Env.toString(), this);
    }

    /**
     * This function is to add the date into the HolidayDates database
     *
     * @param date = the user input of the date they want to add in the database
     * @throws IOException This Exception is thrown if reading file causes error.
     */
    public void AddHoliday(String date) throws IOException {
        this.holiday.add(date);
        TextDB.WriteToTextDB(TextDB.Files.Holiday.toString(), date);
    }

    /**
     * This function is used to delete any stored holiday date in the database
     *
     * @param index = the users input of the date they want to delete from the database
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public void deleteHoliday(int index) throws IOException {
        this.holiday.remove(index);
        TextDB.UpdateToTextDB(TextDB.Files.Holiday.toString(), this.holiday, this);
    }

    /**
     * This function is used to update/change any dates in the database
     *
     * @param index = the original data in the database before changing
     * @param date  = the input that needs to be changed in the database
     * @throws IOException This Exception is thrown if reading file causes error.
     */
    public void editHoliday(int index, String date) throws IOException {
        this.holiday.remove(index);
        this.holiday.add(date);
        TextDB.WriteToTextDB(TextDB.Files.Holiday.toString(), date);
    }

    /**
     * This method clears the console screen.
     */
    public void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}