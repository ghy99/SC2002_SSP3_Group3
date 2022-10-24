package Service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime extends Date {
    public String ToTID(String cinemaCode) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmm");
        return cinemaCode + dateFormatter.format(super.getTime());
    }

    //DateTime test
    public static void main(String[] args) {
        DateTime t = new DateTime();
        System.out.println(t.ToTID("123"));
    }
}
