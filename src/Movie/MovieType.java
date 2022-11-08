package Movie;

/**
 * @author Eddy
 * This class stores the different types of movies.
 */
public class MovieType {
    /**
     * This enum stores the different Genres of Movies available.
     */
    public enum Genre{
        Action, Comedy, Drama, Fantasy, Horror, Mystery, Romance, Thriller, Western;
    }

    /**
     * This enum stores if a Movie is a Blockbuster.
     */
    public enum Blockbuster {
        BLOCKBUSTER("BLOCKBUSTER"),
        NOTBLOCKBUSTER("NOTBLOCKBUSTER");
        public final String Blockbuster;
        Blockbuster(String blockbuster) {
            this.Blockbuster = blockbuster;
        }
    }

    /**
     * This enum stores the Dimension of the Movie.
     */
    public enum Dimension {
        TWO_D("TWO_D"),
        THREE_D("THREE_D");
        public final String Dimension;
        Dimension(String Dimension) {
            this.Dimension = Dimension;
        }
    }

    /**
     * This enum stores the Ratings of each movie.
     */
    public enum Class{
        G, PG, PG13, NC16, M18, R21;
    }
}