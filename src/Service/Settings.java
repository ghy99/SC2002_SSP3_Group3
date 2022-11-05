package Service;

import java.io.IOException;
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

}
