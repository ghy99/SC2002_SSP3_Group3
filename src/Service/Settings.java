package Service;

import Movie.TicketCharges;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Setting class to hold environmental global variable
 */
public class Settings {

    /**
     * Flag for user be able to see top 5 sale movie
     */
    private boolean isSale;
    /**
     * Flag for user be able to see top 5 rating movie
     */
    private boolean isRating;

    private ArrayList<String> holiday;

    private TicketCharges ticketCharges;

    public Settings() throws IOException {
        Boolean[] env = TextDB.ReadFromFile(File.separator + TextDB.Files.Env.toString());
        setSale(env[0]);
        setRating(env[1]);
        System.out.println("EVN variable loaded!! \n\n");

        holiday = TextDB.ReadFromFile(TextDB.Files.Holiday.toString() , this);

        ticketCharges = new TicketCharges();
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

    public void setHoliday(ArrayList<String> holiday) {
        this.holiday = holiday;
    }

    public TicketCharges getTicketCharges() {
        return ticketCharges;
    }

    public void setTicketCharges(TicketCharges ticketCharges) {
        this.ticketCharges = ticketCharges;
    }

    /**
     * Update both isSale and isRating together
     * @param sale Top 5 sales flag
     * @param isRating Top 5 rating flag
     */
    public void setUserSort(Boolean sale , Boolean isRating) throws IOException {
        setRating(isRating);
        setRating(sale);
        TextDB.UpdateToTextDB(TextDB.Files.Env.toString() , this);
    }

    /**
     * This function is to add the date into the HolidayDates database
     * @param date = the user input of the date they want to add in the database
     * @throws IOException This Exception is thrown if reading file causes error.
     */
    public void AddHoliday(String date) throws IOException {
        this.holiday.add(date);
        TextDB.WriteToTextDB(TextDB.Files.Holiday.toString(), date);
    }

    /**
     * This function is used to delete any stored holiday date in the database
     * @param date = the users input of the date they want to delete from the database
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public void deleteHoliday(int index) throws IOException {
        this.holiday.remove(index);

        TextDB.UpdateToTextDB(TextDB.Files.Holiday.toString(), this.holiday,this);

    }

    /**
     * This function is used to update/change any dates in the database
     * @param oldDate = the original data in the database before changing
     * @param newDate = the input that needs to be changed in the database
     * @throws IOException This Exception is thrown if reading file causes error.
     */
    public void editHoliday(int index , String date) throws IOException {

        this.holiday.remove(index);
        this.holiday.add(date);
        TextDB.WriteToTextDB(TextDB.Files.Holiday.toString(), date);

    }


}
