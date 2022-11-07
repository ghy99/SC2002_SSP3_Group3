package Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  * @author CHEW ZHI QI, Eddy
 *  * Datetime class inherit from date class
 */
public class DateTime extends Date {
    /**
     * Time format that we need
     */
    public static final SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy;HH:mm");
    public static final SimpleDateFormat sf1 = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Convert datetime format to Date
     * @param dateTime dd-MM-yyyy;HH:mm
     * @return Date with the passed in dateime
     */
    public static Date StringToDate(String dateTime)
    {
        Date dt = null;
        try {
             dt = sf.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
        return dt;
    }

    /**
     * Convert datetime format to Date
     * @param date dd-MM-yyyy;HH:mm
     * @return Date with the passed in dateime
     */
    public static Date StringToDateOnly(String date)
    {
        Date dt = null;
        try {
            dt = sf1.parse(date);
        } catch (ParseException e) {
            return null;
        }

        return dt;
    }

    /**
     * Convert from long to string datetime format dd-MM-yyyy;HH:mm
     * @param time time in milliseconds
     * @return String dd-MM-yyyy;HH:mm
     */
    public static String convertTime(long time){
        Date date = new Date(time);
        return sf.format(date);
    }

    /**
     * Convert from long to string time format HH:mm
     * @param time time in milliseconds
     * @return String HH:mm
     */
    public static String convertDate(long time){
        Date date = new Date(time);
        return sf1.format(date);
    }

    /**
     * Current date time to TID
     * @param cinemaCode Cinema code
     * @return XXXYYYYMMDDhhmm (Y : year, M : month, D : day, h : hour, m :
     *minutes, XXX : cinema code in letters)
     */
    public String ToTID(String cinemaCode) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmm");
        return cinemaCode + dateFormatter.format(super.getTime());
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
