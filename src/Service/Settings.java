package Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Settings {

    private boolean isSale;
    private boolean isRating;

    private List<Date> Holiday;

    public void setRating(boolean rating) {
        isRating = rating;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public boolean isRating() {
        return isRating;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setUserSort(Boolean sale , Boolean isRating) throws IOException {
        setRating(isRating);
        setRating(sale);
        TextDB.UpdateToTextDB(TextDB.Files.Env.toString() , this);
    }

}
