package Customer;

import java.util.*;

public class Customer {
    private String MovieGoerName;
    private String MobileNumber;
    private String Email;
    //Purchase History
    private int TID;

    public Customer(String movieGoerName, String mobileNumber, String email, int TID) {
        MovieGoerName = movieGoerName;
        MobileNumber = mobileNumber;
        Email = email;
        this.TID = TID;
    }

    public String getMovieGoerName() {
        return MovieGoerName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getEmail() {
        return Email;
    }

    public int getTID() {
        return TID;
    }


}
