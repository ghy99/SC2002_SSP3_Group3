package Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime extends Date {
    public static final SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy;HH:mm");

    public static Date StringToDate(String dateTime)
    {
        Date dt = null;
        try {
             dt = sf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt;
    }

    public String getCurrentTime() {
        return convertTime(super.getTime());
    }

    public static String convertTime(long time){
        Date date = new Date(time);
        return sf.format(date);
    }

    public String ToTID(String cinemaCode) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmm");
        return cinemaCode + dateFormatter.format(super.getTime());
    }

    //DateTime test
    public static void main(String[] args) {
        DateTime t = new DateTime();
        System.out.println(t.getCurrentTime());
    }
}
